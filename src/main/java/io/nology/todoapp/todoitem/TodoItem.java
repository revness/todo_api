package io.nology.todoapp.todoitem;

import io.nology.todoapp.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Table;

@Entity()
@Table(name = "todo_items")
public class TodoItem extends BaseEntity {

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    private String category;

}
