package com.project.autoshop.services;

import com.project.autoshop.exceptions.BadRequestException;
import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Client;
import com.project.autoshop.models.Jobs;
import com.project.autoshop.repositories.ClientRepository;
import com.project.autoshop.repositories.StatusRepository;
import com.project.autoshop.repositories.JobsRepository;
import com.project.autoshop.request.JobsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

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
    public List<Jobs> getJobs(){
        return this.jobsRepository.findAll();
    }

    //method for getting work by clients
    public List<Jobs> getClientJobs(Integer id){
        return this.jobsRepository.findByClient(id);
    }

    //method for getting work by id
    public Jobs getJobById(Integer id){
        Jobs jobs = this.jobsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("work with id: " + id + " not found"));
        return jobs;
    }

    //method for creating work
    public Jobs createJob(JobsRequest newJob){
        Set<ConstraintViolation<JobsRequest>> violations = validator.validate(newJob);
        if (!violations.isEmpty()) {
            StringJoiner sb = new StringJoiner(", ");
            for (ConstraintViolation<JobsRequest> violation : violations) {
                sb.add(violation.getMessage());
            }
            throw new BadRequestException("Error occurred: " + sb.toString());
        }
        Client client = this.clientRepository.findById(newJob.getClient_id())
               .orElseThrow(() -> new NotFoundException("client with id: " + newJob.getClient_id() + " not found"));
        Jobs job = Jobs.builder()
                .make(newJob.getMake())
                .model(newJob.getModel())
                .year(newJob.getYear())
                .labor(newJob.getLabor())
                .description(newJob.getDescription())
                .client(client)
                .build();
        this.jobsRepository.save(job);
        return job;
    }

    @Transactional
    //method for updating work
    public Jobs updateJob(Integer id, JobsRequest update){
        Jobs work = this.jobsRepository.findById(id).orElseThrow(() -> new NotFoundException("work with id: " + id + " not found"));
        Optional.ofNullable(update.getMake())
                .filter(make -> make != null && make.length() > 0 && make != work.getMake())
                .ifPresent(make -> work.setMake(make));

        Optional.ofNullable(update.getModel())
                .filter(model -> model != null && model.length() > 0 && model != work.getModel())
                .ifPresent(model -> work.setModel(model));

        Optional.ofNullable(update.getYear())
                .filter(year -> year != null && year > 1990 && year < 2050 && work.getYear() != year)
                .ifPresent(year -> work.setYear(year));

        Optional.ofNullable(update.getDescription())
                .filter(description -> description != null && description.length() > 0 && description != work.getDescription())
                .ifPresent(description -> work.setDescription(description));

        Optional.ofNullable(update.getLabor())
                .filter(labor -> labor != null && labor < 0 && work.getLabor() != labor)
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
