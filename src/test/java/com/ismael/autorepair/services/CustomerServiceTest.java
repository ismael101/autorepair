package com.ismael.autorepair.services;

import com.ismael.autorepair.exceptions.AlreadyExists;
import com.ismael.autorepair.exceptions.NotFound;
import com.ismael.autorepair.exceptions.UnauthorizedAction;
import com.ismael.autorepair.models.Customer;
import com.ismael.autorepair.models.User;
import com.ismael.autorepair.models.Work;
import com.ismael.autorepair.repositories.CustomerRepository;
import com.ismael.autorepair.repositories.UserRepository;
import com.ismael.autorepair.repositories.WorkRepository;
import com.ismael.autorepair.requests.CustomerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    WorkRepository workRepository;
    @Mock
    CustomerRepository customerRepository;
    CustomerService underTest;

    @BeforeEach
    void setUp(){
        underTest = new CustomerService(customerRepository, workRepository, userRepository);
    }

    @Test
    @WithMockUser(username = "username")
    void getCustomersMethodTest(){

        //section for testing successfully fetching all customer belonging to user
        User user = new User("username", "password");
        Work work = new Work("title", "description",false,  user);
        Customer customer = new Customer("first", "last", "email@gmail.com",6123452343l , work);
        work.setCustomer(customer);
        when(userRepository.findUserByUsername("username")).thenReturn(Optional.of(user));
        when(workRepository.findWorkByUser(user)).thenReturn(List.of(work));
        Map<String, Object> response = underTest.getCustomers();
        verify(userRepository).findUserByUsername("username");
        verify(workRepository).findWorkByUser(user);
        assertEquals(response.get("path"), "/api/v1/customer");
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("customers"), List.of(customer));
    }

    @Test
    @WithMockUser(username = "username")
    void getCustomerByIdMethodTest(){

        //section for testing successfully fetching customer by id that belongs to user
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description", false, user);
        Customer customer = new Customer("first", "last", "email@gmail.com",6123452343l , work);
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        Map<String, Object> response = underTest.getCustomerById(id);
        verify(customerRepository).findById(id);
        assertEquals(response.get("path"), "/api/v1/customer/" + id);
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("customer"), customer);

        //section for testing user fetching customer that doesn't belong to them
        user = new User("ismael101", "password");
        work = new Work("title", "description", false, user);
        customer = new Customer("first", "last", "email@gmail.com",6123452343l , work);
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
            underTest.getCustomerById(id);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing user fetching customer that doesn't exist
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
            underTest.getCustomerById(id);
        });
        assertEquals(exception.getMessage(), "customer with id: " + id + " not found");
    }

    @Test
    @WithMockUser(username = "username")
    void getCustomerByWorkMethodTest(){

        //section for testing successfully fetching customer with work id
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description", false, user);
        work.setId(id);
        Customer customer = new Customer("first", "last", "email@gmail.com",7632275152l , work);
        work.setCustomer(customer);
        when(workRepository.findById(id)).thenReturn(Optional.of(work));
        Map<String, Object> response = underTest.getCustomerByWork(id);
        verify(workRepository).findById(id);
        assertEquals(response.get("path"), "/api/v1/customer/work/" + id);
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("customer"), customer);

        //section for testing user fetching customer that doesn't belong to them
        user = new User("ismael101", "password");
        work = new Work("title", "description", false, user);
        work.setId(id);
        when(workRepository.findById(id)).thenReturn(Optional.of(work));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
           underTest.getCustomerByWork(id);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing user fetching work that doesn't exist
        when(workRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
            underTest.getCustomerByWork(id);
        });
        assertEquals(exception.getMessage(), "work with id: " + id + " not found");
    }

    @Test
    @WithMockUser(username = "username")
    void createCustomerMethodTest(){

        //section for testing successfully creating a customer
        User user = new User("username", "password");
        Work work = new Work("title","description", false, user);
        CustomerRequest request = new CustomerRequest("first", "last", "email", 7632275152l, work.getId().toString());
        when(workRepository.findById(UUID.fromString(request.getWork()))).thenReturn(Optional.of(work));
        Map<String, Object> response = underTest.createCustomer(request);
        verify(customerRepository).save(any());
        assertEquals(response.get("path"), "/api/v1/customer");
        assertEquals(response.get("status"), 201);
        Customer customer = (Customer) response.get("customer");
        assertEquals(customer.getFirst(), "first");
        assertEquals(customer.getLast(), "last");
        assertEquals(customer.getEmail(), "email");
        assertEquals(customer.getPhone(), 7632275152l);

        //section for testing user creating customer for work that doesn't belong to them
        user = new User("ismael101", "password");
        work = new Work("title", "description", false, user);
        when(workRepository.findById(UUID.fromString(request.getWork()))).thenReturn(Optional.of(work));
        Exception exception = assertThrows(UnauthorizedAction.class,  () -> {
            underTest.createCustomer(request);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing creating customer for work that doesn't exist
        when(workRepository.findById(UUID.fromString(request.getWork()))).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
            underTest.createCustomer(request);
        });
        assertEquals(exception.getMessage(), "work with id: " + request.getWork() + " not found");

        //section for testing creating customer for work order that already has a customer
        user = new User("username", "password");
        work.setCustomer(customer);
        work.setUser(user);
        when(workRepository.findById(UUID.fromString(request.getWork()))).thenReturn(Optional.of(work));
        exception = assertThrows(AlreadyExists.class, () -> {
            underTest.createCustomer(request);
        });
        assertEquals(exception.getMessage(), "work order already has a customer");
    }

    @Test
    @WithMockUser(username = "username")
    void updateCustomerMethodTest(){

        //section for testing successfully updating customer
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description",false, user);
        Customer customer = new Customer("first", "last", "email", 7632275152l, work);
        CustomerRequest request = new CustomerRequest("new first", "new last", "new email", 6122443928l, work.getId().toString());
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        Map<String, Object> response = underTest.updateCustomer(id, request);
        verify(customerRepository).findById(id);
        assertEquals(response.get("path"), "/api/v1/customer/" + id);
        assertEquals(response.get("status"), 200);
        customer = (Customer) response.get("customer");
        assertEquals(customer.getFirst(), "new first");
        assertEquals(customer.getLast(), "new last");
        assertEquals(customer.getEmail(), "new email");
        assertEquals(customer.getPhone(), 6122443928l);

        //section for testing user updating customer that doesn't belong to them
        user = new User("ismael101", "password");
        work = new Work("title", "description", false, user);
        customer = new Customer("first", "last", "email", 7632275152l, work);
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
            underTest.updateCustomer(id, request);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing user updating customer that doesn't exist
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class,() -> {
            underTest.updateCustomer(id, request);
        });
        assertEquals(exception.getMessage(), "customer with id: " + id + " not found");
    }

    @Test
    @WithMockUser(username = "username")
    void deleteCustomerMethodTest(){

        //section for testing successfully deleting customer
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description", false, user);
        Customer customer = new Customer("first", "last", "email", 7632275152l, work);
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        Map<String, Object> response = underTest.deleteCustomer(id);
        verify(customerRepository).delete(customer);
        assertEquals(response.get("path"), "/api/v1/customer/" + id);
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("message"), "customer deleted");

        //section for testing deleting customer that doesn't belong to them
        user = new User("ismael101", "password");
        work = new Work("title", "description", false, user);
        customer = new Customer("first", "last", "email", 7632275152l, work);
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
           underTest.deleteCustomer(id);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing deleting customer that doesn't exist
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
           underTest.deleteCustomer(id);
        });
        assertEquals(exception.getMessage(), "customer with id: " + id + " not found");
    }
}