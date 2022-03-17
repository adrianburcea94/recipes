package com.abnamro.recipes.rest.recipe.v1;

import com.abnamro.recipes.services.recipe.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/recipes")
public class RecipeControllerV1 {

    private final RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> listAllRecipes() {
        return ResponseEntity.ok(recipeService.listAllRecipes());
    }

    @PostMapping
    public ResponseEntity<RecipeDTO> createRecipe(@RequestBody RecipeDTO recipeDTO, UriComponentsBuilder uriComponentsBuilder) {
        RecipeDTO createdRecipeDTO = recipeService.createRecipe(recipeDTO);
        URI locationHeaderUri = createLocationHeaderUri(uriComponentsBuilder, createdRecipeDTO);

        return ResponseEntity.created(locationHeaderUri).body(createdRecipeDTO);
    }

    @PutMapping
    public ResponseEntity<RecipeDTO> updateRecipe(@RequestBody RecipeDTO recipeDTO) {
        return ResponseEntity.ok(recipeService.updateRecipe(recipeDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable String id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    private URI createLocationHeaderUri(UriComponentsBuilder uriComponentsBuilder, RecipeDTO recipeDTO) {
        return uriComponentsBuilder.path("/v1/recipes/{id}")
                .buildAndExpand(Map.of("id", recipeDTO.getId()))
                .toUri();
    }
}
