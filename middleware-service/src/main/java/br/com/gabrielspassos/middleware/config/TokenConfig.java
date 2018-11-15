package br.com.gabrielspassos.middleware.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenConfig {

    @Value("${token.secret}")
    private String tokenSecret;
    @Value("${token.prefix}")
    private String tokenPrefix;
    @Value("${token.expire-time")
    private String tokenExpireTime;

    public String getTokenSecret() {
        return tokenSecret;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public long getTokenExpireTime() {
        return Long.parseLong(tokenExpireTime);
    }
}
