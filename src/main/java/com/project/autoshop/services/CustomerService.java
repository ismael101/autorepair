package com.project.autoshop.services;

import com.project.autoshop.exceptions.EmailAlreadyExistsException;
import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Customer;
import com.project.autoshop.models.Job;
import com.project.autoshop.repositories.CustomerRepository;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.request.CustomerRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final JobRepository jobRepository;

    public CustomerService(CustomerRepository customerRepository, JobRepository jobRepository) {
        this.customerRepository = customerRepository;
        this.jobRepository = jobRepository;
    }

    //method getting a list of all clients
    public List<Customer> getClients(){
        return this.customerRepository.findAll();
    }

    //method for getting a single client by id
    public Customer getClient(Integer id){
        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("client with id: " + id + " not found"));
        return customer;
    }

    //methods for creating new clients
    public Customer createClient(CustomerRequest customerRequest){
        Job job = this.jobRepository.findById(customerRequest.getJob())
                .orElseThrow(() -> new NotFoundException("job with id: " + customerRequest.getJob() + " not found"));
        Customer customer = Customer.builder()
                .first(customerRequest.getFirst())
                .last(customerRequest.getLast())
                .email(customerRequest.getEmail())
                .phone(customerRequest.getPhone())
                .job(job)
                .build();
        customerRepository.save(customer);
        return customer;
    }

    @Transactional
    //method for updating client
    public Customer updateClient(Integer id, CustomerRequest customerRequest){
        Customer update = this.customerRepository.findById(id).orElseThrow(() -> new NotFoundException("work with id: " + id + " not found"));
        Optional.ofNullable(customerRequest.getEmail())
                .ifPresent(email -> update.setEmail(email));
        Optional.ofNullable(customerRequest.getFirst())
                .ifPresent(first -> update.setFirst(first) );
        Optional.ofNullable(customerRequest.getLast())
                .ifPresent(last -> update.setLast(last));
        return update;
    }

    //method for deleting clients
    public void deleteClient(Integer id){
        this.customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("client with id: " + id + "not found"));
        this.customerRepository.deleteById(id);
    }
}
