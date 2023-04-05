package com.hostTask.pavelCherepanov.controller;

import com.hostTask.pavelCherepanov.mapper.impl.*;
import com.hostTask.pavelCherepanov.persistence.dao.VehicleRepository;
import com.hostTask.pavelCherepanov.persistence.model.entity.Vehicle;
import com.hostTask.pavelCherepanov.persistence.model.request.AddVehicleRequestBody;
import com.hostTask.pavelCherepanov.persistence.model.request.PutVehicleRequestBody;
import com.hostTask.pavelCherepanov.persistence.model.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * class-controlles of application.
 * Processes requests at service-address:port/vehicle
 */
@RestController
@RequestMapping("/vehicle")
public class MyRestController {

    /**
     * Logger needs for properly showing messages from code.
     */
    public static final Logger logger = LoggerFactory.getLogger(MyRestController.class);
    /**
     * DateTimeFormatter is required to convert date and time, according to the specified pattern.
     */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * variables with beans needs for us
     */
    private final AddVehicleRequestBodyToVehicleMapper vehicleRequestBodyToVehicleMapper;
    private final VehicleRepository vehicleRepository;
    private final AddAndPutVehicleToVehicleResponseBodyMapper vehicleToVehicleResponseBodyMapper;
    private final VehicleToPutVehicleMapper vehicleToPutVehicleMapper;
    private final VehicleToSearchGuidResponseMapper vehicleToSearchGuidResponseMapper;
    private final ArrayListParamTSToArrayListResponseBodyMapper arrayListParamTSToArrayListResponseBodyMapper;
    private final ArrayListParamTSToStringMapper arrayListParamTSToStringMapper;
    private final EntityManager entityManager;

    /**
     * Constructor. Called by the spring context when the controller is created.
     * @Autowired annotaton  is needed in order to spring context put here
     * suitable by type bean-class. Beans can be different types.
     * They are united by the fact that they are all in context, like beans in a jar.
     * spring itself decides which and when to call in a given situation.
     * The class marked with the @RestController annotation is also a bin and
     * is in the context of the application.
     * But since if there is one constructor in the class, then it is by default @autowired,
     * therefore, it is not necessary to explicitly specify this annotation here.
     */
    public MyRestController(AddVehicleRequestBodyToVehicleMapper vehicleRequestBodyToVehicleMapper,
                            AddAndPutVehicleToVehicleResponseBodyMapper vehicleToVehicleResponseBodyMapper,
                            VehicleToPutVehicleMapper vehicleToPutVehicleMapper,
                            VehicleToSearchGuidResponseMapper vehicleToSearchGuidResponseMapper,
                            ArrayListParamTSToArrayListResponseBodyMapper arrayListParamTSToArrayListResponseBodyMapper,
                            ArrayListParamTSToStringMapper arrayListParamTSToStringMapper,
                            VehicleRepository vehicleRepository,
                            EntityManager entityManager) {
        this.entityManager = entityManager;
        this.arrayListParamTSToStringMapper = arrayListParamTSToStringMapper;
        this.arrayListParamTSToArrayListResponseBodyMapper = arrayListParamTSToArrayListResponseBodyMapper;
        this.vehicleRequestBodyToVehicleMapper = vehicleRequestBodyToVehicleMapper;
        this.vehicleToVehicleResponseBodyMapper = vehicleToVehicleResponseBodyMapper;
        this.vehicleToPutVehicleMapper = vehicleToPutVehicleMapper;
        this.vehicleToSearchGuidResponseMapper = vehicleToSearchGuidResponseMapper;
        this.vehicleRepository = vehicleRepository;
        // Here we log the information that the controller was sucefully created.
        logger.info("MyRestController was created!");
    }

    /**
     * The method responsible for processing post requests at
     * service-address:port/vehicle 
     * Method getting object of AddVehicleRequestBody type
     *
     * @param vehicleRequest getting object. spring will do demarshalling for us.
     * @param request        request parameters.
     * @return the response body created and filled with data, with information about the added record.
     */
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<AddAndPutVehicleResponseBody> postVehicle(
            @RequestBody AddVehicleRequestBody vehicleRequest,
            HttpServletRequest request) {
        // Creating object of Vehicle type, and, with help of our mapper, fill it data from request body.
        Vehicle vehicle = vehicleRequestBodyToVehicleMapper.map(vehicleRequest);
        // Putting in our Vehicle data, which we don't have in request body.
        vehicle.setGuid(UUID.randomUUID().toString());
        LocalDateTime date = LocalDateTime.now();
        String formattedDateTime = date.format(formatter);
        date = LocalDateTime.parse(formattedDateTime, formatter);
        vehicle.setDateInsert(date);
        vehicle.setDatePurchase(date);
        // Saving our record to DB via the repository.
        vehicleRepository.save(vehicle);
        // Here we log the information that new record was successfully created.
        logger.info("Vehicle with guid={} was created!", vehicle.getGuid());
        // We return a specially created responsebody, with information about the new record, which we fill with data using a mapper.
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(vehicleToVehicleResponseBodyMapper.map(vehicle));
    }

