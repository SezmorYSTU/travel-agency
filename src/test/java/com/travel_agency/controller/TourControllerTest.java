package com.travel_agency.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TourControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin_sm", roles = {"ADMIN"})
    void getAllTours_AsAdmin_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/tours"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "manager_pt", roles = {"MANAGER"})
    void getAllTours_AsManager_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/tours"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "info@pegas.ru", roles = {"OPERATOR"})
    void getAllTours_AsOperator_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/tours"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllTours_WithoutAuth_ShouldRedirectToLogin() throws Exception {
        // Форм-логин редиректит на страницу входа
        mockMvc.perform(get("/api/tours"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login.html"));
    }
}