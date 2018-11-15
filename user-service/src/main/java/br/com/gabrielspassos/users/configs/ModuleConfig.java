package br.com.gabrielspassos.users.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModuleConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}