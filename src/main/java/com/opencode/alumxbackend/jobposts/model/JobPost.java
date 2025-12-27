package com.opencode.alumxbackend.jobposts.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "job_posts")
public class JobPost {
    @Id
    private String postId;
    @Column(nullable = false)
    private String username;
    @Column(length = 2000)
    private String description;
    @ElementCollection
    @CollectionTable(name = "job_post_images", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
