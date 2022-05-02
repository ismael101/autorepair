package com.project.autoshop.services;

import com.project.autoshop.exceptions.EmailAlreadyExistsException;
import com.project.autoshop.exceptions.BadRequestException;
import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Client;
import com.project.autoshop.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    @Autowired
    private Validator validator;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    //method getting a list of all clients
    public List<Client> getClients(){
        return this.clientRepository.findAll();
    }

    //method for getting a single client by id
    public Client getClient(Integer id){
        Optional<Client> client = this.clientRepository.findById(id);
        if(client.isEmpty()){
            throw new NotFoundException("client doesn't exist");
        }
        return client.get();
    }

    //methods for creating new clients
    public Client createClient(Client client){
        Set<ConstraintViolation<Client>> violations = validator.validate(client);
        if (!violations.isEmpty()) {
            StringJoiner sb = new StringJoiner(" ");
            for (ConstraintViolation<Client> violation : violations) {
                sb.add(violation.getMessage());
            }
            throw new BadRequestException("Error occurred: " + sb.toString());
        }
        if(!this.clientRepository.findClientByEmail(client.getEmail()).isEmpty()){
            throw new EmailAlreadyExistsException("email: " + client.getEmail() + " already exists for other client");
        }
        clientRepository.save(client);
        return client;
    }

    @Transactional
    //method for updating client
    public Client updateClient(Integer id, Client update){
        Client client = this.clientRepository.findById(id).orElseThrow(() -> new NotFoundException("work with id: " + id + " not found"));
        Optional.ofNullable(update.getEmail())
                .ifPresent(email -> {
                    if(this.clientRepository.findClientByEmail(email).isPresent()){
                        throw new EmailAlreadyExistsException("email: " + email + "already exist for other client");
                    }
                    client.setEmail(email);
                });
        Optional.ofNullable(update.getFirst())
                .filter(first -> first != null && first.length() > 0 && first != client.getFirst())
                .ifPresent(first -> client.setFirst(first) );

        Optional.ofNullable(update.getLast())
                .filter(last -> last != null && last.length() > 0 && last != client.getLast())
                .ifPresent(last -> client.setLast(last));
        return client;
    }

    //method for deleting clients
    public void deleteClient(Integer id){
        this.clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("client with id: " + id + "not found"));
        this.clientRepository.deleteById(id);
    }
}
