package com.project.autoshop.repositories;

import com.project.autoshop.models.Job;
import com.project.autoshop.models.Labor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LaborRepositoryTest {
    @Autowired
    public JobRepository jobRepository;
    @Autowired
    public LaborRepository underTest;

    @BeforeAll
    void setUp(){
        Job job = Job
                .builder()
                .id(1)
                .complete(false)
                .description("mock description")
                .build();
        jobRepository.save(job);
        Labor labor = Labor
                .builder()
                .task("mock task")
                .notes("mock notes")
                .cost(100.00)
                .description("mock description")
                .location("mock location")
                .job(job)
                .build();
        underTest.save(labor);
    }

    @AfterAll
    void afterAll(){
        jobRepository.deleteAll();
        underTest.deleteAll();
    }

    @Test
    void itShouldFindLaborsByJob(){
        List<Labor> labors = underTest.findLaborsByJob(1);
        assertFalse(labors.isEmpty());
        assertEquals(labors.size(), 1);
        assertEquals(labors.get(0).getTask(), "mock task");
        assertEquals(labors.get(0).getNotes(), "mock notes");
        assertEquals(labors.get(0).getCost(), 100.00);
        assertEquals(labors.get(0).getDescription(), "mock description");
        assertEquals(labors.get(0).getLocation(), "mock location");
    }

    @Test
    void itShouldNotFindLaborsByJob(){
        List<Labor> labors = underTest.findLaborsByJob(2);
        assertTrue(labors.isEmpty());
    }
}