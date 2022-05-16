package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Image;
import com.project.autoshop.models.Job;
import com.project.autoshop.repositories.ImageRepository;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.request.JobRequest;
import com.project.autoshop.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.Deflater;


@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final ImageRepository imageRepository;

    //method for getting all jobs
    public List<Job> getJobs(){
        return this.jobRepository.findAll();
    }

    //method for getting job by id
    public Job getJobById(Integer id){
        Job jobs = this.jobRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("work with id: " + id + " not found"));
        return jobs;
    }

    //method for creating job
    public Job createJob(JobRequest request){
        Job job = Job.builder()
                .complete(false)
                .description(request.getDescription())
                .labors(List.of())
                .parts(List.of())
                .images(List.of())
                .build();
        jobRepository.save(job);
        return job;
    }

    @Transactional
    //method for updating job
    public Job updateJob(Integer id, JobRequest update){
        Job job = this.jobRepository.findById(id).orElseThrow(() -> new NotFoundException("work with id: " + id + " not found"));
        Optional.ofNullable(update.getDescription())
                .ifPresent(description -> job.setDescription(description));
        Optional.ofNullable(update.getComplete())
                .ifPresent(complete -> job.setComplete(complete));
        return job;
    }

    //method for deleting job
    public void deleteWork(Integer id){
        Job job = this.jobRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("work with id: " + id + " not found"));
        this.jobRepository.delete(job);
    }
}
