package com.ismael.autorepair.controllers;

import com.ismael.autorepair.requests.CustomerRequest;
import com.ismael.autorepair.requests.groups.Create;
import com.ismael.autorepair.requests.groups.Update;
import com.ismael.autorepair.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

//controller for handling customers
@RestController
@RequestMapping(path = "/api/v1/customer")
public class CustomerController {
    private CustomerService customerService;
    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //controller method for fetching all customers associated with current user
    @GetMapping
    public ResponseEntity getCustomers(){
        logger.info("Get Customer Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomers());
    }

    //controller method for fetching a customer by id
    @GetMapping(path = "{id}")
    public ResponseEntity getCustomerById(@PathVariable("id") UUID id){
        logger.info("Get Customer By Id Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerById(id));
    }

    //controller method for fetching customer by work order
    @GetMapping(path = "/work/{id}")
    public ResponseEntity getCustomerByWork(@PathVariable("id") UUID id){
        logger.info("Get Customer By Work Id Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerByWork(id));
    }

    //controller method for creating a customer
    @PostMapping
    public ResponseEntity createCustomer(@RequestBody @Validated(Create.class) CustomerRequest request){
        logger.info("Create Customer Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(request));
    }

    //controller method for updating a customer
    @PutMapping(path = "{id}")
    public ResponseEntity updateCustomer(@PathVariable("id") UUID id, @RequestBody @Validated(Update.class) CustomerRequest request){
        logger.info("Update Customer Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateCustomer(id, request));
    }

    //controller method for deleting a customer
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteCustomer(@PathVariable("id") UUID id){
        logger.info("Delete Customer Method Accessed By: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(customerService.deleteCustomer(id));
    }
}
