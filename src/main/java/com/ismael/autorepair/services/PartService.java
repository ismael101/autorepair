package com.ismael.autorepair.services;

import com.ismael.autorepair.exceptions.NotFound;
import com.ismael.autorepair.exceptions.UnauthorizedAction;
import com.ismael.autorepair.models.Part;
import com.ismael.autorepair.models.User;
import com.ismael.autorepair.models.Work;
import com.ismael.autorepair.repositories.PartRepository;
import com.ismael.autorepair.repositories.UserRepository;
import com.ismael.autorepair.repositories.WorkRepository;
import com.ismael.autorepair.requests.PartRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

//service for managing part
@Service
public class PartService {
    private PartRepository partRepository;
    private WorkRepository workRepository;
    private UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(PartService.class);


    public PartService(PartRepository partRepository, WorkRepository workRepository, UserRepository userRepository) {
        this.partRepository = partRepository;
        this.workRepository = workRepository;
        this.userRepository = userRepository;
    }

    //method for fetching all parts associated with current user
    public Map<String, Object> getParts(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByUsername(username).get();
        List<Work> works = workRepository.findWorkByUser(user);
        List<Part> parts = new ArrayList<>();
        for(Work work : works){
            if(work.getParts() != null){
                parts.addAll(work.getParts());
            }
        }
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/part");
        body.put("parts", parts);
        logger.info(parts + " Fetched By: " + username);
        return body;
    }

    //method for fetching part by id but checking if it belongs to the current user
    public Map<String, Object> getPartById(UUID id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Part part = partRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Not Found Exception Thrown By: " + username + " For Part With Id: " + id);
                    throw new NotFound("part with id: " + id + " not found");
                });
        if(!username.equals(part.getWork().getUser().getUsername())){
            logger.error("Unauthorized Action Exception Thrown By: " + username + " For Part With Id: " + id);
            throw new UnauthorizedAction("user: " + username + " action not allowed");
        }
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/part/" + id);
        body.put("part", part);
        logger.info(part + " Fetched By: " + username);
        return body;
    }

    //method for fetching part by work order but checking if it belongs to the current user
    public Map<String, Object> getPartByWork(UUID id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Work work = workRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Not Found Exception Thrown By: " + username + " For Work With Id: " + id);
                    throw new NotFound("work with id: " + id + " not found");
                });
        if(!username.equals(work.getUser().getUsername())){
            logger.error("Unauthorized Action Exception Thrown By: " + username + " For Work With Id: " + id);
            throw new UnauthorizedAction("user: " + username + " action not allowed");
        }
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/part/work/" + id);
        body.put("parts", work.getParts());
        logger.info(work.getParts() + " Fetched By: " + username);
        return body;
    }

    //method for creating part but checking if the work order belongs to the current user
    public Map<String, Object> createPart(PartRequest request){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Work work = workRepository.findById(UUID.fromString(request.getWork()))
                .orElseThrow(() -> {
                    logger.error("Not Found Exception Thrown By: " + username + " For Work With Id: " + request.getWork());
                    throw new NotFound("work with id: " + request.getWork() + " not found");
                });
        if(!username.equals(work.getUser().getUsername())){
            logger.error("Unauthorized Action Exception Thrown By: " + username + " For Work With Id: " + request.getWork());
            throw new UnauthorizedAction("user: " + username + " action not allowed");
        }
        Part part = new Part(request.getTitle(), request.getLocation(), request.getCost(), Boolean.parseBoolean(request.getOrdered()), work);
        partRepository.save(part);
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 201);
        body.put("path", "/api/v1/part");
        body.put("part", part);
        logger.info(part + " Created By: " + username);
        return body;
    }

    //method for updating part by id but checking if it belongs to the current user
    @Transactional
    public Map<String, Object> updatePart(UUID id, PartRequest request){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Part part = partRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Not Found Exception Thrown By: " + username + " For Part With Id: " + id);
                    throw new NotFound("part with id: " + id + " not found");
                });
        if(!username.equals(part.getWork().getUser().getUsername())){
            logger.error("Unauthorized Exception Thrown By: " + username + " For Part With Id: " + id);
            throw new UnauthorizedAction("user: " + username + " action not allowed");
        }
        Optional.ofNullable(request.getCost())
                .ifPresent(cost -> part.setCost(cost));
        Optional.ofNullable(request.getLocation())
                .ifPresent(location -> part.setLocation(location));
        Optional.ofNullable(request.getTitle())
                .ifPresent(title -> part.setTitle(title));
        Optional.ofNullable(request.getOrdered())
                .ifPresent(ordered -> part.setOrdered(Boolean.parseBoolean(ordered)));
        part.setUpdatedAt(LocalDateTime.now());
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/part/" + id);
        body.put("part", part);
        return body;
    }

    //method for deleting part by id but checking if it belongs to the current user
    public Map<String, Object> deletePart(UUID id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Part part = partRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Not Found Exception Thrown By: " + username + " For Part With Id: " + id);
                    throw new NotFound("part with id: " + id + " not found");
                });
        if(!username.equals(part.getWork().getUser().getUsername())){
            logger.info("Unauthorized Exception Thrown By: " + username + " For Part With Id: " + id);
            throw new UnauthorizedAction("user: " + username + " action not allowed");
        }
        partRepository.delete(part);
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/part/" + id);
        body.put("message", "part deleted");
        logger.info(part + " Deleted By: " + username);
        return body;
    }
}
