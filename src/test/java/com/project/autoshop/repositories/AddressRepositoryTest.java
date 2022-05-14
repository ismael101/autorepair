package com.project.autoshop.repositories;

import com.project.autoshop.models.Address;
import com.project.autoshop.models.Job;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddressRepositoryTest {
    @Autowired
    public AddressRepository underTest;
    @Autowired
    public JobRepository jobRepository;

    @BeforeAll
    void beforeAll() {
        Job job = Job
                .builder()
                .id(1)
                .description("broken transmission")
                .labor(100.0)
                .build();
        jobRepository.save(job);
        Address address = Address
                .builder()
                .id(1)
                .city("minneapolis")
                .state("minnesota")
                .street("washington avenue")
                .zipcode(55432)
                .job(job)
                .build();
        underTest.save(address);

    }

    @AfterAll
    void afterAll(){
        jobRepository.deleteAll();
        underTest.deleteAll();
    }


    @Test
    void itShouldFindAddressByJob(){
        Optional<Address> address = underTest.findAddressByJob(1);
        assertTrue(address.isPresent());
    }
}