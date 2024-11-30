package org.chefcorner.chefcorner.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "recipe_steps")
public class RecipeStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @Column(name = "step_order", nullable = false) // Change column name to avoid using a reserved keyword
    private int stepOrder;

    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    @JsonIgnore
    private Recipe recipe;
}
