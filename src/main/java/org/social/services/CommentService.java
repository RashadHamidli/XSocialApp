package org.social.services;

import lombok.RequiredArgsConstructor;
import org.social.dto.response.CommentResponse;
import org.social.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<CommentResponse> getAllCommentByPostId(Long postId) {
        return commentRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(STR."\{postId} not found"));
        return null;
    }
}
