package com.project.autorepair.controllers;

import com.project.autorepair.request.groups.Create;
import com.project.autorepair.request.groups.Update;
import com.project.autorepair.request.VehicleRequest;
import com.project.autorepair.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/vehicle")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;
    private Logger logger = LoggerFactory.getLogger(VehicleController.class);

    //endpoint for fetching all vehicles
    @GetMapping
    public ResponseEntity getVehicle(){
        logger.info("get request for all vehicles");
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getVehicles());
    }

    //endpoint for fetching vehicle by id
    @GetMapping(path = "{id}")
    public ResponseEntity getVehicle(@PathVariable("id") Integer id){
        logger.info("get request for vehicle with id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getVehicle(id));
    }

    @GetMapping(path = "/job/{id}")
    public ResponseEntity getJobVehicle(@PathVariable("id") Integer id){
        logger.info("get request for vehicle with job id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getJobVehicle(id));
    }

    //endpoint for creating vehicle
    @PostMapping
    public ResponseEntity createVehicle(@RequestBody @Validated(Create.class) VehicleRequest request){
        logger.info("post request for  " + request.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.createVehicle(request));
    }

    //endpoint for updating vehicle
    @PutMapping(path = "{id}")
    public ResponseEntity updateVehicle(@PathVariable("id") Integer id, @RequestBody @Validated(Update.class) VehicleRequest request){
        logger.info("put request for vehicle with id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.updateVehicle(id, request));
    }

    //endpoint for deleting vehicle
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteVehicle(@PathVariable("id") Integer id){
        logger.info("delete request for vehicle with id: " + id);
        vehicleService.deleteVehicle(id);
        return ResponseEntity.status(HttpStatus.OK).body("vehicle deleted");
    }
}
