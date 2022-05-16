package com.project.autoshop.repositories;

import com.project.autoshop.models.Address;
import com.project.autoshop.models.Job;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class AddressRepositoryTest {
    @Autowired
    public AddressRepository underTest;
    @Autowired
    public JobRepository jobRepository;

    @BeforeEach
    void beforeAll() {
        Job job = Job
                .builder()
                .description("broken transmission")
                .complete(false)
                .build();
        jobRepository.save(job);
        Address address = Address
                .builder()
                .city("minneapolis")
                .state("minnesota")
                .street("washington avenue")
                .zipcode(55432)
                .job(job)
                .build();
        underTest.save(address);

    }

    @AfterEach
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