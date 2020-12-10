package org.fmi.masters.parkingmanager.service.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;

import org.apache.commons.compress.utils.Sets;
import org.apache.commons.lang3.StringUtils;
import org.fmi.masters.parkingmanager.dto.ParkingPlaceType;
import org.fmi.masters.parkingmanager.dto.VehicleType;
import org.fmi.masters.parkingmanager.model.ParkingPlaceTypeEntity;
import org.fmi.masters.parkingmanager.model.VehicleTypeEntity;
import org.fmi.masters.parkingmanager.repository.ParkingPlaceTypeRepository;
import org.fmi.masters.parkingmanager.service.ParkingPlaceTypeService;
import org.fmi.masters.parkingmanager.service.converter.ParkingPlaceTypeConverter;
import org.fmi.masters.parkingmanager.service.filter.ParkingPlaceTypeFilter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class ParkingPlaceTypeServiceImpl implements ParkingPlaceTypeService {

    @Resource
    private ParkingPlaceTypeRepository parkingPlaceTypeRepository;

    @Resource
    private ParkingPlaceTypeConverter parkingPlaceTypeConverter;

    @Override
    public Collection<ParkingPlaceType> findAll(ParkingPlaceTypeFilter filter) {
        Collection<ParkingPlaceType> dtos = parkingPlaceTypeRepository.findAll(Example.of(
                createExample(
                filter),
                ExampleMatcher.matchingAll()
                        .withIgnoreCase()))
                .stream()
                .map(parkingPlaceTypeConverter::convertToDto)
                .collect(Collectors.toList());

        if (filter.getVehicleTypeId() != null) {
            dtos.removeIf(dto -> !dto.getVehicleTypes()
                    .stream()
                    .map(VehicleType::getId)
                    .collect(Collectors.toList())
                    .contains(filter.getVehicleTypeId()));
        }

        return dtos;
    }

    @Override
    public ParkingPlaceType findById(Long id) {
        Optional<ParkingPlaceTypeEntity> entity = parkingPlaceTypeRepository.findById(id);

        if (!entity.isPresent()) {
            throw new NotFoundException("No Parking place type record was found for id: " + id);
        }

        return parkingPlaceTypeConverter.convertToDto(entity.get());
    }

    @Override
    public void save(@NotNull ParkingPlaceType bean) {
        parkingPlaceTypeRepository.saveAndFlush(parkingPlaceTypeConverter.convertToEntity(bean));
    }

    @Override
    public void deleteById(@NotNull Long id) {
        parkingPlaceTypeRepository.deleteById(id);
    }

    @Override
    public ParkingPlaceType findByName(@NotNull @NotEmpty String name) {
        return parkingPlaceTypeConverter.convertToDto(parkingPlaceTypeRepository.findByNameIgnoreCase(name));
    }

    private ParkingPlaceTypeEntity createExample(ParkingPlaceTypeFilter filter) {
        ParkingPlaceTypeEntity example = new ParkingPlaceTypeEntity();;
        
        if (StringUtils.isNotBlank(filter.getName())) {
            example.setName(filter.getName());
        }
        
        if (filter.getVehicleTypeId() != null) {
            VehicleTypeEntity vehicleType = new VehicleTypeEntity();
            vehicleType.setId(filter.getVehicleTypeId());

            example.setVehicleTypes(Sets.newHashSet(vehicleType));
        }
        
        return example;
    }

}
