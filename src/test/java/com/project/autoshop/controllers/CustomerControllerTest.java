package com.project.autoshop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.autoshop.exceptions.ErrorExceptionHandler;
import com.project.autoshop.models.Customer;
import com.project.autoshop.models.Job;
import com.project.autoshop.repositories.CustomerRepository;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.request.CustomerRequest;
import com.project.autoshop.services.CustomerService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    @Mock
    public CustomerRepository customerRepository;
    @Mock
    public JobRepository jobRepository;
    public CustomerService customerService;
    public CustomerController underTest;
    public MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        customerService = new CustomerService(customerRepository, jobRepository);
        underTest = new CustomerController(customerService);
        mockMvc = MockMvcBuilders.standaloneSetup(underTest)
                .setControllerAdvice(new ErrorExceptionHandler())
                .build();
    }

    @Test
    void itShouldGetCustomers() throws Exception {
        when(customerRepository.findAll()).thenReturn(List.of(Customer
                .builder()
                .email("mock email")
                .first("mock first")
                .last("mock last")
                .phone("7632275152")
                .job(new Job())
                .build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customer"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].email", Matchers.is("mock email")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].first", Matchers.is("mock first")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].last", Matchers.is("mock last")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].phone", Matchers.is("7632275152")));
    }

    @Test
    void itShouldGetCustomerById() throws Exception {
        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(Customer
                .builder()
                .email("mock email")
                .first("mock first")
                .last("mock last")
                .phone("7632275152")
                .job(new Job())
                .build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customer/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("mock email")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.first", Matchers.is("mock first")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last", Matchers.is("mock last")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone", Matchers.is("7632275152")));
    }

    @Test
    void itShouldGetCustomerByJob() throws Exception{
        when(customerRepository.findCustomerByJob(anyInt())).thenReturn(Optional.of(Customer
                .builder()
                .email("mock email")
                .first("mock first")
                .last("mock last")
                .phone("7632275152")
                .job(new Job())
                .build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customer/job/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("mock email")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.first", Matchers.is("mock first")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last", Matchers.is("mock last")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone", Matchers.is("7632275152")));
    }

    @Test
    void itShouldCreateCustomer() throws Exception{
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(Job.builder().id(1).build()));
        CustomerRequest request = CustomerRequest
                .builder()
                .first("mock first")
                .last("mock last")
                .email("ismaelomermohamed@gmail.com")
                .phone("7632275152")
                .job(1)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("ismaelomermohamed@gmail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.first", Matchers.is("mock first")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last", Matchers.is("mock last")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone", Matchers.is("7632275152")));
    }

    @Test
    void itShouldUpdateAddress() throws Exception{
        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(Customer
                .builder()
                .email("ismaelomermohamed@gmail.com")
                .first("mock first")
                .last("mock last")
                .phone("7632275152")
                .job(new Job())
                .build()));
        CustomerRequest request = CustomerRequest
                .builder()
                .email("qaalibomermohamed@gmail.com")
                .first("qaalib")
                .last("farah")
                .phone("7632275251")
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customer/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("qaalibomermohamed@gmail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.first", Matchers.is("qaalib")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last", Matchers.is("farah")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone", Matchers.is("7632275251")));
    }

    @Test
    void itShouldDeleteCustomer() throws Exception{
        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(Customer
                .builder()
                .email("mock email")
                .first("mock first")
                .last("mock last")
                .phone("7632275152")
                .job(new Job())
                .build()));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customer/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void itShouldThrowNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customer/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/customer/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("customer with id: 1 not found")));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customer/job/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/customer/job/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("customer with job id: 1 not found")));

        CustomerRequest request = CustomerRequest
                .builder()
                .email("qaalibomermohamed@gmail.com")
                .first("qaalib")
                .last("farah")
                .phone("7632275251")
                .job(1)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/customer/")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("job with id: 1 not found")));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customer/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/customer/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("customer with id: 1 not found")));


        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customer/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/customer/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("customer with id: 1 not found")));
    }

    @Test
    void itShouldThrowBadRequest() throws Exception{
        CustomerRequest request = CustomerRequest
                .builder()
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/customer/")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors",
                        Matchers.containsInAnyOrder("first cannot be null","last cannot be null","email cannot be null","first cannot be blank","last cannot be blank", "email cannot be blank", "phone cannot be null","phone cannot be blank","job required")));

        request = CustomerRequest
                .builder()
                .email("ajsdncajsndckansmdc")
                .first("")
                .last("")
                .phone("")
                .build();
        mapper = new ObjectMapper();
        content = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customer/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/customer/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors",
                        Matchers.containsInAnyOrder("first cannot be blank", "last cannot be blank", "email is invalid", "phone cannot be blank")));
    }

}