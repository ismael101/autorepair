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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final EmailValidator emailValidator = new EmailValidator();
    Logger logger = LoggerFactory.logger(ClientService.class);

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
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
    public Client createClient(Client client){
        //validation for client first, last and email
        if(client.getFirst() == null || client.getFirst().length() == 0){
            //throw empty field exception if first name is null or lest than 1 char
            logger.error("empty field exception caused by empty empty/null first name");
            throw new EmptyFieldException("client first name is required");
        }
        if(client.getLast() == null || client.getLast().length() == 0){
            //throw empty field exception if last name is null or lest than 1 char
            logger.error("empty field exception caused by empty empty/null last name");
            throw new EmptyFieldException("client last name is required");
        }
        if(client.getEmail() == null || client.getEmail().length() == 0){
            //throw empty field exception if email is null or lest than 1 char
            logger.error("empty field exception caused by empty empty/null email");
            throw new EmptyFieldException("client email is required");
        }
        //checks if email is valid
        if(!this.emailValidator.validate(client.getEmail())){
            logger.error("invalid email expcetion caused by email: " + client.getEmail());
            throw new InvalidEmailException(client.getEmail() + " is not valid");
        }
        //check if client with the same email exists
        if(this.clientRepository.findClientByEmail(client.getEmail()).isPresent()){
            //throw email exists exception if email is already taken
            logger.error("email already exists exception for email: " + client.getEmail());
            throw new EmailAlreadyExistsException("client with email: "+ client.getEmail() +" already exists");
        }
        //save client to database after validation
        logger.info("new client created: " + client.toString());
        this.clientRepository.save(client);
        return client;
    }

    @Transactional
    public Client updateClient(Integer id, String first, String last, String email){
        //get client from database or throw not found exception if it doesn't exist
         Client client = this.clientRepository.findById(id)
                 .orElseThrow(() -> new NotFoundException("client with id: " + id + " doesnt exist"));
         //validate first name before saving to database
         if(first != null && first.length() > 0 && !Objects.equals(client.getFirst(), first)){
             logger.info("client with id: " + id + " first name updated from: " + client.getFirst() + " to: " + first);
             client.setFirst(first);
         }
         //validate last name before saving to database
         if(last != null && last.length() > 0 && !Objects.equals(client.getLast(), last)) {
             logger.info("client with id: " + id + " last name updated from: " + client.getLast() + " to: " + last);
             client.setLast(last);
         }
         //validate email before saving to database
         if(email != null && email.length() > 0 && !Objects.equals(client.getEmail(), email)){
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
         clientRepository.save(client);
         return client;
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
