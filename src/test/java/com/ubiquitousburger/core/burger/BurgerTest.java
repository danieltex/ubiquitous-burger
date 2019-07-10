package com.ubiquitousburger.core.burger;

import com.ubiquitousburger.core.exceptions.NotEnoughIngredientException;
import com.ubiquitousburger.core.pojos.Burger;
import com.ubiquitousburger.core.pojos.Ingredient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BurgerTest {
    private Ingredient queijo;
    private Ingredient hamburguer;
    private Burger xBurger;

    @BeforeEach
    void setUpTest() {
        // given burger
        queijo = new Ingredient("Queijo", 1.50);
        hamburguer = new Ingredient("Hambúrguer de carne", 3.00);
        xBurger = new Burger("X-Burger", queijo, hamburguer);
    }

    @Test
    void testIngredientAddAndRemoval() {
        // when we add
        xBurger.add(queijo.getName(), 1);

        // then we count
        Map<String, Integer> currentIngredients = xBurger.getIngredients();
        assertEquals(2, currentIngredients.get(queijo.getName()).intValue());
        assertEquals(1, currentIngredients.get(hamburguer.getName()).intValue());
    }

    @Test
    void testRemovalExceeding() {
        // when we remove an exceeding quantity we got an exception
        NotEnoughIngredientException notEnoughIngredient = assertThrows(NotEnoughIngredientException.class,
                () -> xBurger.remove(hamburguer.getName(), 2));

        // than we assert the exception message
        assertEquals(String.format("Tried to remove %d %s but burger only had %d",
                2, hamburguer.getName(), 1), notEnoughIngredient.getMessage());
    }

    @Test
    void testRemovalNonExistent() {
        // given a new ingredient
        Ingredient unknown = new Ingredient("Unknown", 10.0);

        // when we remove a non existent ingredient we get an exception
        NotEnoughIngredientException notEnoughIngredient = assertThrows(NotEnoughIngredientException.class,
                () -> xBurger.remove(unknown.getName(), 2));

        // than we assert message
        assertEquals(String.format("Burger has no %s", unknown.getName()), notEnoughIngredient.getMessage());
    }
}