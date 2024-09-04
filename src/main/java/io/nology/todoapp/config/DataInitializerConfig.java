package io.nology.todoapp.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.nology.todoapp.category.Category;
import io.nology.todoapp.category.CategoryRepository;

@Configuration
public class DataInitializerConfig {

    @Bean
    public CommandLineRunner initializeData(CategoryRepository categoryRepository) {
        return args -> {
            List<String> defaultCategories = Arrays.asList("work", "personal", "urgent", "misc");
            for (String categoryName : defaultCategories) {
                if (categoryRepository.findByName(categoryName).isEmpty()) {
                    Category category = new Category();
                    category.setName(categoryName);
                    categoryRepository.save(category);
                }
            }
        };
    }

}
