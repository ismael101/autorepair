package com.ismael.autorepair.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismael.autorepair.exceptions.ErrorHandler;
import com.ismael.autorepair.requests.VehicleRequest;
import com.ismael.autorepair.services.VehicleService;
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
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class VehicleControllerTest {
    @Mock
    VehicleService vehicleService;
    VehicleController underTest;
    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        underTest = new VehicleController(vehicleService);
        mockMvc = MockMvcBuilders.standaloneSetup(underTest)
                .setControllerAdvice(new ErrorHandler())
                .build();
    }

    @Test
    void createVehicleMethodTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UUID id = UUID.randomUUID();

        //section for testing valid vehicle request
        VehicleRequest request = new VehicleRequest("toyota", "camry", 2017, id.toString());
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/vehicle")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        //section for testing null make, model, year, work uuid
        request = new VehicleRequest();
        content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/vehicle")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors",
                        Matchers.containsInAnyOrder("make cannot be null", "make cannot be blank", "model cannot be null", "model cannot be blank", "year cannot be null", "work cannot be null")));

        //section for testing null, make, model and invalid year, work uuid
        request = new VehicleRequest("", "", 3000,  "invalid uuid");
        content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/vehicle")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors",
                        Matchers.containsInAnyOrder("make cannot be blank", "model cannot be blank", "year is invalid", "invalid uuid")));
    }

    @Test
    void updateVehicleMethodTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UUID id = UUID.randomUUID();

        //section for testing valid vehicle request
        VehicleRequest request = new VehicleRequest("toyota", "camry", 2017, id.toString());
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/vehicle/" + id)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //section for testing blank make, model and invalid year, work uuid
        request = new VehicleRequest("", "", 1300, "invalid uuid");
        content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put( "/api/v1/vehicle/" + id)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", Matchers.containsInAnyOrder("make cannot be blank", "model cannot be blank", "year is invalid", "invalid uuid")));
    }


}