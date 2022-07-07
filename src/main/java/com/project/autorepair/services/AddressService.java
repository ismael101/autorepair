package com.project.autorepair.services;

import com.project.autorepair.exceptions.NotFoundException;
import com.project.autorepair.models.Address;
import com.project.autorepair.models.Job;
import com.project.autorepair.repositories.AddressRepository;
import com.project.autorepair.repositories.JobRepository;
import com.project.autorepair.request.AddressRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//service containing business logic for addresses
@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private Logger logger = LoggerFactory.getLogger(AddressService.class);
    private final JobRepository jobRepository;

    //method that fetches all addresses
    public List<Address> getAddresses(){
        logger.info("all addresses fetched");
        return addressRepository.findAll();
    }

    //method that fetches address by id
    public Address getAddress(Integer id){
        Address address = addressRepository.findById(id).
                orElseThrow(() -> {
                    logger.error("not found exception caused by address with id: " + id);
                    throw new NotFoundException("address with id: " + id + " not found");
                });
        logger.info(address.toString() + " fetched");
        return address;
    }
    //method that fetched address by job
    public Address getJobAddress(Integer id){
        Address address = addressRepository.findAddressByJob(id).
                orElseThrow(() -> {
                    logger.error("not found exception caused by address with job id: " + id);
                    throw new NotFoundException("address with job id: " + id + " not found");
                });
        logger.info(address.toString() + " fetched");
        return address;
    }

    //method that creates a new address
    public Address createAddress(AddressRequest request){
        Job job = jobRepository.findById(request.getJob())
                .orElseThrow(() -> {
                    logger.error("not found exception caused by job with id: " + request.getJob());
                    throw new NotFoundException("job with id: " + request.getJob() + " not found");
                });
        Address address = Address.builder()
                .street(request.getStreet())
                .city(request.getCity())
                .state(request.getState())
                .zipcode(request.getZipcode())
                .job(job)
                .build();
        addressRepository.save(address);
        logger.info(address.toString() + " created");
        return address;
    }

    //method that updates an address
    @Transactional
    public Address updateAddress(Integer id, AddressRequest request){
        Address address = addressRepository.findById(id).
                orElseThrow(() -> {
                    logger.error("not found exception caused by address with id: " + id);
                    throw new NotFoundException("address with id: " + id + " not found");
                });
        Optional.ofNullable(request.getStreet())
                .ifPresent(street -> address.setStreet(street));
        Optional.ofNullable(request.getCity())
                .ifPresent(city -> address.setCity(city));
        Optional.ofNullable(request.getState())
                .ifPresent(state -> address.setState(state));
        Optional.ofNullable(request.getZipcode())
                .ifPresent(zipcode -> address.setZipcode(zipcode));
        logger.info(address.toString() + " updated");
        return address;
    }

    //method that deletes an address
    public void deleteAddress(Integer id){
        Address address = addressRepository.findById(id).
                orElseThrow(() -> {
                    logger.error("not found exception caused by address with id: " + id);
                    throw new NotFoundException("address with id: " + id + " not found");
                });
        //logger.info(address.toString() + " deleted");
        addressRepository.delete(address);
    }
}
