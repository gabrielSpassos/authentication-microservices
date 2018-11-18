package br.com.gabrielspassos.characters.cucumber.stepdefs;

import br.com.gabrielspassos.characters.TestConfig;
import br.com.gabrielspassos.characters.controllers.dto.CharacterDTO;
import br.com.gabrielspassos.characters.cucumber.World;
import br.com.gabrielspassos.characters.error.SimpleError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.google.common.collect.Maps;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;

public class CreateCharacterStepdefs extends TestConfig implements En {

    @Autowired
    private World world;
    @Autowired
    private RestTemplate restTemplate;
    @LocalServerPort
    private int localPort;

    private Map<String, String> bodyMap;

    public CreateCharacterStepdefs() {

        bodyMap = Maps.newHashMap();
        bodyMap.put("with normal permissions", Resources.GET_NORMAL_USER_BODY);
        bodyMap.put("with normal permissions and already have one char", Resources.GET_NORMAL_USER_WITH_ONE_CHAR);
        bodyMap.put("blocked user", Resources.GET_BLOCKED_USER_BODY);
        bodyMap.put("not found user", Resources.NOT_FOUND_USER);
        bodyMap.put("with premium permissions", Resources.GET_PREMIUM_USER_WITH_ONE_CHAR);
        bodyMap.put("with premium permissions and two chars", Resources.GET_PREMIUM_USER_WITH_TWO_CHAR);

        Before(new String[]{"@CreateChar"}, () -> {
            world.wireMockServer = new WireMockServer(wireMockConfig().port(4003));
            world.wireMockServer.start();
        });

        After(new String[]{"@CreateChar"}, () -> {
            world.wireMockServer.stop();
        });

        Given("^user id \"([^\"]*)\"$", (String userId) -> {
            world.map.put("userId", userId);
        });

        Given("^character name \"([^\"]*)\"$", (String charName) -> {
            world.map.put("charName", charName);
        });

        Given("^character class \"([^\"]*)\"$", (String charClass) -> {
            world.map.put("charClass", charClass);
        });

        Given("^this user is a user (.*)$", (String requestBody) -> {
            world.map.put("body", requestBody);
        });

        Given("^find an user with the id \"([^\"]*)\" with status (\\d+)$", (String id, Integer status) -> {
            world.wireMockServer.stubFor(get(WireMock.urlPathEqualTo("/user-service/api/v1/users/" + id))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                            .withStatus(status)
                            .withBody(bodyMap.get(world.map.get("body").toString()))));
        });

        When("^create the user character$", () -> {
            try {
                world.status = HttpStatus.OK.value();
                world.characterDTO = restTemplate.postForEntity(
                        String.format("http://localhost:%s/character-service/api/users/%s/characters",
                                localPort, world.map.get("userId")
                        ), buildCharacterDto(), CharacterDTO.class)
                        .getBody();
            } catch (HttpServerErrorException | HttpClientErrorException e) {
                world.simpleError = new ObjectMapper().readValue(e.getResponseBodyAsString(), SimpleError.class);
                world.status = e.getRawStatusCode();
            }
        });

        Then("^must return the new character$", () -> {
            CharacterDTO expected = createCharacterDTO(
                    world.map.get("charClass").toString(),
                    world.map.get("charName").toString()
            );

            assertEquals(expected, world.characterDTO);
        });

        Then("^a response status equals to (\\d+)$", (Integer status) -> {
            assertEquals(status, world.status);
        });

        Then("^must return the error, because this user hasn't permission to create one more character$", () -> {
            assertEquals("No allowed create more characters", world.simpleError.getMessage());
        });

        Then("^must return error, because the user is blocker$", () -> {
            assertEquals("User isn't valid", world.simpleError.getMessage());
        });

        Then("^must return error, because the user isn't found$", () -> {
            assertEquals("Not found user with this id", world.simpleError.getMessage());
        });
    }

    private CharacterDTO buildCharacterDto() {
        String charClass = Optional.ofNullable(world.map.get("charClass")).isPresent()
                ? world.map.get("charClass").toString()
                : null;

        String charName = Optional.ofNullable(world.map.get("charName")).isPresent()
                ? world.map.get("charName").toString()
                : null;

        return createCharacterDTO(charClass, charName);
    }

    private class Resources {
        static final String GET_NORMAL_USER_BODY = "{\n" +
                "  \"id\": \"5beeddf9271c06542585634d\",\n" +
                "  \"status\": \"ative\",\n" +
                "  \"login\": \"user1\",\n" +
                "  \"password\": \"001\",\n" +
                "  \"accountType\": \"normal\",\n" +
                "  \"characters\": []\n" +
                "}";

        static final String GET_NORMAL_USER_WITH_ONE_CHAR = "{\n" +
                "  \"id\": \"5beeddf9271c06542585634d\",\n" +
                "  \"status\": \"ative\",\n" +
                "  \"login\": \"user1\",\n" +
                "  \"password\": \"001\",\n" +
                "  \"accountType\": \"normal\",\n" +
                "  \"characters\": [\n" +
                "    {\n" +
                "      \"name\": \"TestChar\",\n" +
                "      \"charClass\": \"Tank\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        static final String GET_PREMIUM_USER_WITH_ONE_CHAR = "{\n" +
                "  \"id\": \"hgghjasasduy1231\",\n" +
                "  \"status\": \"ative\",\n" +
                "  \"login\": \"user2\",\n" +
                "  \"password\": \"002\",\n" +
                "  \"accountType\": \"premium\",\n" +
                "  \"characters\": [\n" +
                "    {\n" +
                "      \"name\": \"TestChar\",\n" +
                "      \"charClass\": \"Tank\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        static final String GET_PREMIUM_USER_WITH_TWO_CHAR = "{\n" +
                "  \"id\": \"hgghjasasduy1231\",\n" +
                "  \"status\": \"ative\",\n" +
                "  \"login\": \"user2\",\n" +
                "  \"password\": \"002\",\n" +
                "  \"accountType\": \"premium\",\n" +
                "  \"characters\": [\n" +
                "    {\n" +
                "      \"name\": \"Merlin\",\n" +
                "      \"charClass\": \"Warrior\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"clovis\",\n" +
                "      \"charClass\": \"assassin\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        static final String GET_BLOCKED_USER_BODY = "{\n" +
                "  \"id\": \"5beeddf9271c06542585634d\",\n" +
                "  \"status\": \"blocked\",\n" +
                "  \"login\": \"user1\",\n" +
                "  \"password\": \"001\",\n" +
                "  \"accountType\": \"normal\",\n" +
                "  \"characters\": []\n" +
                "}";

        static final String NOT_FOUND_USER = "{\n" +
                "  \"message\": \"User inexistent\"\n" +
                "}";
    }
}
