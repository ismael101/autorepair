package com.project.autoshop.repositories;
import com.project.autoshop.models.Image;
import com.project.autoshop.models.Job;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ImageRepositoryTest {
    @Autowired
    public JobRepository jobRepository;
    @Autowired
    public ImageRepository underTest;

    @BeforeAll
    void setUp(){
        Job job = Job
                .builder()
                .id(1)
                .complete(false)
                .description("mock description")
                .build();
        jobRepository.save(job);
        Image image = Image
                .builder()
                .name("mock name")
                .data("mock data".getBytes(StandardCharsets.UTF_8))
                .job(job)
                .build();
        underTest.save(image);
    }

    @AfterAll
    void afterAll(){
        jobRepository.deleteAll();
        underTest.deleteAll();
    }

    @Test
    void itShouldFindImagesByJob(){
        List<Image> images = underTest.findImagesByJob(1);
        assertFalse(images.isEmpty());
        assertEquals(images.size(), 1);
        assertEquals(images.get(0).getName(), "mock name");
        assertArrayEquals(images.get(0).getData(), "mock data".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void itShouldNotFindImagesByJob(){
        List<Image> images = underTest.findImagesByJob(2);
        assertTrue(images.isEmpty());
    }
}