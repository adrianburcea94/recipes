package com.abnamro.recipes.services.recipe;

import com.abnamro.recipes.datalayer.recipe.Recipe;
import com.abnamro.recipes.datalayer.recipe.RecipeRepository;
import com.abnamro.recipes.rest.recipe.v1.RecipeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RecipeServiceTests {

    @MockBean
    private RecipeRepository recipeRepository;

    private RecipeMapper recipeMapper;

    private RecipeService recipeService;

    @BeforeEach
    void setup() {
        recipeMapper = new RecipeMapperImpl();
        recipeService = new RecipeService(recipeRepository, recipeMapper);
    }

    @Test
    void testListAllRecipes() {
        final Recipe recipe1 = Recipe.builder()
                .id("Corned Beef and Cabbage")
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

        final Recipe recipe2 = Recipe.builder()
                .id("Oatmeal Raisin Cookies")
                .dateTimeCreated(LocalDateTime.now())
                .vegetarian(true)
                .servings(48)
                .ingredients(Arrays.asList(
                        "1 cup butter, softened",
                        "1 cup white sugar",
                        "1 cup packed light brown sugar",
                        "2 eggs",
                        "1 teaspoon vanilla extract",
                        "2 cups all-purpose flour",
                        "1 cup raisins"))
                .cookingInstructions("""
                            Preheat oven to 375 degrees F (190 degrees C).
                            In large bowl, cream together butter, white sugar, and brown sugar until smooth. Beat in the eggs and vanilla until fluffy. Stir together flour, baking soda, cinnamon, and salt. Gradually beat into butter mixture. Stir in oats and raisins. Drop by teaspoonfuls onto ungreased cookie sheets.
                            Bake 8 to 10 minutes in the preheated oven, or until golden brown. Cool slightly, remove from sheet to wire rack. Cool completely.
                        """)
                .build();

        when(recipeRepository.findAll()).thenReturn(List.of(recipe1, recipe2));

        final List<RecipeDTO> recipeList = recipeService.listAllRecipes();
        assertNotNull(recipeList);
        assertEquals(2, recipeList.size());

        final RecipeDTO recipeDTO1 = recipeList.get(0);
        assertNotNull(recipeDTO1);
        assertEquals(recipe1.getId(), recipeDTO1.getId());
        assertEquals(recipe1.getDateTimeCreated(), recipeDTO1.getDateTimeCreated());
        assertEquals(recipe1.isVegetarian(), recipeDTO1.isVegetarian());
        assertEquals(recipe1.getServings(), recipeDTO1.getServings());
        assertEquals(recipe1.getIngredients(), recipeDTO1.getIngredients());
        assertEquals(recipe1.getCookingInstructions(), recipeDTO1.getCookingInstructions());

        final RecipeDTO recipeDTO2 = recipeList.get(1);
        assertNotNull(recipeDTO2);
        assertEquals(recipe2.getId(), recipeDTO2.getId());
        assertEquals(recipe2.getDateTimeCreated(), recipeDTO2.getDateTimeCreated());
        assertEquals(recipe2.isVegetarian(), recipeDTO2.isVegetarian());
        assertEquals(recipe2.getServings(), recipeDTO2.getServings());
        assertEquals(recipe2.getIngredients(), recipeDTO2.getIngredients());
        assertEquals(recipe2.getCookingInstructions(), recipeDTO2.getCookingInstructions());
    }

    @Test
    void testCreateRecipe() {
        final Recipe recipe1 = Recipe.builder()
                .id("Corned Beef and Cabbage")
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

        when(recipeRepository.findById(recipe1.getId())).thenReturn(Optional.empty());
        when(recipeRepository.save(recipe1)).thenAnswer(invocation -> invocation.getArgument(0));

        final RecipeDTO savedRecipe = recipeService.createRecipe(recipeMapper.map(recipe1));
        assertNotNull(savedRecipe);
        assertEquals(recipe1.getId(), savedRecipe.getId());
        assertEquals(recipe1.getDateTimeCreated(), savedRecipe.getDateTimeCreated());
        assertEquals(recipe1.isVegetarian(), savedRecipe.isVegetarian());
        assertEquals(recipe1.getServings(), savedRecipe.getServings());
        assertEquals(recipe1.getIngredients(), savedRecipe.getIngredients());
        assertEquals(recipe1.getCookingInstructions(), savedRecipe.getCookingInstructions());

        verify(recipeRepository).save(any(Recipe.class));
    }

    @Test
    void testUpdateRecipe() {
        final Recipe recipe1 = Recipe.builder()
                .id("Corned Beef and Cabbage")
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

        when(recipeRepository.save(recipe1)).thenReturn(recipe1);
        final RecipeDTO expectedRecipe = recipeService.updateRecipe(recipeMapper.map(recipe1));

        assertNotNull(expectedRecipe);
        verify(recipeRepository).save(any(Recipe.class));
    }

    @Test
    void testDeleteRecipe() {
        final String recipeId = "Corned Beef and Cabbage";

        recipeService.deleteRecipe(recipeId);
        recipeService.deleteRecipe(recipeId);
        recipeService.deleteRecipe(recipeId);

        verify(recipeRepository, times(3)).deleteById(recipeId);
    }
}
