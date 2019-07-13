package com.ubiquitousburger.core.services;

import com.ubiquitousburger.core.exceptions.NoDiscountAvailableException;
import com.ubiquitousburger.core.pojos.Burger;
import com.ubiquitousburger.core.pojos.Ingredient;
import com.ubiquitousburger.core.repositories.IngredientRepository;
import com.ubiquitousburger.core.services.discounts.Discount;
import com.ubiquitousburger.core.services.discounts.LightDiscount;
import com.ubiquitousburger.core.services.discounts.SoMuchOfDiscount;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PricingService {

    private List<Discount> discountList = Arrays.asList(
            new LightDiscount(),
            new SoMuchOfDiscount("Muita carne","hambúrguer de carne"),
            new SoMuchOfDiscount("Muito queijo","queijo")
    );

    private IngredientRepository ingredientRepository;

    public PricingService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public double fullPrice(Burger burger) {
        return burger.getIngredients()
                    .entrySet()
                    .stream()
                    .map(entry -> {
                        String ingredientName = entry.getKey();
                        Ingredient ingredient = ingredientRepository.getByName(ingredientName);
                        return ingredient.getPrice() * entry.getValue();
                    })
                    .reduce(0., Double::sum);
    }

    /**
     * Calculate the price for an eligible to discount burger
     * @param burger
     * @return the discount price
     * @throws NoDiscountAvailableException
     */
    public double discountPrice(Burger burger) {
        Discount discount = eligibleDiscount(burger);
        if (discount != null) {
            Map<Ingredient, Integer> ingredientsMap = burger.getIngredients()
                    .entrySet()
                    .stream()
                    .collect(
                        Collectors.toMap(entry -> ingredientRepository.getByName(entry.getKey()), Map.Entry::getValue));

            return discount.price(ingredientsMap);
        }

        throw new NoDiscountAvailableException(String.format("Burger %s not eligible to discount", burger));
    }

    public boolean isEligibleToDiscount(Burger burger) {
        return eligibleDiscount(burger) != null;
    }

    public Discount eligibleDiscount(Burger burger) {
        for (Discount discount : discountList) {
            if (discount.accept(burger)) {
                return discount;
            }
        }
        return null;
    }
}
