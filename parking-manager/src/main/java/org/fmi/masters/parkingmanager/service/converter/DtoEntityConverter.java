package org.fmi.masters.parkingmanager.service.converter;

public interface DtoEntityConverter<ENTITY, DTO> {

    DTO convertToDto(ENTITY entity);

    ENTITY convertToEntity(DTO dto);
}
