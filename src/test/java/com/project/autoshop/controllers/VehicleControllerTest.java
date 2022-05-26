package com.project.autoshop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.autoshop.exceptions.ErrorExceptionHandler;
import com.project.autoshop.models.Job;
import com.project.autoshop.models.Vehicle;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.repositories.VehicleRepository;
import com.project.autoshop.request.VehicleRequest;
import com.project.autoshop.services.VehicleService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehicleControllerTest {
    @Mock
    public VehicleRepository vehicleRepository;
    @Mock
    public JobRepository jobRepository;
    public VehicleService vehicleService;
    public VehicleController underTest;
    public MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        vehicleService = new VehicleService(vehicleRepository, jobRepository);
        underTest = new VehicleController(vehicleService);
        mockMvc = MockMvcBuilders.standaloneSetup(underTest)
                .setControllerAdvice(new ErrorExceptionHandler())
                .build();
    }

    @Test
    void itShouldGetVehicle() throws Exception {
        when(vehicleRepository.findAll()).thenReturn(List.of(Vehicle
                .builder()
                .make("mock make")
                .model("mock model")
                .vin("mock vin")
                .year(2020)
                .job(new Job())
                .build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vehicle"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].make", Matchers.is("mock make")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].model", Matchers.is("mock model")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].vin", Matchers.is("mock vin")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].year", Matchers.is(2020)));
    }

    @Test
    void itShouldGetVehicleById() throws Exception {
        when(vehicleRepository.findById(anyInt())).thenReturn(Optional.of(Vehicle
                .builder()
                .make("mock make")
                .model("mock model")
                .vin("mock vin")
                .year(2020)
                .job(new Job())
                .build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vehicle/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.make", Matchers.is("mock make")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Matchers.is("mock model")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("mock vin")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year", Matchers.is(2020)));
    }

    @Test
    void itShouldGetVehicleByJob() throws Exception{
        when(vehicleRepository.findVehicleByJob(anyInt())).thenReturn(Optional.of(Vehicle
                .builder()
                .make("mock make")
                .model("mock model")
                .vin("mock vin")
                .year(2020)
                .job(new Job())
                .build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vehicle/job/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.make", Matchers.is("mock make")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Matchers.is("mock model")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("mock vin")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year", Matchers.is(2020)));
    }

    @Test
    void itShouldCreateVehicle() throws Exception{
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(Job.builder().id(1).build()));
        VehicleRequest request = VehicleRequest
                .builder()
                .make("mock make")
                .model("mock model")
                .vin("mock vin")
                .year(2020)
                .job(1)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/vehicle/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.make", Matchers.is("mock make")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Matchers.is("mock model")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("mock vin")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year", Matchers.is(2020)));
    }

    @Test
    void itShouldUpdateVehicle() throws Exception{
        when(vehicleRepository.findById(anyInt())).thenReturn(Optional.of(Vehicle
                .builder()
                .make("mock make")
                .model("mock model")
                .vin("mock vin")
                .year(2020)
                .job(new Job())
                .build()));
        VehicleRequest request = VehicleRequest
                .builder()
                .make("toyota")
                .model("camry")
                .vin("123456")
                .year(2024)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/vehicle/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.make", Matchers.is("toyota")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Matchers.is("camry")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("123456")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year", Matchers.is(2024)));
    }

    @Test
    void itShouldDeleteVehicle() throws Exception{
        when(vehicleRepository.findById(anyInt())).thenReturn(Optional.of(Vehicle
                .builder()
                .build()));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/vehicle/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void itShouldThrowNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vehicle/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/vehicle/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("vehicle with id: 1 not found")));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vehicle/job/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/vehicle/job/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("vehicle with job id: 1 not found")));

        VehicleRequest request = VehicleRequest
                .builder()
                .make("toyota")
                .model("camry")
                .vin("123456")
                .year(2024)
                .job(1)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/vehicle/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/vehicle/")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("job with id: 1 not found")));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/vehicle/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/vehicle/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("vehicle with id: 1 not found")));


        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/vehicle/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/vehicle/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("vehicle with id: 1 not found")));
    }

    @Test
    void itShouldThrowBadRequest() throws Exception{
        VehicleRequest request = VehicleRequest
                .builder()
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/vehicle/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/vehicle/")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors",
                        Matchers.containsInAnyOrder("make cannot be null", "make cannot be blank", "model cannot be null", "model cannot be blank", "year cannot be null", "vin cannot be null", "vin cannot be blank", "job required")));

        request = VehicleRequest
                .builder()
                .make("")
                .model("")
                .year(3000)
                .vin("")
                .build();
        mapper = new ObjectMapper();
        content = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/vehicle/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/vehicle/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors",
                        Matchers.containsInAnyOrder("make cannot be blank", "model cannot be blank", "year cannot be more then 2050", "vin cannot be blank")));
    }
}