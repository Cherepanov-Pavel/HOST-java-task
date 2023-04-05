package com.hostTask.pavelCherepanov.mapper.impl;

import com.hostTask.pavelCherepanov.mapper.Mapper;
import com.hostTask.pavelCherepanov.persistence.dao.*;
import com.hostTask.pavelCherepanov.persistence.model.entity.*;
import com.hostTask.pavelCherepanov.persistence.model.request.PutVehicleRequestBody;
import org.springframework.stereotype.Component;

@Component
public class VehicleToPutVehicleMapper implements Mapper<PutVehicleRequestBody, Vehicle> {
    private final VehicleTypeRepository vehicleTypeRepository;
    private final EngineRepository engineRepository;
    private final MarqueModelRepository marqueModelRepository;
    private final MarqueRepository marqueRepository;
    private final ModelRepository modelRepository;
    private final StatusRepository statusRepository;

    public VehicleToPutVehicleMapper(
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
    public Vehicle map(PutVehicleRequestBody putVehicleRequestBody) {
        return null;
    }

    @Override
    public void mapFromInputInOutput(PutVehicleRequestBody vehiclePut, Vehicle vehicle) {
        Integer findId = null;
        Integer findId2 = null;
        Integer findId3;
        if (vehiclePut.getVehicleType() != null) {
            VehicleType vehicleType = new VehicleType();
            if (!(vehicle.getVehicletypej().getVehicleType().equals(vehiclePut.getVehicleType()))) {
                findId = vehicleTypeRepository.findByVehicleType(vehiclePut.getVehicleType());
                if (findId != null) {
                    vehicleType = vehicleTypeRepository.getOne(findId);
                }
                else {
                    vehicleType.setVehicleType(vehiclePut.getVehicleType());
                }
                vehicle.setVehicletypej(vehicleType);
            }
        }

        if (vehiclePut.getMarque() != null && vehiclePut.getModel() != null) {
            Marque marque = new Marque();
            findId = marqueRepository.findByMarque(vehiclePut.getMarque());
            if (findId != null) {
                marque = marqueRepository.getOne(findId);
            }
            else {
                marque.setMarque(vehiclePut.getMarque());
            }
            Model model = new Model();
            findId2 = modelRepository.findByModel(vehiclePut.getModel());
            if (findId2 != null) {
                model = modelRepository.getOne(findId2);
            }
            else {
                model.setModel(vehiclePut.getModel());
            }

            MarqueModel marqueModel = new MarqueModel();
            if (findId != null && findId2 != null) {
                findId3 = marqueModelRepository.findByMarqueIDModelID(findId, findId2);
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
        }

        if (vehiclePut.getEngine() != null) {
            Engine engine = new Engine();
            if (!(vehicle.getEnginej().getEngine().equals(vehiclePut.getEngine())) || vehicle.getEnginej().getEnginePowerBhp() != vehiclePut.getEnginePowerBhp()) {
                findId = engineRepository.findByEngineAndEnginePowerBhp(vehiclePut.getEngine(), vehiclePut.getEnginePowerBhp());
                if (findId != null) {
                    engine = engineRepository.getOne(findId);
                } else {
                    engine.setEngine(vehiclePut.getEngine());
                    engine.setEnginePowerBhp(vehiclePut.getEnginePowerBhp());
                }
                vehicle.setEnginej(engine);
            }
        }
        if (vehiclePut.getStatus() != null) {
            Status status = new Status();

            if (!(vehicle.getStatusj().getStatus().equals(vehiclePut.getStatus()))) {
                findId = statusRepository.findByStatus(vehiclePut.getStatus());
                if (findId != null) {
                    status = statusRepository.getOne(findId);
                } else {
                    status.setStatus(vehiclePut.getStatus());
                }
                vehicle.setStatusj(status);
            }
        }

        if (vehiclePut.getTopSpeedMph() != 0) {
            vehicle.setTopSpeedMph(vehiclePut.getTopSpeedMph());
        }

        if (vehiclePut.getCostUsd() != 0) {
            vehicle.setCostUsd(vehiclePut.getCostUsd());
        }

        if (vehiclePut.getPrice() != 0) {
            vehicle.setPrice(vehiclePut.getPrice());
        }
    }
}
