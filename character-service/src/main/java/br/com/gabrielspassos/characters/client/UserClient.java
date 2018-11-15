package br.com.gabrielspassos.characters.client;

import br.com.gabrielspassos.characters.client.dto.UserDTO;
import br.com.gabrielspassos.characters.configs.UserConfig;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserClient {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserConfig userConfig;
    @Autowired
    private ModelMapper modelMapper;

    public UserDTO getUserById(String id) {
        ResponseEntity response = restTemplate.getForEntity(buildUrl(id), UserDTO.class);
        return convertToUserEntity(response.getBody());
    }

    private String buildUrl(String id) {
        return userConfig.getUserServiceUrl() + id;
    }

    private UserDTO convertToUserEntity(Object responseBody) {
        return modelMapper.map(responseBody, UserDTO.class);
    }
}
