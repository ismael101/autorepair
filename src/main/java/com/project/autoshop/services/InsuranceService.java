package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Insurance;
import com.project.autoshop.models.Job;
import com.project.autoshop.repositories.InsuranceRepository;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.request.InsuranceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;
    private final JobRepository jobRepository;

    public List<Insurance> getAllInsurance(){
        return insuranceRepository.findAll();
    }

    public Insurance getJobInsurance(Integer id){
        Insurance insurance = insuranceRepository.findInsuranceByJob(id)
                .orElseThrow(() -> new NotFoundException("insurance with job id " + id + " not found"));
        return insurance;
    }

    public Insurance getInsurance(Integer id){
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("insurance with id " + id + " not found"));
        return insurance;
    }

    public Insurance createInsurance(InsuranceRequest request){
        Job job = jobRepository.findById(request.getJob())
                .orElseThrow(() -> new NotFoundException("job with id: " + request.getJob() + " not found"));
        Insurance insurance = Insurance
                .builder()
                .policy(request.getPolicy())
                .provider(request.getProvider())
                .vin(request.getVin())
                .job(job)
                .build();
        insuranceRepository.save(insurance);
        return insurance;
    }

    @Transactional
    public Insurance updateInsurance(Integer id, InsuranceRequest request){
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("insurance with id " + id + " not found"));
        Optional.ofNullable(request.getPolicy())
                .ifPresent(policy -> insurance.setPolicy(policy));
        Optional.ofNullable(request.getProvider())
                .ifPresent(provider -> insurance.setProvider(provider));
        Optional.ofNullable(request.getVin())
                .ifPresent(vin -> insurance.setVin(vin));
        return insurance;
    }

    public void deleteInsurance(Integer id){
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("insurance with id " + id + " not found"));
        insuranceRepository.delete(insurance);
    }
}
