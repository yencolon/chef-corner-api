package org.chefcorner.chefcorner.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePostRequest {
    @NotBlank(message = "Title is required")
    private String title;
    private String content;
    private boolean published;
    private boolean draft;
}
