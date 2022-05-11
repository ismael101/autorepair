package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Job;
import com.project.autoshop.repositories.ClientRepository;
import com.project.autoshop.repositories.StatusRepository;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.request.JobsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;


@Service
public class JobsService {
    private final JobRepository jobRepository;
    private final StatusRepository statusRepository;
    private final StatusService statusService;
    private final ClientRepository clientRepository;
    @Autowired
    private Validator validator;

    public JobsService(JobRepository jobRepository, StatusRepository statusRepository, StatusService statusService, ClientRepository clientRepository) {
        this.jobRepository = jobRepository;
        this.statusRepository = statusRepository;
        this.statusService = statusService;
        this.clientRepository = clientRepository;
    }

    //method for getting all work
    public List<Job> getJobs(){
        return this.jobRepository.findAll();
    }

    //method for getting work by clients
    public List<Job> getClientJobs(Integer id){
        return this.jobRepository.findJobsByClient(id);
    }

    //method for getting work by id
    public Job getJobById(Integer id){
        Job jobs = this.jobRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("work with id: " + id + " not found"));
        return jobs;
    }

    //method for creating work
    public Job createJob(JobsRequest newJob){
        Job job = Job.builder()
                .labor(newJob.getLabor())
                .description(newJob.getDescription())
                .build();
        this.jobRepository.save(job);
        return job;
    }

    @Transactional
    //method for updating work
    public Job updateJob(Integer id, JobsRequest update){
        Job work = this.jobRepository.findById(id).orElseThrow(() -> new NotFoundException("work with id: " + id + " not found"));
        Optional.ofNullable(update.getDescription())
                .ifPresent(description -> work.setDescription(description));
        Optional.ofNullable(update.getLabor())
                .ifPresent(labor -> work.setLabor(labor));

        return work;
    }

    //method for deleting work
    public void deleteWork(Integer id){
        this.jobRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("work with id: " + id + " not found"));
        this.jobRepository.deleteById(id);
    }

}
