package com.ubiquitousburger.core.services.discounts;

import com.ubiquitousburger.core.models.Burger;
import com.ubiquitousburger.core.models.Ingredient;

import java.util.Map;
import java.util.Optional;

public class SoMuchOfDiscount extends Discount {
    private final String ingredientName;

    public SoMuchOfDiscount(String promoName, String ingredientName) {
        super(promoName, String.format("A cada 3 porções de %s o cliente só paga 2. Se o lanche tiver 6 porções," +
                " o cliente pagará 4. Assim por diante...", ingredientName));
        this.ingredientName = ingredientName;
    }

    @Override
    public boolean accept(Burger burger) {
        Optional<Map.Entry<String, Integer>> optionalEntry = burger.getIngredients()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(ingredientName))
                .findAny();
        return optionalEntry.isPresent() &&
            optionalEntry.get().getValue() > 3;
    }

    @Override
    public double price(Map<Ingredient, Integer> ingredients) {
        double priceWithoutIngredient = ingredients.entrySet()
                .stream()
                .filter(entry -> !entry.getKey().getName().equalsIgnoreCase(ingredientName))
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .reduce(0., Double::sum);

        Map.Entry<Ingredient, Integer> ingredientEntry = ingredients.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getName().equalsIgnoreCase(ingredientName))
                .findAny()
                .get();

        Ingredient ingredient = ingredientEntry.getKey();
        int quantity = ingredientEntry.getValue();
        int payingQuantity = quantity % 3 + (quantity / 3) * 2;
        double ingredientPrice = ingredient.getPrice() * payingQuantity;

        return priceWithoutIngredient + ingredientPrice;
    }
}
