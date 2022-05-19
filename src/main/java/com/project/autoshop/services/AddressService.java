package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Address;
import com.project.autoshop.models.Job;
import com.project.autoshop.repositories.AddressRepository;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.request.AddressRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final JobRepository jobRepository;

    public List<Address> getAddresses(){
        return addressRepository.findAll();
    }

    public Address getAddress(Integer id){
        return addressRepository.findById(id).
                orElseThrow(() -> new NotFoundException("address with id: " + id + " not found"));
    }
    public Address getJobAddress(Integer id){
        return addressRepository.findAddressByJob(id).
                orElseThrow(() -> new NotFoundException("address with job id: " + id + " not found"));
    }

    public Address createAddress(AddressRequest request){
        Job job = jobRepository.findById(request.getJob())
                .orElseThrow(() -> new NotFoundException("job with id: " + request.getJob() + " not found"));
        Address address = Address.builder()
                .street(request.getStreet())
                .city(request.getCity())
                .state(request.getState())
                .zipcode(request.getZipcode())
                .job(job)
                .build();
        addressRepository.save(address);
        return address;
    }

    @Transactional
    public Address updateAddress(Integer id, AddressRequest request){
        Address address = addressRepository.findById(id).
                orElseThrow(() -> new NotFoundException("address with id: " + id + " not found"));
        Optional.ofNullable(request.getStreet())
                .ifPresent(street -> address.setStreet(street));
        Optional.ofNullable(request.getCity())
                .ifPresent(city -> address.setCity(city));
        Optional.ofNullable(request.getState())
                .ifPresent(state -> address.setState(state));
        Optional.ofNullable(request.getZipcode())
                .ifPresent(zipcode -> address.setZipcode(zipcode));
        return address;
    }

    public void deleteAddress(Integer id){
        Address address = addressRepository.findById(id).
                orElseThrow(() -> new NotFoundException("address with id: " + id + " not found"));
        addressRepository.delete(address);
    }
}
