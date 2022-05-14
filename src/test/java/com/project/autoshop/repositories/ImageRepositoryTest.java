package com.project.autoshop.repositories;

import com.project.autoshop.models.Customer;
import com.project.autoshop.models.Image;
import com.project.autoshop.models.Job;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

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
                .description("broken transmission")
                .build();
        jobRepository.save(job);
        Image image = Image
                .builder()
                .id(2)
                .name("main")
                .data("acdadc".getBytes(StandardCharsets.UTF_8))
                .job(job)
                .build();
        underTest.save(image);
    }

    @Test
    void itShouldFindImagesByJob(){
        //given
        List<Image> images = underTest.findImagesByJob(1);

        //then
        assertFalse(images.isEmpty());
        assertEquals(1, images.size());
    }
}