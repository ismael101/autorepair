package com.project.autoshop.repositories;
import com.project.autoshop.models.*;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepositoryTest {
    @Autowired
    public AddressRepository addressRepository;
    @Autowired
    public CustomerRepository customerRepository;
    @Autowired
    public ImageRepository imageRepository;
    @Autowired
    public InsuranceRepository insuranceRepository;
    @Autowired
    public LaborRepository laborRepository;
    @Autowired
    public PartRepository partRepository;
    @Autowired
    public VehicleRepository vehicleRepository;
    @Autowired
    public JobRepository jobRepository;

    public Job job;


    @BeforeAll
    void setUp(){
        job = Job
                .builder()
                .complete(false)
                .description("mock description")
                .build();
        jobRepository.save(job);
    }

    @Test
    void addressRepositoryTest(){
        addressRepository.save(Address
                .builder()
                .street("mock street")
                .city("mock city")
                .state("mock state")
                .zipcode(55555)
                .job(job)
                .build());
        Optional<Address> address = addressRepository.findAddressByJob(job.getId());
        assertTrue(address.isPresent());

    }

    @Test
    void customerRepositoryTest(){
        customerRepository.save(Customer
                .builder()
                .first("mock first")
                .last("mock last")
                .email("mock email")
                .phone("7632275152")
                .job(job)
                .build());
        Optional<Customer> customer = customerRepository.findCustomerByJob(job.getId());
        assertTrue(customer.isPresent());
    }

    @Test
    void imageRepositoryTest(){
        imageRepository.save(Image
                .builder()
                .name("mock name")
                .data("mock data".getBytes(StandardCharsets.UTF_8))
                .job(job)
                .build());
        List<Image> images = imageRepository.findImagesByJob(job.getId());
        assertFalse(images.isEmpty());
    }

    @Test
    void insuranceRepositoryTest(){
        insuranceRepository.save(Insurance
                .builder()
                .provider("mock provider")
                .policy("mock policy")
                .vin("mock vin")
                .job(job)
                .build());
        Optional<Insurance> insurance = insuranceRepository.findInsuranceByJob(job.getId());
        assertTrue(insurance.isPresent());
    }
    @Test
    void laborRepositoryTest(){
        laborRepository.save(Labor
                .builder()
                .task("mock task")
                .notes("mock notes")
                .cost(100.00)
                .description("mock description")
                .location("mock location")
                .job(job)
                .build());
        List<Labor> labors = laborRepository.findLaborsByJob(job.getId());
        assertFalse(labors.isEmpty());
    }
    @Test
    void partRepositoryTest(){
        partRepository.save(Part
                .builder()
                .name("mock name")
                .website("mock website")
                .location("mock location")
                .ordered(false)
                .notes("mock notes")
                .description("mock description")
                .cost(100.00)
                .job(job)
                .build());
        List<Part> parts = partRepository.findPartsByJob(job.getId());
        assertFalse(parts.isEmpty());
    }
    @Test
    void vehicleRepositoryTest(){
        vehicleRepository.save(Vehicle
                .builder()
                .make("mock make")
                .model("mock model")
                .year(2017)
                .vin("mock vin")
                .job(job)
                .build());
        Optional<Vehicle> vehicle = vehicleRepository.findVehicleByJob(job.getId());
        assertTrue(vehicle.isPresent());
    }
}
