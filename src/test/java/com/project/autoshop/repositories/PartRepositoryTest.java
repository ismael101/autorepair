package com.project.autoshop.repositories;

import com.project.autoshop.models.Job;
import com.project.autoshop.models.Part;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PartRepositoryTest {
    @Autowired
    public JobRepository jobRepository;
    @Autowired
    public PartRepository underTest;

    @BeforeAll
    void setUp(){
        Job job = Job
                .builder()
                .id(1)
                .description("mock description")
                .complete(false)
                .build();
        jobRepository.save(job);
        Part part = Part
                .builder()
                .name("mock name")
                .website("mock website")
                .location("mock location")
                .ordered(false)
                .notes("mock notes")
                .description("mock description")
                .cost(100.00)
                .job(job)
                .build();
        underTest.save(part);
    }
    @AfterAll
    void breakDown(){
        jobRepository.deleteAll();
        underTest.deleteAll();
    }

    @Test
    void itShouldFindPartsByJob(){
        List<Part> parts = underTest.findPartsByJob(1);
        assertFalse(parts.isEmpty());
        assertEquals(parts.size(), 1);
        assertEquals(parts.get(0).getName(), "mock name");
        assertEquals(parts.get(0).getOrdered(), false);
        assertEquals(parts.get(0).getWebsite(), "mock website");
        assertEquals(parts.get(0).getNotes(), "mock notes");
        assertEquals(parts.get(0).getCost(), 100.00);
        assertEquals(parts.get(0).getDescription(), "mock description");
        assertEquals(parts.get(0).getLocation(), "mock location");
    }


    @Test
    void itShouldNotFindLaborsByJob(){
        List<Part> parts = underTest.findPartsByJob(2);
        assertTrue(parts.isEmpty());
    }
}