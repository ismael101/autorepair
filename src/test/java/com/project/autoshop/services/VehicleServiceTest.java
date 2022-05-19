package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Job;
import com.project.autoshop.models.Vehicle;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.repositories.VehicleRepository;
import com.project.autoshop.request.VehicleRequest;
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
class VehicleServiceTest {
    @Mock
    public VehicleRepository vehicleRepository;
    @Mock
    public JobRepository jobRepository;
    public VehicleService underTest;

    @BeforeEach
    void setUp(){
        underTest = new VehicleService(vehicleRepository, jobRepository);
    }

    @Test
    void itShouldGetAllVehicles(){
        underTest.getVehicles();
        verify(vehicleRepository).findAll();
    }

    @Test
    void itShouldGetVehicleById(){
        Vehicle vehicle = Vehicle
                .builder()
                .make("toyota")
                .model("camry")
                .year(2017)
                .vin("vinnumber")
                .job(Job.builder().build())
                .build();
        when(vehicleRepository.findById(anyInt())).thenReturn(Optional.of(vehicle));
        underTest.getVehicle(1);
        verify(vehicleRepository).findById(1);
        assertEquals(underTest.getVehicle(1).getMake(), "toyota");
        assertEquals(underTest.getVehicle(1).getModel(), "camry");
        assertEquals(underTest.getVehicle(1).getYear(), 2017);
        assertEquals(underTest.getVehicle(1).getVin(), "vinnumber");
    }

    @Test
    void itShouldGetVehicleByJob(){
        Vehicle vehicle = Vehicle
                .builder()
                .make("toyota")
                .model("camry")
                .year(2017)
                .vin("vinnumber")
                .job(Job.builder().build())
                .build();
        when(vehicleRepository.findVehicleByJob(anyInt())).thenReturn(Optional.of(vehicle));
        underTest.getJobVehicle(1);
        verify(vehicleRepository).findVehicleByJob(1);
        assertEquals(underTest.getJobVehicle(1).getMake(), "toyota");
        assertEquals(underTest.getJobVehicle(1).getModel(), "camry");
        assertEquals(underTest.getJobVehicle(1).getYear(), 2017);
        assertEquals(underTest.getJobVehicle(1).getVin(), "vinnumber");
    }

    @Test
    void itShouldCreateVehicle(){
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(Job.builder().build()));
        Vehicle vehicle = underTest.createVehicle(VehicleRequest
                .builder()
                .make("toyota")
                .model("camry")
                .year(2017)
                .vin("vinnumber")
                .job(1)
                .build()
        );
        verify(jobRepository).findById(1);
        verify(vehicleRepository).save(vehicle);
        assertEquals(vehicle.getMake(), "toyota");
        assertEquals(vehicle.getModel(), "camry");
        assertEquals(vehicle.getYear(), 2017);
        assertEquals(vehicle.getVin(), "vinnumber");
    }

    @Test
    void itShouldUpdateVehicle(){
        when(vehicleRepository.findById(anyInt())).thenReturn(Optional.of(Vehicle
                .builder()
                .make("toyota")
                .model("camry")
                .year(2017)
                .vin("vinnumber")
                .job(Job.builder().build())
                .build()
        ));

        Vehicle vehicle = underTest.updateVehicle(1, VehicleRequest
                .builder()
                .make("hyundai")
                .model("sonata")
                .year(2018)
                .vin("vinfloat")
                .job(1)
                .build()
        );

        verify(vehicleRepository).findById(1);
        assertEquals(vehicle.getMake(), "hyundai");
        assertEquals(vehicle.getModel(), "sonata");
        assertEquals(vehicle.getYear(), 2018);
        assertEquals(vehicle.getVin(), "vinfloat");
    }

    @Test
    void itShouldDeleteVehicle(){
        Vehicle vehicle = Vehicle
                .builder()
                .build();
        when(vehicleRepository.findById(anyInt())).thenReturn(Optional.of(vehicle));
        underTest.deleteVehicle(1);
        verify(vehicleRepository).findById(1);
        verify(vehicleRepository).delete(vehicle);
    }

    @Test
    void itShouldThrowNotFound(){
        Exception exception = assertThrows(NotFoundException.class, () -> {
            underTest.getJobVehicle(1);
        });
        assertEquals(exception.getMessage(), "vehicle with job id: 1 not found");

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.getVehicle(1);
        });
        assertEquals(exception.getMessage(), "vehicle with id: 1 not found");

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.createVehicle(VehicleRequest.builder().job(1).build());
        });
        assertEquals(exception.getMessage(), "job with id: 1 not found");

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.updateVehicle(1, VehicleRequest.builder().build());
        });
        assertEquals(exception.getMessage(), "vehicle with id: 1 not found");

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.deleteVehicle(1);
        });
        assertEquals(exception.getMessage(), "vehicle with id: 1 not found");
    }
}