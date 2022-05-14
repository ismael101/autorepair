package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Job;
import com.project.autoshop.models.Part;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.repositories.PartRepository;
import com.project.autoshop.request.PartRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PartService {
    private final PartRepository partsRepository;
    private final JobRepository jobRepository;

    public PartService(PartRepository partsRepository, JobRepository jobRepository) {
        this.partsRepository = partsRepository;
        this.jobRepository = jobRepository;
    }

    public List<Part> getParts(){
        return partsRepository.findAll();
    }

    public List<Part> getJobParts(Integer id){
        return partsRepository.findPartsByJob(id);
    }

    public Part getPart(Integer id){
        Part part = partsRepository.findById(id).orElseThrow(() -> new NotFoundException("part with id: " + id + " not found"));
        return part;
    }

    public Part createPart(PartRequest request){
        Job jobs = jobRepository.findById(request.getJob()).orElseThrow(() -> new NotFoundException("part with id: " + request.getJob() + " not found"));
        Part part = Part.builder()
                .name(request.getName())
                .website(request.getWebsite())
                .ordered(false)
                .cost(request.getPrice())
                .job(jobs)
                .build();
        partsRepository.save(part);
        return part;
    }

    @Transactional
    public Part updatePart(Integer id, PartRequest partsRequest){
        Part part = partsRepository.findById(id).orElseThrow(() -> new NotFoundException("part with id: " + id + " not found"));
        Optional.ofNullable(partsRequest.getName())
                .ifPresent(name -> part.setName(name));
        Optional.ofNullable(partsRequest.getWebsite())
                .ifPresent(website -> part.setWebsite(website));
        Optional.ofNullable(partsRequest.getPrice())
                .ifPresent(price -> part.setCost(price));

        return part;
    }

    public void deletePart(Integer id){
        partsRepository.findById(id).orElseThrow(() -> new NotFoundException("part with id: " + id + " not found"));;
        partsRepository.deleteById(id);
    }
}
