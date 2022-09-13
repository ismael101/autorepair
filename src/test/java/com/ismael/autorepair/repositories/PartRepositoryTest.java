package com.ismael.autorepair.repositories;

import com.ismael.autorepair.models.Part;
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
class PartRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    WorkRepository workRepository;
    @Autowired
    PartRepository underTest;

    @BeforeAll
    void setUp(){
        User user = new User("username", "password");
        userRepository.save(user);
        Work work = new Work("title", "description", user);
        workRepository.save(work);
        Part part = new Part("title", "location", 0.0, work);
        underTest.save(part);
    }

    @AfterAll
    void breakDown(){
        userRepository.deleteAll();
    }

    @Test
    void itShouldFindPartByWork(){
        List<Work> work = workRepository.findAll();
        List<Part> parts = underTest.findPartByWork(work.get(0));
        assertFalse(parts.isEmpty());
    }

}