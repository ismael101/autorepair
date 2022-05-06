package com.project.autoshop.controllers;

import com.project.autoshop.request.PartsRequest;
import com.project.autoshop.services.PartsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/parts")
public class PartsController {
    private final PartsService partsService;

    public PartsController(PartsService partsService) {
        this.partsService = partsService;
    }

    @GetMapping
    public ResponseEntity getParts(){
        return ResponseEntity.status(HttpStatus.OK).body(partsService.getParts());
    }

    @GetMapping(path = "/jobs/{id}")
    public ResponseEntity getJobParts(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(partsService.getJobParts(id));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity getPart(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(partsService.getPart(id));
    }

    @PostMapping
    public ResponseEntity createPart(@RequestBody @Valid PartsRequest partsRequest){
        return ResponseEntity.status(HttpStatus.OK).body(partsService.createPart(partsRequest));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity updatePart(@PathVariable Integer id, @RequestBody @Valid PartsRequest partsRequest){
        return ResponseEntity.status(HttpStatus.OK).body(partsService.updatePart(id, partsRequest));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity deletePart(@PathVariable Integer id){
        partsService.deletePart(id);
        return ResponseEntity.status(HttpStatus.OK).body("part deleted");
    }
}
