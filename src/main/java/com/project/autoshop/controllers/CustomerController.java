package com.project.autoshop.controllers;

import com.project.autoshop.request.CustomerRequest;
import com.project.autoshop.request.groups.Create;
import com.project.autoshop.request.groups.Update;
import com.project.autoshop.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(path = "api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    //endpoint for fetching all customers
    @GetMapping
    public ResponseEntity getCustomers(){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomers());
    }

    //endpoint for fetching customer by id
    @GetMapping(path = "{id}")
    public ResponseEntity getCustomer(@PathVariable("id") Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomer(id));
    }

    //endpoint for fetching address by id
    @GetMapping(path = "/job/{id}")
    public ResponseEntity getJobCustomer(@PathVariable("id") Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getJobCustomer(id));
    }

    //endpoint for creating customer
    @PostMapping
    public ResponseEntity createCustomer(@RequestBody @Validated(Create.class) CustomerRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(request));
    }

    //endpoint for updating customer
    @PutMapping(path = "{id}")
    public ResponseEntity updateCustomer(@PathVariable("id") Integer id, @RequestBody @Validated(Update.class) CustomerRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateCustomer(id, request));
    }

    //endpoint for deleting customer
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteCustomer(@PathVariable("id") Integer id){
        customerService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.OK).body("client deleted");
    }
}
