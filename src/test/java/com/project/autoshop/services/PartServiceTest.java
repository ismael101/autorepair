package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Job;
import com.project.autoshop.models.Part;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.repositories.PartRepository;
import com.project.autoshop.request.PartRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PartServiceTest {
    @Mock
    public PartRepository partRepository;
    @Mock
    public JobRepository jobRepository;
    public PartService underTest;

    @BeforeEach
    void setUp(){
        underTest = new PartService(partRepository, jobRepository);
    }

    @Test
    void itShouldGetAllParts(){
        underTest.getParts();
        verify(partRepository).findAll();
    }

    @Test
    void itShouldGetPartById(){
        Part part = Part
                .builder()
                .name("changer")
                .description("transmission changer")
                .cost(500.00)
                .location("transmission")
                .ordered(false)
                .website("http://www.google.com")
                .notes("different transmission")
                .job(Job.builder().build())
                .build();
        when(partRepository.findById(anyInt())).thenReturn(Optional.of(part));
        underTest.getPart(1);
        verify(partRepository).findById(1);
        assertEquals(underTest.getPart(1).getName(), "changer");
        assertEquals(underTest.getPart(1).getDescription(), "transmission changer");
        assertEquals(underTest.getPart(1).getCost(), 500.00);
        assertEquals(underTest.getPart(1).getLocation(), "transmission");
        assertEquals(underTest.getPart(1).getOrdered(), false);
        assertEquals(underTest.getPart(1).getWebsite(), "http://www.google.com");
        assertEquals(underTest.getPart(1).getNotes(), "different transmission");
    }

    @Test
    void itShouldGetPartByJob(){
        Part part = Part
                .builder()
                .name("changer")
                .description("transmission changer")
                .cost(500.00)
                .location("transmission")
                .ordered(false)
                .website("http://www.google.com")
                .notes("different transmission")
                .job(Job.builder().build())
                .build();
        when(partRepository.findPartsByJob(anyInt())).thenReturn(List.of(part));
        underTest.getJobParts(1);
        verify(partRepository).findPartsByJob(1);
        assertEquals(underTest.getJobParts(1).get(0).getName(), "changer");
        assertEquals(underTest.getJobParts(1).get(0).getDescription(), "transmission changer");
        assertEquals(underTest.getJobParts(1).get(0).getCost(), 500.00);
        assertEquals(underTest.getJobParts(1).get(0).getLocation(), "transmission");
        assertEquals(underTest.getJobParts(1).get(0).getOrdered(), false);
        assertEquals(underTest.getJobParts(1).get(0).getWebsite(), "http://www.google.com");
        assertEquals(underTest.getJobParts(1).get(0).getNotes(), "different transmission");
    }

    @Test
    void itShouldCreatePart(){
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(Job.builder().build()));
        Part part = underTest.createPart(PartRequest
                .builder()
                .name("replacement")
                .description("drivetrain replacement")
                .cost(500.00)
                .location("drivetrain")
                .ordered(true)
                .website("http://www.yahoo.com")
                .notes("different replacement")
                .job(1)
                .build()
        );
        verify(jobRepository).findById(1);
        verify(partRepository).save(part);
        assertEquals(part.getName(), "replacement");
        assertEquals(part.getDescription(), "drivetrain replacement");
        assertEquals(part.getCost(), 500.00);
        assertEquals(part.getLocation(), "drivetrain");
        assertEquals(part.getOrdered(), true);
        assertEquals(part.getWebsite(), "http://www.yahoo.com");
        assertEquals(part.getNotes(), "different replacement");
    }

    @Test
    void itShouldUpdatePart(){
        when(partRepository.findById(anyInt())).thenReturn(Optional.of(Part
                .builder()
                .name("changer")
                .description("transmission changer")
                .cost(500.00)
                .location("transmission")
                .ordered(false)
                .website("http://www.google.com")
                .notes("different transmission")
                .job(Job.builder().build())
                .build()));
        Part part = underTest.updatePart(1, PartRequest
                .builder()
                .name("replacement")
                .description("drivetrain replacement")
                .cost(500.00)
                .location("drivetrain")
                .ordered(true)
                .website("http://www.yahoo.com")
                .notes("different replacement")
                .job(1)
                .build()
        );
        verify(partRepository).findById(1);
        assertEquals(part.getName(), "replacement");
        assertEquals(part.getDescription(), "drivetrain replacement");
        assertEquals(part.getCost(), 500.00);
        assertEquals(part.getLocation(), "drivetrain");
        assertEquals(part.getOrdered(), true);
        assertEquals(part.getWebsite(), "http://www.yahoo.com");
        assertEquals(part.getNotes(), "different replacement");
    }

    @Test
    void itShouldDeletePart(){
        Part part = Part
                .builder()
                .build();
        when(partRepository.findById(anyInt())).thenReturn(Optional.of(part));
        underTest.deletePart(1);
        verify(partRepository).findById(1);
        verify(partRepository).delete(part);
    }

    @Test
    void itShouldThrowNotFound(){
        Exception exception = assertThrows(NotFoundException.class, () -> {
           underTest.getPart(1);
        });

        assertEquals(exception.getMessage(), "part with id: 1 not found");

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.createPart(PartRequest.builder().job(1).build());
        });

        assertEquals(exception.getMessage(), "job with id: 1 not found");

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.updatePart(1, PartRequest.builder().build());
        });

        assertEquals(exception.getMessage(), "part with id: 1 not found");

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.deletePart(1);
        });

        assertEquals(exception.getMessage(), "part with id: 1 not found");
    }

}