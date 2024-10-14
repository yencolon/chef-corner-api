package org.chefcorner.chefcorner.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String url;
    private String image;
    private int likes;
    private int comments;
    private boolean published;
    private boolean draft;
    private long createdAt;
    private long updatedAt;
    private long publishedAt;
    private long draftedAt;
    private long deletedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
