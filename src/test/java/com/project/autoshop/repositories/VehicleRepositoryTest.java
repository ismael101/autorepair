package com.project.autoshop.repositories;

import com.project.autoshop.models.Job;
import com.project.autoshop.models.Vehicle;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VehicleRepositoryTest {
    @Autowired
    public VehicleRepository underTest;
    @Autowired
    public JobRepository jobRepository;

    @BeforeAll
    void beforeAll() {
        Job job = Job
                .builder()
                .id(1)
                .description("broken transmission")
                .complete(false)
                .build();
        jobRepository.save(job);
        Vehicle vehicle = Vehicle
                .builder()
                .make("mock make")
                .model("mock model")
                .year(2017)
                .vin("mock vin")
                .job(job)
                .build();
        underTest.save(vehicle);

    }

    @AfterAll
    void afterAll(){
        jobRepository.deleteAll();
        underTest.deleteAll();
    }


    @Test
    void itShouldFindVehicleByJob(){
        Optional<Vehicle> vehicle = underTest.findVehicleByJob(1);
        assertFalse(vehicle.isEmpty());
        assertEquals(vehicle.get().getMake(), "mock make");
        assertEquals(vehicle.get().getModel(), "mock model");
        assertEquals(vehicle.get().getYear(), 2017);
        assertEquals(vehicle.get().getVin(), "mock vin");
    }

    @Test
    void itShouldNotFindVehicle(){
        Optional<Vehicle> vehicle = underTest.findVehicleByJob(2);
        assertTrue(vehicle.isEmpty());
    }
}