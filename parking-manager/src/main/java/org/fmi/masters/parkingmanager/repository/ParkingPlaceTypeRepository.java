package org.fmi.masters.parkingmanager.repository;

import org.fmi.masters.parkingmanager.model.ParkingPlaceTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingPlaceTypeRepository extends JpaRepository<ParkingPlaceTypeEntity, Long> {

    ParkingPlaceTypeEntity findByNameIgnoreCase(String name);

}
