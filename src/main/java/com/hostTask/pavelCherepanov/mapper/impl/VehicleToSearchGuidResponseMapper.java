package com.hostTask.pavelCherepanov.mapper.impl;

import com.hostTask.pavelCherepanov.mapper.Mapper;
import com.hostTask.pavelCherepanov.persistence.model.entity.Vehicle;
import com.hostTask.pavelCherepanov.persistence.model.response.SearchResponseBody;
import org.springframework.stereotype.Component;

@Component
public class VehicleToSearchGuidResponseMapper implements Mapper<Vehicle, SearchResponseBody> {
    @Override
    public SearchResponseBody map(Vehicle vehicle) {
        SearchResponseBody searchResponseBody = new SearchResponseBody();
        searchResponseBody.setCostUsd(vehicle.getCostUsd());
        searchResponseBody.setDateInsert(vehicle.getDateInsert());
        searchResponseBody.setEngine(vehicle.getEnginej().getEngine());
        searchResponseBody.setEnginePowerBhp(vehicle.getEnginej().getEnginePowerBhp());
        searchResponseBody.setGuid(vehicle.getGuid());
        searchResponseBody.setMarque(vehicle.getMarqueModelj().getMarquej().getMarque());
        searchResponseBody.setModel(vehicle.getMarqueModelj().getModelj().getModel());
        searchResponseBody.setTopSpeedMph(vehicle.getTopSpeedMph());
        searchResponseBody.setVehicleType(vehicle.getVehicletypej().getVehicleType());
        return searchResponseBody;
    }

    @Override
    public void mapFromInputInOutput(Vehicle vehicle, SearchResponseBody searchResponseBody) {

    }
}
