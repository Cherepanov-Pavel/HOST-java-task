package com.hostTask.pavelCherepanov.mapper.impl;

import com.hostTask.pavelCherepanov.mapper.Mapper;
import com.hostTask.pavelCherepanov.persistence.model.entity.Vehicle;
import com.hostTask.pavelCherepanov.persistence.model.response.AddAndPutVehicleResponseBody;
import org.springframework.stereotype.Component;

@Component
public class AddAndPutVehicleToVehicleResponseBodyMapper implements Mapper<Vehicle, AddAndPutVehicleResponseBody> {
    @Override
    public AddAndPutVehicleResponseBody map(Vehicle vehicle) {
        AddAndPutVehicleResponseBody vehicleResponse = new AddAndPutVehicleResponseBody();

        vehicleResponse.setGuid(vehicle.getGuid());
        vehicleResponse.setVehicleType(vehicle.getVehicletypej().getVehicleType());
        vehicleResponse.setMarque(vehicle.getMarqueModelj().getMarquej().getMarque());
        vehicleResponse.setModel(vehicle.getMarqueModelj().getModelj().getModel());
        vehicleResponse.setEngine(vehicle.getEnginej().getEngine());
        vehicleResponse.setEnginePowerBhp(vehicle.getEnginej().getEnginePowerBhp());
        vehicleResponse.setTopSpeedMph(vehicle.getTopSpeedMph());
        vehicleResponse.setCostUsd(vehicle.getCostUsd());
        vehicleResponse.setPrice(vehicle.getPrice());
        vehicleResponse.setDateInsert(vehicle.getDateInsert());
        vehicleResponse.setDatePurchase(vehicle.getDatePurchase());
        vehicleResponse.setStatus(vehicle.getStatusj().getStatus());

        return vehicleResponse;
    }

    @Override
    public void mapFromInputInOutput(Vehicle vehicle, AddAndPutVehicleResponseBody vehicleResponseBody) {

    }
}
