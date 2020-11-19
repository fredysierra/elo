package io.fysus.elo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(args = {"SHOW_DETAILS", "13"})
class EloApplicationTests {

    @Autowired
    ApplicationRunner runner;

    @Test
    public void contextLoads() {
        assertNotNull(runner);
    }

}
