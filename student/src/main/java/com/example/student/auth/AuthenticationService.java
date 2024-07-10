package com.example.student.auth;

import com.example.student.config.JwtService;
import com.example.student.student.Role;
import com.example.student.student.Student;
import com.example.student.student.StudentRepository;
import com.example.student.token.Token;
import com.example.student.token.TokenRepository;
import com.example.student.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final StudentRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse register(RegisterRequest request, Role role) {
        var student = Student.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role) // Assign the role provided in the request
                .build();
        repository.save(student);
        var jwtToken = jwtService.generateToken(student);
        saveStudentToken(student, jwtToken.getToken());
        return AuthenticationResponse.builder()
                .token(jwtToken.getToken())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findStudentByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        saveStudentToken(user, jwtToken.getToken());
        return AuthenticationResponse.builder()
                .token(jwtToken.getToken())
                .build();
    }

    private void saveStudentToken(Student student, String jwtToken) {
        var token = Token.builder()
                .student(student)
                .token(jwtToken)
                .expiryDate(LocalDateTime.now().plusDays(7))
                .build();
        Token savedToken = tokenRepository.save(token);
        System.out.println("Token saved: " + savedToken);
    }

}
