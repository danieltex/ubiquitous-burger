package com.ubiquitousburger.core.controllers;

import com.ubiquitousburger.core.models.Burger;
import com.ubiquitousburger.core.repositories.BurgerRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BurgerMenuController {

    private BurgerRepository burgerRepository;

    public BurgerMenuController(BurgerRepository burgerRepository) {
        this.burgerRepository = burgerRepository;
    }

    @RequestMapping("/burgers")
    public List<Burger> availableBurgers() {
        return burgerRepository.getBurgers();
    }
}
