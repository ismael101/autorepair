package com.project.autorepair.controllers;

import com.project.autorepair.request.groups.Create;
import com.project.autorepair.request.JobRequest;
import com.project.autorepair.request.groups.Update;
import com.project.autorepair.services.JobService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/v1/job")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class JobController {
    private final JobService jobService;
    private Logger logger = LoggerFactory.getLogger(JobController.class);

    //endpoint for fetching all jobs
    @GetMapping
    public ResponseEntity getJobs(){
        logger.info("get request for all jobs");
        return ResponseEntity.status(HttpStatus.OK).body(jobService.getJobs());
    }

    //endpoint for fetching job by id
    @GetMapping(path = "{id}")
    public ResponseEntity getJob(@PathVariable Integer id){
        logger.info("get request for job with id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(jobService.getJobById(id));
    }

    //endpoint for creating a job
    @PostMapping
    public ResponseEntity createJob(@RequestBody @Validated(Create.class) JobRequest job){
        logger.info("post request for " + job.toString());
        return ResponseEntity.status(HttpStatus.OK).body(jobService.createJob(job));
    }

    //endpoint for updating a job
    @PutMapping(path = "{id}")
    public ResponseEntity updateJob(@PathVariable Integer id, @RequestBody @Validated(Update.class) JobRequest update){
        logger.info("put request for job with id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(this.jobService.updateJob(id, update));
    }

    //endpoint for deleting a job
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteJob(@PathVariable Integer id){
        logger.info("delete request for job with id: " + id);
        this.jobService.deleteJob(id);
        return ResponseEntity.status(HttpStatus.OK).body("job deleted");
    }
}
