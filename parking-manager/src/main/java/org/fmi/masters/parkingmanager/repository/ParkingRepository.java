package org.fmi.masters.parkingmanager.repository;

import org.fmi.masters.parkingmanager.model.ParkingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends JpaRepository<ParkingEntity, Long> {

    ParkingEntity findByNameIgnoreCase(String name);

}
