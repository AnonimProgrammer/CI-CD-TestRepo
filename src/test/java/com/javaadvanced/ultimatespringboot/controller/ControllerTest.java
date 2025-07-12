package com.javaadvanced.ultimatespringboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaadvanced.ultimatespringboot.model.entity.User;
import com.javaadvanced.ultimatespringboot.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;

@WebMvcTest(Controller.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateUser() throws Exception {
        User inputUser = new User();
        inputUser.setUsername("Test User");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("Test User");

        when(userService.createUser(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("Test User"));
    }

    @Test
    void testGetUserById() throws Exception {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        user.setUsername("Test User");

        when(userService.getUserById(id)).thenReturn(user);

        mockMvc.perform(get("/api/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.username").value("Test User"));
    }
}

