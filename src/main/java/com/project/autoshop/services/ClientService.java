package com.project.autoshop.services;

import com.project.autoshop.exceptions.EmailAlreadyExistsException;
import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Client;
import com.project.autoshop.repositories.ClientRepository;
import com.project.autoshop.request.ClientRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    //method getting a list of all clients
    public List<Client> getClients(){
        return this.clientRepository.findAll();
    }

    //method for getting a single client by id
    public Client getClient(Integer id){
        Client client = this.clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("client with id: " + id + " not found"));
        return client;
    }

    //methods for creating new clients
    public Client createClient(ClientRequest clientRequest){
        this.clientRepository.findClientByEmail(clientRequest.getEmail())
                .orElseThrow(() -> new EmailAlreadyExistsException("email: " + clientRequest.getEmail() + " already exists for other client"));
        Client client = Client.builder()
                .first(clientRequest.getFirst())
                .last(clientRequest.getLast())
                .email(clientRequest.getEmail())
                .build();
        clientRepository.save(client);
        return client;
    }

    @Transactional
    //method for updating client
    public Client updateClient(Integer id, ClientRequest clientRequest){
        Client update = this.clientRepository.findById(id).orElseThrow(() -> new NotFoundException("work with id: " + id + " not found"));
        Optional.ofNullable(clientRequest.getEmail())
                .ifPresent(email -> update.setEmail(email));
        Optional.ofNullable(clientRequest.getFirst())
                .ifPresent(first -> update.setFirst(first) );
        Optional.ofNullable(clientRequest.getLast())
                .ifPresent(last -> update.setLast(last));
        return update;
    }

    //method for deleting clients
    public void deleteClient(Integer id){
        this.clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("client with id: " + id + "not found"));
        this.clientRepository.deleteById(id);
    }
}
