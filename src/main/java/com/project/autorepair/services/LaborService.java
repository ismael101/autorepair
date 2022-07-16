package com.project.autorepair.services;

import com.project.autorepair.exceptions.NotFoundException;
import com.project.autorepair.models.Job;
import com.project.autorepair.models.Labor;
import com.project.autorepair.repositories.JobRepository;
import com.project.autorepair.repositories.LaborRepository;
import com.project.autorepair.request.LaborRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//service containing business logic for labor
@Service
@RequiredArgsConstructor
public class LaborService {
    private final LaborRepository laborRepository;
    private final JobRepository jobRepository;
    private Logger logger = LoggerFactory.getLogger(LaborService.class);

    //method for getting all labors
    public List<Labor> getLabors(){
        logger.info("all labors fetched");
        return laborRepository.findAll();
    }

    //method for getting all labors by job
    public List<Labor> getJobLabors(Integer id){
        List<Labor> labors = laborRepository.findLaborsByJob(id);
        logger.info(labors.toString() + " fetched");
        return labors;
    }

    //method for getting labor by id
    public Labor getLabor(Integer id){
        Labor labor = laborRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by labor with id: " + id);
                    throw new NotFoundException("labor with id: " + id + " not found");
                });
        return labor;
    }

    //method for creating a new labor
    public Labor createLabor(LaborRequest request){
        Job job = jobRepository.findById(request.getJob())
                .orElseThrow(() -> {
                    logger.error("not found exception caused by job with id: " + 1);
                    throw new NotFoundException("job with id: " + request.getJob() + " not found");
                });
        Labor labor = Labor.builder()
                .task(request.getTask())
                .location(request.getLocation())
                .cost(request.getCost())
                .notes(request.getNotes())
                .job(job)
                .build();
        laborRepository.save(labor);
        logger.info(labor.toString() + " created");
        return labor;
    }

    //method for updating a labor
    @Transactional
    public Labor updateLabor(Integer id, LaborRequest request){
        Labor labor = laborRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by labor with id: " + id);
                    throw new NotFoundException("labor with id: " + id + " not found");
                });
        Optional.ofNullable(request.getTask())
                .ifPresent(name -> labor.setTask(name));
        Optional.ofNullable(request.getLocation())
                .ifPresent(location -> labor.setLocation(location));
        Optional.ofNullable(request.getCost())
                .ifPresent(price -> labor.setCost(price));
        Optional.ofNullable(request.getNotes())
                .ifPresent(notes -> labor.setNotes(notes));
        logger.info(labor.toString() + " updated");
        return labor;
    }

    //method for deleting a labor
    public void deleteLabor(Integer id){
        Labor labor = laborRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by labor with id: " + id);
                    throw new NotFoundException("labor with id: " + id + " not found");
                });
        logger.info(labor.toString() + " deleted");
        laborRepository.delete(labor);
    }
}
