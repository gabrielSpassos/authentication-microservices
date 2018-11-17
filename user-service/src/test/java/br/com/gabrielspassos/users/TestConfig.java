package br.com.gabrielspassos.users;

import br.com.gabrielspassos.users.controllers.dto.ResponseUserDTO;
import br.com.gabrielspassos.users.controllers.dto.UserDTO;
import org.assertj.core.util.Lists;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {UserApplication.class})
public class TestConfig {

    protected ResponseUserDTO buildResponse(String id, String accountType, String login, String password, String status) {
        ResponseUserDTO responseUserDTO = new ResponseUserDTO();
        responseUserDTO.setId(id);
        responseUserDTO.setAccountType(accountType);
        responseUserDTO.setLogin(login);
        responseUserDTO.setPassword(password);
        responseUserDTO.setStatus(status);
        responseUserDTO.setCharacters(Lists.newArrayList());
        return responseUserDTO;
    }

    protected UserDTO createUserDto(String accountType, String login, String password, String status) {
        UserDTO userDTO = new UserDTO();
        userDTO.setAccountType(accountType);
        userDTO.setLogin(login);
        userDTO.setPassword(password);
        userDTO.setStatus(status);
        return userDTO;
    }
}
