package org.fmi.masters.parkingmanager.service.converter;

import org.fmi.masters.parkingmanager.dto.VehicleType;
import org.fmi.masters.parkingmanager.model.VehicleTypeEntity;
import org.springframework.stereotype.Component;

@Component
public class VehicleTypeConverter implements DtoEntityConverter<VehicleTypeEntity, VehicleType> {

    @Override
    public VehicleType convertToDto(VehicleTypeEntity entity) {
        if (entity == null) {
            return null;
        }

        VehicleType dto = new VehicleType();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        return dto;
    }

    @Override
    public VehicleTypeEntity convertToEntity(VehicleType dto) {
        if (dto == null) {
            return null;
        }

        VehicleTypeEntity entity = new VehicleTypeEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());

        return entity;
    }

}
