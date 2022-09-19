package com.ismael.autorepair.services;

import com.ismael.autorepair.exceptions.NotFound;
import com.ismael.autorepair.exceptions.UnauthorizedAction;
import com.ismael.autorepair.models.Labor;
import com.ismael.autorepair.models.User;
import com.ismael.autorepair.models.Work;
import com.ismael.autorepair.repositories.LaborRepository;
import com.ismael.autorepair.repositories.UserRepository;
import com.ismael.autorepair.repositories.WorkRepository;
import com.ismael.autorepair.requests.LaborRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

//service for managing labor
@Service
public class LaborService {
    private LaborRepository laborRepository;
    private WorkRepository workRepository;
    private UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(LaborService.class);


    public LaborService(LaborRepository laborRepository, WorkRepository workRepository, UserRepository userRepository) {
        this.laborRepository = laborRepository;
        this.workRepository = workRepository;
        this.userRepository = userRepository;
    }

    //method for fetching all labor associated with current user
    public Map<String, Object> getLabors(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByUsername(username).get();
        List<Work> works = workRepository.findWorkByUser(user);
        List<Labor> labors = new ArrayList<>();
        for(Work work: works){
            if(work.getLabors() != null){
                labors.addAll(work.getLabors());
            }
        }
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/labor");
        body.put("labors", labors);
        logger.info(labors + " Fetched By: " + username);
        return body;
    }

    //method for fetching labor by id but checking if it belongs to the current user
    public Map<String, Object> getLaborById(UUID id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Labor labor = laborRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Not Found Exception Thrown By: " + username + " For Labor With Id: " + id);
                    throw new NotFound("labor with id: " + id + " not found");
                });
        if(!username.equals(labor.getWork().getUser().getUsername())){
            logger.error("Unauthorized Action Exception Thrown By: " + username + " For Labor With Id: " + id);
            throw new UnauthorizedAction("user: " + username + " action not allowed");
        }
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/labor/" + id);
        body.put("labor", labor);
        logger.info(labor + " Fetched By: " + username);
        return body;
    }

    //method for fetching labor by work order but checking if it belongs to the current user
    public Map<String, Object> getLaborByWork(UUID id){
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
        body.put("path", "/api/v1/labor/work/" + id);
        body.put("labors", work.getLabors());
        logger.info(work.getLabors() + " Fetched By: " + username);
        return body;
    }

    //method for creating insurance but checking if the work order belongs to the current user
    public Map<String, Object> createLabor(LaborRequest request){
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
        Labor labor = new Labor(request.getTask(), request.getLocation(), request.getCost(), Boolean.parseBoolean(request.getComplete()), work);
        laborRepository.save(labor);
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 201);
        body.put("path", "/api/v1/labor");
        body.put("labor", labor);
        logger.info(labor + " Created By: " + username);
        return body;
    }

    //method for updating labor by id but checking if it belongs to the current user
    @Transactional
    public Map<String, Object> updateLabor(UUID id, LaborRequest request){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Labor labor = laborRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Not Found Exception Thrown By: " + username + " For Labor With Id: " + id);
                    throw new NotFound("labor with id: " + id + " not found");
                });
        if(!username.equals(labor.getWork().getUser().getUsername())){
            logger.error("Unauthorized Exception Thrown By: " + username + " For Labor With Id: " + id);
            throw new UnauthorizedAction("user: " + username + " action not allowed");
        }
        Optional.ofNullable(request.getTask())
                .ifPresent(task -> labor.setTask(task));
        Optional.ofNullable(request.getCost())
                .ifPresent(cost -> labor.setCost(cost));
        Optional.ofNullable(request.getLocation())
                .ifPresent(location -> labor.setLocation(location));
        Optional.of(request.getComplete())
                .ifPresent(complete -> labor.setComplete(Boolean.parseBoolean(complete)));
        labor.setUpdatedAt(LocalDateTime.now());
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/labor/" + id);
        body.put("labor", labor);
        logger.info(labor + " Updated By: " + username);
        return body;
    }

    //method for deleting labor by id but checking if it belongs to the current user
    public Map<String, Object> deleteLabor(UUID id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Labor labor = laborRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Not Found Exception Thrown By: " + username + " For Labor With Id: " + id);
                    throw new NotFound("labor with id: " + id + " not found");
                });
        if(!username.equals(labor.getWork().getUser().getUsername())){
            logger.info("Unauthorized Exception Thrown By: " + username + " For Labor With Id: " + id);
            throw new UnauthorizedAction("user: " + username + " action not allowed");
        }
        laborRepository.delete(labor);
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/labor/" + id);
        body.put("message", "labor deleted");
        logger.info(labor + " Deleted By: " + username);
        return body;
    }

}
