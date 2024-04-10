package org.social.controllers;

import lombok.RequiredArgsConstructor;
import org.social.dto.response.CommentResponse;
import org.social.services.CommentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;

    @GetMapping("/{postId}/comment")
    public List<CommentResponse> getAllCommentByPostId(@RequestParam Long postId) {
        return commentService.getAllCommentByPostId(postId);
    }
}
