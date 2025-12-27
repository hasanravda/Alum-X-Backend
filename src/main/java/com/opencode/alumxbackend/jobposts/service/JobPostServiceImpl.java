package com.opencode.alumxbackend.jobposts.service;

import com.opencode.alumxbackend.jobposts.dto.JobPostRequest;
import com.opencode.alumxbackend.jobposts.model.JobPost;
import com.opencode.alumxbackend.jobposts.repository.JobPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobPostServiceImpl implements JobPostService{
    private final JobPostRepository jobPostRepository;
    @Override
    public JobPost createJobPost(JobPostRequest request) {
        JobPost jobPost = JobPost.builder()
                .postId(UUID.randomUUID().toString())
                .username(request.getUsername())
                .description(request.getDescription())
                .imageUrls(request.getImageUrls())
                .createdAt(LocalDateTime.now())
                .build();


        return jobPostRepository.save(jobPost);
    }
}
