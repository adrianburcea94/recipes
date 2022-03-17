package com.abnamro.recipes.rest.recipe.v1;

import com.abnamro.recipes.services.recipe.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecipeControllerV1.class)
@ExtendWith(MockitoExtension.class)
public class RecipeControllerTests {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RecipeService recipeService;
    private List<RecipeDTO> recipeList;

    @BeforeEach
    void setUp() {
        this.recipeList = new ArrayList<>();

        final RecipeDTO recipe1 = RecipeDTO.builder()
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

        final RecipeDTO recipe2 = RecipeDTO.builder()
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

        this.recipeList.add(recipe1);
        this.recipeList.add(recipe2);
    }

    @Test
    void testListAllRecipesSuccessfully() throws Exception {
        when(recipeService.listAllRecipes()).thenReturn(recipeList);

        this.mockMvc.perform(get("/v1/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    // TODO: Update the test data
    @Test
    void testCreateRecipe() throws Exception {
        when(recipeService.createRecipe(any(RecipeDTO.class))).thenAnswer(invocation -> invocation.getArgument(0));

        final RecipeDTO recipeDTO = RecipeDTO.builder()
                .id("Pasta")
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

        this.mockMvc.perform(post("/v1/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(recipeDTO.getId())))
                .andExpect(jsonPath("$.dateTimeCreated", is(recipeDTO.getDateTimeCreated().format(dateTimeFormatter))))
                .andExpect(jsonPath("$.vegetarian", is(recipeDTO.isVegetarian())))
                .andExpect(jsonPath("$.servings", is(recipeDTO.getServings())))
                .andExpect(jsonPath("$.ingredients", is(recipeDTO.getIngredients())))
                .andExpect(jsonPath("$.cookingInstructions", is(recipeDTO.getCookingInstructions())))
                .andReturn();
    }

    @Test
    void testUpdateRecipe() throws Exception {
        final RecipeDTO recipeDTO = RecipeDTO.builder()
                .id("Oatmeal Raisin Cookies")
                .dateTimeCreated(LocalDateTime.now())
                .vegetarian(false)
                .servings(100)
                .ingredients(Arrays.asList(
                        "2 cup butter, softened",
                        "2 cup white sugar",
                        "2 cup packed light brown sugar",
                        "3 eggs",
                        "2 teaspoon vanilla extract",
                        "4 cups all-purpose flour",
                        "2 cup raisins"))
                .cookingInstructions("""
                            Preheat oven to 375 degrees F (190 degrees C).
                            In large bowl, cream together butter, white sugar, and brown sugar until smooth. Beat in the eggs and vanilla until fluffy. Stir together flour, baking soda, cinnamon, and salt. Gradually beat into butter mixture. Stir in oats and raisins. Drop by teaspoonfuls onto ungreased cookie sheets.
                            Bake 9 minutes in the preheated oven, or until golden brown. Cool slightly, remove from sheet to wire rack. Cool completely.
                        """)
                .build();

        when(recipeService.updateRecipe(any(RecipeDTO.class))).thenAnswer(invocation -> invocation.getArgument(0));
        this.mockMvc.perform(put("/v1/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(recipeDTO.getId())))
                .andExpect(jsonPath("$.dateTimeCreated", is(recipeDTO.getDateTimeCreated().format(dateTimeFormatter))))
                .andExpect(jsonPath("$.vegetarian", is(recipeDTO.isVegetarian())))
                .andExpect(jsonPath("$.servings", is(recipeDTO.getServings())))
                .andExpect(jsonPath("$.ingredients", is(recipeDTO.getIngredients())))
                .andExpect(jsonPath("$.cookingInstructions", is(recipeDTO.getCookingInstructions())))
                .andReturn();
    }

    @Test
    void testDeleteRecipe() throws Exception {
        String recipeId = "Oatmeal Raisin Cookies";
        doNothing().when(recipeService).deleteRecipe(recipeId);

        this.mockMvc.perform(delete("/v1/recipes/{id}", recipeId))
                .andExpect(status().isNoContent());
    }
}
