package org.fmi.masters.parkingmanager.init;

import javax.annotation.Resource;

import org.apache.commons.compress.utils.Sets;
import org.fmi.masters.parkingmanager.model.ParkingPlaceTypeEntity;
import org.fmi.masters.parkingmanager.model.VehicleTypeEntity;
import org.fmi.masters.parkingmanager.repository.ParkingPlaceTypeRepository;
import org.fmi.masters.parkingmanager.repository.VehicleTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Resource
    private VehicleTypeRepository vehicleTypeRepository;

    @Resource
    private ParkingPlaceTypeRepository parkingPlaceTypeRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        /* Creation of vehicle type initial data */
        if (vehicleTypeRepository.count() == 0 && parkingPlaceTypeRepository.count() == 0) {
            VehicleTypeEntity smallVehicle = new VehicleTypeEntity();
            smallVehicle.setId(1L);
            smallVehicle.setName("Small size vehicle");

            VehicleTypeEntity mediumVehicle = new VehicleTypeEntity();
            mediumVehicle.setId(2L);
            mediumVehicle.setName("Medium size vehicle");

            VehicleTypeEntity largeVehicle = new VehicleTypeEntity();
            largeVehicle.setId(3L);
            largeVehicle.setName("Large size vehicle");

            vehicleTypeRepository.save(smallVehicle);
            vehicleTypeRepository.save(mediumVehicle);
            vehicleTypeRepository.save(largeVehicle);

        /* Creation of parking place type initial data */
            ParkingPlaceTypeEntity smallPlace = new ParkingPlaceTypeEntity();
            smallPlace.setId(1L);
            smallPlace.setName("Small size parking place");
            smallPlace.setVehicleTypes(Sets.newHashSet(smallVehicle));

            ParkingPlaceTypeEntity mediumPlace = new ParkingPlaceTypeEntity();
            mediumPlace.setId(2L);
            mediumPlace.setName("Medium size parking place");
            mediumPlace.setVehicleTypes(Sets.newHashSet(smallVehicle, mediumVehicle));

            ParkingPlaceTypeEntity largePlace = new ParkingPlaceTypeEntity();
            largePlace.setId(3L);
            largePlace.setName("Large size parking place");
            largePlace.setVehicleTypes(Sets.newHashSet(smallVehicle, mediumVehicle, largeVehicle));

            parkingPlaceTypeRepository.save(smallPlace);
            parkingPlaceTypeRepository.save(mediumPlace);
            parkingPlaceTypeRepository.save(largePlace);
        }

        logger.info("Initial data created sucessfully");
    }

}
