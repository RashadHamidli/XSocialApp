package org.social.controllers;

import lombok.RequiredArgsConstructor;
import org.social.dto.request.CommentRequest;
import org.social.dto.response.CommentResponse;
import org.social.entities.Comment;
import org.social.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;

    @GetMapping("/{postId}/comment")
    public List<CommentResponse> getAllCommentByPostId(@PathVariable Long postId) {
        return commentService.getAllCommentByPostId(postId);
    }

    @GetMapping("/{username}/comments")
    public List<CommentResponse> getAllCommentByUsername(@PathVariable String username) {
        return commentService.getAllCommentByUsername(username);
    }

    @PostMapping("/{username}/{postId}/comment")
    public CommentResponse createComment(@PathVariable String username, @PathVariable Long postId, @RequestBody CommentRequest commentRequest) {
        return commentService.createComment(username, postId, commentRequest);
    }

    @PutMapping("/{username}/{postId}/{commentId}")
    public CommentResponse updateComment(@PathVariable String username, @PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentRequest commentRequest) {
        return commentService.updateComment(username, postId, commentId, commentRequest);
    }

    @DeleteMapping("/{username}/{postId}/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable String username, @PathVariable Long postId, @PathVariable Long commentId) {
        if (commentService.deleteComment(username, postId, commentId)) {
            return ResponseEntity.ok("Comment deleted");
        } else {
            return ResponseEntity.ok("Comment not deleted");
        }
    }
}
