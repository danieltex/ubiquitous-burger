package com.ubiquitousburger.core;

import com.ubiquitousburger.core.pojos.Burger;
import com.ubiquitousburger.core.pojos.Ingredient;
import com.ubiquitousburger.core.repositories.BurgerRepository;
import com.ubiquitousburger.core.repositories.IngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);

    private BurgerRepository burgerRepository;
    private IngredientRepository ingredientRepository;

    public DataLoader(BurgerRepository burgerRepository, IngredientRepository ingredientRepository) {
        this.burgerRepository = burgerRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Pre-populating ingredients repository");

        Ingredient alface = new Ingredient("alface", 0.40);
        Ingredient bacon = new Ingredient("bacon", 2.00);
        Ingredient hamburguer = new Ingredient("hambúrguer de carne", 3.00);
        Ingredient ovo = new Ingredient("ovo", 0.80);
        Ingredient queijo = new Ingredient("queijo", 1.50);

        ingredientRepository.save(alface);
        ingredientRepository.save(bacon);
        ingredientRepository.save(hamburguer);
        ingredientRepository.save(ovo);
        ingredientRepository.save(queijo);

        LOGGER.info("Pre-populating burgers repository");

        Burger xBacon = new Burger("X-Bacon", bacon, hamburguer, queijo);
        Burger xBurger = new Burger("X-Burger", hamburguer, queijo);
        Burger xEgg = new Burger("X-Egg", ovo, hamburguer, queijo);
        Burger xEggBacon = new Burger("X-Egg Bacon", ovo, bacon, hamburguer, queijo);

        burgerRepository.save(xBacon);
        burgerRepository.save(xBurger);
        burgerRepository.save(xEgg);
        burgerRepository.save(xEggBacon);
    }
}
