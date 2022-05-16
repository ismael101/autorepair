package com.project.autoshop.repositories;

import com.project.autoshop.models.Job;
import com.project.autoshop.models.Part;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class PartRepositoryTest {
    @Autowired
    public JobRepository jobRepository;
    @Autowired
    public PartRepository underTest;

    @BeforeEach
    void setUp(){
        Job job = Job
                .builder()
                .description("broken transmission")
                .complete(false)
                .build();
        jobRepository.save(job);
        Part part = Part
                .builder()
                .name("transmission")
                .website("www.google.com")
                .location("bottom")
                .ordered(false)
                .notes("")
                .description("part for transmission")
                .cost(100.0)
                .job(job)
                .build();
        underTest.save(part);
    }
    @AfterEach
    void breakDown(){
        jobRepository.deleteAll();
        underTest.deleteAll();
    }

    @Test
    void itShouldFindPartsByJob(){
        List<Part> parts = underTest.findPartsByJob(1);
        assertFalse(parts.isEmpty());
        assertEquals(1, parts.size());
    }
}