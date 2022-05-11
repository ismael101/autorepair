package com.project.autoshop.controllers;

import com.project.autoshop.request.CustomerRequest;
import com.project.autoshop.request.Create;
import com.project.autoshop.request.Update;
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

    //endpoint for fetching all clients
    @GetMapping
    public ResponseEntity getClients(){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getClients());
    }

    //endpoint for fetching client by id
    @GetMapping(path = "{id}")
    public ResponseEntity getClient(@PathVariable("id") Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getClient(id));
    }

    //endpoint for creating client
    @PostMapping
    public ResponseEntity createClient(@RequestBody @Validated(Create.class) CustomerRequest customerRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createClient(customerRequest));
    }

    //endpoint for updating client
    @PutMapping(path = "{id}")
    public ResponseEntity updateClient(@PathVariable("id") Integer id, @RequestBody @Validated(Update.class) CustomerRequest customerRequest){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateClient(id, customerRequest));
    }

    //endpoint for deleting client
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteClient(@PathVariable("id") Integer id){
        customerService.deleteClient(id);
        return ResponseEntity.status(HttpStatus.OK).body("client deleted");
    }
}
