package org.fmi.masters.parkingmanager.service.converter;

import javax.annotation.Resource;

import org.fmi.masters.parkingmanager.dto.Vehicle;
import org.fmi.masters.parkingmanager.model.VehicleEntity;
import org.springframework.stereotype.Component;

@Component
public class VehicleConverter implements DtoEntityConverter<VehicleEntity, Vehicle> {

    @Resource
    private VehicleTypeConverter vehicleTypeConverter;

    @Override
    public Vehicle convertToDto(VehicleEntity entity) {
        if (entity == null) {
            return null;
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setId(entity.getId());
        vehicle.setBrand(entity.getBrand());
        vehicle.setModel(entity.getModel());
        vehicle.setNumberPlate(entity.getNumberPlate());
        vehicle.setVehicleType(vehicleTypeConverter.convertToDto(entity.getVehicleType()));

        return vehicle;
    }

    @Override
    public VehicleEntity convertToEntity(Vehicle dto) {
        if (dto == null) {
            return null;
        }

        VehicleEntity entity = new VehicleEntity();
        entity.setId(dto.getId());
        entity.setBrand(dto.getBrand());
        entity.setModel(dto.getModel());
        entity.setNumberPlate(dto.getNumberPlate());
        entity.setVehicleType(vehicleTypeConverter.convertToEntity(dto.getVehicleType()));

        return entity;
    }

}
