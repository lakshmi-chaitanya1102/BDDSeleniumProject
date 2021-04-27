package runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(
features = "src/test/java/features",
glue= {"StepDefinitions","utilities"},
dryRun=false,
plugin={"pretty", "html:target/cucumber-reports"},
monochrome=true

/*
"pretty","json :target/cucumberJson",
"pretty","junit :target/cucumberJunit"*/
		
)
	
public class TestRunner {
}