    /**
     * The method responsible for processing put requests at
     * service-address:port/vehicle
     * Method getting object of AddVehicleRequestBody type
     *
     * @param vehiclePut getting object. spring will do demarshalling for us.
     * @param request    request parameters.
     * @return the response body created and filled with data, with information about the updated record.
     */

    @PutMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<AddAndPutVehicleResponseBody> putVehicle(
            @RequestBody PutVehicleRequestBody vehiclePut,
            HttpServletRequest request) {
        // Finding in DB record by GUID 
        Vehicle vehicle = vehicleRepository.getOne(vehiclePut.getGuid());
        // With help of our mapper, we changing data on record
        vehicleToPutVehicleMapper.mapFromInputInOutput(vehiclePut, vehicle);
        // Updating DatePurchase.
        LocalDateTime date = LocalDateTime.now();
        String formattedDateTime = date.format(formatter);
        date = LocalDateTime.parse(formattedDateTime, formatter);
        vehicle.setDatePurchase(date);
        // Using the repository method, we update the record in the database.
        vehicle = vehicleRepository.save(vehicle);
        // Here we log the information that record was successfully updated.
        logger.info("Vehicle with guid={} was purchase!", vehicle.getGuid());
        // We return a specially created responsebody, with information about the updated record, which we fill with data using a mapper.
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(vehicleToVehicleResponseBodyMapper.map(vehicle));
    }

    /**
     * The method responsible for processing get requests at
     * service-address:port/vehicle/search?{vehicleType}&{marque}&{model}&{engine}&{status}
     * The method accepts search parameters
     * and returning a vehicle from DB like JSON object.
     *
     * @param vehicleType,marque,model,engine,status data that we will use to search for a vehicle (status is optional)
     * @param request                                request parameters.
     * @return the response body created and filled with data, with finded by params records.
     */

