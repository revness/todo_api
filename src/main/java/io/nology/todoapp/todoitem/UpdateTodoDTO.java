package io.nology.todoapp.todoitem;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public class UpdateTodoDTO {

    @Pattern(regexp = ".*\\S.*", message = "Title cannot be empty")
    @Length(min = 5)
    private String title;
    @Pattern(regexp = ".*\\S.*", message = "Content cannot be empty")
    private String content;

    @Min(1)
    private Long categoryId;

    private boolean completed;
    private boolean deleted;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isDeleted() {
        return deleted;
    }

}
