package br.com.gabrielspassos.middleware.service;

import br.com.gabrielspassos.middleware.config.TokenConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

@Service
public class TokenAuthenticationService {

    @Autowired
    private TokenConfig tokenConfig;

    public void addAuthentication(HttpServletResponse response, String login) {
        response.addHeader(HttpHeaders.AUTHORIZATION, tokenConfig.getTokenPrefix() + " " + buildJwt(login));
    }

    private String buildJwt(String login) {
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(new Date(System.currentTimeMillis() + tokenConfig.getTokenExpireTime()))
                .signWith(SignatureAlgorithm.HS512, tokenConfig.getTokenSecret())
                .compact();
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (Objects.nonNull(token)) {
            String user = buildUser(token);
            if (Objects.nonNull(user)) {
                return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
            }
        }
        throw new IllegalStateException("Not Found Token");

    }

    private String buildUser(String token) {
        return Jwts.parser()
                .setSigningKey(tokenConfig.getTokenSecret())
                .parseClaimsJws(token.replace(tokenConfig.getTokenPrefix(), ""))
                .getBody()
                .getSubject();
    }
}
