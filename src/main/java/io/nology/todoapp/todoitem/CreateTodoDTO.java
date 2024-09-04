package io.nology.todoapp.todoitem;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateTodoDTO {
    @NotBlank
    @Length(min = 5)
    private String title;

    @NotBlank
    private String content;
    @NotNull
    @Min(1)
    private Long categoryId;

    private boolean completed;

    private boolean deleted;

    public boolean isCompleted() {
        return completed;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getCategoryId() {
        return categoryId;
    }

}
