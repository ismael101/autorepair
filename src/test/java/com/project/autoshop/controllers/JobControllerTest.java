package com.project.autoshop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.autoshop.exceptions.ErrorExceptionHandler;
import com.project.autoshop.models.Job;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.request.JobRequest;
import com.project.autoshop.services.JobService;
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
class JobControllerTest {
    @Mock
    public JobRepository jobRepository;
    public JobService jobService;
    public JobController underTest;
    public MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        jobService = new JobService(jobRepository);
        underTest = new JobController(jobService);
        mockMvc = MockMvcBuilders.standaloneSetup(underTest)
                .setControllerAdvice(new ErrorExceptionHandler())
                .build();
    }

    @Test
    void itShouldGetJobs() throws Exception {
        when(jobRepository.findAll()).thenReturn(List.of(Job.builder().complete(false).description("mock description").build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/job"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].complete", Matchers.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].description", Matchers.is("mock description")));
    }

    @Test
    void itShouldGetJob() throws Exception {
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(Job.builder().complete(false).description("mock description").build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/job/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.complete", Matchers.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("mock description")));
    }

    @Test
    void itShouldCreateJob() throws Exception {
        JobRequest request = JobRequest
                .builder()
                .complete("false")
                .description("mock description")
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/job")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.complete", Matchers.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("mock description")));
    }

    @Test
    void itShouldUpdateJob() throws Exception {
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(Job.builder().complete(false).description("mock description").build()));
        JobRequest request = JobRequest
                .builder()
                .complete("true")
                .description("transmission issues")
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/job/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.complete", Matchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("transmission issues")));
    }

    @Test
    void itShouldDeleteJob() throws Exception {
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(Job.builder().build()));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/job/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void itShouldThrowNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/job/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/job/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("job with id: 1 not found")));

        JobRequest request = JobRequest
                .builder()
                .complete("true")
                .description("transmission issues")
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/job/1").content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/job/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("job with id: 1 not found")));
    }

    @Test
    void itShouldThrowBadRequest() throws Exception {
        JobRequest request = JobRequest
                .builder()
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/job/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/job/")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(400)))
                  .andExpect(MockMvcResultMatchers.jsonPath("$.errors",
                        Matchers.containsInAnyOrder("description cannot be null","complete cannot be null","description cannot be blank")));

        request = JobRequest
                .builder()
                .description("")
                .complete("mock")
                .build();
        mapper = new ObjectMapper();
        content = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/job/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/job/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors",
                        Matchers.containsInAnyOrder("description cannot be blank", "allowed input: true or false")));
    }

}