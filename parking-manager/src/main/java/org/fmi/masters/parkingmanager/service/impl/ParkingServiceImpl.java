package org.fmi.masters.parkingmanager.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.fmi.masters.parkingmanager.dto.Parking;
import org.fmi.masters.parkingmanager.model.ParkingEntity;
import org.fmi.masters.parkingmanager.repository.ParkingRepository;
import org.fmi.masters.parkingmanager.service.ParkingService;
import org.fmi.masters.parkingmanager.service.converter.ParkingConverter;
import org.fmi.masters.parkingmanager.service.filter.ParkingFilter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class ParkingServiceImpl implements ParkingService {

    @Resource
    private ParkingRepository parkingRepository;

    @Resource
    private ParkingConverter parkingConverter;

    @Override
    public List<Parking> findAll(ParkingFilter filter) {
        return parkingRepository.findAll(Example.of(createExample(
                filter),
                ExampleMatcher.matchingAll()
                        .withIgnoreCase()))
                .stream()
                .map(parkingConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Parking findById(@NotNull Long id) {
        Optional<ParkingEntity> parkingEntity = parkingRepository.findById(id);
        
        if (!parkingEntity.isPresent()) {
            throw new NotFoundException("No Parking record found for id: " + id);
        }
        
        return parkingConverter.convertToDto(parkingEntity.get());
    }

    @Override
    public void save(@NotNull Parking bean) {
        parkingRepository.saveAndFlush(parkingConverter.convertToEntity(bean));
    }

    @Override
    public void deleteById(@NotNull Long id) {
        parkingRepository.deleteById(id);
    }

    @Override
    public Parking findByName(@NotNull @NotEmpty String name) {
        return parkingConverter.convertToDto(parkingRepository.findByNameIgnoreCase(name));
    }

    private ParkingEntity createExample(ParkingFilter filter) {
        ParkingEntity example = new ParkingEntity();

        if (StringUtils.isNotBlank(filter.getName())) {
            example.setName(filter.getName());
        }

        return example;
    }

}
