package org.fmi.masters.parkingmanager.service.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.fmi.masters.parkingmanager.dto.Vehicle;
import org.fmi.masters.parkingmanager.model.VehicleEntity;
import org.fmi.masters.parkingmanager.model.VehicleTypeEntity;
import org.fmi.masters.parkingmanager.repository.VehicleRepository;
import org.fmi.masters.parkingmanager.service.VehicleService;
import org.fmi.masters.parkingmanager.service.converter.VehicleConverter;
import org.fmi.masters.parkingmanager.service.filter.VehicleFilter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Resource
    private VehicleRepository vehicleRepository;

    @Resource
    private VehicleConverter vehicleConverter;

    @Override
    public Collection<Vehicle> findAll(VehicleFilter filter) {
        return vehicleRepository.findAll(Example.of(createExample(filter), ExampleMatcher.matchingAll()
                .withIgnoreCase()))
                .stream()
                .map(vehicleConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Vehicle findById(@NotNull Long id) {
        Optional<VehicleEntity> entity = vehicleRepository.findById(id);

        if (!entity.isPresent()) {
            throw new NotFoundException("No Vehicle record found for id: " + id);
        }

        return vehicleConverter.convertToDto(entity.get());
    }

    @Override
    public void save(@NotNull Vehicle vehicle) {
        vehicleRepository.saveAndFlush(vehicleConverter.convertToEntity(vehicle));
    }

    @Override
    public void deleteById(@NotNull Long id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public Vehicle findByNumberPlate(@NotNull @NotEmpty String numberPlate) {
        return vehicleConverter.convertToDto(vehicleRepository.findByNumberPlateIgnoreCase(numberPlate));
    }

    private VehicleEntity createExample(VehicleFilter filter) {
        VehicleEntity example = new VehicleEntity();
        
        if (StringUtils.isNotBlank(filter.getBrand())) {
            example.setBrand(filter.getBrand());
        }
        
        if (StringUtils.isNotBlank(filter.getModel())) {
            example.setModel(filter.getModel());
        }
        
        if (StringUtils.isNotBlank(filter.getNumberPlate())) {
            example.setNumberPlate(filter.getNumberPlate());
        }
        
        if (filter.getVehicleTypeId() != null) {
            VehicleTypeEntity vehicleType = new VehicleTypeEntity();
            vehicleType.setId(filter.getVehicleTypeId());
            
            example.setVehicleType(vehicleType);
        }
        
        return example;
    }

}
