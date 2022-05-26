package com.project.autoshop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.autoshop.exceptions.ErrorExceptionHandler;
import com.project.autoshop.models.Job;
import com.project.autoshop.models.Labor;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.repositories.LaborRepository;
import com.project.autoshop.request.LaborRequest;
import com.project.autoshop.services.LaborService;
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
class LaborControllerTest {
    @Mock
    JobRepository jobRepository;
    @Mock
    LaborRepository laborRepository;
    LaborService laborService;
    LaborController underTest;
    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        laborService = new LaborService(laborRepository, jobRepository);
        underTest = new LaborController(laborService);
        mockMvc = MockMvcBuilders.standaloneSetup(underTest)
                .setControllerAdvice(new ErrorExceptionHandler())
                .build();
    }

    @Test
    void itShouldGetLabors() throws Exception {
        when(laborRepository.findAll()).thenReturn(List.of(Labor.builder()
                .task("mock task")
                .description("mock description")
                .location("mock location")
                .cost(100.00)
                .notes("mock notes")
                .build()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/labor"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].task", Matchers.is("mock task")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].description", Matchers.is("mock description")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].location", Matchers.is("mock location")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].cost", Matchers.is(100.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].notes", Matchers.is("mock notes")));
    }

    @Test
    void itShouldGetLabor() throws Exception {
        when(laborRepository.findById(anyInt())).thenReturn(Optional.of(Labor.builder()
                .task("mock task")
                .description("mock description")
                .location("mock location")
                .cost(100.00)
                .notes("mock notes")
                .build()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/labor/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.task", Matchers.is("mock task")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("mock description")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location", Matchers.is("mock location")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cost", Matchers.is(100.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.notes", Matchers.is("mock notes")));
    }

    @Test
    void itShouldGetJobLabors() throws Exception {
        when(laborRepository.findLaborsByJob(anyInt())).thenReturn(List.of(Labor.builder()
                .task("mock task")
                .description("mock description")
                .location("mock location")
                .cost(100.00)
                .notes("mock notes")
                .build()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/labor/job/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].task", Matchers.is("mock task")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].description", Matchers.is("mock description")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].location", Matchers.is("mock location")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].cost", Matchers.is(100.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].notes", Matchers.is("mock notes")));
    }

    @Test
    void itShouldCreateLabor() throws Exception {
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(Job.builder().id(1).build()));
        LaborRequest request = LaborRequest
                .builder()
                .task("mock task")
                .description("mock description")
                .location("mock location")
                .cost(100.00)
                .notes("mock notes")
                .job(1)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/labor/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.task", Matchers.is("mock task")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("mock description")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location", Matchers.is("mock location")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cost", Matchers.is(100.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.notes", Matchers.is("mock notes")));
    }

    @Test
    void itShouldUpdateLabor() throws Exception {
        when(laborRepository.findById(anyInt())).thenReturn(Optional.of(Labor.builder()
                .task("mock task")
                .description("mock description")
                .location("mock location")
                .cost(100.00)
                .notes("mock notes")
                .build()));
        LaborRequest request = LaborRequest
                .builder()
                .task("transmission fix")
                .description("fixed transmission in engine")
                .location("engine")
                .cost(200.00)
                .notes("was hard")
                .job(1)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/labor/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.task", Matchers.is("transmission fix")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("fixed transmission in engine")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location", Matchers.is("engine")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cost", Matchers.is(200.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.notes", Matchers.is("was hard")));
    }

    @Test
    void itShouldDeleteLabor() throws Exception {
        when(laborRepository.findById(anyInt())).thenReturn(Optional.of(Labor.builder().build()));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/labor/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void itShouldThrowNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/labor/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/labor/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("labor with id: 1 not found")));

        LaborRequest request = LaborRequest
                .builder()
                .task("transmission fix")
                .description("fixed transmission in engine")
                .location("engine")
                .cost(200.00)
                .notes("was hard")
                .job(1)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/labor/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/labor/")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("job with id: 1 not found")));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/labor/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/labor/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("labor with id: 1 not found")));


        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/labor/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/labor/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("labor with id: 1 not found")));
    }

    @Test
    void itShouldThrowBadRequest() throws Exception{
        LaborRequest request = LaborRequest
                .builder()
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/labor/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/labor/")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors",
                        Matchers.containsInAnyOrder("task cannot be null", "task cannot be blank", "location cannot be null", "location cannot be blank", "description cannot be null", "description cannot be blank", "cost cannot be null", "notes cannot be null", "job required")));

        request = LaborRequest
                .builder()
                .task("")
                .description("")
                .location("")
                .cost(-100.00)
                .notes("")
                .job(1)
                .build();
        mapper = new ObjectMapper();
        content = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/labor/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/labor/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors",
                        Matchers.containsInAnyOrder("task cannot be blank", "description cannot be blank", "location cannot be blank", "cost cannot be less then 0")));
    }

}