package io.nology.todoapp.todoitem;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.nology.todoapp.category.Category;

import io.nology.todoapp.category.CategoryService;
import io.nology.todoapp.common.ValidationErrors;
import io.nology.todoapp.common.exceptions.ServiceValidationException;
import jakarta.validation.Valid;

@Service
public class TodoItemService {
    @Autowired
    private TodoItemRepository repo;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper mapper;

    public TodoItem createTodoItem(@Valid CreateTodoDTO data) throws Exception {
        ValidationErrors errors = new ValidationErrors();
        TodoItem newTodo = mapper.map(data, TodoItem.class);
        Optional<Category> categoryResult = this.categoryService.findById(data.getCategoryId());

        if (categoryResult.isEmpty()) {
            errors.addError("category", String.format("Category with id %s does not exist", data.getCategoryId()));
        } else {
            newTodo.setCategory(categoryResult.get());
        }

        if (errors.hasErrors()) {
            throw new ServiceValidationException(errors);
        }

        return this.repo.save(newTodo);

    }

    public List<TodoItem> findAll() {
        return this.repo.findAll();
    }

    public Optional<TodoItem> findById(Long id) {
        return this.repo.findById(id);
    }

    public Optional<TodoItem> updateTodoItemById(Long id, @Valid UpdateTodoDTO data) throws Exception {
        Optional<TodoItem> result = this.findById(id);
        if (result.isEmpty()) {
            return result;
        }
        TodoItem foundTodo = result.get();

        mapper.map(data, foundTodo);
        ValidationErrors errors = new ValidationErrors();
        if (data.getCategoryId() != null) {
            Optional<Category> categoryResult = this.categoryService.findById(data.getCategoryId());
            if (categoryResult.isEmpty()) {
                errors.addError("category", String.format("Category with id %s does not exist", data.getCategoryId()));
            } else {
                foundTodo.setCategory(categoryResult.get());
            }
        }
        if (errors.hasErrors()) {
            throw new ServiceValidationException(errors);
        }
        TodoItem updatedTodo = this.repo.save(foundTodo);

        return Optional.of(updatedTodo);
    }

    public boolean deleteById(Long id) {
        Optional<TodoItem> result = this.findById(id);
        if (result.isEmpty()) {
            return false;
        }

        this.repo.delete(result.get());

        return true;

    }

}
