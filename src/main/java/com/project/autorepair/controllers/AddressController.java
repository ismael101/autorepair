package com.project.autorepair.controllers;

import com.project.autorepair.request.AddressRequest;
import com.project.autorepair.request.groups.Create;
import com.project.autorepair.request.groups.Update;
import com.project.autorepair.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;
    private Logger logger = LoggerFactory.getLogger(AddressController.class);

    //endpoint for fetching all addresses
    @GetMapping
    public ResponseEntity getAddresses(){
        logger.info("get request for all addresses");
        return ResponseEntity.status(HttpStatus.OK).body(addressService.getAddresses());
    }

    //endpoint for fetching address by id
    @GetMapping(path = "{id}")
    public ResponseEntity getAddress(@PathVariable("id") Integer id){
        logger.info("get request for address with id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(addressService.getAddress(id));
    }

    //endpoint for fetching address by id
    @GetMapping(path = "/job/{id}")
    public ResponseEntity getJobAddress(@PathVariable("id") Integer id){
        logger.info("get request for address with job id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(addressService.getJobAddress(id));
    }

    //endpoint for creating address
    @PostMapping
    public ResponseEntity createAddress(@RequestBody @Validated(Create.class) AddressRequest request){
        logger.info("post request for " + request.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.createAddress(request));
    }

    //endpoint for updating address
    @PutMapping(path = "{id}")
    public ResponseEntity updateAddress(@PathVariable("id") Integer id, @RequestBody @Validated(Update.class) AddressRequest request){
        logger.info("put request for address with id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(addressService.updateAddress(id, request));
    }

    //endpoint for deleting address
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteAddress(@PathVariable("id") Integer id){
        logger.info("delete request for address with id: " + id);
        addressService.deleteAddress(id);
        return ResponseEntity.status(HttpStatus.OK).body("address deleted");
    }
}
