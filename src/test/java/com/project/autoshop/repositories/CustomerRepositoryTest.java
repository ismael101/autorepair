package com.project.autoshop.repositories;

import com.project.autoshop.models.Customer;
import com.project.autoshop.models.Job;
import org.junit.jupiter.api.*;
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
                .complete(false)
                .description("mock description")
                .build();
        jobRepository.save(job);
        Customer customer = Customer
                .builder()
                .first("mock first")
                .last("mock last")
                .email("mock email")
                .phone("7632275152")
                .job(job)
                .build();
        underTest.save(customer);
    }

    @AfterAll
    void afterAll(){
        jobRepository.deleteAll();
        underTest.deleteAll();
    }

    @Test
    void itShouldFindCustomerByJob(){
        Optional<Customer> customer = underTest.findCustomerByJob(1);
        assertFalse(customer.isEmpty());
        assertEquals(customer.get().getFirst(), "mock first");
        assertEquals(customer.get().getLast(), "mock last");
        assertEquals(customer.get().getEmail(), "mock email");
        assertEquals(customer.get().getPhone(), 7632275152l);
    }

    @Test
    void itShouldNotFindCustomerByJob(){
        Optional<Customer> customer = underTest.findCustomerByJob(2);
        assertTrue(customer.isEmpty());
    }
}