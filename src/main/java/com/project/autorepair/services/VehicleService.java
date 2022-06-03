package com.project.autorepair.services;

import com.project.autorepair.exceptions.NotFoundException;
import com.project.autorepair.models.Job;
import com.project.autorepair.models.Vehicle;
import com.project.autorepair.repositories.JobRepository;
import com.project.autorepair.repositories.VehicleRepository;
import com.project.autorepair.request.VehicleRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


//service containing business logic for vehicle
@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final JobRepository jobRepository;
    private Logger logger = LoggerFactory.getLogger(VehicleService.class);

    //method for fetching all vehicles
    public List<Vehicle> getVehicles(){
        logger.info("all vehicles fetched");
        return this.vehicleRepository.findAll();
    }

    //method for fetching vehicle by id
    public Vehicle getVehicle(Integer id){
        Vehicle vehicle = this.vehicleRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by vehicle with id: " + id);
                    throw new NotFoundException("vehicle with id: " + id + " not found");
                });
        logger.info(vehicle.toString() + " fetched");
        return vehicle;
    }

    //method for getting vehicle by job id
    public Vehicle getJobVehicle(Integer id){
        Vehicle vehicle =  this.vehicleRepository.findVehicleByJob(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by vehicle with job id: " + id);
                    throw new NotFoundException("vehicle with job id: " + id + " not found");
                });
        logger.info(vehicle.toString() + " fetched");
        return vehicle;
    }

    //method for creating a vehicle
    public Vehicle createVehicle(VehicleRequest request){
        Job job = jobRepository.findById(request.getJob())
                .orElseThrow(() -> {
                    logger.error("not found exception caused job with id: " + request.getJob());
                    throw new NotFoundException("job with id: " + request.getJob() + " not found");
                });
        Vehicle vehicle = Vehicle.builder()
                .make(request.getMake())
                .model(request.getModel())
                .year(request.getYear())
                .vin(request.getVin())
                .job(job)
                .build();
        vehicleRepository.save(vehicle);
        logger.info(vehicle.toString() + " created");
        return vehicle;
    }

    //method for updating a vehicle
    @Transactional
    public Vehicle updateVehicle(Integer id, VehicleRequest request){
        Vehicle vehicle = vehicleRepository.findById(id)
                        .orElseThrow(() -> {
                            logger.error("not found exception caused by vehicle with id: " + id);
                            throw  new NotFoundException("vehicle with id: " + id + " not found");
                        });
        Optional.ofNullable(request.getMake())
                .ifPresent(make -> vehicle.setMake(make));
        Optional.ofNullable(request.getModel())
                .ifPresent(model -> vehicle.setModel(model));
        Optional.ofNullable(request.getYear())
                .ifPresent(year -> vehicle.setYear(year));
        Optional.ofNullable(request.getVin())
                .ifPresent(vin -> vehicle.setVin(vin));
        logger.info(vehicle.toString() + " updated");
        return vehicle;
    }

    //method for deleting a vehicle
    public void deleteVehicle(Integer id){
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by vehicle with id: " + id);
                    throw new NotFoundException("vehicle with id: " + id + " not found");
                });
        logger.info(vehicle.toString() + " deleted");
        vehicleRepository.delete(vehicle);
    }
}
