package br.com.gabrielspassos.characters.cucumber.stepdefs;

import br.com.gabrielspassos.characters.TestConfig;
import br.com.gabrielspassos.characters.cucumber.World;
import com.github.tomakehurst.wiremock.WireMockServer;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;

public class ValidationsStepdefs extends TestConfig implements En {

    @Autowired
    private World world;

    public ValidationsStepdefs() {
        Before(new String[]{"@Validation"}, () -> {
            world.wireMockServer = new WireMockServer(wireMockConfig().port(4003));
            world.wireMockServer.start();
        });

        After(new String[]{"@Validation"}, () -> {
            world.wireMockServer.stop();
        });

        Given("^character name null$", () -> {
            world.map.put("charName", null);
        });

        Given("^character class null$", () -> {
            world.map.put("charClass", null);
        });

        Then("^must return error, because char name is null$", () -> {
            assertEquals("You must inform the the character name", world.simpleError.getMessage());
        });

        Then("^must return error, because char class is null$", () -> {
            assertEquals("You must inform the the character class name", world.simpleError.getMessage());
        });
    }
}
