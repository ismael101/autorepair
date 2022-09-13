package com.ismael.autorepair.services;

import com.ismael.autorepair.exceptions.AlreadyExists;
import com.ismael.autorepair.exceptions.NotFound;
import com.ismael.autorepair.exceptions.UnauthorizedAction;
import com.ismael.autorepair.models.Customer;
import com.ismael.autorepair.models.User;
import com.ismael.autorepair.models.Work;
import com.ismael.autorepair.repositories.CustomerRepository;
import com.ismael.autorepair.repositories.UserRepository;
import com.ismael.autorepair.repositories.WorkRepository;
import com.ismael.autorepair.requests.CustomerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

//service for managing customers
@Service
public class CustomerService {
    private CustomerRepository customerRepository;
    private WorkRepository workRepository;
    private UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public CustomerService(CustomerRepository customerRepository, WorkRepository workRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.workRepository = workRepository;
        this.userRepository = userRepository;
    }

    //method for fetching all customers associated with the current user
    public Map<String, Object> getCustomers(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByUsername(username).get();
        List<Work> works = workRepository.findWorkByUser(user);
        List<Customer> customers = new ArrayList<>();
        for(Work work : works){
            if(work.getCustomer() != null){
                customers.add(work.getCustomer());
            }
        }
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/customer");
        body.put("customers", customers);
        logger.info(customers + " Fetched By: " + username);
        return body;
    }

    //method for fetching customer by id but checking if it belongs to the current user
    public Map<String, Object> getCustomerById(UUID id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Not Found Exception Thrown By: " + username + " For Customer With Id: " + id);
                    throw new NotFound("customer with id: " + id + " not found");
                });
        if(!username.equals(customer.getWork().getUser().getUsername())){
            logger.error("Unauthorized Action Exception Thrown By: " + username + " For Customer With Id: " + id);
            throw new UnauthorizedAction("user: " + username + " action not allowed");
        }
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/customer/" + id);
        body.put("customer", customer);
        logger.info(customer + " Fetched By: " + username);
        return body;
    }

    //method for fetching customer by wor but checking if it belongs to the current user
    public Map<String, Object> getCustomerByWork(UUID id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Work work = workRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Not Found Exception Thrown By: " + username + " For Work With Id: " + id);
                    throw new NotFound("work with id: " + id + " not found");
                });
        if(!username.equals(work.getUser().getUsername())){
            logger.error("Unauthorized Action Exception Thrown By: " + username + " For Work With Id: " + id);
            throw new UnauthorizedAction("user: " + username + " action not allowed");
        }
        if(work.getCustomer() == null){
            logger.error("Not Found Exception Thrown By: " + username + " For Customer With Work Id: " + id);
            throw new NotFound("customer for work with id: " + id + " not found");
        }
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/customer/work/" + id);
        body.put("customer", work.getCustomer());
        logger.info(work.getCustomer() + " Fetched By: " + username);
        return body;
    }

    //method for creating customer but checking if the work order belongs to the current user
    public Map<String, Object> createCustomer(CustomerRequest request){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Work work = workRepository.findById(UUID.fromString(request.getWork()))
                .orElseThrow(() -> {
                    logger.error("Not Found Exception Thrown By: " + username + " For Work With Id: " + request.getWork());
                    throw new NotFound("work with id: " + request.getWork() + " not found");
                });
        if(!username.equals(work.getUser().getUsername())){
            logger.error("Unauthorized Action Exception Thrown By: " + username + " For Work With Id: " + request.getWork());
            throw new UnauthorizedAction("user: " + username + " action not allowed");
        }
        if(work.getCustomer() != null){
            logger.error("Already Exists Exception Thrown By: " + username + " For Work With Id: " + request.getWork());
            throw new AlreadyExists("work order already has a customer");
        }
        Customer customer = new Customer(request.getFirst(), request.getLast(), request.getEmail(), request.getPhone(), work);
        customerRepository.save(customer);
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 201);
        body.put("path", "/api/v1/customer");
        body.put("customer", customer);
        logger.info(customer + " Created By: " + username);
        return body;
    }


    //method for updating customer by id but checking if it belongs to the current user
    @Transactional
    public Map<String, Object> updateCustomer(UUID id, CustomerRequest request){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Not Found Exception Thrown By: " + username + " For Customer With Id: " + id);
                    throw new NotFound("customer with id: " + id + " not found");
                });
        if(!username.equals(customer.getWork().getUser().getUsername())){
            logger.error("Unauthorized Exception Thrown By: " + username + " For Customer With Id: " + id);
            throw new UnauthorizedAction("user: " + username + " action not allowed");
        }
        Optional.ofNullable(request.getFirst())
                .ifPresent(first -> customer.setFirst(first));
        Optional.ofNullable(request.getLast())
                .ifPresent(last -> customer.setLast(last));
        Optional.ofNullable(request.getEmail())
                .ifPresent(email -> customer.setEmail(email));
        Optional.ofNullable(request.getPhone())
                .ifPresent(phone -> customer.setPhone(phone));
        customer.setUpdatedAt(LocalDateTime.now());
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/customer/" + id);
        body.put("customer", customer);
        logger.info(customer + " Updated By: " + username);
        return body;
    }

    //method for deleting customer but checking if it belongs to the current user
    public Map<String, Object> deleteCustomer(UUID id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Not Found Exception Thrown By: " + username + " For Customer With Id: " + id);
                    throw new NotFound("customer with id: " + id + " not found");
                });
        if(!username.equals(customer.getWork().getUser().getUsername())){
            logger.info("Unauthorized Exception Thrown By: " + username + " For Customer With Id: " + id);
            throw new UnauthorizedAction("user: " + username + " action not allowed");
        }
        customerRepository.delete(customer);
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 200);
        body.put("path", "/api/v1/customer/" + id);
        body.put("message", "customer deleted");
        logger.info(customer + " Deleted By: " + username);
        return body;
    }

}
