package com.project.autorepair.config;

import com.project.autorepair.models.*;
import com.project.autorepair.repositories.*;
import com.project.autorepair.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.zip.Deflater;

//config file for seeding username and password
@Configuration
@RequiredArgsConstructor
public class DBConfig implements CommandLineRunner {
    private final AppUserRepository userRepository;
    private final JobRepository jobRepository;
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    private final ImageRepository imageRepository;
    private final InsuranceRepository insuranceRepository;
    private final LaborRepository laborRepository;
    private final PartRepository partRepository;
    private final VehicleRepository vehicleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    //seeds database with username and password from env
    public void run(String... args) throws Exception {
        Job job = Job
                .builder()
                .complete(false)
                .description("transmission issues")
                .createdAt(LocalDateTime.now())
                .build();
        jobRepository.save(job);
        Address address = Address
                .builder()
                .street("7913 Pleasant View Drive NE")
                .city("Minneapolis")
                .state("Minnesota")
                .zipcode(55432)
                .job(job)
                .build();
        addressRepository.save(address);
        Customer customer = Customer
                .builder()
                .email("ismaelomermohamed@gmail.com")
                .first("Ismael")
                .last("Mohamed")
                .phone("7632275152")
                .job(job)
                .build();
        customerRepository.save(customer);
        Insurance insurance = Insurance
                .builder()
                .policy("6011-90-22-58")
                .vin("19XFC2F54JE035276")
                .provider("Geico")
                .job(job)
                .build();
        insuranceRepository.save(insurance);
        Vehicle vehicle = Vehicle
                .builder()
                .vin("19XFC2F54JE035276")
                .make("Toyota")
                .model("Camry")
                .year(2017)
                .job(job)
                .build();
        vehicleRepository.save(vehicle);
        Labor labor = Labor
                .builder()
                .task("replaced old transmission with new")
                .cost(100.0)
                .location("engine")
                .notes("checked for transmission leakage")
                .job(job)
                .build();
        laborRepository.save(labor);
        Part part = Part
                .builder()
                .name("tranmission changer")
                .description("transmission replacement")
                .cost(100.0)
                .location("transmission")
                .notes("item for transmission replacement")
                .ordered(false)
                .website("http://www.part.com")
                .job(job)
                .build();
        partRepository.save(part);
        Path path = Paths.get(System.getProperty("user.dir") + "/src/main/java/com/project/autorepair/config/DBConfig.jpeg");
        byte[] data = Files.readAllBytes(path);
        Image image = Image
                .builder()
                .name("front")
                .data(FileUtils.compress(data, Deflater.BEST_COMPRESSION, false))
                .job(job)
                .build();
        imageRepository.save(image);
        String username = System.getenv("APPUSERNAME");
        String password = System.getenv("APPPASSWORD");
        String role = "ROLE_ADMIN";
        AppUser user = AppUser
                .builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();
        userRepository.save(user);
    }
}
