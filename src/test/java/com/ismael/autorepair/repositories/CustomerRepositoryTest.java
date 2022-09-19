package com.ismael.autorepair.repositories;

import com.ismael.autorepair.models.Customer;
import com.ismael.autorepair.models.User;
import com.ismael.autorepair.models.Work;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    WorkRepository workRepository;
    @Autowired
    CustomerRepository underTest;

    @BeforeAll
    void setUp(){
        User user = new User("username", "password");
        userRepository.save(user);
        Work work = new Work("title", "description", false, user);
        workRepository.save(work);
        Customer customer = new Customer("first", "last", "email", 7632275152l, work);
        underTest.save(customer);
    }

    @AfterAll
    void breakDown(){
        userRepository.deleteAll();
    }

    @Test
    void itShouldFindCustomerByWork(){
        List<Work> work = workRepository.findAll();
        Optional<Customer> customer = underTest.findCustomerByWork(work.get(0));
        assertTrue(customer.isPresent());
    }


}