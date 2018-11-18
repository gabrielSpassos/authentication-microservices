package br.com.gabrielspassos.characters.cucumber.stepdefs;

import br.com.gabrielspassos.characters.TestConfig;
import br.com.gabrielspassos.characters.controllers.dto.CharClassDTO;
import br.com.gabrielspassos.characters.controllers.dto.CharacterDTO;
import br.com.gabrielspassos.characters.cucumber.World;
import br.com.gabrielspassos.characters.error.SimpleError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
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

import java.util.Optional;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;

public class UpdateCharacterStepdefs extends TestConfig implements En {

    @Autowired
    private World world;
    @Autowired
    private RestTemplate restTemplate;
    @LocalServerPort
    private int localPort;

    public UpdateCharacterStepdefs() {

        Before(new String[]{"@UpdateChar"}, () -> {
            world.wireMockServer = new WireMockServer(wireMockConfig().port(4003));
            world.wireMockServer.start();
        });

        After(new String[]{"@UpdateChar"}, () -> {
            world.wireMockServer.stop();
        });

        When("^update the user character$", () -> {
            try {
                world.status = HttpStatus.OK.value();
                String url = String.format("http://localhost:%s/character-service/api/users/%s/characters/%s",
                        localPort, world.map.get("userId"), world.map.get("charName")
                );
                HttpEntity requestUpdate = createEntity();
                ResponseEntity<CharacterDTO> response = restTemplate.exchange(url, HttpMethod.PUT, requestUpdate, CharacterDTO.class);
                world.characterDTO = response.getBody();
            } catch (HttpServerErrorException | HttpClientErrorException e) {
                world.simpleError = new ObjectMapper().readValue(e.getResponseBodyAsString(), SimpleError.class);
                world.status = e.getRawStatusCode();
            }
        });

        Then("^must return the updated character$", () -> {
            CharacterDTO expected = createCharacterDTO(
                    world.map.get("charClass").toString(),
                    world.map.get("charName").toString()
            );

            assertEquals(expected, world.characterDTO);
        });

        Then("^must return error, because the character doesn't exists$", () -> {
            assertEquals("Not found character with this name", world.simpleError.getMessage());
        });
    }

    private HttpEntity createEntity() {
        String charClass = Optional.ofNullable(world.map.get("charClass")).isPresent()
                ? world.map.get("charClass").toString()
                : null;

        CharClassDTO charClassDTO = createCharClassDTO(charClass);

        return new HttpEntity<>(charClassDTO);
    }
}
