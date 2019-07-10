package com.ubiquitousburger.core.pojos;

import com.ubiquitousburger.core.exceptions.NotEnoughIngredientException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Burger {
    private final String name;
    private Map<String, Integer> ingredients;

    public Burger(String name, String... ingredients) {
        this.name = name;
        this.ingredients = new HashMap<>();
        for (String ingredient : ingredients) {
            add(ingredient, 1);
        }
    }

    public Burger(String name, Ingredient... ingredients) {
        this.name = name;
        this.ingredients = new HashMap<>();
        for (Ingredient ingredient : ingredients) {
            add(ingredient.getName(), 1);
        }
    }

    public String getName() {
        return name;
    }

    /**
     * A map of quantities for each ingredient on the burger
     * @return
     */
    public Map<String, Integer> getIngredients() {
        return new HashMap<>(ingredients);
    }

    public void add(String ingredient, int quantity) {
        int currentQuantity = ingredients.getOrDefault(ingredient, 0);
        ingredients.put(ingredient, currentQuantity + quantity);
    }

    public boolean hasIngredient(String ingredientName) {
        for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(ingredientName)) {
                return entry.getValue() > 0;
            }
        }
        return false;
    }

    /**
     *  Remove a quantity of ingredient from burger
     * @param ingredient
     * @throws NotEnoughIngredientException if not enought ingredients or burger does not contain ingredient
     */
    public void remove(String ingredient, int quantity) {
        if (ingredients.containsKey(ingredient)) {
            int currentQuantity = ingredients.get(ingredient);
            if (currentQuantity < quantity) {
                throw new NotEnoughIngredientException(String.format("Tried to remove %d %s but burger only had %d",
                        quantity, ingredient, currentQuantity));
            }

            if (currentQuantity - quantity == 0) {
                ingredients.remove(ingredient);
            } else {
                ingredients.put(ingredient, currentQuantity - quantity);
            }
        } else {
            throw new NotEnoughIngredientException(String.format("Burger has no %s", ingredient));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Burger burger = (Burger) o;
        return name.equals(burger.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Burger{" +
                "name='" + name + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
