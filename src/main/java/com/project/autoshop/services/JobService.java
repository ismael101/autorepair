package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Image;
import com.project.autoshop.models.Job;
import com.project.autoshop.models.Status;
import com.project.autoshop.repositories.CustomerRepository;
import com.project.autoshop.repositories.ImageRepository;
import com.project.autoshop.repositories.StatusRepository;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.request.JobsRequest;
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
    private final StatusService statusService;

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
    public Job createJob(JobsRequest request){
        Job job = Job.builder()
                .labor(request.getLabor())
                .description(request.getDescription())
                .parts(List.of())
                .build();
        jobRepository.save(job);
        statusService.createStatus(job);
        return job;
    }

    @Transactional
    //method for updating job
    public Job updateJob(Integer id, JobsRequest update){
        Job job = this.jobRepository.findById(id).orElseThrow(() -> new NotFoundException("work with id: " + id + " not found"));
        Optional.ofNullable(update.getDescription())
                .ifPresent(description -> job.setDescription(description));
        Optional.ofNullable(update.getLabor())
                .ifPresent(labor -> job.setLabor(labor));
        return job;
    }

    //method for deleting work
    public void deleteWork(Integer id){
        this.jobRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("work with id: " + id + " not found"));
        this.jobRepository.deleteById(id);
    }

    public Image upload(Integer id, MultipartFile file) throws IOException {
        Job job = this.jobRepository.findById(id).orElseThrow(() -> new NotFoundException("work with id: " + id + " not found"));
        Deflater compressor = new Deflater(Deflater.BEST_COMPRESSION);
        Image image = Image
                .builder()
                .name(file.getOriginalFilename())
                .data(FileUtils.compress(file.getBytes(), Deflater.BEST_COMPRESSION, false))
                .job(job)
                .build();
        return image;
    }
}
