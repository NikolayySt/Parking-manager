package org.fmi.masters.parkingmanager.repository;

import org.fmi.masters.parkingmanager.model.VehicleTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleTypeEntity, Long> {

    VehicleTypeEntity findByNameIgnoreCase(String name);

}
