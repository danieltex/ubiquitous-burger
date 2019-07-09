package com.ubiquitousburger.core.burger.repositories;

import com.ubiquitousburger.core.burger.pojos.Ingredient;

import java.util.List;

public interface IngredientRepository {
    List<Ingredient> getAvailableIngredients();
    Ingredient getByName(String ingredientName);
    void save(Ingredient ingredient);
}
