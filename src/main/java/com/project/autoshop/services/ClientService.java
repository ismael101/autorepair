package com.project.autoshop.services;

import com.project.autoshop.exceptions.EmailAlreadyExistsException;
import com.project.autoshop.exceptions.BadRequestException;
import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Client;
import com.project.autoshop.repositories.ClientRepository;
import com.project.autoshop.request.ClientRequest;
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
    public Client createClient(ClientRequest clientRequest){
        Set<ConstraintViolation<ClientRequest>> violations = validator.validate(clientRequest);
        if (!violations.isEmpty()) {
            StringJoiner sb = new StringJoiner(", ");
            for (ConstraintViolation<ClientRequest> violation : violations) {
                sb.add(violation.getMessage());
            }
            throw new BadRequestException("Error occurred: " + sb.toString());
        }
        if(!this.clientRepository.findClientByEmail(clientRequest.getEmail()).isEmpty()){
            throw new EmailAlreadyExistsException("email: " + clientRequest.getEmail() + " already exists for other client");
        }
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
                .ifPresent(email -> {
                    if(this.clientRepository.findClientByEmail(email).isPresent()){
                        throw new EmailAlreadyExistsException("email: " + email + "already exist for other client");
                    }
                    update.setEmail(email);
                });
        Optional.ofNullable(clientRequest.getFirst())
                .filter(first -> first != null && first.length() > 0 && first != update.getFirst())
                .ifPresent(first -> update.setFirst(first) );

        Optional.ofNullable(clientRequest.getLast())
                .filter(last -> last != null && last.length() > 0 && last != update.getLast())
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
