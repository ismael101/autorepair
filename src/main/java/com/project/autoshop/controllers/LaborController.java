package com.project.autoshop.controllers;

import com.project.autoshop.request.LaborRequest;
import com.project.autoshop.request.PartRequest;
import com.project.autoshop.request.groups.Create;
import com.project.autoshop.request.groups.Update;
import com.project.autoshop.services.LaborService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/labor")
@RequiredArgsConstructor
public class LaborController {
    private final LaborService laborService;

    @GetMapping
    public ResponseEntity getLabors(){
        return ResponseEntity.status(HttpStatus.OK).body(laborService.getLabors());
    }

    @GetMapping(path = "/job/{id}")
    public ResponseEntity getJobLabors(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(laborService.getJobLabors(id));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity getLabor(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(laborService.getLabor(id));
    }

    @PostMapping
    public ResponseEntity createLabor(@RequestBody @Validated(Create.class) LaborRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(laborService.createLabor(request));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity updateLabor(@PathVariable Integer id, @RequestBody @Validated(Update.class) LaborRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(laborService.updateLabor(id, request));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteLabor(@PathVariable Integer id){
        laborService.deleteLabor(id);
        return ResponseEntity.status(HttpStatus.OK).body("labor deleted");
    }
}
