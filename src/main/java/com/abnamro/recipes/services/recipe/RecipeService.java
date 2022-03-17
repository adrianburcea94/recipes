package com.abnamro.recipes.services.recipe;

import com.abnamro.recipes.datalayer.recipe.RecipeRepository;
import com.abnamro.recipes.rest.recipe.v1.RecipeDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    private final RecipeMapper recipeMapper;

    public List<RecipeDTO> listAllRecipes() {
        return recipeMapper.map(recipeRepository.findAll());
    }

    public RecipeDTO createRecipe(RecipeDTO recipeDTO) {
        return recipeMapper.map(recipeRepository.save(recipeMapper.map(recipeDTO)));
    }

    public RecipeDTO updateRecipe(RecipeDTO newRecipeDTO) {
        return recipeRepository.findById(newRecipeDTO.getId()).map(recipeMapper::map).map(recipeDTO -> {
            recipeDTO.setServings(newRecipeDTO.getServings());
            recipeDTO.setVegetarian(newRecipeDTO.isVegetarian());
            recipeDTO.setIngredients(newRecipeDTO.getIngredients());
            recipeDTO.setCookingInstructions(newRecipeDTO.getCookingInstructions());

            return createRecipe(recipeDTO);
        }).orElseGet(() -> createRecipe(newRecipeDTO));
    }

    public void deleteRecipe(String recipeId) {
        recipeRepository.deleteById(recipeId);
    }
}
