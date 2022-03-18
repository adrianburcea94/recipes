package cucumber.steps;

import com.abnamro.recipes.datalayer.recipe.Recipe;
import com.abnamro.recipes.rest.recipe.v1.RecipeControllerV1;
import com.abnamro.recipes.rest.recipe.v1.RecipeDTO;
import cucumber.CucumberTestContext;
import cucumber.actions.MongoDBActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;


@AllArgsConstructor
public class RecipeManagementSteps {

    @Autowired
    private MongoDBActions mongoDBActions;

    @Autowired
    private CucumberTestContext testContext;

    @Autowired
    private RecipeControllerV1 recipeControllerV1;

    @Given("the recipe application is up and running")
    public void theRecipeApplicationIsUpAndRunning() {
    }


    @When("a recipe (.*) is created$")
    public void aRecipeIdIsCreated(String id) {

        final RecipeDTO recipeDTO = RecipeDTO.builder()
                .id(id)
                .dateTimeCreated(LocalDateTime.now())
                .vegetarian(false)
                .servings(6)
                .ingredients(Arrays.asList(
                        "1 corned beef brisket with spice packet",
                        "10 small red potatoes",
                        "5 medium carrots", "1 large head cabbage"))
                .cookingInstructions("""
                           Place corned beef in a Dutch oven and cover with water. Add spice packet, cover, and bring to a boil. Reduce heat and simmer until corned beef is just about fork-tender, about 2 hours.
                           While the corned beef is simmering, cut potatoes in half. Peel carrots and cut into 3-inch pieces. Cut cabbage into small wedges.
                           When corned beef has cooked for 2 hours, add potatoes and carrots; cook until vegetables are almost tender and meat is fork-tender, about 10 minutes. Add cabbage and cook until tender, about 15 more minutes.
                           Remove meat and let rest for 15 minutes. Leave broth and vegetables in the Dutch oven.
                           Slice meat across the grain. Serve with vegetables and broth.
                        """)
                .build();

        Assertions.assertNotNull(recipeControllerV1.createRecipe(recipeDTO, UriComponentsBuilder.newInstance()));
    }

    @Then("the recipe (.*) is added to the database$")
    public void theRecipeIsAddedToTheDatabase(String id) {
        Optional<Recipe> recipe = mongoDBActions.retrieveRecipe(id);
        Assertions.assertTrue(recipe.isPresent());
    }

    @And("a recipe (.*) is deleted$")
    public void aRecipeIdIsDeleted(String id) {
        recipeControllerV1.deleteRecipeById(id);
    }

    @Then("the recipe (.*) is not in the database$")
    public void theRecipeIdIsNotInTheDatabase(String id) {
        Optional<Recipe> recipe = mongoDBActions.retrieveRecipe(id);
        Assertions.assertTrue(recipe.isEmpty());
    }
}
