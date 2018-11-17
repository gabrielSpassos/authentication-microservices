package br.com.gabrielspassos.users.cucumber.stepdefs;

import br.com.gabrielspassos.users.TestConfig;
import br.com.gabrielspassos.users.controllers.dto.ResponseUserDTO;
import br.com.gabrielspassos.users.cucumber.World;
import br.com.gabrielspassos.users.error.SimpleError;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateUserStepdefs extends TestConfig implements En {

    @Autowired
    private World world;
    @LocalServerPort
    private int localPort;

    private RestTemplate restTemplate;

    public CreateUserStepdefs() {

        Before(() -> {
            restTemplate = new RestTemplate();
        });

        Given("^login \"([^\"]*)\"$", (String login) -> {
            world.map.put("login", login);
        });

        Given("^password \"([^\"]*)\"$", (String password) -> {
            world.map.put("password", password);
        });

        Given("^account type \"([^\"]*)\"$", (String accountType) -> {
            world.map.put("accountType", accountType);
        });

        Given("^status \"([^\"]*)\"$", (String status) -> {
            world.map.put("status", status);
        });

        When("^create an user$", () -> {
            try {
                world.status = HttpStatus.OK.value();
                world.responseUserDTO = restTemplate.postForEntity(
                        String.format("http://localhost:%s/user-service/api/v1/users", localPort),
                        createUserDto(
                                world.map.get("accountType").toString(),
                                world.map.get("login").toString(),
                                world.map.get("password").toString(),
                                world.map.get("status").toString()
                        ), ResponseUserDTO.class)
                        .getBody();
            } catch (HttpServerErrorException | HttpClientErrorException e) {
                world.simpleError = new ObjectMapper().readValue(e.getResponseBodyAsString(), SimpleError.class);
                world.status = e.getRawStatusCode();
            }
        });

        Then("^must create an user$", () -> {
            ResponseUserDTO expected = buildResponse(
                    "",
                    world.map.get("accountType").toString(),
                    world.map.get("login").toString(),
                    world.map.get("password").toString(),
                    world.map.get("status").toString()
            );

            assertNotNull(world.responseUserDTO.getId());
            assertEquals(expected.getAccountType(), world.responseUserDTO.getAccountType());
            assertEquals(expected.getLogin(), world.responseUserDTO.getLogin());
            assertEquals(expected.getPassword(), world.responseUserDTO.getPassword());
            assertEquals(expected.getStatus(), world.responseUserDTO.getStatus());
        });
    }
}
