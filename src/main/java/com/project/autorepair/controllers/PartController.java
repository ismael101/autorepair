package com.project.autorepair.controllers;

import com.project.autorepair.request.groups.Create;
import com.project.autorepair.request.PartRequest;
import com.project.autorepair.request.groups.Update;
import com.project.autorepair.services.PartService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/part")
@RequiredArgsConstructor
public class PartController {
    private final PartService partsService;
    private Logger logger = LoggerFactory.getLogger(PartController.class);


    //endpoint for fetching parts
    @GetMapping
    public ResponseEntity getParts(){
        logger.info("get request for all parts");
        return ResponseEntity.status(HttpStatus.OK).body(partsService.getParts());
    }

    //endpoint for fetching parts by job
    @GetMapping(path = "/job/{id}")
    public ResponseEntity getJobParts(@PathVariable Integer id){
        logger.info("get request for part with job id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(partsService.getJobParts(id));
    }

    //endpoint for fetching part by id
    @GetMapping(path = "{id}")
    public ResponseEntity getPart(@PathVariable Integer id){
        logger.info("get request for part with id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(partsService.getPart(id));
    }

    //endpoint for creating part
    @PostMapping
    public ResponseEntity createPart(@RequestBody @Validated(Create.class) PartRequest request){
        logger.info("post request for " + request.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(partsService.createPart(request));
    }

    //endpoint for updating part
    @PutMapping(path = "{id}")
    public ResponseEntity updatePart(@PathVariable Integer id, @RequestBody @Validated(Update.class) PartRequest partsRequest){
        logger.info("put request for customer with id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(partsService.updatePart(id, partsRequest));
    }

    //endpoint for deleting part
    @DeleteMapping(path = "{id}")
    public ResponseEntity deletePart(@PathVariable Integer id){
        logger.info("delete request for customer with id: " + id);
        partsService.deletePart(id);
        return ResponseEntity.status(HttpStatus.OK).body("part deleted");
    }
}
