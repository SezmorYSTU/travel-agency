package com.travel_agency.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DirectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin_sm", roles = {"ADMIN"})
    void getAllDirections_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/directions"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "manager_pt", roles = {"MANAGER"})
    void getDirectionById_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/directions/1"))
                .andExpect(status().isOk());
    }
}