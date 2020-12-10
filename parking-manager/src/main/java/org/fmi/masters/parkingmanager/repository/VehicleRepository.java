package org.fmi.masters.parkingmanager.repository;

import org.fmi.masters.parkingmanager.model.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    VehicleEntity findByNumberPlateIgnoreCase(String numberPlate);

}
