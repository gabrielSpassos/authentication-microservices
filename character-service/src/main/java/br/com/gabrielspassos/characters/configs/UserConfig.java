package br.com.gabrielspassos.characters.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserConfig {

    @Value("${user-service.url}")
    private String userServiceUrl;
    @Value("${user.account-type.premium}")
    private String premiumUserAccountType;
    @Value("${user.account-type.normal}")
    private String normalUserAccountType;
    @Value("${user.normal.max-character-qtd}")
    private Integer normalMaxQtdChar;
    @Value("${user.premium.max-character-qtd}")
    private Integer premiumMaxQtdChar;

    public String getUserServiceUrl() {
        return userServiceUrl;
    }

    public String getPremiumUserAccountType() {
        return premiumUserAccountType;
    }

    public String getNormalUserAccountType() {
        return normalUserAccountType;
    }

    public Integer getNormalMaxQtdChar() {
        return normalMaxQtdChar;
    }

    public Integer getPremiumMaxQtdChar() {
        return premiumMaxQtdChar;
    }
}
