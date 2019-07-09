package com.ubiquitousburger.core.burger.repositories;

import com.ubiquitousburger.core.burger.pojos.Burger;

import java.util.List;

public interface BurgerRepository {
    List<Burger> getBurgers();
    void save(Burger burger);
}
