package com.ismael.autorepair.services;

import com.ismael.autorepair.exceptions.AlreadyExists;
import com.ismael.autorepair.exceptions.NotFound;
import com.ismael.autorepair.exceptions.UnauthorizedAction;
import com.ismael.autorepair.models.User;
import com.ismael.autorepair.models.Vehicle;
import com.ismael.autorepair.models.Work;
import com.ismael.autorepair.repositories.UserRepository;
import com.ismael.autorepair.repositories.VehicleRepository;
import com.ismael.autorepair.repositories.WorkRepository;
import com.ismael.autorepair.requests.VehicleRequest;
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
class VehicleServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    WorkRepository workRepository;
    @Mock
    VehicleRepository vehicleRepository;
    VehicleService underTest;

    @BeforeEach
    void setUp(){
        underTest = new VehicleService(vehicleRepository, workRepository, userRepository);
    }
    @Test
    @WithMockUser(username = "username")
    void getVehiclesMethodTest(){

        //section for testing successfully fetching all vehicles belonging to user
        User user = new User("username", "password");
        Work work = new Work("title", "description", false, user);
        Vehicle vehicle = new Vehicle("make", "model", 2020 , work);
        work.setVehicle(vehicle);
        when(userRepository.findUserByUsername("username")).thenReturn(Optional.of(user));
        when(workRepository.findWorkByUser(user)).thenReturn(List.of(work));
        Map<String, Object> response = underTest.getVehicles();
        verify(userRepository).findUserByUsername("username");
        verify(workRepository).findWorkByUser(user);
        assertEquals(response.get("path"), "/api/v1/vehicle");
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("vehicles"), List.of(vehicle));
    }

    @Test
    @WithMockUser(username = "username")
    void getVehicleByIdMethodTest(){

        //section for testing successfully fetching vehicle by id that belongs to user
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description", false, user);
        Vehicle vehicle = new Vehicle("make", "model", 2020 , work);
        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));
        Map<String, Object> response = underTest.getVehicleById(id);
        verify(vehicleRepository).findById(id);
        assertEquals(response.get("path"), "/api/v1/vehicle/" + id);
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("vehicle"), vehicle);
        //section for testing user fetching vehicle that doesn't belong to them
        user = new User("ismael101", "password");
        work = new Work("title", "description",false,  user);
        vehicle = new Vehicle("make", "model", 2020 , work);
        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
            underTest.getVehicleById(id);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing user fetching vehicle that doesn't exist
        when(vehicleRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
            underTest.getVehicleById(id);
        });
        assertEquals(exception.getMessage(), "vehicle with id: " + id + " not found");
    }

    @Test
    @WithMockUser(username = "username")
    void getVehicleByWorkMethodTest(){

        //section for testing successfully fetching vehicle with work id
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description",false, user);
        work.setId(id);
        Vehicle vehicle = new Vehicle("make", "model", 2020 , work);
        work.setVehicle(vehicle);
        when(workRepository.findById(id)).thenReturn(Optional.of(work));
        Map<String, Object> response = underTest.getVehicleByWork(id);
        verify(workRepository).findById(id);
        assertEquals(response.get("path"), "/api/v1/vehicle/work/" + id);
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("vehicle"), vehicle);

        //section for testing user fetching vehicle that doesn't belong to them
        user = new User("ismael101", "password");
        work = new Work("title", "description", false, user);
        work.setId(id);
        when(workRepository.findById(id)).thenReturn(Optional.of(work));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
            underTest.getVehicleByWork(id);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing user fetching work that doesn't exist
        when(workRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
            underTest.getVehicleByWork(id);
        });
        assertEquals(exception.getMessage(), "work with id: " + id + " not found");

    }

    @Test
    @WithMockUser(username = "username")
    void createVehicleMethodTest(){

        //section for testing successfully creating a vehicle
        User user = new User("username", "password");
        Work work = new Work("title","description",false, user);
        VehicleRequest request = new VehicleRequest("make", "model" ,2020, work.getId().toString());
        when(workRepository.findById(UUID.fromString(request.getWork()))).thenReturn(Optional.of(work));
        Map<String, Object> response = underTest.createVehicle(request);
        verify(vehicleRepository).save(any());
        assertEquals(response.get("path"), "/api/v1/vehicle");
        assertEquals(response.get("status"), 201);
        Vehicle vehicle = (Vehicle) response.get("vehicle");
        assertEquals(vehicle.getMake(), "make");
        assertEquals(vehicle.getModel(), "model");
        assertEquals(vehicle.getYear(), 2020);

        //section for testing user creating vehicle for work that doesn't belong to them
        user = new User("ismael101", "password");
        work = new Work("title", "description", false, user);
        when(workRepository.findById(UUID.fromString(request.getWork()))).thenReturn(Optional.of(work));
        Exception exception = assertThrows(UnauthorizedAction.class,  () -> {
            underTest.createVehicle(request);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing creating vehicle for work that doesn't exist
        when(workRepository.findById(UUID.fromString(request.getWork()))).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
            underTest.createVehicle(request);
        });
        assertEquals(exception.getMessage(), "work with id: " + request.getWork() + " not found");

        //section for testing creating vehicle for work order that already has a vehicle
        user = new User("username", "password");
        work.setVehicle(vehicle);
        work.setUser(user);
        when(workRepository.findById(UUID.fromString(request.getWork()))).thenReturn(Optional.of(work));
        exception = assertThrows(AlreadyExists.class, () -> {
            underTest.createVehicle(request);
        });
        assertEquals(exception.getMessage(), "work order already has a vehicle");
    }

    @Test
    @WithMockUser(username = "username")
    void updateVehicleMethodTest(){

        //section for testing successfully updating vehicle
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description", false, user);
        Vehicle vehicle = new Vehicle("make", "model", 2020, work);
        VehicleRequest request = new VehicleRequest("new make", "new model" ,2022, work.getId().toString());
        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));
        Map<String, Object> response = underTest.updateVehicle(id, request);
        verify(vehicleRepository).findById(id);
        assertEquals(response.get("path"), "/api/v1/vehicle/" + id);
        assertEquals(response.get("status"), 200);
        vehicle = (Vehicle) response.get("vehicle");
        assertEquals(vehicle.getMake(), "new make");
        assertEquals(vehicle.getModel(), "new model");
        assertEquals(vehicle.getYear(), 2022);

        //section for testing user updating vehicle that doesn't belong to them
        user = new User("ismael101", "password");
        work = new Work("title", "description", false, user);
        vehicle = new Vehicle("make", "model", 2020, work);
        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
            underTest.updateVehicle(id, request);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing user updating vehicle that doesn't exist
        when(vehicleRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class,() -> {
            underTest.updateVehicle(id, request);
        });
        assertEquals(exception.getMessage(), "vehicle with id: " + id + " not found");
    }

    @Test
    @WithMockUser(username = "username")
    void deleteVehicleMethodTest(){

        //section for testing successfully deleting vehicle
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description",false, user);
        Vehicle vehicle = new Vehicle("make", "model", 2020, work);
        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));
        Map<String, Object> response = underTest.deleteVehicle(id);
        verify(vehicleRepository).delete(vehicle);
        assertEquals(response.get("path"), "/api/v1/vehicle/" + id);
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("message"), "vehicle deleted");

        //section for testing deleting vehicle that doesn't belong to them
        user = new User("ismael101", "password");
        work = new Work("title", "description", false, user);
        vehicle = new Vehicle("make", "model", 2020, work);
        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
            underTest.deleteVehicle(id);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing deleting vehicle that doesn't exist
        when(vehicleRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
            underTest.deleteVehicle(id);
        });
        assertEquals(exception.getMessage(), "vehicle with id: " + id + " not found");
    }


}