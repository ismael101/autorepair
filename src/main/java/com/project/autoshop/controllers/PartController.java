package com.project.autoshop.controllers;

import com.project.autoshop.request.groups.Create;
import com.project.autoshop.request.PartRequest;
import com.project.autoshop.request.groups.Update;
import com.project.autoshop.services.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/parts")
@RequiredArgsConstructor
public class PartController {
    private final PartService partsService;

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
    public ResponseEntity createPart(@RequestBody @Validated(Create.class) PartRequest partsRequest){
        return ResponseEntity.status(HttpStatus.OK).body(partsService.createPart(partsRequest));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity updatePart(@PathVariable Integer id, @RequestBody @Validated(Update.class) PartRequest partsRequest){
        return ResponseEntity.status(HttpStatus.OK).body(partsService.updatePart(id, partsRequest));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity deletePart(@PathVariable Integer id){
        partsService.deletePart(id);
        return ResponseEntity.status(HttpStatus.OK).body("part deleted");
    }
}
