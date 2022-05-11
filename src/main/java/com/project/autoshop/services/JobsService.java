package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Job;
import com.project.autoshop.models.Vehicle;
import com.project.autoshop.repositories.ClientRepository;
import com.project.autoshop.repositories.StatusRepository;
import com.project.autoshop.repositories.JobsRepository;
import com.project.autoshop.request.JobsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;


@Service
public class JobsService {
    private final JobsRepository jobsRepository;
    private final StatusRepository statusRepository;
    private final StatusService statusService;
    private final ClientRepository clientRepository;
    @Autowired
    private Validator validator;

    public JobsService(JobsRepository jobsRepository, StatusRepository statusRepository, StatusService statusService, ClientRepository clientRepository) {
        this.jobsRepository = jobsRepository;
        this.statusRepository = statusRepository;
        this.statusService = statusService;
        this.clientRepository = clientRepository;
    }

    //method for getting all work
    public List<Job> getJobs(){
        return this.jobsRepository.findAll();
    }

    //method for getting work by clients
    public List<Job> getClientJobs(Integer id){
        return this.jobsRepository.findJobsByClient(id);
    }

    //method for getting work by id
    public Job getJobById(Integer id){
        Job jobs = this.jobsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("work with id: " + id + " not found"));
        return jobs;
    }

    //method for creating work
    public Job createJob(JobsRequest newJob){
        Job job = Job.builder()
                .labor(newJob.getLabor())
                .description(newJob.getDescription())
                .build();
        this.jobsRepository.save(job);
        return job;
    }

    @Transactional
    //method for updating work
    public Job updateJob(Integer id, JobsRequest update){
        Job work = this.jobsRepository.findById(id).orElseThrow(() -> new NotFoundException("work with id: " + id + " not found"));
        Optional.ofNullable(update.getDescription())
                .ifPresent(description -> work.setDescription(description));
        Optional.ofNullable(update.getLabor())
                .ifPresent(labor -> work.setLabor(labor));

        return work;
    }

    //method for deleting work
    public void deleteWork(Integer id){
        this.jobsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("work with id: " + id + " not found"));
        this.jobsRepository.deleteById(id);
    }

}
