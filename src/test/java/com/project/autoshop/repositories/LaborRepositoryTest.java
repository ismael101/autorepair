package com.project.autoshop.repositories;

import com.project.autoshop.models.Job;
import com.project.autoshop.models.Labor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class LaborRepositoryTest {
    @Autowired
    public JobRepository jobRepository;
    @Autowired
    public LaborRepository underTest;

    @BeforeEach
    void setUp(){
        Job job = Job
                .builder()
                .description("broken transmission")
                .complete(false)
                .build();
        jobRepository.save(job);
        Labor labor = Labor
                .builder()
                .location("bottom")
                .description("part for transmission")
                .task("attachment")
                .cost(100.0)
                .job(job)
                .build();
        underTest.save(labor);
    }
    @AfterEach
    void breakDown(){
        jobRepository.deleteAll();
        underTest.deleteAll();
    }

    @Test
    void itShouldFindLaborByJob(){
        List<Labor> labors = underTest.findLaborsByJob(1);
        assertFalse(labors.isEmpty());
        assertEquals(1, labors.size());
    }
}