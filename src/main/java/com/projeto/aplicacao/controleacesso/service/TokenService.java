package com.projeto.aplicacao.controleacesso.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.projeto.aplicacao.controleacesso.model.Usuario;
import com.projeto.aplicacao.controleacesso.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.exceptions.JWTCreationException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;
    private final UsuarioRepository usuarioRepository;

    public TokenService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String gerarToken(Usuario usuario){
        try{
            return JWT.create().withIssuer("Controle de Acesso")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(dataExpiracao())
                    .sign(Algorithm.HMAC256(secret));
        }catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(6).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String tokenJWT){
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("Controle de Acesso")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }
}
