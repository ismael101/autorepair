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
    void setUp(){
        Job job = Job
                .builder()
                .id(1)
                .complete(false)
                .description("mock description")
                .build();
        jobRepository.save(job);
        Insurance insurance = Insurance
                .builder()
                .provider("mock provider")
                .policy("mock policy")
                .vin("mock vin")
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
        assertFalse(insurance.isEmpty());
        assertEquals(insurance.get().getProvider(), "mock provider");
        assertEquals(insurance.get().getPolicy(), "mock policy");
        assertEquals(insurance.get().getVin(), "mock vin");
    }

    @Test
    void itShouldNotFindInsuranceByJob(){
        Optional<Insurance> insurance = underTest.findInsuranceByJob(2);
        assertTrue(insurance.isEmpty());
    }
}