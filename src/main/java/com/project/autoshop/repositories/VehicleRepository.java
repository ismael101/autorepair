package com.project.autoshop.repositories;

import com.project.autoshop.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    //query for fetching vehicle by client id
    @Query("SELECT v FROM Vehicle v WHERE v.client_id = ?1")
    Optional<Vehicle> findVehicleByClient(Integer id);
}
