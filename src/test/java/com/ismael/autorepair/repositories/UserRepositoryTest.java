package com.ismael.autorepair.repositories;

import com.ismael.autorepair.models.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {
    @Autowired
    UserRepository underTest;

    @BeforeAll
    void setUp(){
        User user = new User("username", "password");
        underTest.save(user);
    }
    @AfterAll
    void breakDown(){
        underTest.deleteAll();
    }

    @Test
    void itShouldFindUserByUsername(){
        Optional<User> user = underTest.findUserByUsername("username");
        assertTrue(user.isPresent());
    }

}