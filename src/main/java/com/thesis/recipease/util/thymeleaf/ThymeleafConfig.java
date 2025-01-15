package com.thesis.recipease.util.thymeleaf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThymeleafConfig {

    @Bean
    public static RecipeUtils recipeUtils() {
        return new RecipeUtils();
    }
}