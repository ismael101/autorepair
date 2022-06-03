package com.project.autorepair.controllers;

import com.project.autorepair.request.InsuranceRequest;
import com.project.autorepair.request.groups.Create;
import com.project.autorepair.request.groups.Update;
import com.project.autorepair.services.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/insurance")
@RequiredArgsConstructor
public class InsuranceController {
    private final InsuranceService insuranceService;
    private Logger logger = LoggerFactory.getLogger(InsuranceController.class);

    //endpoint for fetching all insurance
    @GetMapping
    public ResponseEntity getInsurance(){
        logger.info("get request for all insurance");
        return ResponseEntity.status(HttpStatus.OK).body(insuranceService.getInsurances());
    }

    //endpoint for fetching insurance by id
    @GetMapping(path = "{id}")
    public ResponseEntity getInsurance(@PathVariable("id") Integer id){
        logger.info("get request for insurance with id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(insuranceService.getInsurance(id));
    }

    //endpoint for fetching insurance by id
    @GetMapping(path = "/job/{id}")
    public ResponseEntity getJobInsurance(@PathVariable("id") Integer id){
        logger.info("get request for insurance with job id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(insuranceService.getJobInsurance(id));
    }

    //endpoint for creating insurance
    @PostMapping
    public ResponseEntity createInsurance(@RequestBody @Validated(Create.class) InsuranceRequest request){
        logger.info("post request for " + request.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(insuranceService.createInsurance(request));
    }

    //endpoint for updating insurance
    @PutMapping(path = "{id}")
    public ResponseEntity updateInsurance(@PathVariable("id") Integer id, @RequestBody @Validated(Update.class) InsuranceRequest request){
        logger.info("put request for customer with id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(insuranceService.updateInsurance(id, request));
    }

    //endpoint for deleting insurance
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteInsurance(@PathVariable("id") Integer id){
        logger.info("delete request for customer with id: " + id);
        insuranceService.deleteInsurance(id);
        return ResponseEntity.status(HttpStatus.OK).body("insurance deleted");
    }
}
