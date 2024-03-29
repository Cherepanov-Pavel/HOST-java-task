package com.hostTask.pavelCherepanov.persistence.dao;

import com.hostTask.pavelCherepanov.persistence.model.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Map;


public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    // Query in pure sql to search for all types of vehicles
    @Query(nativeQuery = true, value = "SELECT T2.vehicle_type FROM Vehicle T1 JOIN vehicle_type T2 ON T2.vehicle_typeid = T1.vehicle_typeid")
    ArrayList<String> queryTypesTS(
    );

    // Query in pure sql to search for all marques of vehicles
    @Query(nativeQuery = true, value = "SELECT T3.marque FROM Vehicle T1 JOIN marque_model T2 ON T1.marque_modelid = T2.marque_modelid JOIN marque T3 ON T2.marqueID = T3.marqueID ")
    ArrayList<String> queryMarqueTS();

    // Query in pure sql to search for all statuses of vehicles
    @Query(nativeQuery = true, value = "SELECT T2.status FROM Vehicle T1 JOIN status T2 ON T2.statusid = T1.statusid")
    ArrayList<String> queryStatusTS();

    // Pure SQL query to search the database for a random vehicle and return the fields guid, 
    // vehicle type, marque, model, engine name, its power, cost and date of addition entity
    @Query(nativeQuery = true, value = "SELECT T1.guid, T2.vehicle_type, T3.marque, T4.model, T5.engine, T5.engine_power_bhp, T1.top_speed_mph, T1.cost_usd, T1.date_insert FROM Vehicle T1 JOIN vehicle_type T2 ON T2.vehicle_typeid = T1.vehicle_typeid JOIN marque_model T6 ON T1.marque_modelid = T6.marque_modelid JOIN marque T3 ON T3.marqueID = T6.marqueID JOIN model T4 ON T4.modelID = T6.modelID JOIN Engine T5 ON T5.engineID = T1.engineID ORDER BY random() LIMIT 1")
    Map<String, Object> queryRandomTS();

    // Query in pure SQL to search the database for the GUID of a random vehicle
    @Query(nativeQuery = true, value = "SELECT guid FROM Vehicle ORDER BY random() LIMIT 1")
    Map<String, Object> queryRandomTSSheduler();

}
