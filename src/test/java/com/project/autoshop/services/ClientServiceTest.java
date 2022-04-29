package com.project.autoshop.services;

import com.project.autoshop.exceptions.EmailAlreadyExistsException;
import com.project.autoshop.exceptions.EmptyFieldException;
import com.project.autoshop.exceptions.InvalidEmailException;
import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Client;
import com.project.autoshop.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;
    private ClientService underTest;

    @BeforeEach
    void setUp(){
        clientRepository.deleteAll();
        underTest = new ClientService(clientRepository);
    }

    @Test
    void itShouldFindAll(){
        underTest.getClients();
        verify(clientRepository).findAll();
    }

    @Test
    void itShouldFindOne(){
        Client client = new Client(1,"ismael","mohamed", "ismaelomermohamed@gmail.com");
        when(clientRepository.findById(anyInt())).thenReturn(Optional.of(client));
        Client test = underTest.getClient(1);
        verify(clientRepository).findById(1);
        assertThat(test).isSameAs(client);

    }

    @Test
    void itShouldThrowNotFound(){
        Exception exception = assertThrows(NotFoundException.class, () -> {
           underTest.getClient(1);
        });
        assertEquals("client doesn't exist", exception.getMessage());
    }

    @Test
    void itShouldCreateClient(){
        Client client = new Client(1,"ismael","mohamed", "ismaelomermohamed@gmail.com");
        when(clientRepository.findClientByEmail(anyString())).thenReturn(Optional.empty());
        underTest.createClient(client);
        verify(clientRepository).save(client);
    }

    @Test
    void itShouldFailToCreate(){
        Exception emptyFirst = assertThrows(EmptyFieldException.class, () -> {
            Client client = new Client();
            client.setLast("last");
            client.setEmail("ismael@gmail.com");
            underTest.createClient(client);
        });
        assertEquals("client first name is required", emptyFirst.getMessage());

        Exception emptyLast = assertThrows(EmptyFieldException.class, () -> {
            Client client = new Client();
            client.setFirst("first");
            client.setEmail("ismael@gmail.com");
            underTest.createClient(client);
        });
        assertEquals("client last name is required", emptyLast.getMessage());

        Exception emptyEmail = assertThrows(EmptyFieldException.class, () -> {
            Client client = new Client();
            client.setLast("last");
            client.setFirst("first");
            underTest.createClient(client);
        });
        assertEquals("client email is required", emptyEmail.getMessage());

        Exception invalidEmail = assertThrows(InvalidEmailException.class, () -> {
            Client client = new Client();
            client.setLast("last");
            client.setFirst("first");
            client.setEmail("email");
            underTest.createClient(client);
        });

        assertEquals("email is not valid", invalidEmail.getMessage());


        when(clientRepository.findClientByEmail(anyString())).thenReturn(Optional.of(new Client()));

        Exception emailAlreadyExist = assertThrows(EmailAlreadyExistsException.class, () -> {
            Client emailExistClient = new Client();
            emailExistClient.setFirst("first");
            emailExistClient.setLast("last");
            emailExistClient.setEmail("ismael@gmail.com");
            underTest.createClient(emailExistClient);
        });

        assertEquals("client with email: ismael@gmail.com already exists", emailAlreadyExist.getMessage());
    }


    @Test
    void itShouldUpdateClient(){
        when(clientRepository.findById(anyInt()))
                .thenReturn(Optional.of(new Client("first", "last", "email@gmail.com")));
        Client client = underTest.updateClient(1, "ismael","mohamed","ismael@gmail.com");
        assertEquals(client.getFirst(), "ismael");
        assertEquals(client.getLast(), "mohamed");
        assertEquals(client.getEmail(), "ismael@gmail.com");
    }

    @Test
    void itShouldFailToUpdate(){
        when(clientRepository.findById(anyInt()))
                .thenReturn(Optional.of(new Client("first", "last", "email@gmail.com")));
        Client testNull = underTest.updateClient(1, null, null, null);
        assertEquals(testNull.getFirst(), "first");
        assertEquals(testNull.getLast(), "last");
        assertEquals(testNull.getEmail(), "email@gmail.com");

        Client testEmpty = underTest.updateClient(1, "","","");
        assertEquals(testNull.getFirst(), "first");
        assertEquals(testNull.getLast(), "last");
        assertEquals(testNull.getEmail(), "email@gmail.com");


        Exception invalidEmail = assertThrows(InvalidEmailException.class, () -> {
            Client client = underTest.updateClient(1, null, null, "email");
        });

        assertEquals(invalidEmail.getMessage(), "email is not valid");

        Exception emailAlreadyExist = assertThrows(EmailAlreadyExistsException.class, () -> {
            when(clientRepository.findClientByEmail(anyString())).thenReturn(Optional.of(new Client()));
            Client client = underTest.updateClient(1, null, null, "ismael@gmail.com");
        });

        assertEquals(emailAlreadyExist.getMessage(), "client with email: ismael@gmail.com already exists");

    }

    @Test
    void itShouldDeleteClient(){
        when(clientRepository.findById(anyInt())).thenReturn(Optional.of(new Client()));
        underTest.deleteClient(1);
        verify(clientRepository).deleteById(1);
    }

    @Test
    void itShouldFailToDelete(){
        when(clientRepository.findById(anyInt())).thenReturn(Optional.empty());
        Exception notFound = assertThrows(NotFoundException.class, () -> {
           underTest.deleteClient(1);
        });

        assertEquals(notFound.getMessage(), "client doesn't exist");
    }
}