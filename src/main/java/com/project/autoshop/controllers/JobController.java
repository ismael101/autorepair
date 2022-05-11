package com.project.autoshop.controllers;

import com.project.autoshop.request.Create;
import com.project.autoshop.request.JobsRequest;
import com.project.autoshop.request.Update;
import com.project.autoshop.services.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/v1/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    //endpoint for fetching all works
    @GetMapping
    public ResponseEntity getJobs(){
        return ResponseEntity.status(HttpStatus.OK).body(jobService.getJobs());
    }

    //endpoint for fetching work by id
    @GetMapping(path = "{id}")
    public ResponseEntity getJob(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(jobService.getJobById(id));
    }

    //endpoint for creating work
    @PostMapping
    public ResponseEntity createJob(@RequestBody @Validated(Create.class) JobsRequest job){
        return ResponseEntity.status(HttpStatus.OK).body(this.jobService.createJob(job));
    }

    //endpoint for updating work
    @PutMapping(path = "{id}")
    public ResponseEntity updateJob(@PathVariable Integer id, @RequestBody @Validated(Update.class) JobsRequest update){
        return ResponseEntity.status(HttpStatus.OK).body(this.jobService.updateJob(id, update));
    }

    //endpoint for deleting work
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteJob(@PathVariable Integer id){
        this.jobService.deleteWork(id);
        return ResponseEntity.status(HttpStatus.OK).body("work deleted");
    }
}
