package com.project.autoshop.repositories;

import com.project.autoshop.models.Address;
import com.project.autoshop.models.Job;
import com.project.autoshop.models.Vehicle;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class VehicleRepositoryTest {
    @Autowired
    public VehicleRepository underTest;
    @Autowired
    public JobRepository jobRepository;

    @BeforeEach
    void beforeAll() {
        Job job = Job
                .builder()
                .id(60)
                .description("broken transmission")
                .complete(false)
                .build();
        jobRepository.save(job);
        Vehicle vehicle = Vehicle
                .builder()
                .id(61)
                .make("toyota")
                .model("camry")
                .year(2017)
                .vin("asdcasdcasdc")
                .job(job)
                .build();
        underTest.save(vehicle);

    }

    @AfterEach
    void afterAll(){
        jobRepository.deleteById(60);
        underTest.deleteById(61);
    }


    @Test
    void itShouldFindVehicleByJob(){
        Optional<Vehicle> vehicle = underTest.findVehicleByJob(60);
        assertTrue(vehicle.isPresent());
    }
}