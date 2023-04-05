package com.hostTask.pavelCherepanov.service;

import com.hostTask.pavelCherepanov.persistence.dao.VehicleRepository;
import com.hostTask.pavelCherepanov.persistence.model.entity.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

/**
 * Class for running scheduled tasks.
 */
@Service
public class SchedulerService {
    /**
     * Creating logger for this class and bean of Vehicle table.
     */
    private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);
    private final VehicleRepository vehicleRepository;

    /**
     * Class constructor, for fill a bean.
     */
    public SchedulerService(
            VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
        // Here we log the information that the service was created.
        log.info("SchedulerService was created!");
    }

    /**
     * The Scheduled annotation indicates that this method will be executed on a schedule.
     * The initialDelayString parameter specifies the time in ms that will elapse before the first start of this task.
     * The fixedDelayString parameter specifies the time in ms between runs of this task.
     * ${scheduler.delay} - means that this time is specified in application.properties
     */
    @Scheduled(initialDelayString = "${scheduler.delay}", fixedDelayString = "${scheduler.delay}")
    // Here I have provided an annotation to fix the error that caused access to the repository from this method to be missing.
    @Transactional
    /**
     * A method that runs on a schedule.
     */
    public void doWork() throws InterruptedException {
        // Logging information that process was started.
        log.info("Start process");
        // Getting random GUID of random vehicle from DB.
        Map<String, Object> result = vehicleRepository.queryRandomTSSheduler();
        // With getOne method getting record with this GUID from DB.
        Vehicle vehicle = vehicleRepository.getOne(result.get("guid").toString());
        // Setting price this record to zero.
        vehicle.setPrice(0);
        // Saving record to DB.
        vehicleRepository.save(vehicle);
        // Logging information that process was ended.
        log.info("End process");
    }
}
