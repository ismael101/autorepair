package com.project.autoshop.repositories;

import com.project.autoshop.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    //query for fetching vehicle by job
    @Query("SELECT v FROM Vehicle v WHERE v.job = ?1")
    Optional<Vehicle> findVehicleByJob(Integer id);
}
