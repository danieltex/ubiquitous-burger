package com.ubiquitousburger.core.controllers;

import com.ubiquitousburger.core.models.Burger;
import com.ubiquitousburger.core.models.Ingredient;
import com.ubiquitousburger.core.repositories.BurgerRepository;
import com.ubiquitousburger.core.repositories.IngredientRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@RestController
public class BurgerMenuController {

    private BurgerRepository burgerRepository;
    private IngredientRepository ingredientRepository;

    public BurgerMenuController(BurgerRepository burgerRepository, IngredientRepository ingredientRepository) {
        this.burgerRepository = burgerRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @RequestMapping("/burgers")
    public List<Burger> availableBurgers() {
        return burgerRepository.getBurgers();
    }

    @RequestMapping("/ingredients")
    public Map<String, Double> availableIngredients() {
        return ingredientRepository.getAvailableIngredients()
                .stream()
                .collect(toMap(Ingredient::getName, Ingredient::getPrice));
    }
}
