package com.project.autorepair.repositories;

import com.project.autorepair.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//repository for fetching vehicle from part
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    //query for fetching vehicle by job
    @Query("SELECT v FROM Vehicle v WHERE v.job.id = ?1")
    Optional<Vehicle> findVehicleByJob(Integer id);
}
