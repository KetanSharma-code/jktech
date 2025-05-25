package com.jktech.assignment.documentmanager.service;

import com.jktech.assignment.documentmanager.model.User;
import com.jktech.assignment.documentmanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDetailsServiceImplTest {

    private UserRepository userRepository;
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userDetailsService = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    void testLoadUserByUsername_found() {
        User user = new User();
        user.setUsername("bob");
        user.setPassword("hashed");
        user.setRoles(Set.of("EDITOR"));

        when(userRepository.findByUsername("bob")).thenReturn(Optional.of(user));

        UserDetails details = userDetailsService.loadUserByUsername("bob");

        assertEquals("bob", details.getUsername());
        assertEquals("hashed", details.getPassword());

        assertTrue(details.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_EDITOR")));

        verify(userRepository).findByUsername("bob");
    }

    @Test
    void testLoadUserByUsername_notFound() {
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("ghost");
        });

        verify(userRepository).findByUsername("ghost");
    }
}