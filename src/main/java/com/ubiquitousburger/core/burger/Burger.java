package com.ubiquitousburger.core.burger;

import com.ubiquitousburger.core.burger.exceptions.NotEnoughIngredient;

import java.util.HashMap;
import java.util.Map;

public class Burger {
    private final String name;
    private Map<Ingredient, Integer> ingredients;

    protected Burger(String name, Map<Ingredient, Integer> ingredients) {
        this.name = name;
        this.ingredients = new HashMap<>(ingredients);
    }

    public String getName() {
        return name;
    }

    /**
     * A map of quantities for each ingredient on the burger
     * @return
     */
    public Map<Ingredient, Integer> getIngredients() {
        return new HashMap<>(ingredients);
    }

    public void add(Ingredient ingredient, int quantity) {
        int currentQuantity = ingredients.getOrDefault(ingredient, 0);
        ingredients.put(ingredient, currentQuantity + quantity);
    }

    /**
     *  Remove a quantity of ingredient from burger
     * @param ingredient
     * @throws NotEnoughIngredient if not enought ingredients or burger does not contain ingredient
     */
    public void remove(Ingredient ingredient, int quantity) {
        if (ingredients.containsKey(ingredient)) {
            int currentQuantity = ingredients.get(ingredient);
            if (currentQuantity < quantity) {
                throw new NotEnoughIngredient(String.format("Tried to remove %d %s but burger only had %d",
                        quantity, ingredient.getName(), currentQuantity));
            }

            if (currentQuantity - quantity == 0) {
                ingredients.remove(ingredient);
            } else {
                ingredients.put(ingredient, currentQuantity - quantity);
            }
        } else {
            throw new NotEnoughIngredient(String.format("Burger has no %s", ingredient.getName()));
        }
    }

    @Override
    public String toString() {
        return "Burger{" +
                "name='" + name + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}