package com.project.autoshop.controllers;

import com.project.autoshop.models.Client;
import com.project.autoshop.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/clients")
public class ClientController {
    private final ClientService clientService;

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
    @GetMapping(path = "{id}")
    public ResponseEntity getClient(@PathVariable("id") Integer id){
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
    @PutMapping(path = "{id}")
    //optional to pass in client information
    public ResponseEntity updateClient(@PathVariable("id") Integer id,
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
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteClient(@PathVariable("id") Integer id){
        //deletes client
        clientService.deleteClient(id);
        //return json body with status code and message
        return ResponseEntity.status(HttpStatus.OK).body("client deleted");
    }
}
