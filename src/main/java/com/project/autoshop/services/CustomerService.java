package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Customer;
import com.project.autoshop.models.Job;
import com.project.autoshop.repositories.CustomerRepository;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.request.CustomerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final JobRepository jobRepository;

    //method getting a list of all clients
    public List<Customer> getCustomers(){
        return this.customerRepository.findAll();
    }

    //method for getting a single client by id
    public Customer getCustomer(Integer id){
        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("customer with id: " + id + " not found"));
        return customer;
    }

    public Customer getJobCustomer(Integer id){
        Customer customer = this.customerRepository.findCustomerByJob(id)
                .orElseThrow(() -> new NotFoundException("customer with job id: " + id + " not found"));
        return customer;
    }

    //methods for creating new clients
    public Customer createCustomer(CustomerRequest customerRequest){
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
    public Customer updateCustomer(Integer id, CustomerRequest customerRequest){
        Customer update = this.customerRepository.findById(id).orElseThrow(() -> new NotFoundException("customer with id: " + id + " not found"));
        Optional.ofNullable(customerRequest.getEmail())
                .ifPresent(email -> update.setEmail(email));
        Optional.ofNullable(customerRequest.getFirst())
                .ifPresent(first -> update.setFirst(first) );
        Optional.ofNullable(customerRequest.getLast())
                .ifPresent(last -> update.setLast(last));
        Optional.ofNullable(customerRequest.getPhone())
                .ifPresent(phone -> update.setPhone(phone));
        return update;
    }

    //method for deleting clients
    public void deleteCustomer(Integer id){
        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("customer with id: " + id + " not found"));
        this.customerRepository.delete(customer);
    }
}
