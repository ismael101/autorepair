package com.project.autoshop.controllers;

import com.project.autoshop.models.Work;
import com.project.autoshop.services.WorkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/v1/work")
public class WorkController {
    private final WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    //endpoint for fetching all works
    @GetMapping
    public ResponseEntity getWorks(){
        return ResponseEntity.status(HttpStatus.OK).body(this.workService.getWorks());
    }

    //endpoint for fetching work by id
    @GetMapping(path = "{id}")
    public ResponseEntity getWork(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(this.workService.getWorkById(id));
    }

    //endpoint for fetching work by client id
    @GetMapping(path = "/client/{id}")
    public ResponseEntity getClientWorks(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(this.workService.getWorksByClient(id));
    }

    //endpoint for creating work
    @PostMapping
    public ResponseEntity createWork(@RequestBody Work work){
        return ResponseEntity.status(HttpStatus.OK).body(this.workService.createWork(work));
    }

    //endpoint for updating work
    @PutMapping(path = "{id}")
    public ResponseEntity updateWork(@PathVariable Integer id, @RequestBody Work update){
        return ResponseEntity.status(HttpStatus.OK).body(this.workService.updateWork(id, update));
    }

    //endpoint for deleting work
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteWork(@PathVariable Integer id){
        this.workService.deleteWork(id);
        return ResponseEntity.status(HttpStatus.OK).body("work deleted");
    }
}
