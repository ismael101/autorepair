package com.project.autoshop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.autoshop.exceptions.ErrorExceptionHandler;
import com.project.autoshop.models.Job;
import com.project.autoshop.models.Part;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.repositories.PartRepository;
import com.project.autoshop.request.PartRequest;
import com.project.autoshop.services.PartService;
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
class PartControllerTest {
    @Mock
    JobRepository jobRepository;
    @Mock
    PartRepository partRepository;
    PartService partService;
    PartController underTest;
    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        partService = new PartService(partRepository, jobRepository);
        underTest = new PartController(partService);
        mockMvc = MockMvcBuilders.standaloneSetup(underTest)
                .setControllerAdvice(new ErrorExceptionHandler())
                .build();
    }

    @Test
    void itShouldGetParts() throws Exception {
        when(partRepository.findAll()).thenReturn(List.of(Part.builder()
                .name("mock name")
                .description("mock description")
                .location("mock location")
                .website("https://www.mock.com")
                .cost(100.00)
                .ordered(false)
                .notes("mock notes")
                .build()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/part"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].name", Matchers.is("mock name")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].description", Matchers.is("mock description")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].location", Matchers.is("mock location")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].website", Matchers.is("https://www.mock.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].cost", Matchers.is(100.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].ordered", Matchers.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].notes", Matchers.is("mock notes")));
    }

    @Test
    void itShouldGetPart() throws Exception {
        when(partRepository.findById(anyInt())).thenReturn(Optional.of(Part.builder()
                .name("mock name")
                .description("mock description")
                .location("mock location")
                .website("https://www.mock.com")
                .cost(100.00)
                .ordered(false)
                .notes("mock notes")
                .build()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/part/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("mock name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("mock description")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location", Matchers.is("mock location")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.website", Matchers.is("https://www.mock.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cost", Matchers.is(100.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ordered", Matchers.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.notes", Matchers.is("mock notes")));
    }

    @Test
    void itShouldGetJobParts() throws Exception {
        when(partRepository.findPartsByJob(anyInt())).thenReturn(List.of(Part.builder()
                .name("mock name")
                .description("mock description")
                .location("mock location")
                .website("https://www.mock.com")
                .cost(100.00)
                .ordered(false)
                .notes("mock notes")
                .build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/part/job/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].name", Matchers.is("mock name")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].description", Matchers.is("mock description")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].location", Matchers.is("mock location")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].website", Matchers.is("https://www.mock.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].cost", Matchers.is(100.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].ordered", Matchers.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].notes", Matchers.is("mock notes")));
    }

    @Test
    void itShouldCreatePart() throws Exception {
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(Job.builder().id(1).build()));
        PartRequest request = PartRequest
                .builder()
                .name("mock name")
                .description("mock description")
                .location("mock location")
                .website("https://www.mock.com")
                .cost(100.00)
                .ordered("false")
                .notes("mock notes")
                .job(1)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/part/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("mock name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("mock description")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location", Matchers.is("mock location")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.website", Matchers.is("https://www.mock.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cost", Matchers.is(100.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ordered", Matchers.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.notes", Matchers.is("mock notes")));
    }

    @Test
    void itShouldUpdatePart() throws Exception {
        when(partRepository.findById(anyInt())).thenReturn(Optional.of(Part.builder()
                .name("mock name")
                .description("mock description")
                .location("mock location")
                .website("https://www.mock.com")
                .cost(100.00)
                .ordered(false)
                .notes("mock notes")
                .build()));

        PartRequest request = PartRequest
                .builder()
                .name("transmission")
                .description("transmission replacement")
                .location("engine")
                .website("https://www.part.com")
                .cost(200.00)
                .ordered("true")
                .notes("was hard")
                .job(1)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/part/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("transmission")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("transmission replacement")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location", Matchers.is("engine")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.website", Matchers.is("https://www.part.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cost", Matchers.is(200.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ordered", Matchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.notes", Matchers.is("was hard")));
    }

    @Test
    void itShouldDeletePart() throws Exception {
        when(partRepository.findById(anyInt())).thenReturn(Optional.of(Part.builder().build()));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/part/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void itShouldThrowNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/part/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/part/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("part with id: 1 not found")));

        PartRequest request = PartRequest
                .builder()
                .name("mock name")
                .description("mock description")
                .location("mock location")
                .website("https://www.mock.com")
                .cost(100.00)
                .ordered("false")
                .notes("mock notes")
                .job(1)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/part/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/part/")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("job with id: 1 not found")));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/part/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/part/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("part with id: 1 not found")));


        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/part/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/part/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("part with id: 1 not found")));
    }

    @Test
    void itShouldThrowBadRequest() throws Exception{
        PartRequest request = PartRequest
                .builder()
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/part/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/part/")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors",
                        Matchers.containsInAnyOrder("name cannot be null","description cannot be null","location cannot be null","website cannot be null" ,"website cannot be blank","cost cannot be null","ordered cannot be null","notes cannot be null" ,"description cannot be blank","location cannot be blank","job required")));
        request = PartRequest
                .builder()
                .name("")
                .description("")
                .location("")
                .website("mock")
                .cost(100.00)
                .ordered("")
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
                        Matchers.containsInAnyOrder("task cannot be blank", "description cannot be blank", "location cannot be blank", "notes cannot be blank", "cost cannot be less then 0")));
    }
}
