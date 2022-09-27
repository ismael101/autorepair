package com.ismael.autorepair.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismael.autorepair.exceptions.ErrorHandler;
import com.ismael.autorepair.requests.InsuranceRequest;
import com.ismael.autorepair.services.InsuranceService;
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
class InsuranceControllerTest {
    @Mock
    InsuranceService insuranceService;
    InsuranceController underTest;
    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        underTest = new InsuranceController(insuranceService);
        mockMvc = MockMvcBuilders.standaloneSetup(underTest)
                .setControllerAdvice(new ErrorHandler())
                .build();
    }

    @Test
    @WithMockUser(username = "username")
    void createInsuranceMethodTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UUID id = UUID.randomUUID();

        //section for testing valid insurance request
        InsuranceRequest request = new InsuranceRequest("provider", 7894568743l, "vin" , id.toString());
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/insurance")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        //section for testing null provider, license, policy, vin, work
        request = new InsuranceRequest();
        content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/insurance")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error",
                        Matchers.containsInAnyOrder("provider cannot be null", "provider cannot be blank", "policy cannot be null",
                                 "vin cannot be null",  "vin cannot be blank", "work cannot be null")));

        //section for testing blank provider license, policy, vin and invalid work uuid
        request = new InsuranceRequest("", 0l, "", "invalid uuid");
        content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/insurance")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.containsInAnyOrder("provider cannot be blank", "policy number is invalid", "vin cannot be blank", "invalid uuid")));
    }

    @Test
    @WithMockUser(username = "username")
    void updateInsuranceMethodTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UUID id = UUID.randomUUID();

        //section for testing valid insurance request
        InsuranceRequest request = new InsuranceRequest("provider", 7894568743l, "vin" , id.toString());
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/insurance/" + id)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //section for testing blank provider license, policy, vin and invalid work uuid
        request = new InsuranceRequest("", 0l, "" , "invalid uuid");
        content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put( "/api/v1/insurance/" + id)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.containsInAnyOrder("provider cannot be blank", "policy number is invalid", "vin cannot be blank", "invalid uuid")));
    }

}