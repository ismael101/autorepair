package com.project.autorepair.controllers;

import com.project.autorepair.request.CustomerRequest;
import com.project.autorepair.request.groups.Create;
import com.project.autorepair.request.groups.Update;
import com.project.autorepair.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(path = "api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private Logger logger = LoggerFactory.getLogger(CustomerController.class);

    //endpoint for fetching all customers
    @GetMapping
    public ResponseEntity getCustomers(){
        logger.info("get request for for all customers" );
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomers());
    }

    //endpoint for fetching customer by id
    @GetMapping(path = "{id}")
    public ResponseEntity getCustomer(@PathVariable("id") Integer id){
        logger.info("get request for customer with id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomer(id));
    }

    //endpoint for fetching address by id
    @GetMapping(path = "/job/{id}")
    public ResponseEntity getJobCustomer(@PathVariable("id") Integer id){
        logger.info("get request for customer with job id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getJobCustomer(id));
    }

    //endpoint for creating customer
    @PostMapping
    public ResponseEntity createCustomer(@RequestBody @Validated(Create.class) CustomerRequest request){
        logger.info("post request for " + request.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(request));
    }

    //endpoint for updating customer
    @PutMapping(path = "{id}")
    public ResponseEntity updateCustomer(@PathVariable("id") Integer id, @RequestBody @Validated(Update.class) CustomerRequest request){
        logger.info("put request for customer with id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateCustomer(id, request));
    }

    //endpoint for deleting customer
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteCustomer(@PathVariable("id") Integer id){
        logger.info("delete request for customer with id: " + id);
        customerService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.OK).body("client deleted");
    }
}
