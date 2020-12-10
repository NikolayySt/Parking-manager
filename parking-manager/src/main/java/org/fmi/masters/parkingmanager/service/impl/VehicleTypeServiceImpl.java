package org.fmi.masters.parkingmanager.service.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.fmi.masters.parkingmanager.dto.VehicleType;
import org.fmi.masters.parkingmanager.model.VehicleTypeEntity;
import org.fmi.masters.parkingmanager.repository.VehicleTypeRepository;
import org.fmi.masters.parkingmanager.service.VehicleTypeService;
import org.fmi.masters.parkingmanager.service.converter.VehicleTypeConverter;
import org.fmi.masters.parkingmanager.service.filter.VehicleTypeFilter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class VehicleTypeServiceImpl implements VehicleTypeService {

    @Resource
    private VehicleTypeRepository vehicleTypeRepository;

    @Resource
    private VehicleTypeConverter vehicleTypeConverter;

    @Override
    public Collection<VehicleType> findAll(VehicleTypeFilter filter) {
        return vehicleTypeRepository.findAll(Example.of(createExample(filter), ExampleMatcher.matchingAll()
                .withIgnoreCase()))
                .stream()
                .map(vehicleTypeConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleType findByName(@NotNull @NotEmpty String name) {
        return vehicleTypeConverter.convertToDto(vehicleTypeRepository.findByNameIgnoreCase(name));
    }

    @Override
    public VehicleType findById(Long id) {
        Optional<VehicleTypeEntity> entity = vehicleTypeRepository.findById(id);

        if (!entity.isPresent()) {
            throw new NotFoundException("No Vehicle type record found for id: " + id);
        }

        return vehicleTypeConverter.convertToDto(entity.get());
    }

    @Override
    public void save(@NotNull VehicleType vehicleType) {
        vehicleTypeRepository.saveAndFlush(vehicleTypeConverter.convertToEntity(vehicleType));
    }

    @Override
    public void deleteById(@NotNull Long id) {
        vehicleTypeRepository.deleteById(id);
    }

    private VehicleTypeEntity createExample(VehicleTypeFilter filter) {
        VehicleTypeEntity example = new VehicleTypeEntity();

        if (StringUtils.isNotBlank(filter.getName())) {
            example.setName(filter.getName());
        }

        return example;
    }

}
