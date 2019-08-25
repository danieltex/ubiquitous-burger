package com.ubiquitousburger.core.repositories;

import com.ubiquitousburger.core.models.Ingredient;

import java.util.List;

public interface IngredientRepository {
    List<Ingredient> getAvailableIngredients();
    Ingredient getByName(String ingredientName);
    void save(Ingredient ingredient);
}
