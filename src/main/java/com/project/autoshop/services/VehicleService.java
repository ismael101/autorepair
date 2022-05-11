package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Job;
import com.project.autoshop.models.Vehicle;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.repositories.VehicleRepository;
import com.project.autoshop.request.VehicleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final JobRepository jobRepository;

    public List<Vehicle> getVehicles(){
        return this.vehicleRepository.findAll();
    }

    public Vehicle getVehicle(Integer id){
        return this.vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("vehicle with id: " + id + " not found"));
    }

    public Vehicle getJobVehicle(Integer id){
        return this.vehicleRepository.findVehicleByJob(id)
                .orElseThrow(() -> new NotFoundException("vehicle with id: " + id + " not found"));
    }

    public Vehicle createVehicle(VehicleRequest request){
        Job job = jobRepository.findById(request.getJob())
                .orElseThrow(() -> new NotFoundException("job with id: " + request.getJob() + " not found"));
        Vehicle vehicle = Vehicle.builder()
                .make(request.getMake())
                .model(request.getModel())
                .year(request.getYear())
                .job(job)
                .build();
        return vehicle;
    }

    @Transactional
    public Vehicle updateVehicle(Integer id, VehicleRequest request){
        Vehicle vehicle = vehicleRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("vehicle with id: " + id + " not found"));
        Optional.ofNullable(request.getMake())
                .ifPresent(make -> vehicle.setMake(make));
        Optional.ofNullable(request.getModel())
                .ifPresent(model -> vehicle.setModel(model));
        Optional.ofNullable(request.getYear())
                .ifPresent(year -> request.setYear(year));
        return vehicle;
    }

    public void deleteVehicle(Integer id){
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("vehicle with id: " + id + " not found"));
        vehicleRepository.delete(vehicle);
    }
}
