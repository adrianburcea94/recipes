package cucumber;

import io.cucumber.java.After;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
public class CucumberScenarioLifecycle {

    @Autowired
    private CucumberTestContext testContext;

    @After
    public void cleanUp() {

        testContext.reset();

    }

}

