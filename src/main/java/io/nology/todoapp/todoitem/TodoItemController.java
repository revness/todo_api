package io.nology.todoapp.todoitem;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nology.todoapp.common.exceptions.NotFoundException;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("todoitems")
public class TodoItemController {

    @Autowired
    private TodoItemService todoItemService;

    @PostMapping
    public ResponseEntity<TodoItem> createTodoItem(@Valid @RequestBody CreateTodoDTO data) throws Exception {
        TodoItem createdTodo = this.todoItemService.createTodoItem(data);
        return new ResponseEntity<TodoItem>(createdTodo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TodoItem>> findAllTodoItems() {
        List<TodoItem> todoItems = this.todoItemService.findAll();
        return new ResponseEntity<List<TodoItem>>(todoItems, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoItem> findTodoItemById(@PathVariable Long id) throws NotFoundException {
        Optional<TodoItem> result = this.todoItemService.findById(id);
        TodoItem foundTodo = result.orElseThrow(() -> new NotFoundException("Could not find todo with id " + id));
        return new ResponseEntity<>(foundTodo, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TodoItem> updateTodoItemById(@PathVariable Long id,
            @Valid @RequestBody UpdateTodoDTO data) throws Exception {
        Optional<TodoItem> result = this.todoItemService.updateTodoItemById(id, data);
        TodoItem foundPost = result.orElseThrow(() -> new NotFoundException("Could not find todo with id " + id));
        return new ResponseEntity<>(foundPost, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoItemById(@PathVariable Long id) throws NotFoundException {
        boolean deleteSuccessful = this.todoItemService.deleteById(id);
        if (deleteSuccessful == false) {
            throw new NotFoundException("Could not find todo item with id " + id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
