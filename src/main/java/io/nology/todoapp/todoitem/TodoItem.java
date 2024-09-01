package io.nology.todoapp.todoitem;

import io.nology.todoapp.category.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.nology.todoapp.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity()
@Table(name = "todo_items")
public class TodoItem extends BaseEntity {
    public TodoItem() {

    }

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("posts")
    private Category category;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Category getCategory() {
        return category;
    }

}
