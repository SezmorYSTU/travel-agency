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
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "manager_pt", roles = {"MANAGER"})
    void getAllAddresses_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/addresses"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "manager_pt", roles = {"MANAGER"})
    void createAddress_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/api/addresses")
                        .contentType("application/json")
                        .content("{\"region\":\"Moscow\",\"city\":\"Moscow\",\"street\":\"Lenina\",\"house\":\"1\"}"))
                .andExpect(status().is2xxSuccessful());
    }
}
