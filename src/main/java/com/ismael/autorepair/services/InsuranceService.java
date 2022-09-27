package com.ismael.autorepair.services;

import com.ismael.autorepair.exceptions.AlreadyExists;
import com.ismael.autorepair.exceptions.NotFound;
import com.ismael.autorepair.exceptions.UnauthorizedAction;
import com.ismael.autorepair.models.Insurance;
import com.ismael.autorepair.models.User;
import com.ismael.autorepair.models.Work;
import com.ismael.autorepair.repositories.InsuranceRepository;
import com.ismael.autorepair.repositories.UserRepository;
import com.ismael.autorepair.repositories.WorkRepository;
import com.ismael.autorepair.requests.InsuranceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;


//service for managing insurance
@Service
public class InsuranceService {
    private InsuranceRepository insuranceRepository;
    private WorkRepository workRepository;
    private UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(InsuranceService.class);

    public InsuranceService(InsuranceRepository insuranceRepository, WorkRepository workRepository, UserRepository userRepository) {
        this.insuranceRepository = insuranceRepository;
        this.workRepository = workRepository;
        this.userRepository = userRepository;
    }

    //method for fetching all insurances associated with current user
    public Map<String, Object> getInsurances(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByUsername(username).get();
        List<Work> works = workRepository.findWorkByUser(user);
        List<Insurance> insurances = new ArrayList<>();
        for(Work work: works){
            if(work.getInsurance() != null){
                insurances.add(work.getInsurance());
            }
        }
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/insurance");
        body.put("insurances", insurances);
        logger.info(insurances + " Fetched By: " + username);
        return body;
    }

    //method for fetching insurance by id but checking if it belongs to the current user
    public Map<String, Object> getInsuranceById(UUID id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Not Found Exception Thrown By: " + username + " For Insurance With Id: " + id);
                    throw new NotFound("insurance with id: " + id + " not found");
                });
        if(!username.equals(insurance.getWork().getUser().getUsername())){
            logger.error("Unauthorized Exception Thrown By: " + username + " For Insurance With Id: " + id);
            throw new UnauthorizedAction("user: " + username + " action not allowed");
        }
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/insurance/" + id);
        body.put("insurance", insurance);
        logger.info(insurance + " Fetched By: " + username);
        return body;
    }

    //method for fetching insurance by work but checking if it belongs to the current user
    public Map<String, Object> getInsuranceByWork(UUID id){
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
        body.put("path", "/api/v1/insurance/work/" + id);
        body.put("insurance", work.getInsurance());
        logger.info(work.getInsurance() + " Fetched By: " + username);
        return body;
    }

    //method for creating insurance but checking if the work order belongs to the current user
    public Map<String, Object> createInsurance(InsuranceRequest request){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Work work = workRepository.findById(UUID.fromString(request.getWork()))
                .orElseThrow(() -> {
                    logger.error("Not Found Exception Thrown By: " + username + " For Work With Id: " + request.getWork());
                    throw new NotFound("work with id: " + request.getWork() + " not found");
                });
        if(!username.equals(work.getUser().getUsername())){
            throw new UnauthorizedAction("user: " + username + " action not allowed");
        }
        if(work.getInsurance() != null){
            logger.error("Unauthorized Action Exception Thrown By: " + username + " For Work With Id: " + request.getWork());
            throw new AlreadyExists("work order already has a insurance");
        }
        Insurance insurance = new Insurance(request.getProvider(), request.getPolicy(), request.getVin(), work);
        insuranceRepository.save(insurance);
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 201);
        body.put("path", "/api/v1/insurance");
        body.put("insurance", insurance);
        logger.info(insurance + " Created By: " + username);
        return body;
    }

    //method for updating insurance by id but checking if it belongs to the current user
    @Transactional
    public Map<String, Object> updateInsurance(UUID id, InsuranceRequest request){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Not Found Exception Thrown By: " + username + " For Insurance With Id: " + id);
                    throw new NotFound("insurance with id: " + id + " not found");
                });
        if(!username.equals(insurance.getWork().getUser().getUsername())){
            logger.error("Unauthorized Exception Thrown By: " + username + " For Insurance With Id: " + id);
            throw new UnauthorizedAction("user: " + username + " action not allowed");
        }
        Optional.ofNullable(request.getProvider())
                .ifPresent(provider -> insurance.setProvider(provider));
        Optional.ofNullable(request.getPolicy())
                .ifPresent(policy -> insurance.setPolicy(policy));
        Optional.ofNullable(request.getVin())
                .ifPresent(vin -> insurance.setVin(vin));
        insurance.setUpdatedAt(LocalDateTime.now());
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/insurance/" + id);
        body.put("insurance", insurance);
        logger.info(insurance + " Updated By: " + username);
        return body;
    }

    //method for deleting insurance but checking if it belongs to the current user
    public Map<String, Object> deleteInsurance(UUID id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Not Found Exception Thrown By: " + username + " For Insurance With Id: " + id);
                    throw new NotFound("insurance with id: " + id + " not found");
                });
        if(!username.equals(insurance.getWork().getUser().getUsername())){
            logger.info("Unauthorized Exception Thrown By: " + username + " For Insurance With Id: " + id);
            throw new UnauthorizedAction("user: " + username + " action not allowed");
        }
        insuranceRepository.delete(insurance);
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/insurance/" + id);
        body.put("message", "insurance deleted");
        logger.info(insurance + " Deleted By: " + username);
        return body;
    }

}
