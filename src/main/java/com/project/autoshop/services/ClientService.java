package com.project.autoshop.services;

import com.project.autoshop.exceptions.EmailAlreadyExistsException;
import com.project.autoshop.exceptions.EmptyFieldException;
import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Client;
import com.project.autoshop.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    //method getting a list of all clients
    public List<Client> getClients(){
        return this.clientRepository.findAll();
    }

    //method for getting a single client by id
    public Client getClient(Integer id){
        //validate if user with this id exists
        Optional<Client> client = this.clientRepository.findById(id);
        if(client.isEmpty()){
            //throw not found exception if client doesnt exist
            throw new NotFoundException("client doesnt exist");
        }
        return client.get();
    }

    //methods for creating new clients
    public void createClient(Client newClient){
        //validation for client first, last and email
        if(newClient.getFirst() != null || newClient.getFirst().length() == 0){
            //throw empty field exception if first name is null or lest than 1 char
            throw new EmptyFieldException("client first name is required");
        }
        if(newClient.getLast() != null || newClient.getLast().length() == 0){
            //throw empty field exception if last name is null or lest than 1 char
            throw new EmptyFieldException("client last name is required");
        }
        if(newClient.getEmail() != null || newClient.getEmail().length() == 0){
            //throw empty field exception if email is null or lest than 1 char
            throw new EmptyFieldException("client email is required");
        }
        //TODO validate email
        //check if client with the same email exists
        if(this.clientRepository.findClientByEmail(newClient.getEmail()).isPresent()){
            //throw email exists exception if email is already taken
            throw new EmailAlreadyExistsException("client with email: "+ newClient.getEmail() +" already exists");
        }
        //save client to database after validation
        this.clientRepository.save(newClient);
    }

    @Transactional
    //method for updating client
    public void updateClient(Integer id, String first, String last, String email){
         Client client = this.clientRepository.findById(id)
                 .orElseThrow(() -> new NotFoundException("client with id: " + id + " doesnt exist"));
         //validate first name before saving to database
         if(first != null && first.length() > 0 && !Objects.equals(first, client.getFirst())){
             client.setFirst(first);
         }
         //validate last name before saving to database
         if(last != null && last.length() > 0 && !Objects.equals(last, client.getLast())) {
             client.setLast(last);
         }
         //validate email before saving to database
         if(email != null && email.length() > 0 && !Objects.equals(email, client.getEmail())){
              if(this.clientRepository.findClientByEmail(email).isPresent()){
                  throw new EmailAlreadyExistsException("client with email: "+ email +" already exists");
              }
              //TODO validate email
             client.setEmail(email);
         }
    }

    //method for deleting clients
    public void deleteClient(Integer id){
        //validate if user with this id exists
        Optional<Client> client = this.clientRepository.findById(id);
        if(client.isEmpty()){
            //throw not found exception if client doesnt exist
            throw new NotFoundException("client doesnt exist");
        }
        //delete the client
        this.clientRepository.deleteById(id);
    }
}
