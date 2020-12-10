package org.fmi.masters.parkingmanager.service.converter;

import javax.annotation.Resource;

import org.fmi.masters.parkingmanager.dto.Driver;
import org.fmi.masters.parkingmanager.model.DriverEntity;
import org.springframework.stereotype.Component;

@Component
public class DriverConverter implements DtoEntityConverter<DriverEntity, Driver> {

    @Resource
    private VehicleConverter vehicleConverter;

    @Override
    public Driver convertToDto(DriverEntity entity) {
        if (entity == null) {
            return null;
        }

        Driver driver = new Driver();
        driver.setId(entity.getId());
        driver.setFirstName(entity.getFirstName());
        driver.setLastName(entity.getLastName());
        driver.setVehicle(vehicleConverter.convertToDto(entity.getVehicle()));

        return driver;
    }

    @Override
    public DriverEntity convertToEntity(Driver dto) {
        if (dto == null) {
            return null;
        }

        DriverEntity entity = new DriverEntity();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setVehicle(vehicleConverter.convertToEntity(dto.getVehicle()));

        return entity;
    }



}
