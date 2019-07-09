package com.ubiquitousburger.core.burger.repositories;

import com.ubiquitousburger.core.burger.pojos.Burger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class BurgerRepositoryInMemory implements BurgerRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(BurgerRepositoryInMemory.class);
    private Set<Burger> burgers = new HashSet<>();

    @Override
    public List<Burger> getBurgers() {
        return new ArrayList<>(burgers);
    }

    @Override
    public void save(Burger burger) {
        if (!burgers.add(burger)) {
            burgers.remove(burger);
            burgers.add(burger);
            LOGGER.info("Updating burger '{}'", burger.getName());
        } else {
            LOGGER.info("Saved burger '{}'", burger.getName());
        }
    }
}
