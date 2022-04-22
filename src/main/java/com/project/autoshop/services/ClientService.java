package com.project.autoshop.services;

import com.project.autoshop.exceptions.EmailAlreadyExistsException;
import com.project.autoshop.exceptions.EmptyFieldException;
import com.project.autoshop.exceptions.InvalidEmailException;
import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Client;
import com.project.autoshop.repositories.ClientRepository;
import com.project.autoshop.utils.EmailValidator;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final EmailValidator emailValidator;
    Logger logger = LoggerFactory.logger(ClientService.class);

    @Autowired
    public ClientService(ClientRepository clientRepository, EmailValidator emailValidator) {
        this.clientRepository = clientRepository;
        this.emailValidator = emailValidator;
    }

    //method getting a list of all clients
    public List<Client> getClients(){
        logger.info("all clients fetched");
        return this.clientRepository.findAll();
    }

    //method for getting a single client by id
    public Client getClient(Integer id){
        //validate if user with this id exists
        Optional<Client> client = this.clientRepository.findById(id);
        if(client.isEmpty()){
            //throw not found exception if client doesn't exist
            logger.error("not found exception thrown for client with id: " + id);
            throw new NotFoundException("client doesn't exist");
        }
        logger.info("client with id: " + id + " fetched");
        return client.get();
    }

    //methods for creating new clients
    public void createClient(Client newClient){
        //validation for client first, last and email
        if(newClient.getFirst() != null || newClient.getFirst().length() == 0){
            //throw empty field exception if first name is null or lest than 1 char
            logger.error("empty field exception caused by empty empty/null first name");
            throw new EmptyFieldException("client first name is required");
        }
        if(newClient.getLast() != null || newClient.getLast().length() == 0){
            //throw empty field exception if last name is null or lest than 1 char
            logger.error("empty field exception caused by empty empty/null last name");
            throw new EmptyFieldException("client last name is required");
        }
        if(newClient.getEmail() != null || newClient.getEmail().length() == 0){
            //throw empty field exception if email is null or lest than 1 char
            logger.error("empty field exception caused by empty empty/null email");
            throw new EmptyFieldException("client email is required");
        }
        //checks if email is valid
        if(!this.emailValidator.validate(newClient.getEmail())){
            logger.error("invalid email expcetion caused by email: " + newClient.getEmail());
            throw new InvalidEmailException(newClient.getEmail() + " is not valid");
        }
        //check if client with the same email exists
        if(this.clientRepository.findClientByEmail(newClient.getEmail()).isPresent()){
            //throw email exists exception if email is already taken
            logger.error("email already exists exception for email: " + newClient.getEmail());
            throw new EmailAlreadyExistsException("client with email: "+ newClient.getEmail() +" already exists");
        }
        //save client to database after validation
        logger.info("new client created: " + newClient.toString());
        this.clientRepository.save(newClient);
    }

    @Transactional
    //method for updating client
    public void updateClient(Integer id, String first, String last, String email){
        //get client from database or throw not found exception if it doesn't exist
         Client client = this.clientRepository.findById(id)
                 .orElseThrow(() -> new NotFoundException("client with id: " + id + " doesnt exist"));
         //validate first name before saving to database
         if(first != null && first.length() > 0 && !Objects.equals(first, client.getFirst())){
             logger.info("client with id: " + id + " first name updated from: " + client.getFirst() + " to: " + first);
             client.setFirst(first);
         }
         //validate last name before saving to database
         if(last != null && last.length() > 0 && !Objects.equals(last, client.getLast())) {
             logger.info("client with id: " + id + " last name updated from: " + client.getLast() + " to: " + last);
             client.setLast(last);
         }
         //validate email before saving to database
         if(email != null && email.length() > 0 && !Objects.equals(email, client.getEmail())){
             //check if new email is already used
              if(this.clientRepository.findClientByEmail(email).isPresent()) {
                  logger.error("email already exists exception for email: " + email);
                  throw new EmailAlreadyExistsException("client with email: " + email + " already exists");
              }
              //check if email is valid
             if(!this.emailValidator.validate(email)){
                 logger.error("invalid email exception caused by email: " + email);
                 throw new InvalidEmailException(email + " is not valid");
             }
             logger.info("client with id: " + id + " email updated from: " + client.getEmail() + " to: " + email);
             client.setEmail(email);
         }
    }

    //method for deleting clients
    public void deleteClient(Integer id){
        //validate if user with this id exists
        Optional<Client> client = this.clientRepository.findById(id);
        if(client.isEmpty()){
            logger.error("not found exception thrown for client with id: " + id);
            throw new NotFoundException("client doesn't exist");
        }
        //delete the client
        logger.info("client with id: " + id + " deleted");
        this.clientRepository.deleteById(id);
    }
}
