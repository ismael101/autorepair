package com.project.autoshop.repositories;

import com.project.autoshop.models.Customer;
import com.project.autoshop.models.Job;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class CustomerRepositoryTest {
    @Autowired
    public JobRepository jobRepository;
    @Autowired
    public CustomerRepository underTest;

    @BeforeEach
    void setUp(){
        Job job = Job
                .builder()
                .description("broken transmission")
                .complete(false)
                .build();
        jobRepository.save(job);
        Customer customer = Customer
                .builder()
                .first("ismael")
                .last("mohamed")
                .email("ismaelomermohamed@gmail.com")
                .phone(7632275152l)
                .job(job)
                .build();
        underTest.save(customer);
    }
    @AfterEach
    void breakDown(){
        jobRepository.deleteAll();
        underTest.deleteAll();
    }

    @Test
    void itShouldFindCustomerByJob(){
        Optional<Customer> customer = underTest.findCustomerByJob(1);
        assertTrue(customer.isPresent());
    }
}