package cucumber;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@TestComponent
@RequiredArgsConstructor
@Data
public class CucumberTestContext {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public void reset() {
        // TODO:
    }
}
