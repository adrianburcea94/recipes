package cucumber;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import static cucumber.TestContainersSetup.getMongoDBContainerUri;

public class SpringBootContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

        TestContainersSetup.initTestContainers();

        TestPropertyValues values = TestPropertyValues.of(
                "spring.data.mongodb.uri=" + getMongoDBContainerUri()
        );

        values.applyTo(configurableApplicationContext);
    }
}
