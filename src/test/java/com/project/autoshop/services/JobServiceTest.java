package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Job;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.request.JobRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobServiceTest {
    @Mock
    public JobRepository jobRepository;
    public JobService underTest;

    @BeforeEach
    void setUp(){
        underTest = new JobService(jobRepository);
    }

    @Test
    void itShouldGetAllJobs(){
        underTest.getJobs();
        Mockito.verify(jobRepository).findAll();
    }

    @Test
    void itShouldGetJobById(){
        Job job = Job.builder().build();
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(job));
        underTest.getJobById(1);
        verify(jobRepository).findById(1);
        assertEquals(underTest.getJobById(1), job);
    }

    @Test
    void itShouldCreateJob(){
        Job job = underTest.createJob(JobRequest
                .builder()
                .description("change transmission")
                .complete(false)
                .build());
        verify(jobRepository).save(job);
        assertEquals(job.getDescription(), "change transmission");
        assertEquals(job.getComplete(), false);
    }

    @Test
    void itShouldUpdateJob(){
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(Job
                .builder()
                .description("transmission issue")
                .complete(false)
                .build()
        ));

        Job job = underTest.updateJob(1, JobRequest
                .builder()
                .description("brake issues")
                .complete(true)
                .build());

        verify(jobRepository).findById(1);
        assertEquals(job.getDescription(), "brake issues");
        assertEquals(job.getComplete(), true);
    }

    @Test
    void itShouldDeleteJob(){
        Job job = Job
                .builder()
                .build();
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(job));
        underTest.deleteJob(1);
        verify(jobRepository).findById(1);
        verify(jobRepository).delete(job);
    }

    @Test
    void itShouldThrowNotFound(){
        Exception exception =  assertThrows(NotFoundException.class, () -> {
            underTest.getJobById(1);
        });
        assertEquals("job with id: 1 not found", exception.getMessage());

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.updateJob(1, JobRequest.builder().build());
        });
        assertEquals("job with id: 1 not found", exception.getMessage());

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.deleteJob(1);
        });
        assertEquals("job with id: 1 not found", exception.getMessage());
    }
}