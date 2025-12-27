package com.opencode.alumxbackend.jobposts.controller;

import com.opencode.alumxbackend.jobposts.dto.JobPostRequest;
import com.opencode.alumxbackend.jobposts.model.JobPost;
import com.opencode.alumxbackend.jobposts.service.JobPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/dev/job-posts")
@RequiredArgsConstructor
public class JobPostController {
    private final JobPostService jobPostService;
    private static final String DUMMY_TOKEN = "alumx-dev-token";
    private static final Logger logger = Logger.getLogger(JobPostController.class.getName());

    @PostMapping
    public ResponseEntity<?> createJobPost(
            @RequestHeader(value = "X-DUMMY-TOKEN", required = false) String token,
            @Valid @RequestBody JobPostRequest request
    ){
        // 1. Temporary auth
        if (token == null || !token.equals(DUMMY_TOKEN)) {
            logger.warning("Unauthorized job post attempt. Token: " + token);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Unauthorized: Invalid or missing X-DUMMY-TOKEN header"));
        }
        try{
            logger.info("Processing job post creation for user: " + request.getUsername());
            JobPost savedPost = jobPostService.createJobPost(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "message", "Job post created successfully",
                    "postId", savedPost.getPostId(),
                    "username", savedPost.getUsername(),
                    "createdAt", savedPost.getCreatedAt()
            ));
        }catch (Exception e){
            logger.severe("Error creating job post: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An internal server error occurred"));
        }
    }

}
