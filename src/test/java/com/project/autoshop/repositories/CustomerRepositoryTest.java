package com.project.autoshop.repositories;

import com.project.autoshop.models.Customer;
import com.project.autoshop.models.Job;
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
class CustomerRepositoryTest {
    @Autowired
    public JobRepository jobRepository;
    @Autowired
    public CustomerRepository underTest;

    @BeforeAll
    void setUp(){
        Job job = Job
                .builder()
                .id(1)
                .description("broken transmission")
                .labor(100.0)
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
    @AfterAll
    void breakDown(){
        jobRepository.deleteAll();
        underTest.deleteAll();
    }

    @Test
    void itShouldFindClientByJob(){
        Optional<Customer> customer = underTest.findClientByJob(1);
        assertTrue(customer.isPresent());
    }
}