    @GetMapping("/search")
    public ResponseEntity<List<GetVehicleByParamResponseBody>> getVehicleByParam(
            @RequestParam("vehicleType") String vehicleType,
            @RequestParam("marque") String marque,
            @RequestParam("model") String model,
            @RequestParam("engine") String engine,
            @RequestParam(value = "status", required = false) String status,
            HttpServletRequest request) {
        // Creating an sql query to search the database for records that match the provided parameters
        String sql = "SELECT vehicle.guid, vehicle_type.vehicle_type, marque.marque, model.model, " +
                "engine.engine, engine.engine_power_bhp, vehicle.top_speed_mph, vehicle.cost_usd, vehicle.price, " +
                "vehicle.date_insert, status.status  FROM vehicle " +
                "JOIN vehicle_type ON vehicle.vehicle_typeid = vehicle_type.vehicle_typeid " +
                "JOIN marque_model ON vehicle.marque_modelid = marque_model.marque_modelid " +
                "JOIN marque ON marque_model.marqueid = marque.marqueid " +
                "JOIN model ON marque_model.modelid = model.modelid " +
                "JOIN engine ON vehicle.engineid = engine.engineid " +
                "JOIN status ON vehicle.statusid = status.statusid " +
                "WHERE vehicle_type.vehicle_type = '" + vehicleType + "' " +
                "AND marque.marque = '" + marque + "' " +
                "AND model.model = '" + model + "' " +
                "AND engine.engine = '" + engine + "' ";
        // Since the status parameter is optional, we check its presence, and if it is present...
        if (status != null) {
            //...then we connect it to the search
            sql += "AND status.status = '" + status + "'";
        }
        // We execute a query to the database, and we get all the records that correspond to the provided parameters and return their list using the method getResultList
        List<GetVehicleByParamResponseBody> getVehicleByParamResponseBodies = entityManager.createNativeQuery(sql).getResultList();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getVehicleByParamResponseBodies);
    }

    /**
     * The method responsible for processing get requests at
     * service-address:port/vehicle/{guid} 
     * The method accepts GUID of vehicle,
     * and returning a vehicle from DB like JSON object.
     *
     * @param guid    the primary key in the database of the object by which we will search for the vehicle.
     * @param request request params.
     * @return the response body created and filled with data, with finded by GUID record.
     */
    @GetMapping("/{guid}")
    public ResponseEntity<SearchResponseBody> getVehicleByGuid(
            @PathVariable String guid,
            HttpServletRequest request) {
        // We are finding for a record by GUID and putting it to the mapper, which creates responsebody
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(vehicleToSearchGuidResponseMapper.map(vehicleRepository.getOne(guid)));
    }

    /**
     * The method responsible for processing get requests at
     * http://localhost:8080/vehicle/types 
     * The method calculating and returning types of Vehicle entity.
     *
     * @param request request params.
     * @return the response body created and filled with data, with finded records.
     */
    @GetMapping("/types")
    public ResponseEntity<ArrayList<GetTSPopularParamResponseBody>> getTSByType(
            HttpServletRequest request) {
        // Using an sql query in the repository, we get all the types of vehicles that are in the Vehicle entity.
        ArrayList<String> countVehicleType = vehicleRepository.queryTypesTS();
        // Doing mapping and returning result.
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(arrayListParamTSToArrayListResponseBodyMapper.map(countVehicleType));
    }

    /**
     * The method responsible for processing get requests at
     * http://localhost:8080/vehicle/marque. 
     * The method calculating and returning marques of Vehicle entity.
     *
     * @param request request params.
     * @return the response body created and filled with data, with finded records.
     */

    @GetMapping("/marque")
    public ResponseEntity<ArrayList<GetTSPopularParamResponseBody>> getTSByMarque(
            HttpServletRequest request) {
        // Using an sql query in the repository, we get all the marques of vehicles that are in the Vehicle entity.
        ArrayList<String> countVehicleType = vehicleRepository.queryMarqueTS();
        // Doing mapping and returning result.
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(arrayListParamTSToArrayListResponseBodyMapper.map(countVehicleType));
    }

    /**
     * The method responsible for processing get requests at
     * http://localhost:8080/vehicle/status. 
     * The method calculating and returning statuses of Vehicle entity.
     *
     * @param request request params.
     * @return the response body created and filled with data, with finded records.
     */
    @GetMapping("/status")
    public ResponseEntity<ArrayList<GetTSPopularParamResponseBody>> getTSByStatus(
            HttpServletRequest request) {
        // Using an sql query in the repository, we get all the statuses of vehicles that are in the Vehicle entity.
        ArrayList<String> countVehicleType = vehicleRepository.queryStatusTS();
        // Doing mapping and returning result.
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(arrayListParamTSToArrayListResponseBodyMapper.map(countVehicleType));
    }

    /**
     * The method responsible for processing get requests at
     * http://localhost:8080/vehicle/populartype. 
     * The method calculating and returning the most popular marques, types and statuses of vehicles
     *
     * @param request request params.
     * @return the response body created and filled with data,
     * which contains the names and number of the most popular marques, types and statuses of vehicles
     */
    @GetMapping("/populartype")
    public ResponseEntity<GetTSMostPopularParamResponseBody> getTSByMostPopular(
            HttpServletRequest request) {
        // Creating response body
        GetTSMostPopularParamResponseBody tsMostPopularParamResponseBody = new GetTSMostPopularParamResponseBody();
        // Using the sql query in the repository, we getting all vehicles types
        ArrayList<String> countVehicleType = vehicleRepository.queryTypesTS();
        // With mapper we find the most popular type, and put it to tsMostPopularParamResponseBody
        tsMostPopularParamResponseBody.setType(arrayListParamTSToStringMapper.map(countVehicleType));
        // Using the sql query in the repository, we getting all vehicles marques
        countVehicleType = vehicleRepository.queryMarqueTS();
        // With mapper we find the most popular marque, and put it to tsMostPopularParamResponseBody
        tsMostPopularParamResponseBody.setMarque(arrayListParamTSToStringMapper.map(countVehicleType));
        // Using the sql query in the repository, we getting all vehicles statuses
        countVehicleType = vehicleRepository.queryStatusTS();
        // With mapper we find the most popular status, and put it to tsMostPopularParamResponseBody
        tsMostPopularParamResponseBody.setStatus(arrayListParamTSToStringMapper.map(countVehicleType));
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(tsMostPopularParamResponseBody);
    }

    /**
     * The method responsible for processing get requests at
     * http://localhost:8080/vehicle/random. 
     * The method get random vehicle record from DB and replaces the values of all string parameters in the response with strings with characters in reverse order.
     *
     * @param request request params.
     * @return the response body created and filled with random record, with changed strings.
     */
    @GetMapping("/random")
    public ResponseEntity<Map<String, Object>> getRandom(
            HttpServletRequest request) {
        // Using the sql query in the repository, we getting random vehicle
        Map<String, Object> resultSql = vehicleRepository.queryRandomTS();
        // Creating new hashmap for changed entity
        Map<String, Object> resultChange = new HashMap<>();
        // Iterating all values from resultSql
        for (Map.Entry<String, Object> entry : resultSql.entrySet()) {
            //if we find the String type
            if (entry.getValue().getClass().getName() == "java.lang.String") {
                //we putting the symbols in it in reverse order
                String reversedString = new StringBuilder(entry.getValue().toString()).reverse().toString();
                //pupping to resultChange
                resultChange.put(entry.getKey(), reversedString);

                //if we don't find the String type
            } else {
                //just putting it to resultChange
                resultChange.put(entry.getKey(), entry.getValue());
            }
        }
        //returning HashMap
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resultChange);
    }
}