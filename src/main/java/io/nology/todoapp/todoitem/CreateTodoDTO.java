package io.nology.todoapp.todoitem;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public class CreateTodoDTO {
    @NotBlank
    @Length(min = 5)
    private String title;

    @NotBlank
    private String content;
    @NotBlank
    private String category;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "CreateTodoDTO [title=" + title + ", content=" + content + ", category=" + category + "]";
    }

}
