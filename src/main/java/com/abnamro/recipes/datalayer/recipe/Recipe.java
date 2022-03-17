package com.abnamro.recipes.datalayer.recipe;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    public String id;

    @DateTimeFormat(style = "dd-MM-yyyy HH:mm")
    @CreatedDate
    private LocalDateTime dateTimeCreated;

    private boolean vegetarian;

    private Integer servings;

    private List<String> ingredients;

    private String cookingInstructions;
}
