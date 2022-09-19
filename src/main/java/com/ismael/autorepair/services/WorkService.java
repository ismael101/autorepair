package com.ismael.autorepair.services;

import com.ismael.autorepair.exceptions.NotFound;
import com.ismael.autorepair.exceptions.UnauthorizedAction;
import com.ismael.autorepair.models.User;
import com.ismael.autorepair.models.Work;
import com.ismael.autorepair.repositories.UserRepository;
import com.ismael.autorepair.repositories.WorkRepository;
import com.ismael.autorepair.requests.WorkRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

//service for managing work orders
@Service
public class WorkService {
    private WorkRepository workRepository;
    private UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(WorkService.class);

    public WorkService(WorkRepository workRepository, UserRepository userRepository) {
        this.workRepository = workRepository;
        this.userRepository = userRepository;
    }

    //method for fetching all work orders associated with the current user
    public Map<String, Object> getWorks(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByUsername(username).get();
        List<Work> works = workRepository.findWorkByUser(user);
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/work");
        body.put("works", works);
        logger.info(works + " Fetched By: " + username);
        return body;
    }


    //method for fetching a work order by id but checking if it belongs to the current user
    public Map<String, Object> getWorkById(UUID id){
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
        body.put("path", "/api/v1/work/" + id);
        body.put("work", work);
        logger.info(work + " Fetched By: " + username);
        return body;
    }

    //method for creating a new work order but checking if the current user is sending the correct user id
    public Map<String, Object> createWork(WorkRequest request){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByUsername(username).get();
        Work work = new Work(request.getTitle(), request.getDescription(),Boolean.parseBoolean(request.getComplete()), user);
        workRepository.save(work);
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 201);
        body.put("path", "/api/v1/work");
        body.put("work", work);
        logger.info(work + " Created By: " + username);
        return body;
    }

    //method for updating a work order by checking if it belongs to the current user
    @Transactional
    public Map<String, Object> updateWork(UUID id, WorkRequest request){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Work work = workRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Not Found Exception Thrown By: " + username + " For Work With Id: " + id);
                    throw new NotFound("work with id: " + id + " not found");
                });
        if(!username.equals(work.getUser().getUsername())){
            logger.error("Unauthorized Exception Thrown By: " + username + " For Work With Id: " + id);
            throw new UnauthorizedAction("user: " + username + " action not allowed");
        }
        Optional.ofNullable(request.getTitle())
                .ifPresent(title -> work.setTitle(title));
        Optional.ofNullable(request.getDescription())
                .ifPresent(description -> work.setDescription(description));
        Optional.ofNullable(request.getComplete())
                .ifPresent(complete -> work.setComplete(Boolean.parseBoolean(complete)));
        work.setUpdatedAt(LocalDateTime.now());
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/work/" + id);
        body.put("work", work);
        logger.info(work + " Updated By: " + username);
        return body;
    }

    //method for deleting work order by checking if it belongs to the current user
    public Map<String, Object> deleteWork(UUID id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Work work = workRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Not Found Exception Thrown By: " + username + " For Work With Id: " + id);
                    throw new NotFound("work with id: " + id + " not found");
                });
        if(!username.equals(work.getUser().getUsername())){
            logger.info("Unauthorized Exception Thrown By: " + username + " For Work With Id: " + id);
            throw new UnauthorizedAction("user: " + username + " action not allowed");
        }
        workRepository.delete(work);
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/work/" + id);
        body.put("message", "work order deleted");
        logger.info(work + " Deleted By: " + username);
        return body;
    }
}
