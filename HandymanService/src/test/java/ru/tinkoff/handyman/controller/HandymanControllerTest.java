package ru.tinkoff.handyman.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HandymanControllerTest {

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
                .andExpect(MockMvcResultMatchers.jsonPath("HandymanService").value("CONNECTING"));

    }

    @Test
    public void testForceMalfuncitonWithTrueState() throws Exception {
        //GIVEN
        mockMvc.perform(get("/system/force/malfunction?isMalfunction=true"));

        //WHEN
        var result = mockMvc.perform(get("/system/readiness"));

        //THEN
        result.andExpect(MockMvcResultMatchers.jsonPath("HandymanService").value("Malfunction"));
    }

    @Test
    public void testForceMalfunctionWithFalseState() throws Exception {
        //GIVEN
        mockMvc.perform(get("/system/force/malfunction?isMalfunction=false"));

        //WHEN
        var result = mockMvc.perform(get("/system/readiness"));

        //THEN
        result.andExpect(MockMvcResultMatchers.jsonPath("HandymanService").value("IDLE"));
    }

}