package com.project.autoshop.controllers;

import com.project.autoshop.models.Client;
import com.project.autoshop.request.ClientRequest;
import com.project.autoshop.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "api/v1/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    //endpoint for fetching all clients
    @GetMapping
    public ResponseEntity getClients(){
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getClients());
    }

    //endpoint for fetching client by id
    @GetMapping(path = "{id}")
    public ResponseEntity getClient(@PathVariable("id") Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getClient(id));
    }

    //endpoint for creating client
    @PostMapping
    public ResponseEntity createClient(@RequestBody @Valid ClientRequest clientRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(clientRequest));
    }

    //endpoint for updating client
    @PutMapping(path = "{id}")
    public ResponseEntity updateClient(@PathVariable("id") Integer id, @Valid @ModelAttribute ClientRequest clientRequest){
        return ResponseEntity.status(HttpStatus.OK).body(clientService.updateClient(id, clientRequest));
    }

    //endpoint for deleting client
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteClient(@PathVariable("id") Integer id){
        clientService.deleteClient(id);
        return ResponseEntity.status(HttpStatus.OK).body("client deleted");
    }
}
