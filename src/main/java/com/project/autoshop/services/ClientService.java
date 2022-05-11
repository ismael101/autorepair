package com.project.autoshop.services;

import com.project.autoshop.exceptions.EmailAlreadyExistsException;
import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Customer;
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
    public List<Customer> getClients(){
        return this.clientRepository.findAll();
    }

    //method for getting a single client by id
    public Customer getClient(Integer id){
        Customer customer = this.clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("client with id: " + id + " not found"));
        return customer;
    }

    //methods for creating new clients
    public Customer createClient(ClientRequest clientRequest){
        this.clientRepository.findClientByEmail(clientRequest.getEmail())
                .orElseThrow(() -> new EmailAlreadyExistsException("email: " + clientRequest.getEmail() + " already exists for other client"));
        Customer customer = Customer.builder()
                .first(clientRequest.getFirst())
                .last(clientRequest.getLast())
                .email(clientRequest.getEmail())
                .build();
        clientRepository.save(customer);
        return customer;
    }

    @Transactional
    //method for updating client
    public Customer updateClient(Integer id, ClientRequest clientRequest){
        Customer update = this.clientRepository.findById(id).orElseThrow(() -> new NotFoundException("work with id: " + id + " not found"));
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
