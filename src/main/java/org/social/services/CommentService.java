package org.social.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.social.dto.request.CommentRequest;
import org.social.dto.response.CommentResponse;
import org.social.entities.Comment;
import org.social.entities.Post;
import org.social.entities.User;
import org.social.repositories.CommentRepository;
import org.social.repositories.PostRepository;
import org.social.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<CommentResponse> getAllCommentByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(STR."\{postId} not found"));
        List<Comment> comments = commentRepository.findByPost(post);
        return comments.stream().map(CommentResponse::converteCommnetToCommentResponse).toList();
    }

    public List<CommentResponse> getAllCommentByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(STR."\{username} not found"));
        List<Comment> comments = commentRepository.findByUser(user);
        return comments.stream().map(CommentResponse::converteCommnetToCommentResponse).toList();
    }

    @Transactional
    public CommentResponse createComment(String username, Long postId, CommentRequest commentRequest) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(STR."\{username} not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(STR."\{postId} not found"));
        Comment comment = CommentRequest.convertCommentResponseToComment(commentRequest);
        comment.setUser(user);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        return CommentResponse.converteCommnetToCommentResponse(savedComment);
    }

    @Transactional
    public CommentResponse updateComment(String username, Long postId, Long commentId, CommentRequest commentRequest) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(STR."\{username} not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(STR."\{postId} not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException(STR."\{commentId} not found"));
        if (comment.getUser().equals(user) && comment.getPost().equals(post)) {
            Optional.ofNullable(commentRequest.commentText()).ifPresent(comment::setCommentText);
            Comment savedComment = commentRepository.save(comment);
            return CommentResponse.converteCommnetToCommentResponse(savedComment);
        }
        return null;
    }

    @Transactional
    public boolean deleteComment(String username, Long postId, Long commentId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(STR."\{username} not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(STR."\{postId} not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException(STR."\{commentId} not found"));
        if (comment.getUser().equals(user) && comment.getPost().equals(post)) {
            commentRepository.delete(comment);
            return true;
        }
        return false;
    }
}
