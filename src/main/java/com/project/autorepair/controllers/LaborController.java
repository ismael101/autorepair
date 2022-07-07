package com.project.autorepair.controllers;

import com.project.autorepair.request.LaborRequest;
import com.project.autorepair.request.groups.Create;
import com.project.autorepair.request.groups.Update;
import com.project.autorepair.services.LaborService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/labor")
@RequiredArgsConstructor
public class LaborController {
    private final LaborService laborService;
    private Logger logger = LoggerFactory.getLogger(LaborController.class);

    //endpoint for fetching labors
    @GetMapping
    public ResponseEntity getLabors(){
        logger.info("get request for all labor");
        return ResponseEntity.status(HttpStatus.OK).body(laborService.getLabors());
    }

    //endpoint for fetching labor by job
    @GetMapping(path = "/job/{id}")
    public ResponseEntity getJobLabors(@PathVariable Integer id){
        logger.info("get request for labor with job id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(laborService.getJobLabors(id));
    }

    //endpoint for fetching labor by id
    @GetMapping(path = "{id}")
    public ResponseEntity getLabor(@PathVariable Integer id){
        logger.info("get request for labor with id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(laborService.getLabor(id));
    }

    //endpoint for fetching create labor
    @PostMapping
    public ResponseEntity createLabor(@RequestBody @Validated(Create.class) LaborRequest request){
        logger.info("post request for " + request.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(laborService.createLabor(request));
    }

    //endpoint for fetching update labor
    @PutMapping(path = "{id}")
    public ResponseEntity updateLabor(@PathVariable Integer id, @RequestBody @Validated(Update.class) LaborRequest request){
        logger.info("put request for labor with id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(laborService.updateLabor(id, request));
    }

    //endpoint for deleting labor
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteLabor(@PathVariable Integer id){
        logger.info("delete request for labor with id: " + id);
        laborService.deleteLabor(id);
        return ResponseEntity.status(HttpStatus.OK).body("labor deleted");
    }
}
