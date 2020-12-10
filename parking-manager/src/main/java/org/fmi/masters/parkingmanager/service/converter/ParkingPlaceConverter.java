package org.fmi.masters.parkingmanager.service.converter;

import javax.annotation.Resource;

import org.fmi.masters.parkingmanager.dto.ParkingPlace;
import org.fmi.masters.parkingmanager.model.ParkingPlaceEntity;
import org.springframework.stereotype.Component;

@Component
public class ParkingPlaceConverter implements DtoEntityConverter<ParkingPlaceEntity, ParkingPlace> {

    @Resource
    private DriverConverter driverConverter;

    @Resource
    private ParkingConverter parkingConverter;

    @Resource
    private ParkingPlaceTypeConverter parkingPlaceTypeConverter;

    @Override
    public ParkingPlace convertToDto(ParkingPlaceEntity entity) {
        if (entity == null) {
            return null;
        }

        ParkingPlace dto = new ParkingPlace();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDriver(driverConverter.convertToDto(entity.getDriver()));
        dto.setParking(parkingConverter.convertToDto(entity.getParking()));
        dto.setParkingPlaceType(parkingPlaceTypeConverter.convertToDto(entity.getPlaceType()));

        return dto;
    }

    @Override
    public ParkingPlaceEntity convertToEntity(ParkingPlace dto) {
        if (dto == null) {
            return null;
        }

        ParkingPlaceEntity entity = new ParkingPlaceEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDriver(driverConverter.convertToEntity(dto.getDriver()));
        entity.setParking(parkingConverter.convertToEntity(dto.getParking()));
        entity.setPlaceType(parkingPlaceTypeConverter.convertToEntity(dto.getParkingPlaceType()));

        return entity;
    }

}
