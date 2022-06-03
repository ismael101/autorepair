package com.project.autorepair.services;

import com.project.autorepair.exceptions.NotFoundException;
import com.project.autorepair.models.Job;
import com.project.autorepair.repositories.JobRepository;
import com.project.autorepair.request.JobRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


//service containing business logic for job
@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private Logger logger = LoggerFactory.getLogger(JobService.class);

    //method for getting all jobs
    public List<Job> getJobs() {
        logger.info("all jobs fetched");
        return this.jobRepository.findAll();
    }

    //method for getting job by id
    public Job getJobById(Integer id){
        Job jobs = this.jobRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by job with id: " + id);
                    throw new NotFoundException("job with id: " + id + " not found");
                });
        logger.info(jobs.toString() + " fetched");
        return jobs;
    }

    //method for creating job
    public Job createJob(JobRequest request){
        Job job = Job.builder()
                .complete(Boolean.parseBoolean(request.getComplete()))
                .description(request.getDescription())
                .labors(List.of())
                .parts(List.of())
                .images(List.of())
                .build();
        jobRepository.save(job);
        logger.info(job.toString() + " created");
        return job;
    }

    @Transactional
    //method for updating job
    public Job updateJob(Integer id, JobRequest update){
        Job job = this.jobRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by job with id: " + id);
                    throw new NotFoundException("job with id: " + id + " not found");
                });
        Optional.ofNullable(update.getDescription())
                .ifPresent(description -> job.setDescription(description));
        Optional.ofNullable(update.getComplete())
                .ifPresent(complete -> job.setComplete(Boolean.parseBoolean(update.getComplete())));
        logger.info(job.toString() + " created");
        return job;
    }

    //method for deleting job
    public void deleteJob(Integer id){
        Job job = this.jobRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by job with id: " + id);
                    throw new NotFoundException("job with id: " + id + " not found");
                });
        logger.info(job.toString() + " deleted");
        this.jobRepository.delete(job);
    }
}
