package com.ismael.autorepair.repositories;

import com.ismael.autorepair.models.*;
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
class VehicleRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    WorkRepository workRepository;
    @Autowired
    VehicleRepository underTest;

    @BeforeAll
    void setUp(){
        User user = new User("username", "password");
        userRepository.save(user);
        Work work = new Work("title", "description", false, user);
        workRepository.save(work);
        Vehicle vehicle = new Vehicle("make", "model", 1999, work);
        underTest.save(vehicle);
    }

    @AfterAll
    void breakDown(){
        userRepository.deleteAll();
    }

    @Test
    void itShouldFindVehicleByWork(){
        List<Work> work = workRepository.findAll();
        Optional<Vehicle> vehicle = underTest.findVehicleByWork(work.get(0));
        assertTrue(vehicle.isPresent());
    }

}