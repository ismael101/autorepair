package com.project.autoshop.repositories;

import com.project.autoshop.models.Customer;
import com.project.autoshop.models.Image;
import com.project.autoshop.models.Job;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class ImageRepositoryTest {
    @Autowired
    public JobRepository jobRepository;
    @Autowired
    public ImageRepository underTest;

    @BeforeEach
    void setUp(){
        Job job = Job
                .builder()
                .description("broken transmission")
                .complete(false)
                .build();
        jobRepository.save(job);
        Image image = Image
                .builder()
                .name("main")
                .data("acdadc".getBytes(StandardCharsets.UTF_8))
                .job(job)
                .build();
        underTest.save(image);
    }

    @AfterEach
    void breakDown(){
        jobRepository.deleteAll();
        underTest.deleteAll();
    }

    @Test
    void itShouldFindImagesByJob(){
        List<Image> images = underTest.findImagesByJob(1);
        assertFalse(images.isEmpty());
        assertEquals(1, images.size());
    }
}