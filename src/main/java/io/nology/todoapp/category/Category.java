package io.nology.todoapp.category;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.nology.todoapp.todoitem.TodoItem;
import io.nology.todoapp.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    @JsonIgnoreProperties("category")
    private List<TodoItem> todos;

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TodoItem> getTodoItems() {
        return todos;
    }

    public void setTodoItems(List<TodoItem> todos) {
        this.todos = todos;
    }

    @Override
    public String toString() {
        return "Category [name=" + name + ", todos=" + todos + "]";
    }
}
