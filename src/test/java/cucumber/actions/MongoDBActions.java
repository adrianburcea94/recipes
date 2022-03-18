package cucumber.actions;

import com.abnamro.recipes.datalayer.recipe.Recipe;
import com.abnamro.recipes.datalayer.recipe.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import java.util.Optional;

@TestComponent
public class MongoDBActions {

    @Autowired
    private RecipeRepository recipeRepository;

    public Optional<Recipe> retrieveRecipe(String recipeId) {
        return recipeRepository.findById(recipeId);
    }

}