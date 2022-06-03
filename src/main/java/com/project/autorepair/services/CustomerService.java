package com.project.autorepair.services;

import com.project.autorepair.exceptions.NotFoundException;
import com.project.autorepair.models.Customer;
import com.project.autorepair.models.Job;
import com.project.autorepair.repositories.CustomerRepository;
import com.project.autorepair.repositories.JobRepository;
import com.project.autorepair.request.CustomerRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

//service containing business logic for customer
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final JobRepository jobRepository;
    private Logger logger = LoggerFactory.getLogger(CustomerService.class);

    //method that fetches all customers
    public List<Customer> getCustomers(){
        logger.info("all customers fetched");
        return this.customerRepository.findAll();
    }

    //method that fetches customer by id
    public Customer getCustomer(Integer id){
        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by customer with id: " + id);
                    throw new NotFoundException("customer with id: " + id + " not found");
                });
        logger.info(customer.toString() + " fetched");
        return customer;
    }

    //method that fetches customer by job id
    public Customer getJobCustomer(Integer id){
        Customer customer = this.customerRepository.findCustomerByJob(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by customer with job id: " + id);
                    throw new NotFoundException("customer with job id: " + id + " not found");
                });
        logger.info(customer.toString() + " fetched");
        return customer;
    }

    //method that creates a new customer
    public Customer createCustomer(CustomerRequest request){
        Job job = this.jobRepository.findById(request.getJob())
                .orElseThrow(() -> {
                    logger.error("not found exception thrown for job with id: " + request.getJob());
                    throw new NotFoundException("job with id: " + request.getJob() + " not found");
                });
        Customer customer = Customer.builder()
                .first(request.getFirst())
                .last(request.getLast())
                .email(request.getEmail())
                .phone(request.getPhone())
                .job(job)
                .build();
        customerRepository.save(customer);
        logger.info(customer.toString() + " created");
        return customer;
    }

    @Transactional
    //method that updates a customer
    public Customer updateCustomer(Integer id, CustomerRequest request){
        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by customer with id: " + id);
                    throw new NotFoundException("customer with id: " + id + " not found");
                });
        Optional.ofNullable(request.getEmail())
                .ifPresent(email -> customer.setEmail(email));
        Optional.ofNullable(request.getFirst())
                .ifPresent(first -> customer.setFirst(first) );
        Optional.ofNullable(request.getLast())
                .ifPresent(last -> customer.setLast(last));
        Optional.ofNullable(request.getPhone())
                .ifPresent(phone -> customer.setPhone(phone));
        logger.info(customer.toString() + " updated");
        return customer;
    }

    //method for deleting customers
    public void deleteCustomer(Integer id){
        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by customer with id: " + id);
                    throw new NotFoundException("customer with id: " + id + " not found");
                });
        logger.info(customer.toString() + " deleted");
        this.customerRepository.delete(customer);
    }
}
