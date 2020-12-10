package org.fmi.masters.parkingmanager.service.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.fmi.masters.parkingmanager.dto.Parking;
import org.fmi.masters.parkingmanager.dto.ParkingPlace;
import org.fmi.masters.parkingmanager.model.DriverEntity;
import org.fmi.masters.parkingmanager.model.ParkingEntity;
import org.fmi.masters.parkingmanager.model.ParkingPlaceEntity;
import org.fmi.masters.parkingmanager.model.ParkingPlaceTypeEntity;
import org.fmi.masters.parkingmanager.repository.ParkingPlaceRepository;
import org.fmi.masters.parkingmanager.service.ParkingPlaceService;
import org.fmi.masters.parkingmanager.service.converter.ParkingConverter;
import org.fmi.masters.parkingmanager.service.converter.ParkingPlaceConverter;
import org.fmi.masters.parkingmanager.service.filter.ParkingPlaceFilter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class ParkingPlaceServiceImpl implements ParkingPlaceService {

    @Resource
    private ParkingPlaceRepository parkingPlaceRepository;

    @Resource
    private ParkingPlaceConverter parkingPlaceConverter;

    @Resource
    private ParkingConverter parkingConverter;

    @Override
    public Collection<ParkingPlace> findAll(@NotNull ParkingPlaceFilter filter) {
        return parkingPlaceRepository.findAll(Example.of(createExample(
                filter),
                ExampleMatcher.matchingAll()
                        .withIgnoreCase()))
                .stream()
                .map(parkingPlaceConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParkingPlace findById(@NotNull Long id) {
        Optional<ParkingPlaceEntity> entity = parkingPlaceRepository.findById(id);

        if (!entity.isPresent()) {
            throw new NotFoundException("No Parking place record found for id: " + id);
        }

        return parkingPlaceConverter.convertToDto(entity.get());
    }

    @Override
    public void save(@NotNull ParkingPlace bean) {
        parkingPlaceRepository.saveAndFlush(parkingPlaceConverter.convertToEntity(bean));
    }

    @Override
    public void deleteById(@NotNull Long id) {
        parkingPlaceRepository.deleteById(id);
    }

    @Override
    public ParkingPlace findByNameAndParking(@NotNull @NotEmpty String name, @NotNull Parking parking) {
        return parkingPlaceConverter.convertToDto(parkingPlaceRepository.findByNameIgnoreCaseAndParking(name, parkingConverter.convertToEntity(parking)));
    }
    
    private ParkingPlaceEntity createExample(@NotNull ParkingPlaceFilter filter) {
        ParkingPlaceEntity example = new ParkingPlaceEntity();
        
        if (StringUtils.isNotBlank(filter.getName())) {
            example.setName(filter.getName());
        }
        
        if (filter.getParkingId() != null) {
            ParkingEntity parking = new ParkingEntity();
            parking.setId(filter.getParkingId());

            example.setParking(parking);
        }
        
        if (filter.getDriverId() != null) {
            DriverEntity driver = new DriverEntity();
            driver.setId(filter.getDriverId());

            example.setDriver(driver);
        }

        if (filter.getPlaceTypeId() != null) {
            ParkingPlaceTypeEntity placeType = new ParkingPlaceTypeEntity();
            placeType.setId(filter.getPlaceTypeId());

            example.setPlaceType(placeType);
        }

        return example;
    }

}
