package br.com.gabrielspassos.users.cucumber.stepdefs;

import br.com.gabrielspassos.users.UserApplication;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = "br.com.gabrielspassos.users.cucumber")
@ContextConfiguration(loader = SpringBootContextLoader.class, classes = UserApplication.class)
public class CucumberTestsRunner {
}
