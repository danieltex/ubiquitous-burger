package com.ubiquitousburger.core.repositories;

import com.ubiquitousburger.core.models.Burger;

import java.util.List;

public interface BurgerRepository {
    List<Burger> getBurgers();
    void save(Burger burger);
}
