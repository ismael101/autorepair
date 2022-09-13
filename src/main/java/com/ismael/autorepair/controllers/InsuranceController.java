package com.ismael.autorepair.controllers;

import com.ismael.autorepair.requests.InsuranceRequest;
import com.ismael.autorepair.requests.groups.Create;
import com.ismael.autorepair.requests.groups.Update;
import com.ismael.autorepair.services.InsuranceService;
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
@RequestMapping(path = "/api/v1/insurance")
public class InsuranceController {
    private InsuranceService insuranceService;
    Logger logger = LoggerFactory.getLogger(InsuranceController.class);

    public InsuranceController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    //controller method for fetching all insurance associated with a user
    @GetMapping
    public ResponseEntity getInsurances(){
        logger.info("Get Insurance Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(insuranceService.getInsurances());
    }

    //controller method for fetching insurance by id
    @GetMapping(path = "{id}")
    public ResponseEntity getInsuranceById(@PathVariable("id") UUID id){
        logger.info("Get Insurance By Id Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(insuranceService.getInsuranceById(id));
    }

    //controller method for fetching insurance by work order
    @GetMapping(path = "/work/{id}")
    public ResponseEntity getInsuranceByWork(@PathVariable("id") UUID id){
        logger.info("Get Insurance By Work Id Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(insuranceService.getInsuranceByWork(id));
    }

    //controller method for creating insurance
    @PostMapping
    public ResponseEntity createInsurance(@RequestBody @Validated(Create.class) InsuranceRequest request){
        logger.info("Create Insurance Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(insuranceService.createInsurance(request));
    }

    //controller method for updating insurance
    @PutMapping(path = "{id}")
    public ResponseEntity updateInsurance(@PathVariable("id") UUID id, @RequestBody @Validated(Update.class) InsuranceRequest request){
        logger.info("Update Insurance Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(insuranceService.updateInsurance(id, request));
    }

    //controller method for deleting insurance
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteInsurance(@PathVariable("id") UUID id){
        logger.info("Delete Insurance Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(insuranceService.deleteInsurance(id));
    }
}
