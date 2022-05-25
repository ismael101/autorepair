package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Job;
import com.project.autoshop.models.Part;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.repositories.PartRepository;
import com.project.autoshop.request.PartRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartService {
    private final PartRepository partsRepository;
    private final JobRepository jobRepository;

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
        Job job = jobRepository.findById(request.getJob()).orElseThrow(() -> new NotFoundException("job with id: " + request.getJob() + " not found"));
        Part part = Part.builder()
                .name(request.getName())
                .description(request.getDescription())
                .website(request.getWebsite())
                .ordered(request.getOrdered())
                .location(request.getLocation())
                .notes(request.getNotes())
                .cost(request.getCost())
                .job(job)
                .build();
        partsRepository.save(part);
        return part;
    }

    @Transactional
    public Part updatePart(Integer id, PartRequest request){
        Part part = partsRepository.findById(id).orElseThrow(() -> new NotFoundException("part with id: " + id + " not found"));
        Optional.ofNullable(request.getName())
                .ifPresent(name -> part.setName(name));
        Optional.ofNullable(request.getDescription())
                .ifPresent(description -> part.setDescription(description));
        Optional.ofNullable(request.getLocation())
                .ifPresent(location -> part.setLocation(location));
        Optional.ofNullable(request.getOrdered())
                .ifPresent(ordered -> part.setOrdered(ordered));
        Optional.ofNullable(request.getWebsite())
                .ifPresent(website -> part.setWebsite(website));
        Optional.ofNullable(request.getCost())
                .ifPresent(price -> part.setCost(price));
        Optional.ofNullable(request.getNotes())
                .ifPresent(notes -> part.setNotes(notes));

        return part;
    }

    public void deletePart(Integer id){
        Part part = partsRepository.findById(id).orElseThrow(() -> new NotFoundException("part with id: " + id + " not found"));;
        partsRepository.delete(part);
    }
}
