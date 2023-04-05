package com.hostTask.pavelCherepanov.mapper.impl;

import com.hostTask.pavelCherepanov.mapper.Mapper;
import com.hostTask.pavelCherepanov.persistence.dao.*;
import com.hostTask.pavelCherepanov.persistence.model.entity.*;
import com.hostTask.pavelCherepanov.persistence.model.request.AddVehicleRequestBody;
import org.springframework.stereotype.Component;

@Component
public class AddVehicleRequestBodyToVehicleMapper implements Mapper<AddVehicleRequestBody, Vehicle> {
    private final VehicleTypeRepository vehicleTypeRepository;
    private final EngineRepository engineRepository;
    private final MarqueModelRepository marqueModelRepository;
    private final MarqueRepository marqueRepository;
    private final ModelRepository modelRepository;
    private final StatusRepository statusRepository;

    public AddVehicleRequestBodyToVehicleMapper(
            VehicleTypeRepository vehicleTypeRepository,
            EngineRepository engineRepository,
            MarqueModelRepository marqueModelRepository,
            MarqueRepository marqueRepository,
            ModelRepository modelRepository,
            StatusRepository statusRepository
    ) {
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.engineRepository = engineRepository;
        this.marqueModelRepository = marqueModelRepository;
        this.marqueRepository = marqueRepository;
        this.modelRepository = modelRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public Vehicle map(AddVehicleRequestBody vehicleRequestBody) {
        Vehicle vehicle = new Vehicle();

        Integer findId = vehicleTypeRepository.findByVehicleType((vehicleRequestBody.getVehicleType()));

        VehicleType vehicleType = new VehicleType();

        if (findId != null) {
            vehicleType = vehicleTypeRepository.getOne(findId);
        } else {
            vehicleType.setVehicleType(vehicleRequestBody.getVehicleType());
        }
        vehicle.setVehicletypej(vehicleType);

        Engine engine = new Engine();
        findId = engineRepository.findByEngineAndEnginePowerBhp(vehicleRequestBody.getEngine(), vehicleRequestBody.getEnginePowerBhp());
        if (findId != null) {
            engine = engineRepository.getOne(findId);
        } else {
            engine.setEngine(vehicleRequestBody.getEngine());
            engine.setEnginePowerBhp(vehicleRequestBody.getEnginePowerBhp());
        }
        vehicle.setEnginej(engine);

        Marque marque = new Marque();
        findId = marqueRepository.findByMarque(vehicleRequestBody.getMarque());
        if (findId != null) {
            marque = marqueRepository.getOne(findId);
        } else {
            marque.setMarque(vehicleRequestBody.getMarque());
        }

        Model model = new Model();

        Integer findId2 = modelRepository.findByModel(vehicleRequestBody.getModel());
        if (findId2 != null) {
            model = modelRepository.getOne(findId);
        } else {
            model.setModel(vehicleRequestBody.getModel());
        }

        MarqueModel marqueModel = new MarqueModel();
        if (findId != null && findId2 != null) {
            Integer findId3 = marqueModelRepository.findByMarqueIDModelID(findId, findId2);
            if (findId3 != null) {
                marqueModel = marqueModelRepository.getOne(findId3);
            } else {
                marqueModel.setModelj(model);
                marqueModel.setMarquej(marque);
            }
        } else {
            marqueModel.setModelj(model);
            marqueModel.setMarquej(marque);
        }
        vehicle.setMarqueModelj(marqueModel);

        String statusR = vehicleRequestBody.getStatus();
        if (statusR == null) {
            findId = statusRepository.findByStatus("in stock");
            vehicle.setStatusj(statusRepository.getOne(findId));
        } else {
            findId = statusRepository.findByStatus(vehicleRequestBody.getStatus());
            Status status = new Status();

            if (findId != null) {
                status = statusRepository.getOne(findId);
            } else {
                status.setStatus(vehicleRequestBody.getStatus());
            }

            vehicle.setStatusj(status);
        }

        vehicle.setTopSpeedMph(vehicleRequestBody.getTopSpeedMph());
        vehicle.setCostUsd(vehicleRequestBody.getCostUsd());
        vehicle.setPrice(vehicleRequestBody.getPrice());

        return vehicle;
    }

    @Override
    public void mapFromInputInOutput(AddVehicleRequestBody vehicleRequestBody, Vehicle vehicle) {

    }
}
