package com.project.autorepair.services;

import com.project.autorepair.exceptions.NotFoundException;
import com.project.autorepair.models.Job;
import com.project.autorepair.models.Labor;
import com.project.autorepair.repositories.JobRepository;
import com.project.autorepair.repositories.LaborRepository;
import com.project.autorepair.request.LaborRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LaborServiceTest {
    @Mock
    public JobRepository jobRepository;
    @Mock
    public LaborRepository laborRepository;
    public LaborService underTest;

    @BeforeEach
    void setUp(){
        underTest = new LaborService(laborRepository, jobRepository);
    }

    @Test
    void itShouldGetAllLabors(){
        underTest.getLabors();
        verify(laborRepository).findAll();
    }

    @Test
    void itShouldGetLaborById(){
        Labor labor = Labor
                .builder()
                .task("transmission change")
                .cost(500.00)
                .location("transmission")
                .notes("different transmission")
                .job(Job.builder().build())
                .build();
        when(laborRepository.findById(anyInt())).thenReturn(Optional.of(labor));
        underTest.getLabor(1);
        verify(laborRepository).findById(1);
        assertEquals(underTest.getLabor(1).getTask(), "transmission change");
        assertEquals(underTest.getLabor(1).getCost(), 500.00);
        assertEquals(underTest.getLabor(1).getLocation(), "transmission");
        assertEquals(underTest.getLabor(1).getNotes(), "different transmission");
    }

    @Test
    void itShouldGetLaborByJob(){
        Labor labor = Labor
                .builder()
                .task("changer")
                .cost(500.00)
                .location("transmission")
                .notes("different transmission")
                .job(Job.builder().build())
                .build();
        when(laborRepository.findLaborsByJob(anyInt())).thenReturn(List.of(labor));
        underTest.getJobLabors(1);
        verify(laborRepository).findLaborsByJob(1);
        assertEquals(underTest.getJobLabors(1).get(0).getTask(), "changer");
        assertEquals(underTest.getJobLabors(1).get(0).getCost(), 500.00);
        assertEquals(underTest.getJobLabors(1).get(0).getLocation(), "transmission");
        assertEquals(underTest.getJobLabors(1).get(0).getNotes(), "different transmission");
    }

    @Test
    void itShouldCreateLabor(){
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(Job.builder().build()));
        Labor labor = underTest.createLabor(LaborRequest
                .builder()
                .cost(500.00)
                .location("drivetrain")
                .notes("different replacement")
                .job(1)
                .build()
        );
        verify(jobRepository).findById(1);
        verify(laborRepository).save(labor);
        assertEquals(labor.getCost(), 500.00);
        assertEquals(labor.getLocation(), "drivetrain");
        assertEquals(labor.getNotes(), "different replacement");
    }

    @Test
    void itShouldUpdateLabor(){
        when(laborRepository.findById(anyInt())).thenReturn(Optional.of(Labor
                .builder()
                .task("changer")
                .cost(500.00)
                .location("transmission")
                .notes("different transmission")
                .job(Job.builder().build())
                .build()));
        Labor labor = underTest.updateLabor(1, LaborRequest
                .builder()
                .task("replacement")
                .cost(400.00)
                .location("drivetrain")
                .notes("different drivetrain")
                .job(1)
                .build()
        );
        verify(laborRepository).findById(1);
        assertEquals(labor.getTask(), "replacement");
        assertEquals(labor.getCost(), 400.00);
        assertEquals(labor.getLocation(), "drivetrain");
        assertEquals(labor.getNotes(), "different drivetrain");
    }

    @Test
    void itShouldDeleteLabor(){
        Labor labor = Labor
                .builder()
                .build();
        when(laborRepository.findById(anyInt())).thenReturn(Optional.of(labor));
        underTest.deleteLabor(1);
        verify(laborRepository).findById(1);
        verify(laborRepository).delete(labor);
    }

    @Test
    void itShouldThrowNotFound(){
        Exception exception = assertThrows(NotFoundException.class, () -> {
            underTest.getLabor(1);
        });

        assertEquals(exception.getMessage(), "labor with id: 1 not found");

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.createLabor(LaborRequest.builder().job(1).build());
        });

        assertEquals(exception.getMessage(), "job with id: 1 not found");

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.updateLabor(1, LaborRequest.builder().build());
        });

        assertEquals(exception.getMessage(), "labor with id: 1 not found");

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.deleteLabor(1);
        });

        assertEquals(exception.getMessage(), "labor with id: 1 not found");
    }

}