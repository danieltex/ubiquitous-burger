package com.ubiquitousburger.core.services;

import com.ubiquitousburger.core.pojos.Burger;
import com.ubiquitousburger.core.pojos.Ingredient;

import java.util.Map;
import java.util.Optional;

public class SoMuchMeatDiscount extends Discount {
    public SoMuchMeatDiscount() {
        super("Muita carne", "A cada 3 porções de carne o cliente só paga 2. Se o lanche tiver " +
                "6 porções, o cliente pagará 4. Assim por diante...");
    }

    @Override
    public boolean accept(Burger burger) {
        Optional<Map.Entry<String, Integer>> optionalEntry = burger.getIngredients()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase("hambúrguer de carne"))
                .findAny();
        return optionalEntry.isPresent() &&
            optionalEntry.get().getValue() > 3;
    }

    @Override
    public double price(Map<Ingredient, Integer> ingredients) {
        double priceWithoutMeat = ingredients.entrySet()
                .stream()
                .filter(entry -> !entry.getKey().getName().equalsIgnoreCase("hambúrguer de carne"))
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .reduce(0., Double::sum);

        Map.Entry<Ingredient, Integer> entryMeat = ingredients.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getName().equalsIgnoreCase("hambúrguer de carne"))
                .findAny()
                .get();

        Ingredient meat = entryMeat.getKey();
        int meatQuantity = entryMeat.getValue();
        int payingQuantity = meatQuantity % 3 + (meatQuantity / 3) * 2;
        double meatPrice = meat.getPrice() * payingQuantity;

        return priceWithoutMeat + meatPrice;
    }
}
