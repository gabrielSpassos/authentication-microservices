package br.com.gabrielspassos.users.cucumber.stepdefs;

import br.com.gabrielspassos.users.TestConfig;
import br.com.gabrielspassos.users.controllers.dto.ResponseUserDTO;
import br.com.gabrielspassos.users.controllers.dto.UserDTO;
import br.com.gabrielspassos.users.cucumber.World;
import br.com.gabrielspassos.users.error.SimpleError;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

public class UpdateUserStepdefs extends TestConfig implements En {

    @Autowired
    private World world;
    @LocalServerPort
    private int localPort;

    private RestTemplate restTemplate;

    public UpdateUserStepdefs() {

        Before(() -> {
            restTemplate = new RestTemplate();
        });

        When("^update an user$", () -> {
            try {
                world.status = HttpStatus.OK.value();
                String url = String.format("http://localhost:%s/user-service/api/v1/users/%s", localPort, world.map.get("id"));
                HttpEntity requestUpdate = createEntity();
                ResponseEntity<ResponseUserDTO> response = restTemplate.exchange(url, HttpMethod.PUT, requestUpdate, ResponseUserDTO.class);
                world.responseUserDTO = response.getBody();
            } catch (HttpServerErrorException | HttpClientErrorException e) {
                world.simpleError = new ObjectMapper().readValue(e.getResponseBodyAsString(), SimpleError.class);
                world.status = e.getRawStatusCode();
            }
        });

        Then("^must update the user$", () -> {
            ResponseUserDTO expected = buildResponse(
                    world.map.get("id").toString(),
                    world.map.get("accountType").toString(),
                    world.map.get("login").toString(),
                    world.map.get("password").toString(),
                    world.map.get("status").toString()
            );

            assertEquals(expected.getId(), world.responseUserDTO.getId());
            assertEquals(expected.getAccountType(), world.responseUserDTO.getAccountType());
            assertEquals(expected.getLogin(), world.responseUserDTO.getLogin());
            assertEquals(expected.getPassword(), world.responseUserDTO.getPassword());
            assertEquals(expected.getStatus(), world.responseUserDTO.getStatus());
        });
    }

    private HttpEntity createEntity() {
        UserDTO userDTO = createUserDto(
                world.map.get("accountType").toString(),
                world.map.get("login").toString(),
                world.map.get("password").toString(),
                world.map.get("status").toString()
        );
        return new HttpEntity<>(userDTO);
    }
}
