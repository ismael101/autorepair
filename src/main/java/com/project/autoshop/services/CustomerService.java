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

    //method getting a list of all customers
    public List<Customer> getCustomers(){
        return this.customerRepository.findAll();
    }

    //method for getting a single customer by id
    public Customer getCustomer(Integer id){
        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("customer with id: " + id + " not found"));
        return customer;
    }

    //method for getting customer by job
    public Customer getJobCustomer(Integer id){
        Customer customer = this.customerRepository.findCustomerByJob(id)
                .orElseThrow(() -> new NotFoundException("customer with job id: " + id + " not found"));
        return customer;
    }

    //methods for creating new customers
    public Customer createCustomer(CustomerRequest request){
        Job job = this.jobRepository.findById(request.getJob())
                .orElseThrow(() -> new NotFoundException("job with id: " + request.getJob() + " not found"));
        Customer customer = Customer.builder()
                .first(request.getFirst())
                .last(request.getLast())
                .email(request.getEmail())
                .phone(request.getPhone())
                .job(job)
                .build();
        customerRepository.save(customer);
        return customer;
    }

    @Transactional
    //method for updating customer
    public Customer updateCustomer(Integer id, CustomerRequest request){
        Customer customer = this.customerRepository.findById(id).orElseThrow(() -> new NotFoundException("customer with id: " + id + " not found"));
        Optional.ofNullable(request.getEmail())
                .ifPresent(email -> customer.setEmail(email));
        Optional.ofNullable(request.getFirst())
                .ifPresent(first -> customer.setFirst(first) );
        Optional.ofNullable(request.getLast())
                .ifPresent(last -> customer.setLast(last));
        Optional.ofNullable(request.getPhone())
                .ifPresent(phone -> customer.setPhone(phone));
        return customer;
    }

    //method for deleting customers
    public void deleteCustomer(Integer id){
        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("customer with id: " + id + " not found"));
        this.customerRepository.delete(customer);
    }
}
