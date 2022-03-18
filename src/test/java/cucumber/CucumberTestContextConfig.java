package cucumber;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = {"cucumber"})
public class CucumberTestContextConfig {

}
