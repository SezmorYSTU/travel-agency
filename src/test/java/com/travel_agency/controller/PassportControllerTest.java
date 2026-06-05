package com.travel_agency.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PassportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "manager_pt", roles = {"MANAGER"})
    void getAllPassports_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/passports"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "manager_pt", roles = {"MANAGER"})
    void createPassport_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/api/passports")
                        .contentType("application/json")
                        .content("{\"series\":\"1234\",\"number\":\"567890\",\"issuedBy\":\"MVD\",\"issueDate\":\"2020-01-01\"}"))
                .andExpect(status().is2xxSuccessful());
    }
}