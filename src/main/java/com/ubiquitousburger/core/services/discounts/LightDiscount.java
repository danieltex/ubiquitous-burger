package com.ubiquitousburger.core.services.discounts;

import com.ubiquitousburger.core.models.Burger;
import com.ubiquitousburger.core.models.Ingredient;

import java.util.Map;

public class LightDiscount extends Discount {

    public LightDiscount() {
        super("Light","Se o lanche tem alface e não tem bacon, ganha 10% de desconto.");
    }

    @Override
    public boolean accept(Burger burger) {
        if (!burger.hasIngredient("alface")) {
            return false;
        }

        return !burger.hasIngredient("bacon");
    }

    @Override
    public double price(Map<Ingredient, Integer> ingredients) {
        double price = ingredients.entrySet()
                .stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .reduce(0., Double::sum);

        return price * 0.9;
    }
}
