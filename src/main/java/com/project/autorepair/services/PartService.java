package com.project.autorepair.services;

import com.project.autorepair.exceptions.NotFoundException;
import com.project.autorepair.models.Job;
import com.project.autorepair.models.Part;
import com.project.autorepair.repositories.JobRepository;
import com.project.autorepair.repositories.PartRepository;
import com.project.autorepair.request.PartRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//service containing business logic for part
@Service
@RequiredArgsConstructor
public class PartService {
    private final PartRepository partsRepository;
    private final JobRepository jobRepository;
    private Logger logger = LoggerFactory.getLogger(PartService.class);

    //method for fetching all parts
    public List<Part> getParts(){
        logger.info("all parts fetched");
        return partsRepository.findAll();
    }

    //method for fetching parts by job
    public List<Part> getJobParts(Integer id){
        List<Part> parts = partsRepository.findPartsByJob(id);
        logger.info(parts.toString() + " fetched");
        return parts;
    }

    //method for fetching part by id
    public Part getPart(Integer id){
        Part part = partsRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by part with id: " + id);
                    throw new NotFoundException("part with id: " + id + " not found");
                });
        logger.info(part.toString() + " fetched");
        return part;
    }

    //method for creating a new part
    public Part createPart(PartRequest request){
        Job job = jobRepository.findById(request.getJob()).orElseThrow(() -> {
            logger.error("not found exception caused by job with id: " + request.getJob());
            throw new NotFoundException("job with id: " + request.getJob() + " not found");
        });
        Part part = Part.builder()
                .name(request.getName())
                .description(request.getDescription())
                .website(request.getWebsite())
                .ordered(Boolean.parseBoolean(request.getOrdered()))
                .location(request.getLocation())
                .notes(request.getNotes())
                .cost(request.getCost())
                .job(job)
                .build();
        partsRepository.save(part);
        logger.info(part.toString() + " created");
        return part;
    }

    //method for updating a part
    @Transactional
    public Part updatePart(Integer id, PartRequest request){
        Part part = partsRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by part with id: " + id);
                    throw new NotFoundException("part with id: " + id + " not found");
                });
        Optional.ofNullable(request.getName())
                .ifPresent(name -> part.setName(name));
        Optional.ofNullable(request.getDescription())
                .ifPresent(description -> part.setDescription(description));
        Optional.ofNullable(request.getLocation())
                .ifPresent(location -> part.setLocation(location));
        Optional.ofNullable(request.getOrdered())
                .ifPresent(ordered -> part.setOrdered(Boolean.parseBoolean(ordered)));
        Optional.ofNullable(request.getWebsite())
                .ifPresent(website -> part.setWebsite(website));
        Optional.ofNullable(request.getCost())
                .ifPresent(price -> part.setCost(price));
        Optional.ofNullable(request.getNotes())
                .ifPresent(notes -> part.setNotes(notes));
        logger.info(part.toString() + " updated");
        return part;
    }

    //method for deleting a part
    public void deletePart(Integer id){
        Part part = partsRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by part with id: " + id);
                    throw new NotFoundException("part with id: " + id + " not found");
                });
        logger.info(part.toString() + " deleted");
        partsRepository.delete(part);
    }
}
