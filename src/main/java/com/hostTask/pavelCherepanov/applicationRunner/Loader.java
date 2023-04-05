package com.hostTask.pavelCherepanov.applicationRunner;

import com.hostTask.pavelCherepanov.persistence.dao.StatusRepository;
import com.hostTask.pavelCherepanov.persistence.dao.VehicleTypeRepository;
import com.hostTask.pavelCherepanov.persistence.model.entity.Status;
import com.hostTask.pavelCherepanov.persistence.model.entity.VehicleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * ApplicationRunner responsible for the actions that will occur when the service is launched.
 */
@Component
public class Loader implements ApplicationRunner {

    // Creating logger for this class.
    private static final Logger log = LoggerFactory.getLogger(Loader.class);
    // Creating variables for our repositories.
    private VehicleTypeRepository vehicleTypeRepository;
    private StatusRepository statusRepository;
    // Creating constructor of class, and specify @Autowired annotation.
    @Autowired
    public Loader(VehicleTypeRepository vehicleTypeRepository, StatusRepository statusRepository) {
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.statusRepository = statusRepository;
    }

    /**
     * @param args command line arguments passed when the application is started.
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // If we don't have vechicle types on DB.
        if (vehicleTypeRepository.count() == 0) {
            // Adding necessary types, and save it in DB.
            VehicleType vehicleType = new VehicleType();
            vehicleType.setVehicleType("supercar");
            vehicleTypeRepository.save(vehicleType);
            vehicleType = new VehicleType();
            vehicleType.setVehicleType("jet");
            vehicleTypeRepository.save(vehicleType);
            vehicleType = new VehicleType();
            vehicleType.setVehicleType("ship");
            vehicleTypeRepository.save(vehicleType);
            vehicleType = new VehicleType();
            vehicleType.setVehicleType("helicopter");
            vehicleTypeRepository.save(vehicleType);
        }
       // If we don't have vechicle statuses on DB.
        if (statusRepository.count() == 0) {
            // Adding necessary statuses, and save it in DB.
            Status status = new Status();
            status.setStatus("in stock");
            statusRepository.save(status);
            status = new Status();
            status.setStatus("sold");
            statusRepository.save(status);
            status = new Status();
            status.setStatus("reserved");
            statusRepository.save(status);
        }
    }
}
