package com.project.autoshop.repositories;

import com.project.autoshop.models.Job;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JobRepositoryTest {
    @Autowired
    public JobRepository underTest;

    @Test
    void itShouldCreateJob(){
        underTest.save(Job
                .builder()
                .id(1)
                .description("transmission issues")
                .labor(100.0)
                .build());
        assertTrue(underTest.findById(1).isPresent());
    }
}