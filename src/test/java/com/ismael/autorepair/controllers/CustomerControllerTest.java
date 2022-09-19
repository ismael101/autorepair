package com.ismael.autorepair.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismael.autorepair.exceptions.ErrorHandler;
import com.ismael.autorepair.requests.CustomerRequest;
import com.ismael.autorepair.services.CustomerService;
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
class CustomerControllerTest {
    @Mock
    CustomerService customerService;
    CustomerController underTest;
    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        underTest = new CustomerController(customerService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(underTest)
                .setControllerAdvice(new ErrorHandler())
                .build();
    }

    @Test
    @WithMockUser(username = "username")
    void createCustomerMethodTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        //section for testing valid customer request
        UUID id = UUID.randomUUID();
        CustomerRequest request = new CustomerRequest("ismael", "mohamed", "ismaelmohamed@gmail.com", 6122492278l, id.toString());
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        //section for testing null first, last, email, phone, work
        request = new CustomerRequest();
        content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error",
                        Matchers.containsInAnyOrder("first cannot be null", "first cannot be blank", "last cannot be null", "last cannot be blank", "email cannot be null",
                                "phone cannot be null", "work cannot be null")));

        //section for testing blank title, last, email and invalid phone number and work uuid
        request = new CustomerRequest("", "", "ismael", 12345632l, "invalid uuid");
        content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.containsInAnyOrder("first cannot be blank", "last cannot be blank", "email is invalid", "phone number is invalid", "invalid uuid")));
    }

    @Test
    @WithMockUser(username = "username")
    void updateCustomerMethodTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        //section for testing valid customer request
        UUID id = UUID.randomUUID();
        CustomerRequest request = new CustomerRequest("ismael", "mohamed", "ismaelmohamed@gmail.com", 6122492278l, id.toString());
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customer/" + id)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //section for testing blank first, last and invalid email, phone number and work uuid
        request = new CustomerRequest("", "", "ismaelgmail.com", 78942221l, "invalid uuid");
        content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put( "/api/v1/customer/" + id)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.containsInAnyOrder("first cannot be blank", "last cannot be blank", "email is invalid", "phone number is invalid", "invalid uuid")));

    }

}