package com.projeto.aplicacao.controleacesso.config.security;

import com.projeto.aplicacao.controleacesso.repository.UsuarioRepository;
import com.projeto.aplicacao.controleacesso.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;

    public SecurityFilter(UsuarioRepository usuarioRepository, TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenJWT = recuperarToken(request);
        if(!Objects.isNull(tokenJWT)){
            String subject = tokenService.getSubject(tokenJWT);
            UserDetails usuario = usuarioRepository.findByEmail(subject);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()));
        }
        filterChain.doFilter(request, response);
    }


    private String recuperarToken(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if(!Objects.isNull(authorizationHeader)){
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
