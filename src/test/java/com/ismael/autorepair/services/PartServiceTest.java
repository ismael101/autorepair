package com.ismael.autorepair.services;

import com.ismael.autorepair.exceptions.NotFound;
import com.ismael.autorepair.exceptions.UnauthorizedAction;
import com.ismael.autorepair.models.Part;
import com.ismael.autorepair.models.User;
import com.ismael.autorepair.models.Work;
import com.ismael.autorepair.repositories.PartRepository;
import com.ismael.autorepair.repositories.UserRepository;
import com.ismael.autorepair.repositories.WorkRepository;
import com.ismael.autorepair.requests.PartRequest;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@ExtendWith(MockitoExtension.class)
class PartServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    WorkRepository workRepository;
    @Mock
    PartRepository partRepository;
    PartService underTest;

    @BeforeEach
    void setUp(){
        underTest = new PartService(partRepository, workRepository, userRepository);
    }

    @Test
    @WithMockUser(username = "username")
    void getPartsMethodTest(){

        //section for testing successfully fetching all parts belonging to user
        User user = new User("username", "password");
        Work work = new Work("title", "description", false, user);
        Part part = new Part("task", "location", 100.00, false, work);
        work.setParts(List.of(part));
        when(userRepository.findUserByUsername("username")).thenReturn(Optional.of(user));
        when(workRepository.findWorkByUser(user)).thenReturn(List.of(work));
        Map<String, Object> response = underTest.getParts();
        verify(userRepository).findUserByUsername("username");
        verify(workRepository).findWorkByUser(user);
        assertEquals(response.get("path"), "/api/v1/part");
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("parts"), List.of(part));
    }

    @Test
    @WithMockUser(username = "username")
    void getPartByIdMethodTest(){

        //section for testing successfully fetching part by id that belongs to user
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description",false, user);
        Part part = new Part("task", "location", 100.00, false, work);
        when(partRepository.findById(id)).thenReturn(Optional.of(part));
        Map<String, Object> response = underTest.getPartById(id);
        verify(partRepository).findById(id);
        assertEquals(response.get("path"), "/api/v1/part/" + id);
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("part"), part);

        //section for testing user fetching part that doesn't belong to him
        user = new User("ismael101", "password");
        work = new Work("title", "description", false, user);
        part = new Part("task", "location", 100.00, false, work);
        when(partRepository.findById(id)).thenReturn(Optional.of(part));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
            underTest.getPartById(id);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing user fetching part that doesn't exist
        when(partRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
            underTest.getPartById(id);
        });
        assertEquals(exception.getMessage(), "part with id: " + id + " not found");
    }

    @Test
    @WithMockUser(username = "username")
    void getPartByWorkMethodTest(){

        //section for testing successfully fetching part with work id
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description",false, user);
        work.setId(id);
        Part part = new Part("title", "location", 100.00, false, work);
        work.setParts(List.of(part));
        when(workRepository.findById(id)).thenReturn(Optional.of(work));
        Map<String, Object> response = underTest.getPartByWork(id);
        verify(workRepository).findById(id);
        assertEquals(response.get("path"), "/api/v1/part/work/" + id);
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("parts"), List.of(part));

        //section for testing user fetching part that doesn't belong to them
        user = new User("ismael101", "password");
        work = new Work("title", "description",false, user);
        work.setId(id);
        when(workRepository.findById(id)).thenReturn(Optional.of(work));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
            underTest.getPartByWork(id);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing user fetching work that doesn't exist
        when(workRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
            underTest.getPartByWork(id);
        });
        assertEquals(exception.getMessage(), "work with id: " + id + " not found");
    }

    @Test
    @WithMockUser(username = "username")
    void createPartMethodTest(){

        //section for testing successfully creating a part
        User user = new User("username", "password");
        Work work = new Work("title","description", false, user);
        PartRequest request = new PartRequest("title", "location", 100.00, "false", work.getId().toString());
        when(workRepository.findById(UUID.fromString(request.getWork()))).thenReturn(Optional.of(work));
        Map<String, Object> response = underTest.createPart(request);
        verify(partRepository).save(any());
        assertEquals(response.get("path"), "/api/v1/part");
        assertEquals(response.get("status"), 201);
        Part part = (Part) response.get("part");
        assertEquals(part.getTitle(), "title");
        assertEquals(part.getLocation(), "location");
        assertEquals(part.getCost(), 100.00);

        //section for testing user creating part for work that doesn't belong to them
        user = new User("ismael101", "password");
        work = new Work("title", "description",false, user);
        when(workRepository.findById(UUID.fromString(request.getWork()))).thenReturn(Optional.of(work));
        Exception exception = assertThrows(UnauthorizedAction.class,  () -> {
            underTest.createPart(request);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing creating part for work that doesn't exist
        when(workRepository.findById(UUID.fromString(request.getWork()))).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
            underTest.createPart(request);
        });
        assertEquals(exception.getMessage(), "work with id: " + request.getWork() + " not found");

    }

    @Test
    @WithMockUser(username = "username")
    void updatePartMethodTest(){

        //section for testing successfully updating part
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description", false, user);
        Part part = new Part("title", "location", 100.00, false, work);
        PartRequest request = new PartRequest("new title", "new location", 200.00, "false", work.getId().toString());
        when(partRepository.findById(id)).thenReturn(Optional.of(part));
        Map<String, Object> response = underTest.updatePart(id, request);
        verify(partRepository).findById(id);
        assertEquals(response.get("path"), "/api/v1/part/" + id);
        assertEquals(response.get("status"), 200);
        part = (Part) response.get("part");
        assertEquals(part.getTitle(), "new title");
        assertEquals(part.getLocation(), "new location");
        assertEquals(part.getCost(), 200.00);

        //section for testing user updating part that doesn't belong to them
        user = new User("ismael101", "password");
        work = new Work("title", "description",false, user);
        part =  new Part("title", "location", 100.00, false, work);
        when(partRepository.findById(id)).thenReturn(Optional.of(part));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
            underTest.updatePart(id, request);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing user updating part that doesn't exist
        when(partRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class,() -> {
            underTest.updatePart(id, request);
        });
        assertEquals(exception.getMessage(), "part with id: " + id + " not found");
    }

    @Test
    @WithMockUser(username = "username")
    void deletePartMethodTest(){

        //section for testing successfully deleting part
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description",false, user);
        Part part = new Part("title", "location", 100.00,false, work);
        when(partRepository.findById(id)).thenReturn(Optional.of(part));
        Map<String, Object> response = underTest.deletePart(id);
        verify(partRepository).delete(part);
        assertEquals(response.get("path"), "/api/v1/part/" + id);
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("message"), "part deleted");

        //section for testing deleting part that doesn't belong to them
        user = new User("ismael101", "password");
        work = new Work("title", "description", false, user);
        part = new Part("title", "location", 100.00,false, work);
        when(partRepository.findById(id)).thenReturn(Optional.of(part));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
            underTest.deletePart(id);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing deleting part that doesn't exist
        when(partRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
            underTest.deletePart(id);
        });
        assertEquals(exception.getMessage(), "part with id: " + id + " not found");

    }

}