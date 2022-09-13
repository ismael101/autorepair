package com.ismael.autorepair.controllers;

import com.ismael.autorepair.requests.PartRequest;
import com.ismael.autorepair.requests.groups.Create;
import com.ismael.autorepair.requests.groups.Update;
import com.ismael.autorepair.services.PartService;
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
@RequestMapping(path = "/api/v1/part")
public class PartController {
    private PartService partService;
    Logger logger = LoggerFactory.getLogger(PartController.class);

    public PartController(PartService partService) {
        this.partService = partService;
    }

    //controller method for fetching all parts associated with a user
    @GetMapping
    public ResponseEntity getParts(){
        logger.info("Get Parts Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(partService.getParts());
    }

    //controller method for fetching part by id
    @GetMapping(path = "{id}")
    public ResponseEntity getPartById(@PathVariable("id") UUID id){
        logger.info("Get Part By Id Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(partService.getPartById(id));
    }

    //controller method for fetching parts by work id
    @GetMapping(path = "/work/{id}")
    public ResponseEntity getPartsByWork(@PathVariable("id") UUID id){
        logger.info("Get Part By Work Id Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(partService.getPartByWork(id));
    }

    //controller method for creating part
    @PostMapping
    public ResponseEntity createPart(@RequestBody @Validated(Create.class) PartRequest request){
        logger.info("Create Part Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(partService.createPart(request));
    }

    //controller method for updating part
    @PutMapping(path = "{id}")
    public ResponseEntity updatePart(@PathVariable("id") UUID id, @RequestBody @Validated(Update.class) PartRequest request){
        logger.info("Update Part Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(partService.updatePart(id, request));
    }

    //controller method for deleting part
    @DeleteMapping(path = "{id}")
    public ResponseEntity deletePart(@PathVariable("id") UUID id){
        logger.info("Delete Part Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(partService.deletePart(id));
    }
}
