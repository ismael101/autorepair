package com.ismael.autorepair.controllers;

import com.ismael.autorepair.requests.LaborRequest;
import com.ismael.autorepair.requests.groups.Create;
import com.ismael.autorepair.requests.groups.Update;
import com.ismael.autorepair.services.LaborService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

//controller for handling customers
@RestController
@RequestMapping(path = "/api/v1/labor")
public class LaborController {
    private LaborService laborService;
    Logger logger = LoggerFactory.getLogger(LaborController.class);

    public LaborController(LaborService laborService) {
        this.laborService = laborService;
    }

    //controller method for fetching all labors associated with a user
    @GetMapping
    public ResponseEntity getLabors(){
        logger.info("Get Labors Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(laborService.getLabors());
    }

    //controller method for fetching labor by id
    @GetMapping(path = "{id}")
    public ResponseEntity getLaborById(@PathVariable("id") UUID id){
        logger.info("Get Labor By Id Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(laborService.getLaborById(id));
    }

    //controller method for fetching labors by work order
    @GetMapping(path = "/work/{id}")
    public ResponseEntity getLaborByWork(@PathVariable("id") UUID id){
        logger.info("Get Labor By Work Id Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(laborService.getLaborByWork(id));
    }

    //controller method for creating labor
    @PostMapping
    public ResponseEntity createLabor(@RequestBody @Validated(Create.class) LaborRequest request){
        logger.info("Create Labor Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(laborService.createLabor(request));
    }

    //controller method for updating labor
    @PutMapping(path = "{id}")
    public ResponseEntity updateLabor(@PathVariable("id") UUID id, @RequestBody @Validated(Update.class) LaborRequest request){
        logger.info("Update Labor By Id Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(laborService.updateLabor(id, request));
    }

    //controller method for deleting labor
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteLabor(@PathVariable("id") UUID id){
        logger.info("Delete Labor By Id Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(laborService.deleteLabor(id));
    }
}
