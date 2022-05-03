package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Status;
import com.project.autoshop.models.Work;
import com.project.autoshop.repositories.StatusRepository;
import com.project.autoshop.repositories.WorkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {
    private final StatusRepository statusRepository;
    private final WorkRepository workRepository;

    public StatusService(StatusRepository statusRepository, WorkRepository workRepository) {
        this.statusRepository = statusRepository;
        this.workRepository = workRepository;
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

    public void createStatus(Work work){
        this.workRepository.findById(work.getId())
                .orElseThrow(() -> new NotFoundException("work with id: " + work.getId() + " not found"));
        Status status = new Status(work);
        this.statusRepository.save(status);
    }

    @Transactional
    public Status updateStatus(Integer id, Status update){
        Status status = this.statusRepository.findStatusByWork(id)
               .orElseThrow(() -> new NotFoundException("status with id: " + id + " not found"));

        Optional.ofNullable(update.getRejected())
                .ifPresent(rejected -> {
                    if(rejected == true){
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
                    if(approved == true){
                        status.setApproved(true);
                        status.setRejected(false);
                    }else{
                        status.setRejected(true);
                        status.setApproved(false);
                        status.setProgress(false);
                        status.setComplete(false);
                    }
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
