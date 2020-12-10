package org.fmi.masters.parkingmanager.config;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

import org.fmi.masters.parkingmanager.service.DriverService;
import org.fmi.masters.parkingmanager.service.ParkingPlaceService;
import org.fmi.masters.parkingmanager.service.ParkingPlaceTypeService;
import org.fmi.masters.parkingmanager.service.ParkingService;
import org.fmi.masters.parkingmanager.service.VehicleService;
import org.fmi.masters.parkingmanager.service.VehicleTypeService;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@ApplicationPath("/data")
public class RestConfig extends ResourceConfig {

    @PostConstruct
    public void init() {
        register(DriverService.class);
        register(ParkingPlaceService.class);
        register(ParkingPlaceTypeService.class);
        register(ParkingService.class);
        register(VehicleService.class);
        register(VehicleTypeService.class);
    }

}