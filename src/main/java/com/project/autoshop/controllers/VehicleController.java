package com.project.autoshop.controllers;

import com.project.autoshop.request.groups.Create;
import com.project.autoshop.request.groups.Update;
import com.project.autoshop.request.VehicleRequest;
import com.project.autoshop.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/vehicle")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    //endpoint for fetching all vehicles
    @GetMapping
    public ResponseEntity getVehicle(){
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getVehicles());
    }

    //endpoint for fetching vehicle by id
    @GetMapping(path = "{id}")
    public ResponseEntity getVehicle(@PathVariable("id") Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getVehicle(id));
    }

    //endpoint for creating vehicle
    @PostMapping
    public ResponseEntity createAddress(@RequestBody @Validated(Create.class) VehicleRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.createVehicle(request));
    }

    //endpoint for updating vehicle
    @PutMapping(path = "{id}")
    public ResponseEntity updateAddress(@PathVariable("id") Integer id, @RequestBody @Validated(Update.class) VehicleRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.updateVehicle(id, request));
    }

    //endpoint for deleting vehicle
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteClient(@PathVariable("id") Integer id){
        vehicleService.deleteVehicle(id);
        return ResponseEntity.status(HttpStatus.OK).body("vehicle deleted");
    }
}
