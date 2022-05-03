package com.project.autoshop.controllers;

import com.project.autoshop.models.Status;
import com.project.autoshop.services.StatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/status")
public class StatusController {
    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping
    public ResponseEntity getAllStatus(){
        return ResponseEntity.status(HttpStatus.OK).body(this.statusService.getAllStatus());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity getStatus(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.statusService.getStatus(id));
    }

    @GetMapping(path = "/work/{id}")
    public ResponseEntity getWorkStatus(@PathVariable("id") Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(this.statusService.getStatusByWork(id));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity updateStatus(@PathVariable("id") Integer id, @RequestBody Status update){
        return ResponseEntity.status(HttpStatus.OK).body(this.statusService.updateStatus(id, update));
    }

}
