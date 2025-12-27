package com.opencode.alumxbackend.jobposts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobPostRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Job description is required")
    private String description;

    @NotEmpty(message = "At least one image URL is required")
    private List<String> imageUrls;
}


