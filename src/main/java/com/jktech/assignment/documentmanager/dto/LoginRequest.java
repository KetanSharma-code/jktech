package com.jktech.assignment.documentmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class LoginRequest {

    private String username;

    private String password;

    public LoginRequest() {}

    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
