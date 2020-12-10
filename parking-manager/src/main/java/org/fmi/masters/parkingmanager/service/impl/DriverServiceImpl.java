package org.fmi.masters.parkingmanager.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.fmi.masters.parkingmanager.dto.Driver;
import org.fmi.masters.parkingmanager.model.DriverEntity;
import org.fmi.masters.parkingmanager.model.VehicleEntity;
import org.fmi.masters.parkingmanager.repository.DriverRepository;
import org.fmi.masters.parkingmanager.service.DriverService;
import org.fmi.masters.parkingmanager.service.converter.DriverConverter;
import org.fmi.masters.parkingmanager.service.filter.DriverFilter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class DriverServiceImpl implements DriverService {

    @Resource
    private DriverRepository driverRepository;

    @Resource
    private DriverConverter driverConverter;

    @Override
    public List<Driver> findAll(DriverFilter filter) {
        return driverRepository.findAll(Example.of(createExample(filter), ExampleMatcher.matchingAll()
                .withIgnoreCase()))
                .stream()
                .map(driverConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Driver findById(@NotNull Long id) {
        Optional<DriverEntity> driverEntity = driverRepository.findById(id);

        if (!driverEntity.isPresent()) {
            throw new NotFoundException("No driver record found for id: " + id);
        }

        return driverConverter.convertToDto(driverEntity.get());
    }

    @Override
    public void save(@NotNull Driver driver) {
        driverRepository.saveAndFlush(driverConverter.convertToEntity(driver));
    }

    @Override
    public void deleteById(@NotNull Long id) {
        driverRepository.deleteById(id);
    }

    private DriverEntity createExample(DriverFilter filter) {
        DriverEntity example = new DriverEntity();

        if (StringUtils.isNotBlank(filter.getFirstName())) {
            example.setFirstName(filter.getFirstName());
        }

        if (StringUtils.isNotBlank(filter.getLastName())) {
            example.setLastName(filter.getLastName());
        }

        if (filter.getVehicleId() != null) {
            VehicleEntity vehicle = new VehicleEntity();
            vehicle.setId(filter.getVehicleId());

            example.setVehicle(vehicle);
        }

        return example;
    }

}
