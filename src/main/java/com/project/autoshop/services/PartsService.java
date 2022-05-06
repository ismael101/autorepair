package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Jobs;
import com.project.autoshop.models.Parts;
import com.project.autoshop.repositories.JobsRepository;
import com.project.autoshop.repositories.PartsRepository;
import com.project.autoshop.request.PartsRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PartsService {
    private final PartsRepository partsRepository;
    private final JobsRepository jobsRepository;

    public PartsService(PartsRepository partsRepository, JobsRepository jobsRepository) {
        this.partsRepository = partsRepository;
        this.jobsRepository = jobsRepository;
    }

    public List<Parts> getParts(){
        return partsRepository.findAll();
    }

    public List<Parts> getJobParts(Integer id){
        return partsRepository.findPartsByJob(id);
    }

    public Parts getPart(Integer id){
        Parts part = partsRepository.findById(id).orElseThrow(() -> new NotFoundException("part with id: " + id + " not found"));
        return part;
    }

    public Parts createPart(PartsRequest partsRequest){
        Jobs jobs = jobsRepository.findById(partsRequest.getJob_id()).orElseThrow(() -> new NotFoundException("part with id: " + partsRequest.getJob_id() + " not found"));
        Parts part = Parts.builder()
                .name(partsRequest.getName())
                .website(partsRequest.getWebsite())
                .price(partsRequest.getPrice())
                .job(jobs)
                .build();
        partsRepository.save(part);
        return part;
    }

    @Transactional
    public Parts updatePart(Integer id, PartsRequest partsRequest){
        Parts part = partsRepository.findById(id).orElseThrow(() -> new NotFoundException("part with id: " + id + " not found"));
        Optional.ofNullable(partsRequest.getName())
                .ifPresent(name -> part.setName(name));

        Optional.ofNullable(partsRequest.getWebsite())
                .ifPresent(website -> part.setWebsite(website));

        Optional.ofNullable(partsRequest.getPrice())
                .ifPresent(price -> part.setPrice(price));

        return part;
    }

    public void deletePart(Integer id){
        partsRepository.findById(id).orElseThrow(() -> new NotFoundException("part with id: " + id + " not found"));;
        partsRepository.deleteById(id);
    }
}
