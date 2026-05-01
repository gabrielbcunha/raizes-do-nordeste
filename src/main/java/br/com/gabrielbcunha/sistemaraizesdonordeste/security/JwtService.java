package br.com.gabrielbcunha.sistemaraizesdonordeste.security;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final String CHAVE_SECRETA = "SistemaRaizesDoNordesteChaveSecretaApiKeyTeste";

    public String geradorToken(Usuario usuario) {
        return Jwts.builder()
                .setIssuer("API Raízes do Nordeste")
                .setSubject(usuario.getUsername())
                .claim("perfil", usuario.getPerfil())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))
                .signWith(obterChaveDeAssinatura(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extrairUserName(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(obterChaveDeAssinatura())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Key obterChaveDeAssinatura() {
        return Keys.hmacShaKeyFor(CHAVE_SECRETA.getBytes());
    }

}
