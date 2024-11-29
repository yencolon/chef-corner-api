package org.chefcorner.chefcorner.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.HashSet;
import java.util.Set;

// TODO: add tags, categories

@Data
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    // @Column(nullable = false)
    //  private String content;
   // private String url;
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

    @OneToMany(mappedBy = "post", cascade = { CascadeType.REMOVE , CascadeType.PERSIST, CascadeType.REFRESH })
    private Set<IngredientRecipe> recipeIngredients = new HashSet<>();

    public void addRecipeIngredient(IngredientRecipe ingredientRecipe) {
        ingredientRecipe.setPost(this);
        this.recipeIngredients.add(ingredientRecipe);
    }
}
