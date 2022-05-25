package com.project.autoshop.repositories;

import com.project.autoshop.models.Address;
import com.project.autoshop.models.Job;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
    void setUp(){
        Job job = Job
                .builder()
                .id(1)
                .complete(false)
                .description("mock description")
                .build();
        jobRepository.save(job);
        Address address = Address
                .builder()
                .street("mock street")
                .city("mock city")
                .state("mock state")
                .zipcode(55555)
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
        assertFalse(address.isEmpty());
        assertEquals(address.get().getStreet(), "mock street");
        assertEquals(address.get().getCity(), "mock city");
        assertEquals(address.get().getState(), "mock state");
        assertEquals(address.get().getZipcode(), 55555);
    }

    @Test
    void itShouldNotFindAddressByJob(){
        Optional<Address> address = underTest.findAddressByJob(2);
        assertTrue(address.isEmpty());
    }

}