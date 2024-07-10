package com.example.student.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
}