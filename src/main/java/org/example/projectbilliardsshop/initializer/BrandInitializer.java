package org.example.projectbilliardsshop.initializer;

import org.example.projectbilliardsshop.model.Brand;
import org.example.projectbilliardsshop.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BrandInitializer implements CommandLineRunner {
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public void run(String... args) throws Exception {
        if (brandRepository.count() == 0) {
            Brand predator = new Brand("Predator", "USA", 1970, "Cue stick manufacturer", "predator");
            Brand cuetec = new Brand("Cuetec", "USA", 1970, "Cue stick manufacturer", "cuetec");
            Brand fury = new Brand("Fury", "China", 1970, "Cue stick manufacturer", "fury");
            Brand peri = new Brand("Peri", "China", 1970, "Cue stick manufacturer", "peri");
            Brand rhino = new Brand("Rhino", "China", 1970, "Cue stick manufacturer", "rhino");

            brandRepository.saveAll(List.of(predator, cuetec, fury, peri, rhino));
        }
    }
}
