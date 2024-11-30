package org.chefcorner.chefcorner.dto.request;

import lombok.Data;

@Data
public class RecipeStepModelRequest {
    private int order;
    private String title;
    private String content;

}
