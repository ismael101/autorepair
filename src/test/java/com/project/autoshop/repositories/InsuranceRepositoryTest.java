package com.project.autoshop.repositories;

import com.project.autoshop.models.Insurance;
import com.project.autoshop.models.Job;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InsuranceRepositoryTest  {
    @Autowired
    public InsuranceRepository underTest;
    @Autowired
    public JobRepository jobRepository;

    @BeforeAll
    void beforeAll() {
        Job job = Job
                .builder()
                .description("broken transmission")
                .complete(false)
                .build();
        jobRepository.save(job);
        Insurance insurance = Insurance
                .builder()
                .policy("acasdcasdc")
                .provider("geico")
                .vin("vin")
                .job(job)
                .build();
        underTest.save(insurance);

    }

    @AfterAll
    void afterAll(){
        jobRepository.deleteAll();
        underTest.deleteAll();
    }


    @Test
    void itShouldFindInsuranceByJob(){
        Optional<Insurance> insurance = underTest.findInsuranceByJob(1);
        assertTrue(insurance.isPresent());
    }
}