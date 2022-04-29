package com.project.autoshop.repositories;

import com.project.autoshop.models.Client;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClientRepositoryTest {

    @Autowired
    private ClientRepository underTest;

    @BeforeAll
    void createClient(){
        Client client = new Client(1,"ismael","mohamed","ismaelomermohamed@gmail.com");
        underTest.save(client);
    }

    @AfterAll
    void deleteClient(){
        underTest.deleteById(1);
    }

    @Test
    void itShouldGetClientByEmail(){
        Optional<Client> client = underTest.findClientByEmail("ismaelomermohamed@gmail.com");
        assertTrue(client.isPresent());
    }

    @Test
    void itShouldThrowException(){
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            Client client = new Client();
            underTest.save(client);
        });
    }

}