package com.example.student.config;

import com.example.student.token.Token;
import com.example.student.token.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor

public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    private final TokenRepository tokenRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        System.out.println("JWT" + jwt);

        userEmail = jwtService.extractUsername(jwt);

        System.out.println(  "USEREMIAL:"  + userEmail);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // Debugging token retrieval
            var retrievedToken = tokenRepository.findByToken(jwt);
            System.out.println("Token retrieved: " + retrievedToken.isPresent());

            var isTokenValid = retrievedToken
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);

            System.out.println(" Token Valid" + isTokenValid );
            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                System.out.println(" Authenticated");
                System.out.println(userDetails.getAuthorities());
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            } else {
                System.out.println("Not Authenticated");
            }
        }
        filterChain.doFilter(request, response);
    }




}
