package com.ismael.autorepair.repositories;

import com.ismael.autorepair.models.Insurance;
import com.ismael.autorepair.models.User;
import com.ismael.autorepair.models.Work;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InsuranceRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    WorkRepository workRepository;
    @Autowired
    InsuranceRepository underTest;

    @BeforeAll
    void setUp(){
        User user = new User("username", "password");
        userRepository.save(user);
        Work work = new Work("title", "description", user);
        workRepository.save(work);
        Insurance insurance = new Insurance("provider", "license", "policy", "vin", work);
        underTest.save(insurance);
    }

    @AfterAll
    void breakDown(){
        userRepository.deleteAll();
    }

    @Test
    void itShouldFindInsuranceByWork(){
        List<Work> work = workRepository.findAll();
        Optional<Insurance> insurance = underTest.findInsuranceByWork(work.get(0));
        assertTrue(insurance.isPresent());
    }


}