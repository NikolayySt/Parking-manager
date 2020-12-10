package org.fmi.masters.parkingmanager.repository;

import org.fmi.masters.parkingmanager.model.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, Long> {

}
