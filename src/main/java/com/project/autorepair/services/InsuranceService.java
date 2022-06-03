package com.project.autorepair.services;

import com.project.autorepair.exceptions.NotFoundException;
import com.project.autorepair.models.Insurance;
import com.project.autorepair.models.Job;
import com.project.autorepair.repositories.InsuranceRepository;
import com.project.autorepair.repositories.JobRepository;
import com.project.autorepair.request.InsuranceRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;
    private final JobRepository jobRepository;
    private Logger logger = LoggerFactory.getLogger(InsuranceService.class);

    //method for fetching all insurance
    public List<Insurance> getInsurances(){
        logger.info("all insurance fetched");
        return insuranceRepository.findAll();
    }

    //method for fetching insurance by job id
    public Insurance getJobInsurance(Integer id){
        Insurance insurance = insuranceRepository.findInsuranceByJob(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by insurance with id: " + id);
                    throw new NotFoundException("insurance with job id: " + id + " not found");
                });
        logger.info(insurance.toString(), " fetched");
        return insurance;
    }

    //method for fetching insurance by id
    public Insurance getInsurance(Integer id){
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by insurance with id: " + id);
                    throw new NotFoundException("insurance with id: " + id + " not found");
                });
        logger.info(insurance.toString() + " fetched");
        return insurance;
    }

    //method for creating a new insurance
    public Insurance createInsurance(InsuranceRequest request){
        Job job = jobRepository.findById(request.getJob())
                .orElseThrow(() -> {
                    logger.error("not found exception caused by job with id: " + request.getJob());
                    throw new NotFoundException("job with id: " + request.getJob() + " not found");
                });
        Insurance insurance = Insurance
                .builder()
                .policy(request.getPolicy())
                .provider(request.getProvider())
                .vin(request.getVin())
                .job(job)
                .build();
        insuranceRepository.save(insurance);
        logger.info(insurance.toString() + " created");
        return insurance;
    }

    //method for updating an insurance
    @Transactional
    public Insurance updateInsurance(Integer id, InsuranceRequest request){
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by insurance with id: " + id);
                    throw new NotFoundException("insurance with id: " + id + " not found");
                });
        Optional.ofNullable(request.getPolicy())
                .ifPresent(policy -> insurance.setPolicy(policy));
        Optional.ofNullable(request.getProvider())
                .ifPresent(provider -> insurance.setProvider(provider));
        Optional.ofNullable(request.getVin())
                .ifPresent(vin -> insurance.setVin(vin));
        logger.info(insurance.toString() + " updated");
        return insurance;
    }

    //method for deleting an insurance
    public void deleteInsurance(Integer id){
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by insurance with id: " + id);
                    throw new NotFoundException("insurance with id: " + id + " not found");
                });
        logger.info(insurance.toString() + " deleted");
        insuranceRepository.delete(insurance);
    }
}
