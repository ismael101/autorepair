package com.project.autorepair.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.autorepair.exceptions.ErrorExceptionHandler;
import com.project.autorepair.models.Insurance;
import com.project.autorepair.models.Job;
import com.project.autorepair.repositories.InsuranceRepository;
import com.project.autorepair.repositories.JobRepository;
import com.project.autorepair.request.InsuranceRequest;
import com.project.autorepair.services.InsuranceService;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InsuranceControllerTest {
    @Mock
    InsuranceRepository insuranceRepository;
    @Mock
    public JobRepository jobRepository;
    public InsuranceService insuranceService;
    public InsuranceController underTest;
    public MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        insuranceService = new InsuranceService(insuranceRepository, jobRepository);
        underTest = new InsuranceController(insuranceService) ;
        mockMvc = MockMvcBuilders.standaloneSetup(underTest)
                .setControllerAdvice(new ErrorExceptionHandler())
                .build();
    }

    @Test
    void itShouldGetInsurnace() throws Exception {
        when(insuranceRepository.findAll()).thenReturn(List.of(Insurance
                .builder()
                .policy("mock policy")
                .vin("mock vin")
                .provider("mock provider")
                .build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/insurance"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].policy", Matchers.is("mock policy")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].vin", Matchers.is("mock vin")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].provider", Matchers.is("mock provider")));
    }

    @Test
    void itShouldGetInsuranceById() throws Exception {
        when(insuranceRepository.findById(anyInt())).thenReturn(Optional.of(Insurance
                        .builder()
                        .policy("mock policy")
                        .vin("mock vin")
                        .provider("mock provider")
                        .build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/insurance/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.policy", Matchers.is("mock policy")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("mock vin")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.provider", Matchers.is("mock provider")));
    }

    @Test
    void itShouldGetInsuranceByJob() throws Exception{
        when(insuranceRepository.findInsuranceByJob(anyInt())).thenReturn(Optional.of(Insurance
                .builder()
                .policy("mock policy")
                .vin("mock vin")
                .provider("mock provider")
                .build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/insurance/job/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.policy", Matchers.is("mock policy")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("mock vin")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.provider", Matchers.is("mock provider")));
    }

    @Test
    void itShouldCreateInsurance() throws Exception{
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(Job.builder().id(1).build()));
        InsuranceRequest request = InsuranceRequest
                .builder()
                .policy("mock policy")
                .vin("mock vin")
                .provider("mock provider")
                .job(1)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/insurance/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.policy", Matchers.is("mock policy")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("mock vin")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.provider", Matchers.is("mock provider")));
    }

    @Test
    void itShouldUpdateInsurance() throws Exception{
        when(insuranceRepository.findById(anyInt())).thenReturn(Optional.of(Insurance
                .builder()
                .policy("mock policy")
                .vin("mock vin")
                .provider("mock provider")
                .build()));
        InsuranceRequest request = InsuranceRequest
                .builder()
                .policy("123456")
                .vin("ABCDEF")
                .provider("geico")
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/insurance/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.policy", Matchers.is("123456")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("ABCDEF")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.provider", Matchers.is("geico")));
    }

    @Test
    void itShouldDeleteInsurance() throws Exception{
        when(insuranceRepository.findById(anyInt())).thenReturn(Optional.of(Insurance
                .builder()
                .build()));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/insurance/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void itShouldThrowNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/insurance/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/insurance/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("insurance with id: 1 not found")));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/insurance/job/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/insurance/job/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("insurance with job id: 1 not found")));

        InsuranceRequest request = InsuranceRequest
                .builder()
                .policy("123456")
                .vin("ABCDEF")
                .provider("geico")
                .job(1)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/insurance/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/insurance/")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("job with id: 1 not found")));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/insurance/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/insurance/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("insurance with id: 1 not found")));


        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/insurance/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/insurance/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("insurance with id: 1 not found")));
    }

    @Test
    void itShouldThrowBadRequest() throws Exception{
        InsuranceRequest request = InsuranceRequest
                .builder()
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/insurance/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/insurance/")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors",
                        Matchers.containsInAnyOrder("policy cannot be null", "policy cannot be blank", "provider cannot be null", "provider cannot be blank", "vin cannot be null", "vin cannot be blank", "job cannot be null")));

        request = InsuranceRequest
                .builder()
                .policy("")
                .vin("")
                .provider("")
                .build();
        mapper = new ObjectMapper();
        content = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/insurance/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/insurance/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors",
                        Matchers.containsInAnyOrder("policy cannot be blank", "provider cannot be blank", "vin cannot be blank")));
    }
}