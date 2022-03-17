package com.abnamro.recipes.services.recipe;
import com.abnamro.recipes.datalayer.recipe.Recipe;
import com.abnamro.recipes.rest.recipe.v1.RecipeDTO;
import lombok.NoArgsConstructor;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    Recipe map(RecipeDTO source);

    @InheritInverseConfiguration
    RecipeDTO map(Recipe source);

    List<RecipeDTO> map(List<Recipe> source);
}