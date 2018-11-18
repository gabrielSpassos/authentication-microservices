package br.com.gabrielspassos.characters.cucumber.stepdefs;

import br.com.gabrielspassos.characters.CharacterApplication;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = "br.com.gabrielspassos.characters.cucumber")
@ContextConfiguration(loader = SpringBootContextLoader.class, classes = CharacterApplication.class)
public class CucumberTestsRunner {
}