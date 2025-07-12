package com.javaadvanced.ultimatespringboot.service;

import com.javaadvanced.ultimatespringboot.model.entity.User;
import com.javaadvanced.ultimatespringboot.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        User inputUser = new User();
        inputUser.setUsername("Test User");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("Test User");

        when(userRepository.save(inputUser)).thenReturn(savedUser);

        User result = userService.createUser(inputUser);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test User", result.getUsername());
        verify(userRepository, times(1)).save(inputUser);
    }

    @Test
    void testGetUserById_Success() {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        user.setUsername("Test User");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.getUserById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Test User", result.getUsername());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void testGetUserById_NotFound() {
        Long id = 99L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.getUserById(id));
        assertEquals("User not found with id: 99", exception.getMessage());
        verify(userRepository, times(1)).findById(id);
    }
}
