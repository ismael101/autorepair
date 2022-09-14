package com.ismael.autorepair.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismael.autorepair.exceptions.ErrorHandler;
import com.ismael.autorepair.requests.LaborRequest;
import com.ismael.autorepair.services.LaborService;
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
class LaborControllerTest {
    @Mock
    LaborService laborService;
    LaborController underTest;
    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        underTest = new LaborController(laborService);
        mockMvc = MockMvcBuilders.standaloneSetup(underTest)
                .setControllerAdvice(new ErrorHandler())
                .build();
    }

    @Test
    @WithMockUser(username = "username")
    void createLaborMethodTest() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        UUID id = UUID.randomUUID();

        //section for testing valid labor request
        LaborRequest request = new LaborRequest("changing piston","engine",100.00, id.toString());
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/labor")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        //section for testing null task, location, cost, work uuid
        request = new LaborRequest();
        content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/labor")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors",
                        Matchers.containsInAnyOrder("task cannot be null", "task cannot be blank", "location cannot be null", "location cannot be blank",
                                "cost cannot be null", "work cannot be null")));

        //section for testing blank task location and invalid cost and work uuid
        request = new LaborRequest("", "", -100.00, "invalid uuid");
        content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/labor")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors",
                        Matchers.containsInAnyOrder("task cannot be blank", "location cannot be blank", "cost is invalid", "invalid uuid")));
    }

    @Test
    @WithMockUser(username = "username")
    void updateLaborMethodTest() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        UUID id = UUID.randomUUID();

        //section for testing valid insurance request
        LaborRequest request = new LaborRequest("changing piston","engine",100.00, id.toString());
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/labor/" + id)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //section for testing blank task, location and cost and work uuid
        request = new LaborRequest("","",-100.00, "invalid uuid");
        content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put( "/api/v1/labor/" + id)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors",
                        Matchers.containsInAnyOrder("task cannot be blank", "location cannot be blank", "cost is invalid", "invalid uuid")));
    }

}