package com.ismael.autorepair.services;

import com.ismael.autorepair.exceptions.NotFound;
import com.ismael.autorepair.exceptions.UnauthorizedAction;
import com.ismael.autorepair.models.Labor;
import com.ismael.autorepair.models.User;
import com.ismael.autorepair.models.Work;
import com.ismael.autorepair.repositories.LaborRepository;
import com.ismael.autorepair.repositories.UserRepository;
import com.ismael.autorepair.repositories.WorkRepository;
import com.ismael.autorepair.requests.LaborRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@ExtendWith(MockitoExtension.class)
class LaborServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    WorkRepository workRepository;
    @Mock
    LaborRepository laborRepository;
    LaborService underTest;

    @BeforeEach
    void setUp(){
        underTest = new LaborService(laborRepository, workRepository, userRepository);
    }

    @Test
    @WithMockUser(username = "username")
    void getLaborsMethodTest(){

        //section for testing successfully fetching all labor belonging to user
        User user = new User("username", "password");
        Work work = new Work("title", "description", user);
        Labor labor = new Labor("task", "location" , 100.00, work);
        work.setLabors(List.of(labor));
        when(userRepository.findUserByUsername("username")).thenReturn(Optional.of(user));
        when(workRepository.findWorkByUser(user)).thenReturn(List.of(work));
        Map<String, Object> response = underTest.getLabors();
        verify(userRepository).findUserByUsername("username");
        verify(workRepository).findWorkByUser(user);
        assertEquals(response.get("path"), "/api/v1/labor");
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("labors"), List.of(labor));
    }

    @Test
    @WithMockUser(username = "username")
    void getLaborByIdMethodTest(){

        //section for testing successfully fetching labor by id that belongs to user
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description", user);
        Labor labor = new Labor("task", "location", 100.00, work);
        when(laborRepository.findById(id)).thenReturn(Optional.of(labor));
        Map<String, Object> response = underTest.getLaborById(id);
        verify(laborRepository).findById(id);
        assertEquals(response.get("path"), "/api/v1/labor/" + id);
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("labor"), labor);

        //section for testing user fetching labor that doesn't belong to him
        user = new User("ismael101", "password");
        work = new Work("title", "description", user);
        labor = new Labor("task", "location", 100.00, work);
        when(laborRepository.findById(id)).thenReturn(Optional.of(labor));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
            underTest.getLaborById(id);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing user fetching labor that doesn't exist
        when(laborRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
            underTest.getLaborById(id);
        });
        assertEquals(exception.getMessage(), "labor with id: " + id + " not found");
    }

    @Test
    @WithMockUser(username = "username")
    void getLaborByWorkMethodTest(){

        //section for testing successfully fetching labor with work id
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description", user);
        work.setId(id);
        Labor labor = new Labor("task", "location", 100.00, work);
        work.setLabors(List.of(labor));
        when(workRepository.findById(id)).thenReturn(Optional.of(work));
        Map<String, Object> response = underTest.getLaborByWork(id);
        verify(workRepository).findById(id);
        assertEquals(response.get("path"), "/api/v1/labor/work/" + id);
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("labors"), List.of(labor));

        //section for testing user fetching labor that doesn't belong to them
        user = new User("ismael101", "password");
        work = new Work("title", "description", user);
        work.setId(id);
        when(workRepository.findById(id)).thenReturn(Optional.of(work));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
            underTest.getLaborByWork(id);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing user fetching work that doesn't exist
        when(workRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
            underTest.getLaborByWork(id);
        });
        assertEquals(exception.getMessage(), "work with id: " + id + " not found");
    }

    @Test
    @WithMockUser(username = "username")
    void createLaborMethodTest(){

        //section for testing successfully creating a labor
        User user = new User("username", "password");
        Work work = new Work("title","description", user);
        LaborRequest request = new LaborRequest("task", "location", 100.00, work.getId().toString());
        when(workRepository.findById(UUID.fromString(request.getWork()))).thenReturn(Optional.of(work));
        Map<String, Object> response = underTest.createLabor(request);
        verify(laborRepository).save(any());
        assertEquals(response.get("path"), "/api/v1/labor");
        assertEquals(response.get("status"), 201);
        Labor labor = (Labor) response.get("labor");
        assertEquals(labor.getTask(), "task");
        assertEquals(labor.getLocation(), "location");
        assertEquals(labor.getCost(), 100.00);

        //section for testing user creating labor for work that doesn't belong to them
        user = new User("ismael101", "password");
        work = new Work("title", "description", user);
        when(workRepository.findById(UUID.fromString(request.getWork()))).thenReturn(Optional.of(work));
        Exception exception = assertThrows(UnauthorizedAction.class,  () -> {
            underTest.createLabor(request);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing creating labor for work that doesn't exist
        when(workRepository.findById(UUID.fromString(request.getWork()))).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
            underTest.createLabor(request);
        });
        assertEquals(exception.getMessage(), "work with id: " + request.getWork() + " not found");

    }

    @Test
    @WithMockUser(username = "username")
    void updateLaborMethodTest(){

        //section for testing successfully updating labor
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description", user);
        Labor labor = new Labor("task", "location" , 100.00, work);
        LaborRequest request = new LaborRequest("new task", "new location", 200.00, work.getId().toString());
        when(laborRepository.findById(id)).thenReturn(Optional.of(labor));
        Map<String, Object> response = underTest.updateLabor(id, request);
        verify(laborRepository).findById(id);
        assertEquals(response.get("path"), "/api/v1/labor/" + id);
        assertEquals(response.get("status"), 200);
        labor = (Labor) response.get("labor");
        assertEquals(labor.getTask(), "new task");
        assertEquals(labor.getLocation(), "new location");
        assertEquals(labor.getCost(), 200.00);

        //section for testing user updating labor that doesn't belong to them
        user = new User("ismael101", "password");
        work = new Work("title", "description", user);
        labor =  new Labor("task", "location", 100.00, work);
        when(laborRepository.findById(id)).thenReturn(Optional.of(labor));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
            underTest.updateLabor(id, request);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing user updating labor that doesn't exist
        when(laborRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class,() -> {
            underTest.updateLabor(id, request);
        });
        assertEquals(exception.getMessage(), "labor with id: " + id + " not found");
    }

    @Test
    @WithMockUser(username = "username")
    void deleteLaborMethodTest(){

        //section for testing successfully deleting labor
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description", user);
        Labor labor = new Labor("task", "location", 100.00, work);
        when(laborRepository.findById(id)).thenReturn(Optional.of(labor));
        Map<String, Object> response = underTest.deleteLabor(id);
        verify(laborRepository).delete(labor);
        assertEquals(response.get("path"), "/api/v1/labor/" + id);
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("message"), "labor deleted");

        //section for testing deleting labor that doesn't belong to them
        user = new User("ismael101", "password");
        work = new Work("title", "description", user);
        labor = new Labor("task", "location", 100.00, work);
        when(laborRepository.findById(id)).thenReturn(Optional.of(labor));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
            underTest.deleteLabor(id);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing deleting labor that doesn't exist
        when(laborRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
            underTest.deleteLabor(id);
        });
        assertEquals(exception.getMessage(), "labor with id: " + id + " not found");

    }
}