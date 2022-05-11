package com.project.autoshop.repositories;

import com.project.autoshop.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    @Query("SELECT v FROM Vehicle v WHERE v.job_id = ?1")
    Optional<Vehicle> findVehicleByJob(Integer id);
}
