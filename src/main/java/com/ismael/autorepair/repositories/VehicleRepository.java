package com.ismael.autorepair.repositories;

import com.ismael.autorepair.models.Vehicle;
import com.ismael.autorepair.models.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    Optional<Vehicle> findVehicleByWork(Work work);
}
