package com.ismael.autorepair.services;

import com.ismael.autorepair.exceptions.AlreadyExists;
import com.ismael.autorepair.exceptions.NotFound;
import com.ismael.autorepair.exceptions.UnauthorizedAction;
import com.ismael.autorepair.models.User;
import com.ismael.autorepair.models.Vehicle;
import com.ismael.autorepair.models.Work;
import com.ismael.autorepair.repositories.UserRepository;
import com.ismael.autorepair.repositories.VehicleRepository;
import com.ismael.autorepair.repositories.WorkRepository;
import com.ismael.autorepair.requests.VehicleRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;


//service for managing vehicles
@Service
public class VehicleService {
    private VehicleRepository vehicleRepository;
    private WorkRepository workRepository;
    private UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(VehicleService.class);


    public VehicleService(VehicleRepository vehicleRepository, WorkRepository workRepository, UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.workRepository = workRepository;
        this.userRepository = userRepository;
    }

    //method for getting all vehicle associated with a user
    public Map<String, Object> getVehicles(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByUsername(username).get();
        List<Work> works = workRepository.findWorkByUser(user);
        List<Vehicle> vehicles = new ArrayList<>();
        for(Work work: works){
            if(work.getVehicle() != null){
                vehicles.add(work.getVehicle());
            }
        }
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/vehicle");
        body.put("vehicles", vehicles);
        logger.info(vehicles + " Fetched By: " + username);
        return body;
    }

    //method for fetching a vehicle by id but checking if it belongs to the current user
    public Map<String, Object> getVehicleById(UUID id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Not Found Exception Thrown By: " + username + " For Vehicle With Id: " + id);
                    throw new NotFound("vehicle with id: " + id + " not found");
                });
        if(!username.equals(vehicle.getWork().getUser().getUsername())){
            logger.error("Unauthorized Action Exception Thrown By: " + username + " For Vehicle With Id: " + id);
            throw new UnauthorizedAction("user: " + username + " action not allowed");
        }
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/vehicle/" + id);
        body.put("vehicle", vehicle);
        logger.info(vehicle + " Fetched By: " + username);
        return body;
    }

    //method for fetching a vehicle by work order but checking if it belongs to the current user
    public Map<String, Object> getVehicleByWork(UUID id){
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
        if(work.getVehicle() == null){
            logger.error("Not Found Exception Thrown By: " + username + " For Vehicle With Work Id: " + id);
            throw new NotFound("vehicle for work with id: " + id + " not found");
        }
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/vehicle/work/" + id);
        body.put("vehicle", work.getVehicle());
        logger.info(work.getVehicle() + " Fetched By: " + username);
        return body;
    }

    //method for creating a vehicle but checking if the work order belongs to the current user
    public Map<String, Object> createVehicle(VehicleRequest request){
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
        if(work.getVehicle() != null){
            logger.error("Already Exists Exception Thrown By: " + username + " For Work With Id: " + request.getWork());
            throw new AlreadyExists("work order already has a vehicle");
        }
        Vehicle vehicle = new Vehicle(request.getMake(), request.getModel(), request.getYear(), work);
        vehicleRepository.save(vehicle);
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 201);
        body.put("path", "/api/v1/vehicle");
        body.put("vehicle", vehicle);
        logger.info(vehicle + " Created By: " + username);
        return body;
    }

    //method for updating a vehicle but checking if the work order belongs to the current user
    @Transactional
    public Map<String, Object> updateVehicle(UUID id, VehicleRequest request){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Not Found Exception Thrown By: " + username + " For Vehicle With Id: " + id);
                    throw new NotFound("vehicle with id: " + id + " not found");
                });
        if(!username.equals(vehicle.getWork().getUser().getUsername())){
            logger.error("Unauthorized Exception Thrown By: " + username + " For Vehicle With Id: " + id);
            throw new UnauthorizedAction("user: " + username + " action not allowed");
        }
        Optional.ofNullable(request.getMake())
                .ifPresent(make -> vehicle.setMake(make));
        Optional.ofNullable(request.getModel())
                .ifPresent(model -> vehicle.setModel(model));
        Optional.ofNullable(request.getYear())
                .ifPresent(year -> vehicle.setYear(year));
        vehicle.setUpdatedAt(LocalDateTime.now());
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/vehicle/" + id);
        body.put("vehicle", vehicle);
        logger.info(vehicle + " Updated By: " + username);
        return body;
    }

    //method for deleting a vehicle but checking if the vehicle belongs to the current user
    public Map<String, Object> deleteVehicle(UUID id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Not Found Exception Thrown By: " + username + " For Vehicle With Id: " + id);
                    throw new NotFound("vehicle with id: " + id + " not found");
                });
        if(!username.equals(vehicle.getWork().getUser().getUsername())){
            logger.info("Unauthorized Exception Thrown By: " + username + " For Vehicle With Id: " + id);
            throw new UnauthorizedAction("user: " + username + " action not allowed");
        }
        vehicleRepository.delete(vehicle);
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/vehicle/" + id);
        body.put("message", "vehicle deleted");
        logger.info(vehicle + " Deleted By: " + username);
        return body;
    }
}
