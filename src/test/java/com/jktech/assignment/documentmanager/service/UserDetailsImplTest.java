package com.jktech.assignment.documentmanager.service;

import com.jktech.assignment.documentmanager.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserDetailsImplTest {

    @Test
    void testUserDetailsFieldsAndAuthorities() {
        User user = new User();
        user.setUsername("alice");
        user.setPassword("secret");
        user.setRoles(Set.of("ADMIN", "VIEWER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        assertEquals("alice", userDetails.getUsername());
        assertEquals("secret", userDetails.getPassword());

        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(java.util.stream.Collectors.toSet());

        assertTrue(roles.contains("ROLE_ADMIN"));
        assertTrue(roles.contains("ROLE_VIEWER"));
        assertEquals(2, roles.size());

        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isCredentialsNonExpired());
    }
}