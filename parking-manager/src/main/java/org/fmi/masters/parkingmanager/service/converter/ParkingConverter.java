package org.fmi.masters.parkingmanager.service.converter;

import org.fmi.masters.parkingmanager.dto.Parking;
import org.fmi.masters.parkingmanager.model.ParkingEntity;
import org.springframework.stereotype.Component;

@Component
public class ParkingConverter implements DtoEntityConverter<ParkingEntity, Parking> {

    @Override
    public Parking convertToDto(ParkingEntity entity) {
        if (entity == null) {
            return null;
        }

        Parking dto = new Parking();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        return dto;
    }

    @Override
    public ParkingEntity convertToEntity(Parking dto) {
        if (dto == null) {
            return null;
        }

        ParkingEntity entity = new ParkingEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());

        return entity;
    }

}
