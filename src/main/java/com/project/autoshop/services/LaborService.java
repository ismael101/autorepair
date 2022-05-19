package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Job;
import com.project.autoshop.models.Labor;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.repositories.LaborRepository;
import com.project.autoshop.request.LaborRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LaborService {
    private final LaborRepository laborRepository;
    private final JobRepository jobRepository;

    public List<Labor> getLabors(){
        return laborRepository.findAll();
    }

    public List<Labor> getJobLabors(Integer id){
        return laborRepository.findLaborsByJob(id);
    }

    public Labor getLabor(Integer id){
        Labor labor = laborRepository.findById(id).orElseThrow(() -> new NotFoundException("labor with id: " + id + " not found"));
        return labor;
    }

    public Labor createLabor(LaborRequest request){
        Job job = jobRepository.findById(request.getJob()).orElseThrow(() -> new NotFoundException("job with id: " + request.getJob() + " not found"));
        Labor labor = Labor.builder()
                .task(request.getTask())
                .description(request.getDescription())
                .location(request.getLocation())
                .cost(request.getCost())
                .notes(request.getNotes())
                .job(job)
                .build();
        laborRepository.save(labor);
        return labor;
    }

    @Transactional
    public Labor updateLabor(Integer id, LaborRequest request){
        Labor labor = laborRepository.findById(id).orElseThrow(() -> new NotFoundException("labor with id: " + id + " not found"));
        Optional.ofNullable(request.getTask())
                .ifPresent(name -> labor.setTask(name));
        Optional.ofNullable(request.getDescription())
                .ifPresent(description -> labor.setDescription(description));
        Optional.ofNullable(request.getLocation())
                .ifPresent(location -> labor.setLocation(location));
        Optional.ofNullable(request.getCost())
                .ifPresent(price -> labor.setCost(price));
        Optional.ofNullable(request.getNotes())
                .ifPresent(notes -> labor.setNotes(notes));
        return labor;
    }

    public void deleteLabor(Integer id){
        Labor labor = laborRepository.findById(id).orElseThrow(() -> new NotFoundException("labor with id: " + id + " not found"));;
        laborRepository.delete(labor);
    }
}
