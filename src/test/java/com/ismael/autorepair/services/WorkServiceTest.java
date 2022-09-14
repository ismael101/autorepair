package com.ismael.autorepair.services;

import com.ismael.autorepair.exceptions.NotFound;
import com.ismael.autorepair.exceptions.UnauthorizedAction;
import com.ismael.autorepair.models.User;
import com.ismael.autorepair.models.Work;
import com.ismael.autorepair.repositories.UserRepository;
import com.ismael.autorepair.repositories.WorkRepository;
import com.ismael.autorepair.requests.WorkRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@ExtendWith(MockitoExtension.class)
class WorkServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    WorkRepository workRepository;
    WorkService underTest;

    @BeforeEach
    void setUp(){
        underTest = new WorkService(workRepository, userRepository);
    }

    @Test
    @WithMockUser(username = "username")
    void getWorksMethodTest(){

        //section for testing successfully fetching all work orders for a user
        User user = new User("username", "password");
        Work work = new Work("title", "description", user);
        when(userRepository.findUserByUsername("username")).thenReturn(Optional.of(user));
        when(workRepository.findWorkByUser(user)).thenReturn(List.of(work));
        Map<String, Object> response = underTest.getWorks();
        verify(userRepository).findUserByUsername("username");
        verify(workRepository).findWorkByUser(user);
        assertEquals(response.get("path"), "/api/v1/work");
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("works"), List.of(work));
    }

    @Test
    @WithMockUser(username = "username")
    void getWorkByIdMethodTest(){

        //section for testing successfully fetching user by id
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description", user);
        work.setId(id);
        when(workRepository.findById(id)).thenReturn(Optional.of(work));
        Map<String, Object> response = underTest.getWorkById(id);
        verify(workRepository).findById(id);
        assertEquals(response.get("path"), "/api/v1/work/" + id);
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("work"), work);

        //section for testing user fetching work order that doesn't belong to them
        user = new User("ismael101", "password");
        work = new Work("title", "description", user);
        work.setId(id);
        when(workRepository.findById(id)).thenReturn(Optional.of(work));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
            underTest.getWorkById(id);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing user fetching work order that doesn't exist
        when(workRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
            underTest.getWorkById(id);
        });
        assertEquals(exception.getMessage(), "work with id: " + id + " not found");
    }

    @Test
    @WithMockUser(username = "username")
    void itShouldCreateWork(){

        //section for testing successfully creating a new work order
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description", user);
        work.setId(id);
        when(userRepository.findUserByUsername("username")).thenReturn(Optional.of(user));
        WorkRequest request = new WorkRequest("title", "description");
        Map<String, Object> response = underTest.createWork(request);
        verify(workRepository).save(any());
        assertEquals(response.get("path"), "/api/v1/work");
        assertEquals(response.get("status"), 201);
        work = (Work) response.get("work");
        assertEquals(work.getTitle() , "title");
        assertEquals(work.getDescription(), "description");
    }

    @Test
    @WithMockUser(username = "username")
    void itShouldUpdateWork(){

        //section for testing successfully updating a work order
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description", user);
        work.setId(id);
        when(workRepository.findById(id)).thenReturn(Optional.of(work));
        WorkRequest request = new WorkRequest("new title", "new description");
        Map<String, Object> response = underTest.updateWork(id, request);
        verify(workRepository).findById(id);
        assertEquals(response.get("path"), "/api/v1/work/" + id);
        assertEquals(response.get("status"), 200);
        work = (Work) response.get("work");
        assertEquals(work.getTitle(), "new title");
        assertEquals(work.getDescription(), "new description");

        //section for testing updating work order that doesn't belong to the user
        user = new User("ismael101", "password");
        work = new Work("title", "description", user);
        work.setId(id);
        when(workRepository.findById(id)).thenReturn(Optional.of(work));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
            underTest.updateWork(id, request);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing updating work order that doesn't exist
        when(workRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
            underTest.updateWork(id, request);
        });
        assertEquals(exception.getMessage(), "work with id: " + id + " not found");

    }

    @Test
    @WithMockUser(username = "username")
    void itShouldDeleteWork(){

        //section for testing successfully deleting a work order
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description", user);
        work.setId(id);
        when(workRepository.findById(id)).thenReturn(Optional.of(work));
        Map<String, Object> response = underTest.deleteWork(id);
        verify(workRepository).findById(id);
        verify(workRepository).delete(work);
        assertEquals(response.get("path"), "/api/v1/work/" + id);
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("message"), "work order deleted");
        //section for testing deleting work order that doesn't belong to the user
        user = new User("ismael101", "password");
        work = new Work("title", "description", user);
        when(workRepository.findById(id)).thenReturn(Optional.of(work));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
           underTest.deleteWork(id);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing deleting work order that doesn't exist
        when(workRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
           underTest.deleteWork(id);
        });
        assertEquals(exception.getMessage(), "work with id: " + id + " not found");
    }






}