package com.project.autoshop.services;

import com.project.autoshop.exceptions.BadRequestException;
import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Work;
import com.project.autoshop.repositories.StatusRepository;
import com.project.autoshop.repositories.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class WorkService {
    private final WorkRepository workRepository;
    private final StatusRepository statusRepository;
    private final StatusService statusService;
    @Autowired
    private Validator validator;

    public WorkService(WorkRepository workRepository, StatusRepository statusRepository, StatusService statusService) {
        this.workRepository = workRepository;
        this.statusRepository = statusRepository;
        this.statusService = statusService;
    }

    //method for getting all work
    public List<Work> getWorks(){
        return this.workRepository.findAll();
    }

    //method for getting work by clients
    public List<Work> getWorksByClient(Integer id){
        return this.workRepository.findByClient(id);
    }

    //method for getting work by id
    public Work getWorkById(Integer id){
        Work work = this.workRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("work with id: " + id + " not found"));
        return work;
    }

    //method for creating work
    public Work createWork(Work work){
        Set<ConstraintViolation<Work>> violations = validator.validate(work);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Work> violation : violations) {
                sb.append(violation.getMessage());
            }
            throw new BadRequestException("Error occurred: " + sb.toString());
        }
        this.workRepository.save(work);
        this.statusService.createStatus(work);
        return work;
    }

    @Transactional
    //method for updating work
    public Work updateWork(Integer id, Work update){
        Work work = this.workRepository.findById(id).orElseThrow(() -> new NotFoundException("work with id: " + id + " not found"));
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
        this.workRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("work with id: " + id + " not found"));
        this.workRepository.deleteById(id);
    }

}
