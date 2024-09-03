package io.nology.todoapp.category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nology.todoapp.common.exceptions.NotFoundException;
import io.nology.todoapp.todoitem.TodoItem;
import io.nology.todoapp.todoitem.UpdateTodoDTO;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CreateCategoryDTO data) throws Exception {
        Category newCategory = this.categoryService.create(data);
        return new ResponseEntity<Category>(newCategory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> allCategories = this.categoryService.findAll();
        return new ResponseEntity<List<Category>>(allCategories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable Long id) throws NotFoundException {
        Optional<Category> result = this.categoryService.findById(id);
        Category foundCategory = result.orElseThrow(() -> new NotFoundException("Could not find todo with id " + id));
        return new ResponseEntity<>(foundCategory, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Category> updateTodoItemById(@PathVariable Long id,
            @Valid @RequestBody CreateCategoryDTO data) throws Exception {
        Optional<Category> result = this.categoryService.updateCategoryById(id, data);
        Category foundCategory = result.orElseThrow(() -> new NotFoundException("Could not find todo with id " + id));
        return new ResponseEntity<>(foundCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable long id) throws NotFoundException {
        boolean deleteSuccessful = this.categoryService.deleteById(id);
        if (deleteSuccessful == false) {
            throw new NotFoundException("Could not find category with id " + id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
