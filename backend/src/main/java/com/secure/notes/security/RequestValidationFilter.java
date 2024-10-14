package com.secure.notes.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RequestValidationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Aqui estamos criando um filtro de validação, de acordo com o HEADER da requisição;
        // Se o HEADER da requisição estiver com o nome X-Valid-Request, e com o valor TRUE, ele passa e vai para os próximos filtros,
        // Se não tiver com o valor TRUE, é devolvido 1 erro.
        String header = request.getHeader("X-Valid-Request");
        if (header == null || !header.equals("true")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
            return;
        }
        // Caso não entre no if acima, o request prosseguirá para os próximos filtros.
        filterChain.doFilter(request, response);
    }
}