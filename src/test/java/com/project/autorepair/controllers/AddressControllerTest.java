package com.project.autorepair.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.autorepair.exceptions.ErrorExceptionHandler;
import com.project.autorepair.models.Address;
import com.project.autorepair.models.Job;
import com.project.autorepair.repositories.AddressRepository;
import com.project.autorepair.repositories.JobRepository;
import com.project.autorepair.request.AddressRequest;
import com.project.autorepair.services.AddressService;
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
class AddressControllerTest {
    @Mock
    public AddressRepository addressRepository;
    @Mock
    public JobRepository jobRepository;
    public AddressService addressService;
    public AddressController underTest;
    public MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        addressService = new AddressService(addressRepository, jobRepository);
        underTest = new AddressController(addressService);
        mockMvc = MockMvcBuilders.standaloneSetup(underTest)
                .setControllerAdvice(new ErrorExceptionHandler())
                .build();
    }

    @Test
    void itShouldGetAddresses() throws Exception {
        when(addressRepository.findAll()).thenReturn(List.of(Address
                .builder()
                .street("mock street")
                .city("mock city")
                .state("mock state")
                .zipcode(55432)
                .job(new Job())
                .build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/address"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].street", Matchers.is("mock street")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].city", Matchers.is("mock city")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].state", Matchers.is("mock state")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].zipcode", Matchers.is(55432)));
    }

    @Test
    void itShouldGetAddressByJob() throws Exception{
        when(addressRepository.findAddressByJob(anyInt())).thenReturn(Optional.of(Address
                .builder()
                .street("mock street")
                .city("mock city")
                .state("mock state")
                .zipcode(55432)
                .job(new Job())
                .build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/address/job/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.street", Matchers.is("mock street")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city", Matchers.is("mock city")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state", Matchers.is("mock state")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.zipcode", Matchers.is(55432)));
    }

    @Test
    void itShouldGetAddressById() throws Exception{
        when(addressRepository.findById(anyInt())).thenReturn(Optional.of(Address
                .builder()
                .street("mock street")
                .city("mock city")
                .state("mock state")
                .zipcode(55432)
                .job(new Job())
                .build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/address/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.street", Matchers.is("mock street")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city", Matchers.is("mock city")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state", Matchers.is("mock state")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.zipcode", Matchers.is(55432)));
    }

    @Test
    void itShouldCreateAddress() throws Exception{
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(Job.builder().id(1).build()));
        AddressRequest request = AddressRequest
                .builder()
                .city("mock city")
                .street("mock street")
                .state("mock state")
                .zipcode(55432)
                .job(1)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/address/")
                        .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.street", Matchers.is("mock street")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city", Matchers.is("mock city")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state", Matchers.is("mock state")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.zipcode", Matchers.is(55432)));
    }

    @Test
    void itShouldUpdateAddress() throws Exception{
        when(addressRepository.findById(anyInt())).thenReturn(Optional.of(Address
                .builder()
                .street("mock street")
                .city("mock city")
                .state("mock state")
                .zipcode(55432)
                .job(new Job())
                .build()));
        AddressRequest request = AddressRequest
                .builder()
                .city("minneapolis")
                .street("washington street")
                .state("minnesota")
                .zipcode(55555)
                .job(1)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/address/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.street", Matchers.is("washington street")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city", Matchers.is("minneapolis")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state", Matchers.is("minnesota")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.zipcode", Matchers.is(55555)));
    }

    @Test
    void itShouldDeleteAddress() throws Exception{
        when(addressRepository.findById(anyInt())).thenReturn(Optional.of(Address
                .builder()
                .build()));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/address/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void itShouldThrowNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/address/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/address/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("address with id: 1 not found")));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/address/job/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/address/job/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("address with job id: 1 not found")));

        AddressRequest request = AddressRequest
                .builder()
                .city("mock city")
                .street("mock street")
                .state("mock state")
                .zipcode(55432)
                .job(1)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/address/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/address/")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("job with id: 1 not found")));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/address/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/address/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("address with id: 1 not found")));


        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/address/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/address/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("address with id: 1 not found")));
    }
    @Test
    void itShouldThrowBadRequest() throws Exception{
        AddressRequest request = AddressRequest
                .builder()
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/address/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/address/")))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(400)))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.errors",
                                Matchers.containsInAnyOrder("city cannot be null","zipcode cannot be null","state cannot be null","city cannot be blank","street cannot be blank", "state cannot be blank","job required","street cannot be null")));

        request = AddressRequest
                .builder()
                .street("")
                .state("")
                .city("")
                .zipcode(1000000)
                .build();
        mapper = new ObjectMapper();
        content = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/address/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/address/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors",
                        Matchers.containsInAnyOrder("street cannot be blank", "state cannot be blank", "city cannot be blank", "zipcode is invalid")));
    }
}