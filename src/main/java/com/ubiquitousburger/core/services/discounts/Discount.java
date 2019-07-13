package com.ubiquitousburger.core.services.discounts;

import com.ubiquitousburger.core.pojos.Burger;
import com.ubiquitousburger.core.pojos.Ingredient;

import java.util.Map;


public abstract class Discount {
    private final String name;
    private final String description;

    public Discount(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract boolean accept(Burger burger);

    public abstract double price(Map<Ingredient, Integer> ingredients);
}
