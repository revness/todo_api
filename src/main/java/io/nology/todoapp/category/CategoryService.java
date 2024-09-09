package io.nology.todoapp.category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.nology.todoapp.common.exceptions.ServiceValidationException;
import io.nology.todoapp.common.ValidationErrors;
import jakarta.validation.Valid;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repo;

    public Category create(@Valid CreateCategoryDTO data) throws ServiceValidationException {
        ValidationErrors errors = new ValidationErrors();
        String formattedName = data.getName().trim().toLowerCase();
        if (repo.existsByName(formattedName)) {
            errors.addError("name", String.format("category with name '%s' already exists", formattedName));
        }
        if (errors.hasErrors()) {
            throw new ServiceValidationException(errors);
        }
        Category newCategory = new Category();
        newCategory.setName(formattedName);
        return this.repo.save(newCategory);

    }

    public List<Category> findAll() {
        return this.repo.findAll();

    }

    public Optional<Category> findById(Long categoryId) {
        return this.repo.findById(categoryId);
    }

    public Optional<Category> deleteById(Long id) {
        Optional<Category> category = this.findById(id);
        if (category.isEmpty()) {
            return category;
        }

        this.repo.deleteById(id);

        return category;
    }

    public Category updateCategoryById(Long id, CreateCategoryDTO data) throws Exception {
        Optional<Category> result = this.findById(id);
        if (result.isEmpty()) {
            throw new Exception("Category not found with id " + id);
        }
        Category foundCategory = result.get();
        String formattedName = data.getName().trim().toLowerCase();
        if (repo.existsByName(formattedName)) {
            throw new Exception("Category already exists");
        }
        foundCategory.setName(formattedName);
        return this.repo.save(foundCategory);
    }

}
