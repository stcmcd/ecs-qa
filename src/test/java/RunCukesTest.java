package steve;

import cucumber.api.junit.Cucumber;
import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = {"pretty", "html:target/cucumber-html-report", "json:target/cucumber.json"},
        tags = {"@steve"},
		glue = {"steve.step_definitions"}, features = {"C:/Projects/SteveTests1/src/test/resources/steve/features"},
        monochrome = true)
public class RunCukesTest {
}