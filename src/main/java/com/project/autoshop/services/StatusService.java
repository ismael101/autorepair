package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Stage;
import com.project.autoshop.models.Status;
import com.project.autoshop.models.Work;
import com.project.autoshop.repositories.StatusRepository;
import com.project.autoshop.repositories.WorkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void createStatus(Integer id){
        Work work = this.workRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("work with id: " + id + " not found"));
        Status status = new Status(Stage.PENDING, work);
        this.statusRepository.save(status);
    }

    public void updateStatus(Integer id, Stage stage){
           Status status = this.statusRepository.findStatusByWork(id)
                   .orElseThrow(() -> new NotFoundException("status with id: " + id + " not found"));

           switch (stage){
               case PENDING:
                   //todo send email to client of
           }
    }
}
