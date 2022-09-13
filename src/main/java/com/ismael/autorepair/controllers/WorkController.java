package com.ismael.autorepair.controllers;

import com.ismael.autorepair.requests.WorkRequest;
import com.ismael.autorepair.requests.groups.Create;
import com.ismael.autorepair.requests.groups.Update;
import com.ismael.autorepair.services.WorkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

//controller for handling work orders
@RestController
@RequestMapping(path = "/api/v1/work")
public class WorkController {
    private WorkService workService;
    Logger logger = LoggerFactory.getLogger(WorkController.class);

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    //controller method for fetching all work orders associated with a user
    @GetMapping
    public ResponseEntity getWorks(){
        logger.info("Get Works Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(workService.getWorks());
    }

    //controller method for fetching a work order by id
    @GetMapping(path = "{id}")
    public ResponseEntity getWorkById(@PathVariable("id") UUID id){
        logger.info("Get Work By Id Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(workService.getWorkById(id));
    }

    //controller method for creating a work order
    @PostMapping
    public ResponseEntity createWork(@RequestBody @Validated(Create.class) WorkRequest request){
        logger.info("Create Work Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(workService.createWork(request));
    }

    //controller method for updating a work order
    @PutMapping(path = "{id}")
    public ResponseEntity updateWork(@PathVariable("id") UUID id, @RequestBody @Validated(Update.class) WorkRequest request){
        logger.info("Update Work Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(workService.updateWork(id, request));
    }

    //controller method deleting work order
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteWork(@PathVariable("id") UUID id){
        logger.info("Delete Work Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(workService.deleteWork(id));
    }

}
