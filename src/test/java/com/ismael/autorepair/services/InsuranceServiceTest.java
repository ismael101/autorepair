package com.ismael.autorepair.services;

import com.ismael.autorepair.exceptions.AlreadyExists;
import com.ismael.autorepair.exceptions.NotFound;
import com.ismael.autorepair.exceptions.UnauthorizedAction;
import com.ismael.autorepair.models.Insurance;
import com.ismael.autorepair.models.User;
import com.ismael.autorepair.models.Work;
import com.ismael.autorepair.repositories.InsuranceRepository;
import com.ismael.autorepair.repositories.UserRepository;
import com.ismael.autorepair.repositories.WorkRepository;
import com.ismael.autorepair.requests.InsuranceRequest;
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
class InsuranceServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    WorkRepository workRepository;
    @Mock
    InsuranceRepository insuranceRepository;
    InsuranceService underTest;

    @BeforeEach
    void setUp(){
        underTest = new InsuranceService(insuranceRepository, workRepository, userRepository);
    }

    @Test
    @WithMockUser(username = "username")
    void getInsurancesMethodTest(){

        //section for testing successfully fetching all insurance belonging to the user
        User user = new User("username", "password");
        Work work = new Work("title", "description", false, user);
        Insurance insurance = new Insurance("provider", 8423409939l, "vin", work);
        work.setInsurance(insurance);
        when(userRepository.findUserByUsername("username")).thenReturn(Optional.of(user));
        when(workRepository.findWorkByUser(user)).thenReturn(List.of(work));
        Map<String, Object> response = underTest.getInsurances();
        verify(userRepository).findUserByUsername("username");
        verify(workRepository).findWorkByUser(user);
        assertEquals(response.get("path"), "/api/v1/insurance");
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("insurances"), List.of(insurance));
    }

    @Test
    @WithMockUser(username = "username")
    void getInsuranceByIdMethodTest(){

        //section for testing successfully fetching insurance by id that belongs to the user
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description", false, user);
        Insurance insurance = new Insurance("provider", 8423409939l, "vin", work);
        when(insuranceRepository.findById(id)).thenReturn(Optional.of(insurance));
        Map<String, Object> response = underTest.getInsuranceById(id);
        verify(insuranceRepository).findById(id);
        assertEquals(response.get("path"), "/api/v1/insurance/" + id);
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("insurance"), insurance);

        //section for testing fetching insurance that doesn't belong to the user
        user = new User("ismael101", "password");
        work = new Work("title", "description",false, user);
        insurance = new Insurance("provider", 8423409939l, "vin", work);
        when(insuranceRepository.findById(id)).thenReturn(Optional.of(insurance));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
            underTest.getInsuranceById(id);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing fetching insurance that doesn't exist
        when(insuranceRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
           underTest.getInsuranceById(id);
        });
        assertEquals(exception.getMessage(), "insurance with id: " + id + " not found");
    }

    @Test
    @WithMockUser(username = "username")
    void getInsuranceByWorkMethodTest(){

        //section for testing successfully fetching insurance by work that belongs to the user
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description", false, user);
        Insurance insurance = new Insurance("provider", 8423409939l, "vin", work);
        work.setInsurance(insurance);
        when(workRepository.findById(id)).thenReturn(Optional.of(work));
        Map<String, Object> response = underTest.getInsuranceByWork(id);
        assertEquals(response.get("path"), "/api/v1/insurance/work/" + id);
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("insurance"), insurance);

        //section for testing fetching insurance that doesn't belong to the user
        user = new User("ismael101", "password");
        work = new Work("title", "description",false, user);
        when(workRepository.findById(id)).thenReturn(Optional.of(work));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
            underTest.getInsuranceByWork(id);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing fetching insurance from work that doesn't exist
        when(workRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
           underTest.getInsuranceByWork(id);
        });
        assertEquals(exception.getMessage(), "work with id: " + id + " not found");

    }

    @Test
    @WithMockUser(username = "username")
    void createInsuranceMethodTest(){

        //section for testing successfully creating insurance
        User user = new User("username", "password");
        Work work = new Work("title", "description",false, user);
        InsuranceRequest request = new InsuranceRequest("provider", 8423409939l, "vin", work.getId().toString());
        when(workRepository.findById(UUID.fromString(request.getWork()))).thenReturn(Optional.of(work));
        Map<String, Object> response = underTest.createInsurance(request);
        verify(insuranceRepository).save(any());
        assertEquals(response.get("path"), "/api/v1/insurance");
        assertEquals(response.get("status"), 201);
        Insurance insurance = (Insurance) response.get("insurance");
        assertEquals(insurance.getPolicy(), 8423409939l);
        assertEquals(insurance.getProvider(), "provider");
        assertEquals(insurance.getVin(), "vin");

        //section for testing creating insurance for work that doesn't belong to them
        user = new User("ismael101", "password");
        work = new Work("title", "description", false, user);
        when(workRepository.findById(UUID.fromString(request.getWork()))).thenReturn(Optional.of(work));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
           underTest.createInsurance(request);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for testing creating insurance for work that doesn't exist
        when(workRepository.findById(UUID.fromString(request.getWork()))).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
           underTest.createInsurance(request);
        });
        assertEquals(exception.getMessage(), "work with id: " + request.getWork() + " not found");
        user = new User("ismael101", "password");
        work = new Work("title", "description",false, user);
        work.setInsurance(new Insurance());

        //section for testing creating insurance for work that already has insurance
        user = new User("username", "password");
        work = new Work("title", "description", false, user);
        work.setInsurance(insurance);
        when(workRepository.findById(UUID.fromString(request.getWork()))).thenReturn(Optional.of(work));
        exception = assertThrows(AlreadyExists.class, () -> {
           underTest.createInsurance(request);
        });
        assertEquals(exception.getMessage(), "work order already has a insurance");

    }

    @Test
    @WithMockUser(username = "username")
    void updateInsuranceMethodTest(){

        //section for testing successfully updating insurance
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description", false, user);
        Insurance insurance = new Insurance("provider", 8423409939l, "vin", work);
        InsuranceRequest request = new InsuranceRequest("new provider", 5678932340l, "new vin", work.getId().toString());
        when(insuranceRepository.findById(id)).thenReturn(Optional.of(insurance));
        Map<String, Object> response = underTest.updateInsurance(id, request);
        assertEquals(response.get("path"), "/api/v1/insurance/" + id);
        assertEquals(response.get("status"), 200);
        insurance = (Insurance) response.get("insurance");
        assertEquals(insurance.getProvider(), "new provider");
        assertEquals(insurance.getPolicy(), 5678932340l);
        assertEquals(insurance.getVin(), "new vin");

        //section for updating insurance that doesn't belong to them
        user = new User("ismael101", "password");
        work =  new Work("title", "description", false, user);
        insurance = new Insurance("provider", 5678932340l, "vin", work);
        when(insuranceRepository.findById(id)).thenReturn(Optional.of(insurance));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
           underTest.updateInsurance(id, request);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for updating insurance that doesn't exist
        when(insuranceRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
             underTest.updateInsurance(id, request);
        });
        assertEquals(exception.getMessage(), "insurance with id: " + id + " not found");
    }

    @Test
    @WithMockUser(username = "username")
    void deleteInsuranceMethodTest(){

        //section for successfully deleting insurance
        UUID id = UUID.randomUUID();
        User user = new User("username", "password");
        Work work = new Work("title", "description", false, user);
        Insurance insurance = new Insurance("provider", 5678932340l, "vin", work);
        when(insuranceRepository.findById(id)).thenReturn(Optional.of(insurance));
        Map<String, Object> response = underTest.deleteInsurance(id);
        verify(insuranceRepository).delete(insurance);
        assertEquals(response.get("path"), "/api/v1/insurance/" + id);
        assertEquals(response.get("status"), 200);
        assertEquals(response.get("message"), "insurance deleted");

        //section for deleting insurance that doesn't belong to them
        user = new User("ismael101", "password");
        work = new Work("title", "description", false, user);
        insurance = new Insurance("provider", 5678932340l, "vin", work);
        when(insuranceRepository.findById(id)).thenReturn(Optional.of(insurance));
        Exception exception = assertThrows(UnauthorizedAction.class, () -> {
           underTest.deleteInsurance(id);
        });
        assertEquals(exception.getMessage(), "user: username action not allowed");

        //section for deleting insurance that doesn't exist
        when(insuranceRepository.findById(id)).thenReturn(Optional.empty());
        exception = assertThrows(NotFound.class, () -> {
            underTest.deleteInsurance(id);
        });
        assertEquals(exception.getMessage(), "insurance with id: " + id + " not found");
    }


}