package org.fmi.masters.parkingmanager.service.converter;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.fmi.masters.parkingmanager.dto.ParkingPlaceType;
import org.fmi.masters.parkingmanager.dto.VehicleType;
import org.fmi.masters.parkingmanager.model.ParkingPlaceTypeEntity;
import org.fmi.masters.parkingmanager.model.VehicleTypeEntity;
import org.springframework.stereotype.Component;

@Component
public class ParkingPlaceTypeConverter implements DtoEntityConverter<ParkingPlaceTypeEntity, ParkingPlaceType> {

    @Resource
    private VehicleTypeConverter vehicleTypeConverter;

    @Override
    public ParkingPlaceType convertToDto(ParkingPlaceTypeEntity entity) {
        if (entity == null) {
            return null;
        }

        ParkingPlaceType dto = new ParkingPlaceType();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        if (entity.getVehicleTypes() != null) {
            Set<VehicleType> vehicleTypes = new HashSet<>();

            for (VehicleTypeEntity vehicleTypeEntity : entity.getVehicleTypes()) {
                vehicleTypes.add(vehicleTypeConverter.convertToDto(vehicleTypeEntity));
            }

            dto.setVehicleTypes(vehicleTypes);
        }

        return dto;
    }

    @Override
    public ParkingPlaceTypeEntity convertToEntity(ParkingPlaceType dto) {
        if (dto == null) {
            return null;
        }

        ParkingPlaceTypeEntity entity = new ParkingPlaceTypeEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());

        if (dto.getVehicleTypes() != null) {
            Set<VehicleTypeEntity> vehicleTypes = new HashSet<>();

            for (VehicleType vehicleType : dto.getVehicleTypes()) {
                vehicleTypes.add(vehicleTypeConverter.convertToEntity(vehicleType));
            }

            entity.setVehicleTypes(vehicleTypes);
        }

        return entity;
    }

}
