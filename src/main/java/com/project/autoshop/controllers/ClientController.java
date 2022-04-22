package com.project.autoshop.controllers;

import com.project.autoshop.models.Client;
import com.project.autoshop.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    //endpoint for fetching all the clients in the database
    @GetMapping
    public ResponseEntity getClients(){
        //return json body with status code and list of all clients
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getClients());
    }

    //endpoint for fetching a client by id
    @GetMapping(path = "{clientId}")
    public ResponseEntity getClient(@PathVariable("clientId") Integer id){
        //return json body with status code and client
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getClient(id));
    }

    //endpoint for creating a new client
    @PostMapping()
    public ResponseEntity createClient(@RequestBody Client client){
        //creates client
        clientService.createClient(client);
        //return json body with status code and message
        return ResponseEntity.status(HttpStatus.CREATED).body("client created");
    }

    //endpoint for updating client information
    @PutMapping(path = "{clientId}")
    //optional to pass in client information
    public ResponseEntity updateClient(@PathVariable("clientId") Integer id,
                                       @RequestParam(required = false) String first,
                                       @RequestParam(required = false) String last,
                                       @RequestParam(required = false) String email
                                       ){
        //updates client
        clientService.updateClient(id, first, last, email);
        //return json body with status code and message
        return ResponseEntity.status(HttpStatus.OK).body("client updated");
    }

    //endpoint for deleting client
    @DeleteMapping(path = "{clientId}")
    public ResponseEntity deleteClient(@PathVariable("clientId") Integer id){
        //deletes client
        clientService.deleteClient(id);
        //return json body with status code and message
        return ResponseEntity.status(HttpStatus.OK).body("client deleted");
    }
}
