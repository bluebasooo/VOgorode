package ru.tinkoff.landscape.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LandscapeControllerTest {

    @Autowired
    private MockMvc mockMvc;



    @Test
    public void livenessOfService() throws Exception {
        //GIVEN
        //...

        //WHEN
        var result = mockMvc.perform(get("/system/liveness"));

        //THEN
        result.andExpect(status().isOk());
    }

    @Test
    public void readinessOfService() throws Exception {
        //GIVEN
        //...

        //WHEN
        var result = mockMvc.perform(get("/system/readiness"));

        //THEN
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("LandscapeService").value("OK"));

    }


}