package br.com.gabrielspassos.users.cucumber.stepdefs;

import br.com.gabrielspassos.users.TestConfig;
import br.com.gabrielspassos.users.controllers.dto.ResponseUserDTO;
import br.com.gabrielspassos.users.cucumber.World;
import br.com.gabrielspassos.users.error.SimpleError;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class GetUserStepdefs extends TestConfig implements En {

    @Autowired
    private World world;
    @LocalServerPort
    private int localPort;
    private RestTemplate restTemplate;

    public GetUserStepdefs() {

        Before(() -> {
            restTemplate = new RestTemplate();
        });

        Given("^id \"([^\"]*)\"$", (String id) -> {
            world.map.put("id", id);
        });

        Given("^none password$", () -> {
            world.map.put("password", null);
        });

        When("^search the users by id$", () -> {
            try {
                world.status = HttpStatus.OK.value();
                world.responseUserDTO = restTemplate.getForEntity(
                        String.format("http://localhost:%s/user-service/api/v1/users/%s", localPort, world.map.get("id")),
                        ResponseUserDTO.class)
                        .getBody();
            } catch (HttpServerErrorException | HttpClientErrorException e) {
                world.simpleError = new ObjectMapper().readValue(e.getResponseBodyAsString(), SimpleError.class);
                world.status = e.getRawStatusCode();
            }
        });

        When("^search the users by login and password$", () -> {
            try {
                world.status = HttpStatus.OK.value();

                String password = Optional.ofNullable(world.map.get("password")).isPresent()
                        ?  world.map.get("password").toString()
                        : null;
                HttpEntity entity = createHeaders(password);
                String url = String.format("http://localhost:%s/user-service/api/v1/users/login/%s", localPort, world.map.get("login"));

                ResponseEntity<ResponseUserDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, ResponseUserDTO.class);
                world.responseUserDTO = response.getBody();
            } catch (HttpServerErrorException | HttpClientErrorException e) {
                world.simpleError = new ObjectMapper().readValue(e.getResponseBodyAsString(), SimpleError.class);
                world.status = e.getRawStatusCode();
            }
        });

        Then("^must return the user id \"([^\"]*)\"$", (String id) -> {
            ResponseUserDTO expected = buildResponse(
                    id,
                    "normal",
                    "tester",
                    "123",
                    "ative"
            );

            assertEquals(expected, world.responseUserDTO);
        });

        Then("^a response status equals to (\\d+)$", (Integer status) -> {
            assertEquals(status, world.status);
        });

        Then("^must return error informing the user not exists$", () -> {
            assertEquals("User inexistent", world.simpleError.getMessage());
        });

        Then("^must return error informing the need of a password$", () -> {
            assertEquals("You must inform the password", world.simpleError.getMessage());
        });
    }

    private HttpEntity createHeaders(String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("password", password);
        return new HttpEntity(headers);
    }
}
