package com.ubiquitousburger.core.services;

import com.ubiquitousburger.core.pojos.Burger;
import com.ubiquitousburger.core.pojos.Ingredient;
import com.ubiquitousburger.core.repositories.IngredientRepository;
import com.ubiquitousburger.core.services.discounts.LightDiscount;
import com.ubiquitousburger.core.services.discounts.SoMuchOfDiscount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class PricingServiceTest {

    @Test
    void testGetFullPrice() {
        Ingredient queijo = new Ingredient("queijo", 1.50);
        Ingredient hamburguer = new Ingredient("hambúrguer de carne", 3.00);
        Burger xBurger = new Burger("Burger", queijo, hamburguer);

        IngredientRepository mockIngredientRepository = mock(IngredientRepository.class);
        when(mockIngredientRepository.getByName("queijo")).thenReturn(queijo);
        when(mockIngredientRepository.getByName("hambúrguer de carne")).thenReturn(hamburguer);

        PricingService pricingService = new PricingService(mockIngredientRepository);

        assertEquals(4.5, pricingService.fullPrice(xBurger));
    }

    @Test
    void testLighDiscount() {
        Ingredient queijo = new Ingredient("queijo", 1.50);
        Ingredient alface = new Ingredient("alface", 0.40);
        Burger burger = new Burger("Weird Burger", queijo, alface);

        IngredientRepository mockIngredientRepository = mock(IngredientRepository.class);
        when(mockIngredientRepository.getByName("queijo")).thenReturn(queijo);
        when(mockIngredientRepository.getByName("alface")).thenReturn(alface);

        PricingService pricingService = new PricingService(mockIngredientRepository);

        assertEquals(1.90, pricingService.fullPrice(burger));
        assertEquals(LightDiscount.class, pricingService.eligibleDiscount(burger).getClass());
        assertEquals(1.90 * 0.9, pricingService.discountPrice(burger));
    }

    @Test
    void testSoMuchMeatDiscount() {
        Ingredient queijo = new Ingredient("queijo", 1.50);
        Ingredient carne = new Ingredient("hambúrguer de carne", 3.0);
        Burger burger = new Burger("Super Meat Burger", carne, carne, carne, carne, queijo);

        IngredientRepository mockIngredientRepository = mock(IngredientRepository.class);
        when(mockIngredientRepository.getByName("hambúrguer de carne")).thenReturn(carne);
        when(mockIngredientRepository.getByName("queijo")).thenReturn(queijo);

        PricingService pricingService = new PricingService(mockIngredientRepository);

        // with 1 cheese and 4 meat, should charge for 1 cheese and 4 meat
        assertEquals(3.0 * 4 + 1.5, pricingService.fullPrice(burger));
        assertEquals(SoMuchOfDiscount.class, pricingService.eligibleDiscount(burger).getClass());
        assertEquals(3.0 * 3 + 1.5, pricingService.discountPrice(burger));

        burger.add(carne.getName(), 3);

        // with 1 cheese and 7 meat, should charge for 1 cheese and 7 meat
        assertEquals(3.0 * 7 + 1.5, pricingService.fullPrice(burger));
        assertEquals(3.0 * 5 + 1.5, pricingService.discountPrice(burger));
    }

    @Test
    void testSoMuchCheeseDiscount() {
        Ingredient queijo = new Ingredient("queijo", 1.50);
        Ingredient carne = new Ingredient("hambúrguer de carne", 3.0);
        Burger burger = new Burger("Super Meat Burger", carne, queijo, queijo, queijo, queijo);

        IngredientRepository mockIngredientRepository = mock(IngredientRepository.class);
        when(mockIngredientRepository.getByName("hambúrguer de carne")).thenReturn(carne);
        when(mockIngredientRepository.getByName("queijo")).thenReturn(queijo);

        PricingService pricingService = new PricingService(mockIngredientRepository);

        // with 4 cheese and 1 meat, should charge for 3 cheese and 1 meat
        assertEquals(3.0 + 1.5 * 4, pricingService.fullPrice(burger));
        assertEquals(SoMuchOfDiscount.class, pricingService.eligibleDiscount(burger).getClass());
        assertEquals(3.0 + 1.5 * 3, pricingService.discountPrice(burger));

        burger.add(queijo.getName(), 3);

        // with 7 cheese and 1 meat, should charge for 5 cheese and 1 meat
        assertEquals(3.0 + 1.5 * 7 , pricingService.fullPrice(burger));
        assertEquals(3.0 + 1.5 * 5, pricingService.discountPrice(burger));
    }
}