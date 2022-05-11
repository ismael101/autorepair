package com.project.autoshop.repositories;

import com.project.autoshop.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    @Query("SELECT v FROM Vehicle v WHERE v.client_id == ?1")
    List<Vehicle> findVehiclesByClient(Integer id);
}
