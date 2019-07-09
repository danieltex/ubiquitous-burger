package com.ubiquitousburger.core.burger.repositories;

import com.ubiquitousburger.core.burger.pojos.Ingredient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class IngredientRepositoryInMemory implements IngredientRepository {
    private static Logger LOGGER = LoggerFactory.getLogger(IngredientRepositoryInMemory.class);
    private Set<Ingredient> ingredients;

    public IngredientRepositoryInMemory() {
        this.ingredients = new HashSet<>();
    }

    @Override
    public List<Ingredient> getAvailableIngredients() {
        return new ArrayList<>(ingredients);
    }

    @Override
    public Ingredient getByName(String ingredientName) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equals(ingredientName)) {
                return ingredient;
            }
        }
        LOGGER.info("No ingredient name {} found", ingredientName);
        return null;
    }

    @Override
    public void save(Ingredient ingredient) {
        if (!ingredients.add(ingredient)) {
            ingredients.remove(ingredient);
            ingredients.add(ingredient);
            LOGGER.info("Updating ingredient '{}'", ingredient.getName());
        } else {
            LOGGER.info("Saved ingredient '{}'", ingredient.getName());
        }
    }
}
