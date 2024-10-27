package org.chefcorner.chefcorner.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    private String url;
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
}
