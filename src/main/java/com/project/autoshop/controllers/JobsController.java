package com.project.autoshop.controllers;

import com.project.autoshop.models.Jobs;
import com.project.autoshop.request.JobsRequest;
import com.project.autoshop.services.JobsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "api/v1/jobs")
public class JobsController {
    private final JobsService jobsService;

    public JobsController(JobsService jobsService) {
        this.jobsService = jobsService;
    }

    //endpoint for fetching all works
    @GetMapping
    public ResponseEntity getJobs(){
        return ResponseEntity.status(HttpStatus.OK).body(jobsService.getJobs());
    }

    //endpoint for fetching work by id
    @GetMapping(path = "{id}")
    public ResponseEntity getJob(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(jobsService.getJobById(id));
    }

    //endpoint for fetching work by client id
    @GetMapping(path = "/client/{id}")
    public ResponseEntity getClientJobs(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(this.jobsService.getClientJobs(id));
    }

    //endpoint for creating work
    @PostMapping
    public ResponseEntity createJob(@RequestBody JobsRequest job){
        return ResponseEntity.status(HttpStatus.OK).body(this.jobsService.createJob(job));
    }

    //endpoint for updating work
    @PutMapping(path = "{id}")
    public ResponseEntity updateJob(@PathVariable Integer id, @RequestBody JobsRequest update){
        return ResponseEntity.status(HttpStatus.OK).body(this.jobsService.updateJob(id, update));
    }

    //endpoint for deleting work
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteJob(@PathVariable Integer id){
        this.jobsService.deleteWork(id);
        return ResponseEntity.status(HttpStatus.OK).body("work deleted");
    }
}
