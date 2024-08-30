package io.nology.todoapp.todoitem;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class TodoItemService {

    public String createTodoItem(@Valid CreateTodoDTO data) {
        System.out.println("from service " + data);
        return "got to service";
    }

}
