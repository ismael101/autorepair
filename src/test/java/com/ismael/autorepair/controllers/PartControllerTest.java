package com.ismael.autorepair.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismael.autorepair.exceptions.ErrorHandler;
import com.ismael.autorepair.requests.PartRequest;
import com.ismael.autorepair.services.PartService;
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
class PartControllerTest {
    @Mock
    PartService partService;
    PartController underTest;
    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        underTest = new PartController(partService);
        mockMvc = MockMvcBuilders.standaloneSetup(underTest)
                .setControllerAdvice(new ErrorHandler())
                .build();
    }

    @Test
    @WithMockUser(username = "username")
    void createPartMethodTest() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        UUID id = UUID.randomUUID();

        //section for testing valid part request
        PartRequest request = new PartRequest("piston tool","engine",100.00, "false", id.toString());
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/part")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        //section for testing null title, location, cost, work uuid
        request = new PartRequest();
        content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/part")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error",
                        Matchers.containsInAnyOrder("title cannot be null", "title cannot be blank", "location cannot be null", "location cannot be blank",
                                "cost cannot be null", "work cannot be null", "ordered cannot be null")));

        //section for testing blank title, location and invalid cost and work uuid
        request = new PartRequest("", "", -100.00,"not false", "invalid uuid");
        content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/part")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error",
                        Matchers.containsInAnyOrder("allowed input: true or false", "title cannot be blank", "location cannot be blank", "cost is invalid", "invalid uuid")));
    }

    @Test
    @WithMockUser(username = "username")
    void updatePartMethodTest() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        UUID id = UUID.randomUUID();

        //section for testing valid part request
        PartRequest request = new PartRequest("piston tool","engine",100.00,"false", id.toString());
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/part/" + id)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //section for testing blank title, location and cost and work uuid
        request = new PartRequest("","",-100.00, "not false", "invalid uuid");
        content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put( "/api/v1/part/" + id)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error",
                        Matchers.containsInAnyOrder("allowed input: true or false", "title cannot be blank", "location cannot be blank", "cost is invalid", "invalid uuid")));
    }

}