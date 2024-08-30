package io.nology.todoapp.todoitem;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("todoitems")
public class TodoItemController {

    @Autowired
    private TodoItemService todoItemService;

    @PostMapping
    public String postMethodName(@Valid @RequestBody CreateTodoDTO data) {
        System.out.println(data);
        return "Creates todoItem post";
    }

}
