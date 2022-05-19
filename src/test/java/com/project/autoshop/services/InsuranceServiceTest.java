package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Insurance;
import com.project.autoshop.models.Job;
import com.project.autoshop.repositories.InsuranceRepository;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.request.InsuranceRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InsuranceServiceTest {
    @Mock
    public JobRepository jobRepository;
    @Mock
    public InsuranceRepository  insuranceRepository;
    public InsuranceService underTest;

    @BeforeEach
    void setUp(){
        underTest = new InsuranceService(insuranceRepository, jobRepository);
    }

    @Test
    void itShouldGetAllInsurance(){
        underTest.getAllInsurance();
        verify(insuranceRepository).findAll();
    }

    @Test
    void itShouldGetInsuranceById(){
        Insurance insurance = Insurance
                .builder()
                .policy("goodpolicy")
                .provider("geico")
                .vin("vinnumber")
                .job(Job.builder().build())
                .build();
        when(insuranceRepository.findById(anyInt())).thenReturn(Optional.of(insurance));
        underTest.getInsurance(1);
        verify(insuranceRepository).findById(1);
        assertEquals(underTest.getInsurance(1).getPolicy(), "goodpolicy");
        assertEquals(underTest.getInsurance(1).getProvider(), "geico");
        assertEquals(underTest.getInsurance(1).getVin(), "vinnumber");
    }

    @Test
    void itShouldGetInsuranceByJob(){
        Insurance insurance = Insurance
                .builder()
                .policy("goodpolicy")
                .provider("geico")
                .vin("vinnumber")
                .job(Job.builder().build())
                .build();
        when(insuranceRepository.findInsuranceByJob(anyInt())).thenReturn(Optional.of(insurance));
        underTest.getJobInsurance(1);
        verify(insuranceRepository).findInsuranceByJob(1);
        assertEquals(underTest.getJobInsurance(1).getPolicy(), "goodpolicy");
        assertEquals(underTest.getJobInsurance(1).getProvider(), "geico");
        assertEquals(underTest.getJobInsurance(1).getVin(), "vinnumber");
    }

    @Test
    void itShouldCreateInsurance(){
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(Job.builder().build()));
        Insurance insurance = underTest.createInsurance(InsuranceRequest
                .builder()
                .policy("policy")
                .provider("geico")
                .vin("vinnumber")
                .job(1)
                .build());
        verify(jobRepository).findById(1);
        verify(insuranceRepository).save(insurance);
        assertEquals(insurance.getPolicy(), "policy");
        assertEquals(insurance.getProvider(), "geico");
        assertEquals(insurance.getVin(), "vinnumber");
    }

    @Test
    void itShouldUpdateInsurance(){
        when(insuranceRepository.findById(anyInt())).thenReturn(Optional.of(Insurance
                .builder()
                .policy("badpolicy")
                .provider("geico")
                .vin("vinnumber")
                .build()
        ));

        Insurance insurance = underTest.updateInsurance(1, InsuranceRequest
                .builder()
                .policy("goodpolicy")
                .provider("statefarm")
                .vin("vinfloat")
                .job(1)
                .build());

        verify(insuranceRepository).findById(1);
        assertEquals(insurance.getPolicy(), "goodpolicy");
        assertEquals(insurance.getProvider(), "statefarm");
        assertEquals(insurance.getVin(), "vinfloat");
    }

    @Test
    void itShouldDeleteInsurance(){
        Insurance insurance = Insurance
                .builder()
                .build();
        when(insuranceRepository.findById(anyInt())).thenReturn(Optional.of(insurance));
        underTest.deleteInsurance(1);
        verify(insuranceRepository).findById(1);
        verify(insuranceRepository).delete(insurance);
    }

    @Test
    void itShouldThrowNotFound(){
        Exception exception =  assertThrows(NotFoundException.class, () -> {
            underTest.getJobInsurance(1);
        });
        assertEquals("insurance with job id: 1 not found", exception.getMessage());

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.getInsurance(1);
        });
        assertEquals("insurance with id: 1 not found", exception.getMessage());

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.createInsurance(InsuranceRequest.builder().job(1).build());
        });
        assertEquals("job with id: 1 not found", exception.getMessage());

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.updateInsurance(1, InsuranceRequest.builder().build());
        });
        assertEquals("insurance with id: 1 not found", exception.getMessage());

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.deleteInsurance(1);
        });
        assertEquals("insurance with id: 1 not found", exception.getMessage());
    }
}