package com.project.autoshop.controllers;

import com.project.autoshop.request.AddressRequest;
import com.project.autoshop.request.groups.Create;
import com.project.autoshop.request.groups.Update;
import com.project.autoshop.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    //endpoint for fetching all addresses
    @GetMapping
    public ResponseEntity getAddresses(){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.getAddresses());
    }

    //endpoint for fetching address by id
    @GetMapping(path = "{id}")
    public ResponseEntity getAddress(@PathVariable("id") Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.getAddress(id));
    }

    //endpoint for fetching address by id
    @GetMapping(path = "/job/{id}")
    public ResponseEntity getJobAddress(@PathVariable("id") Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.getJobAddress(id));
    }

    //endpoint for creating address
    @PostMapping
    public ResponseEntity createAddress(@RequestBody @Validated(Create.class) AddressRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.createAddress(request));
    }

    //endpoint for updating address
    @PutMapping(path = "{id}")
    public ResponseEntity updateAddress(@PathVariable("id") Integer id, @RequestBody @Validated(Update.class) AddressRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.updateAddress(id, request));
    }

    //endpoint for deleting address
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteAddress(@PathVariable("id") Integer id){
        addressService.deleteAddress(id);
        return ResponseEntity.status(HttpStatus.OK).body("address deleted");
    }
}
