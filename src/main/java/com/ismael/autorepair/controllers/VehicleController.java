package com.ismael.autorepair.controllers;

import com.ismael.autorepair.requests.VehicleRequest;
import com.ismael.autorepair.requests.groups.Create;
import com.ismael.autorepair.requests.groups.Update;
import com.ismael.autorepair.services.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

//controller for handling vehicles
@RestController
@RequestMapping(path = "/api/v1/vehicle")
public class VehicleController {
    Logger logger = LoggerFactory.getLogger(VehicleController.class);
    public VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    //controller method for fetching all vehicles associated with a user
    @GetMapping
    public ResponseEntity getVehicles(){
        logger.info("Get Vehicles Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getVehicles());
    }

    //controller method for fetching vehicle by id
    @GetMapping(path = "{id}")
    public ResponseEntity getVehicleById(@PathVariable("id") UUID id){
        logger.info("Get Vehicle By Id Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getVehicleById(id));
    }

    //controller method for fetching vehicle by work order
    @GetMapping(path = "/work/{id}")
    public ResponseEntity getVehicleByWork(@PathVariable("id") UUID id){
        logger.info("Get Vehicle By Work Id Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getVehicleByWork(id));
    }

    //controller method for creating a vehicle
    @PostMapping
    public ResponseEntity createVehicle(@RequestBody @Validated(Create.class) VehicleRequest request){
        logger.info("Create Vehicle Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.createVehicle(request));
    }

    //controller method for fetching all insurance associate with a user
    @PutMapping(path = "{id}")
    public ResponseEntity updateVehicle(@PathVariable("id") UUID id, @RequestBody @Validated(Update.class) VehicleRequest request){
        logger.info("Update Vehicle Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.updateVehicle(id, request));
    }

    //controller method for deleting a vehicle
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteVehicle(@PathVariable("id") UUID id){
        logger.info("Delete Vehicle Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.deleteVehicle(id));
    }


}
