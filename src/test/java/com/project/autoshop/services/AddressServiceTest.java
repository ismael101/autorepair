package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Address;
import com.project.autoshop.models.Job;
import com.project.autoshop.repositories.AddressRepository;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.request.AddressRequest;
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
class AddressServiceTest {
    @Mock
    public AddressRepository addressRepository;
    @Mock
    public JobRepository jobRepository;
    public AddressService underTest;


    @BeforeEach
    void setUp(){
        underTest = new AddressService(addressRepository, jobRepository);
    }

    @Test
    void itShouldGetAllAddresses(){
        underTest.getAddresses();
        verify(addressRepository).findAll();
    }

    @Test
    void itShouldGetAddressById(){
        Address address = Address
                .builder()
                .street("washington street")
                .city("minneapolis")
                .state("minnesota")
                .zipcode(55432)
                .job(new Job())
                .build();
        when(addressRepository.findById(anyInt())).thenReturn(Optional.of(address));
        underTest.getAddress(1);
        verify(addressRepository).findById(1);
        assertEquals(underTest.getAddress(1).getStreet(), "washington street");
        assertEquals(underTest.getAddress(1).getCity(), "minneapolis");
        assertEquals(underTest.getAddress(1).getState(), "minnesota");
        assertEquals(underTest.getAddress(1).getZipcode(), 55432);
    }

    @Test
    void itShouldGetAddressByJob(){
        Address address = Address
                .builder()
                .street("washington street")
                .city("minneapolis")
                .state("minnesota")
                .zipcode(55432)
                .job(new Job())
                .build();
        when(addressRepository.findAddressByJob(anyInt())).thenReturn(Optional.of(address));
        underTest.getJobAddress(1);
        verify(addressRepository).findAddressByJob(1);
        assertEquals(underTest.getJobAddress(1).getStreet(), "washington street");
        assertEquals(underTest.getJobAddress(1).getCity(), "minneapolis");
        assertEquals(underTest.getJobAddress(1).getState(), "minnesota");
        assertEquals(underTest.getJobAddress(1).getZipcode(), 55432);
    }

    @Test
    void itShouldCreateAddress(){
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(Job.builder().build()));
        Address address = underTest.createAddress(AddressRequest
                .builder()
                .street("washington street")
                .city("minneapolis")
                .state("minnesota")
                .zipcode(55432)
                .job(1)
                .build());
        verify(jobRepository).findById(1);
        verify(addressRepository).save(address);
        assertEquals(address.getStreet(), "washington street");
        assertEquals(address.getCity(), "minneapolis");
        assertEquals(address.getState(), "minnesota");
        assertEquals(address.getZipcode(), 55432);
    }

    @Test
    void itShouldUpdateAddress(){
        when(addressRepository.findById(anyInt())).thenReturn(Optional.of(Address
                .builder()
                .street("washington street")
                .city("minneapolis")
                .state("minnesota")
                .zipcode(55432)
                .job(new Job())
                .build()));

        Address address = underTest.updateAddress(1, AddressRequest
                .builder()
                .street("jefferson street")
                .city("des moines")
                .state("iowa")
                .zipcode(55367)
                .build());

        verify(addressRepository).findById(1);
        assertEquals(address.getStreet(), "jefferson street");
        assertEquals(address.getCity(), "des moines");
        assertEquals(address.getState(), "iowa");
        assertEquals(address.getZipcode(), 55367);
    }

    @Test
    void itShouldDeleteAddress(){
        Address address = Address
                .builder()
                .build();
        when(addressRepository.findById(anyInt())).thenReturn(Optional.of(address));
        underTest.deleteAddress(1);
        verify(addressRepository).findById(1);
        verify(addressRepository).delete(address);
    }

    @Test
    void itShouldThrowNotFound(){
        Exception exception =  assertThrows(NotFoundException.class, () -> {
            underTest.getJobAddress(1);
        });
        assertEquals("address with job id: 1 not found", exception.getMessage());

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.getAddress(1);
        });
        assertEquals("address with id: 1 not found", exception.getMessage());

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.createAddress(AddressRequest.builder().job(1).build());
        });
        assertEquals("job with id: 1 not found", exception.getMessage());

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.updateAddress(1, AddressRequest.builder().build());
        });
        assertEquals("address with id: 1 not found", exception.getMessage());

        exception = assertThrows(NotFoundException.class, () -> {
            underTest.deleteAddress(1);
        });
        assertEquals("address with id: 1 not found", exception.getMessage());
    }

}