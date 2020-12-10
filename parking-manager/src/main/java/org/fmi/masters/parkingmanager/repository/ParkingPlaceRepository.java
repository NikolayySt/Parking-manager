package org.fmi.masters.parkingmanager.repository;

import org.fmi.masters.parkingmanager.model.ParkingEntity;
import org.fmi.masters.parkingmanager.model.ParkingPlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingPlaceRepository extends JpaRepository<ParkingPlaceEntity, Long> {

    ParkingPlaceEntity findByNameIgnoreCaseAndParking(String name, ParkingEntity parking);

}
