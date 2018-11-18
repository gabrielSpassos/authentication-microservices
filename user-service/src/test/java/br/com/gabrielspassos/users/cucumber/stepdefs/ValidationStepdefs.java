package br.com.gabrielspassos.users.cucumber.stepdefs;

import br.com.gabrielspassos.users.TestConfig;
import br.com.gabrielspassos.users.cucumber.World;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class ValidationStepdefs extends TestConfig implements En {

    @Autowired
    private World world;

    public ValidationStepdefs() {

        Given("^status null$", () -> {
            world.map.put("status", null);
        });

        Then("^must return error, because status is null$", () -> {
            assertEquals("You must inform the user status", world.simpleError.getMessage());
        });

        Given("^account type null$", () -> {
            world.map.put("accountType", null);
        });

        Then("^must return error, because account type is null$", () -> {
            assertEquals("You must inform the user account type", world.simpleError.getMessage());
        });

        Given("^password null$", () -> {
            world.map.put("password", null);
        });

        Then("^must return error, because password is null$", () -> {
            assertEquals("You must inform the user password", world.simpleError.getMessage());
        });

        Given("^login null$", () -> {
            world.map.put("login", null);
        });

        Then("^must return error, because login is null$", () -> {
            assertEquals("You must inform the user login", world.simpleError.getMessage());
        });
    }
}
