package com.ubiquitousburger.core.services.discounts;

import com.ubiquitousburger.core.models.Burger;
import com.ubiquitousburger.core.models.Ingredient;

import java.util.Map;

/**
 * Needs more clarifying
 */
public class Inflation extends Discount {
    public Inflation() {
        super("Inflação", "Os valores dos ingredientes são alterados com frequência e não gastaríamos " +
                "que isso influenciasse nos testes automatizados.");
    }

    @Override
    public boolean accept(Burger burger) {
        return false;
    }

    @Override
    public double price(Map<Ingredient, Integer> ingredients) {
        return 0;
    }
}
