package cucumber;

import com.abnamro.recipes.RecipesApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(
        initializers = { SpringBootContextInitializer.class },
        classes = {RecipesApplication.class, CucumberTestContextConfig.class}
)
@ActiveProfiles(profiles={"cucumber"})
public class CucumberSpringBootContext {
}
