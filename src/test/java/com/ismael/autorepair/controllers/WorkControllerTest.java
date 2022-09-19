package com.ismael.autorepair.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismael.autorepair.exceptions.ErrorHandler;
import com.ismael.autorepair.requests.WorkRequest;
import com.ismael.autorepair.services.WorkService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.UUID;

@SpringJUnitConfig
@ExtendWith(MockitoExtension.class)
class WorkControllerTest {
    @Mock
    WorkService workService;
    WorkController underTest;
    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        underTest = new WorkController(workService);
        mockMvc = MockMvcBuilders.standaloneSetup(underTest)
                .setControllerAdvice(new ErrorHandler())
                .build();
    }

    @Test
    @WithMockUser(username = "username")
    void createWorkMethodTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        //section for testing valid work request
        WorkRequest request = new WorkRequest("title", "description", "false");
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/work")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        //section for testing null title and description
        request = new WorkRequest();
        content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/work")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error",
                        Matchers.containsInAnyOrder("complete cannot be null", "title cannot be null", "title cannot be blank", "description cannot be null", "description cannot be blank")));

        //section for testing blank title and description
        request = new WorkRequest("", "", "not false");
        content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/work")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error",
                        Matchers.containsInAnyOrder("allowed input: true or false", "title cannot be blank", "description cannot be blank")));
    }

    @Test
    @WithMockUser(username = "username")
    void updateWorkMethodTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UUID id = UUID.randomUUID();

        //section for testing valid work request
        WorkRequest request = new WorkRequest("title", "description", "false");
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/work/" + id)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //section for testing blank title and description
        request = new WorkRequest("", "", "not false");
        content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put( "/api/v1/work/" + id)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error",
                        Matchers.containsInAnyOrder("allowed input: true or false","title cannot be blank", "description cannot be blank")));
    }


}