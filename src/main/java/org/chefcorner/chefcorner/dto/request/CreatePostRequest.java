package org.chefcorner.chefcorner.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePostRequest {
    @NotBlank(message = "Title is required")
    private String title;
    private String content;
    @NotNull(message = "Category is required")
    private Long categoryId;
    private boolean published;
    private boolean draft;
}
