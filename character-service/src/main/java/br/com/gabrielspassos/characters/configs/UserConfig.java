package br.com.gabrielspassos.characters.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserConfig {

    @Value("${user-service.url}")
    private String userServiceUrl;

    public String getUserServiceUrl() {
        return userServiceUrl;
    }
}
