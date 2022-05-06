package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Jobs;
import com.project.autoshop.models.Status;
import com.project.autoshop.repositories.StatusRepository;
import com.project.autoshop.repositories.JobsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {
    private final StatusRepository statusRepository;
    private final EmailService emailService;
    private final JobsRepository workRepository;

    public StatusService(StatusRepository statusRepository, EmailService emailService, JobsRepository jobsRepository) {
        this.statusRepository = statusRepository;
        this.emailService = emailService;
        this.workRepository = jobsRepository;
    }

    public List<Status> getAllStatus(){
        return this.statusRepository.findAll();
    }

    public Status getStatus(Integer id){
        Status status = this.statusRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("status with id: " + id + " not found"));
        return status;
    }

    public Status getStatusByWork(Integer id){
        Status status = this.statusRepository.findStatusByWork(id)
                .orElseThrow(() -> new NotFoundException("status with id: " + id + " not found"));
        return status;
    }

    public void createStatus(Jobs work){
        this.workRepository.findById(work.getId())
                .orElseThrow(() -> new NotFoundException("work with id: " + work.getId() + " not found"));
        Status status = new Status();
        this.statusRepository.save(status);
    }

    @Transactional
    public Status updateStatus(Integer id, Status update){
        Status status = this.statusRepository.findStatusByWork(id)
               .orElseThrow(() -> new NotFoundException("status with id: " + id + " not found"));
        Optional.ofNullable(update.getRejected())
                .ifPresent(rejected -> {
                    if(rejected){
                        status.setRejected(true);
                        status.setApproved(false);
                        status.setProgress(false);
                        status.setComplete(false);
                    }else{
                        status.setRejected(false);
                        status.setApproved(true);
                    }
                });

        Optional.ofNullable(update.getApproved())
                .ifPresent(approved -> {
                    if(approved){
                        status.setApproved(true);
                        status.setRejected(false);
                    }else{
                        status.setRejected(true);
                        status.setApproved(false);
                        status.setProgress(false);
                        status.setComplete(false);
                    }
                });

        Optional.ofNullable(update.getOrdered())
                .filter(partOrdered -> status.getApproved() == true)
                .ifPresent(partsOrdered -> {
                    status.setOrdered(partsOrdered);
                });

        Optional.ofNullable(update.getProgress())
                .filter(progress -> status.getApproved() == true && status.getRejected() == false && status.getComplete() == false)
                .ifPresent(progress -> status.setProgress(progress));

        Optional.ofNullable(update.getComplete())
                .filter(complete -> status.getRejected() == false && status.getComplete() == true)
                .ifPresent(complete -> {
                    update.setComplete(complete);
                });

        return update;
    }


}
