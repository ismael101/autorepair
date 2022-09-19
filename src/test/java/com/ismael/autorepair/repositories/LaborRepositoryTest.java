package com.ismael.autorepair.repositories;

import com.ismael.autorepair.models.Labor;
import com.ismael.autorepair.models.User;
import com.ismael.autorepair.models.Work;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LaborRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    WorkRepository workRepository;
    @Autowired
    LaborRepository underTest;

    @BeforeAll
    void setUp(){
        User user = new User("username", "password");
        userRepository.save(user);
        Work work = new Work("title", "description", false, user);
        workRepository.save(work);
        Labor labor = new Labor("task", "location", 0.0, false, work);
        underTest.save(labor);
    }

    @AfterAll
    void breakDown(){
        userRepository.deleteAll();
    }

    @Test
    void itShouldFindLaborByWork(){
        List<Work> work = workRepository.findAll();
        List<Labor> labors = underTest.findLaborByWork(work.get(0));
        assertFalse(labors.isEmpty());
    }

}