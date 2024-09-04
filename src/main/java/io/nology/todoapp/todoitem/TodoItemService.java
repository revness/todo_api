package io.nology.todoapp.todoitem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public TodoItem createTodoItem(@Valid CreateTodoDTO data) throws Exception {
        ValidationErrors errors = new ValidationErrors();
        TodoItem newTodo = new TodoItem();
        newTodo.setTitle(data.getTitle().trim());

        Optional<Category> categoryResult = this.categoryService.findById(data.getCategoryId());

        if (categoryResult.isEmpty()) {
            errors.addError("category", String.format("Category with id %s does not exist", data.getCategoryId()));
        }

        if (errors.hasErrors()) {
            throw new ServiceValidationException(errors);
        }
        newTodo.setCategory(categoryResult.get());
        newTodo.setCompleted(false);
        newTodo.setDeleted(false);
        newTodo.setContent(data.getContent());

        return this.repo.save(newTodo);

    }

    public List<TodoItem> findAll() {
        List<TodoItem> allTodoItems = this.repo.findAll();
        return allTodoItems.stream()
                .filter(todoItem -> !todoItem.isDeleted())
                .collect(Collectors.toList());
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

        if (data.getTitle() != null) {
            foundTodo.setTitle(data.getTitle());
        }
        if (data.getContent() != null) {
            foundTodo.setContent(data.getContent());

        }
        if (data.isCompleted() != foundTodo.isCompleted()) {
            foundTodo.setCompleted(data.isCompleted());

        }

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
        TodoItem todoItem = result.get();
        todoItem.setDeleted(true); // Mark as deleted
        this.repo.save(todoItem); // Save the updated item
        return true;

    }

}
