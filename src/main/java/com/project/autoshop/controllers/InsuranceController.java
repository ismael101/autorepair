package com.project.autoshop.controllers;

import com.project.autoshop.request.AddressRequest;
import com.project.autoshop.request.InsuranceRequest;
import com.project.autoshop.request.groups.Create;
import com.project.autoshop.request.groups.Update;
import com.project.autoshop.services.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/insurance")
@RequiredArgsConstructor
public class InsuranceController {
    private final InsuranceService insuranceService;

    //endpoint for fetching all addresses
    @GetMapping
    public ResponseEntity getInsurance(){
        return ResponseEntity.status(HttpStatus.OK).body(insuranceService.getInsurances());
    }

    //endpoint for fetching address by id
    @GetMapping(path = "{id}")
    public ResponseEntity getInsurance(@PathVariable("id") Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(insuranceService.getInsurance(id));
    }

    //endpoint for fetching address by id
    @GetMapping(path = "/job/{id}")
    public ResponseEntity getJobInsurance(@PathVariable("id") Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(insuranceService.getJobInsurance(id));
    }

    //endpoint for creating address
    @PostMapping
    public ResponseEntity createInsurance(@RequestBody @Validated(Create.class) InsuranceRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(insuranceService.createInsurance(request));
    }

    //endpoint for updating address
    @PutMapping(path = "{id}")
    public ResponseEntity updateInsurance(@PathVariable("id") Integer id, @RequestBody @Validated(Update.class) InsuranceRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(insuranceService.updateInsurance(id, request));
    }

    //endpoint for deleting address
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteInsurance(@PathVariable("id") Integer id){
        insuranceService.deleteInsurance(id);
        return ResponseEntity.status(HttpStatus.OK).body("insurance deleted");
    }
}
