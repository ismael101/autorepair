package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Customer;
import com.project.autoshop.models.Job;
import com.project.autoshop.repositories.CustomerRepository;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.request.CustomerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    public CustomerRepository customerRepository;
    @Mock
    public JobRepository jobRepository;
    public CustomerService underTest;

    @BeforeEach
    void setUp(){
        underTest = new CustomerService(customerRepository, jobRepository);
    }

    @Test
    void itShouldGetAllCustomers(){
        underTest.getCustomers();
        verify(customerRepository).findAll();
    }

    @Test
    void itShouldGetCustomerById(){
        Customer customer = Customer
                .builder()
                .first("ismael")
                .last("mohamed")
                .email("ismaelomermohamed@gmail.com")
                .phone(7632275152l)
                .job(new Job())
                .build();
        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));
        underTest.getCustomer(1);
        verify(customerRepository).findById(1);
        assertEquals(underTest.getCustomer(1).getFirst(), "ismael");
        assertEquals(underTest.getCustomer(1).getLast(), "mohamed");
        assertEquals(underTest.getCustomer(1).getEmail(), "ismaelomermohamed@gmail.com");
        assertEquals(underTest.getCustomer(1).getPhone(), 7632275152l);
    }

    @Test
    void itShouldGetCustomerByJob(){
        Customer customer = Customer
                .builder()
                .first("ismael")
                .last("mohamed")
                .email("ismaelomermohamed@gmail.com")
                .phone(7632275152l)
                .job(new Job())
                .build();
        when(customerRepository.findCustomerByJob(anyInt())).thenReturn(Optional.of(customer));
        underTest.getJobCustomer(1);
        verify(customerRepository).findCustomerByJob(1);
        assertEquals(underTest.getJobCustomer(1).getFirst(), "ismael");
        assertEquals(underTest.getJobCustomer(1).getLast(), "mohamed");
        assertEquals(underTest.getJobCustomer(1).getEmail(), "ismaelomermohamed@gmail.com");
        assertEquals(underTest.getJobCustomer(1).getPhone(), 7632275152l);
    }

    @Test
    void itShouldCreateCustomer(){
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(Job.builder().build()));
        Customer customer = underTest.createCustomer(CustomerRequest
                .builder()
                .first("ismael")
                .last("mohamed")
                .email("ismaelomermohamed@gmail.com")
                .phone(7632275152l)
                .job(1)
                .build()
        );
        verify(jobRepository).findById(1);
        verify(customerRepository).save(customer);
        assertEquals(customer.getFirst(), "ismael");
        assertEquals(customer.getLast(), "mohamed");
        assertEquals(customer.getEmail(), "ismaelomermohamed@gmail.com");
        assertEquals(customer.getPhone(), 7632275152l);
    }

    @Test
    void itShouldUpdateCustomer(){
        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(Customer
                .builder()
                .first("ismael")
                .last("mohamed")
                .email("ismaelomermohamed@gmail.com")
                .phone(7632275152l)
                .job(new Job())
                .build()
        ));
        Customer customer = underTest.updateCustomer(1, CustomerRequest
                .builder()
                .first("qaalib")
                .last("farah")
                .email("qaalibomerfarah@gmail.com")
                .phone(7632275251l)
                .build()
        );
        verify(customerRepository).findById(1);
        assertEquals(customer.getFirst(), "qaalib");
        assertEquals(customer.getLast(), "farah");
        assertEquals(customer.getEmail(), "qaalibomerfarah@gmail.com");
        assertEquals(customer.getPhone(), 7632275251l);
    }

    @Test
    void itShouldDeleteCustomer(){
        Customer customer = Customer
                .builder()
                .build();
        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));
        underTest.deleteCustomer(1);
        verify(customerRepository).findById(1);
        verify(customerRepository).delete(customer);
    }

    @Test
    void itShouldThrowNotFound(){
        Exception exception = assertThrows(NotFoundException.class, () -> {
            underTest.getJobCustomer(1);
        });
        assertEquals(exception.getMessage(), "customer with job id: 1 not found");
        exception = assertThrows(NotFoundException.class, () -> {
            underTest.getCustomer(1);
        });
        assertEquals(exception.getMessage(), "customer with id: 1 not found");

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.createCustomer(CustomerRequest.builder().job(1).build());
        });
        assertEquals(exception.getMessage(), "job with id: 1 not found");

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.updateCustomer(1, CustomerRequest.builder().build());
        });
        assertEquals(exception.getMessage(), "customer with id: 1 not found");

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.deleteCustomer(1);
        });
        assertEquals(exception.getMessage(), "customer with id: 1 not found");

    }


}