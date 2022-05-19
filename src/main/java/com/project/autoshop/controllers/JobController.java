package com.project.autoshop.controllers;

import com.project.autoshop.request.groups.Create;
import com.project.autoshop.request.JobRequest;
import com.project.autoshop.request.groups.Update;
import com.project.autoshop.services.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/v1/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;


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
    public ResponseEntity createJob(@RequestBody @Validated(Create.class) JobRequest job){
        return ResponseEntity.status(HttpStatus.OK).body(jobService.createJob(job));
    }

    //endpoint for updating work
    @PutMapping(path = "{id}")
    public ResponseEntity updateJob(@PathVariable Integer id, @RequestBody @Validated(Update.class) JobRequest update){
        return ResponseEntity.status(HttpStatus.OK).body(this.jobService.updateJob(id, update));
    }

    //endpoint for deleting work
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteJob(@PathVariable Integer id){
        this.jobService.deleteJob(id);
        return ResponseEntity.status(HttpStatus.OK).body("work deleted");
    }
}
