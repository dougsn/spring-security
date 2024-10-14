package com.secure.notes.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class CustomLoggingFilter extends OncePerRequestFilter {

    // Criando um filtro personalizado para imprimir A URI e o StatusCode no console, um exemplo básico de criação de filtro
    // personalizado na aplicação

    // A extensão do OncePerRequest significa que o filtro entrará em "ação" uma vez por request.

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("CustomLoggingFilter - Request URI: " + request.getRequestURI());
        filterChain.doFilter(request, response);
        System.out.println("CustomLoggingFilter - Response Status: " + response.getStatus());
    }
}