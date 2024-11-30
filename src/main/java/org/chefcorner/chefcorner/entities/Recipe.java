package org.chefcorner.chefcorner.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// TODO: add tags, categories

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    private int time;
    private String image;
    private int likes;
    private int comments;
    private boolean published;
    private boolean draft;
    @CreationTimestamp
    private long createdAt;
    @UpdateTimestamp
    private long updatedAt;
    private long publishedAt;
    private long draftedAt;
    private long deletedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "recipe", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
    private Set<RecipeIngredient> recipeIngredients = new HashSet<>();

    @OneToMany(mappedBy = "recipe", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
    private List<RecipeStep> recipeSteps = new ArrayList<>();

    public void addRecipeIngredient(RecipeIngredient recipeIngredient) {
        recipeIngredient.setRecipe(this);
        this.recipeIngredients.add(recipeIngredient);
    }

    public void addRecipeStep(RecipeStep recipeStep) {
        recipeStep.setRecipe(this);
        this.recipeSteps.add(recipeStep);
    }
}
