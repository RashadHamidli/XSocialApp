package org.social.dto.response;

import org.social.entities.Post;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponse(String username,
                           Long postId,
                           String postText,
                           LocalDateTime createdPost) {
    public static PostResponse convertPostToPostResponse(Post posts) {
        return new PostResponse(posts.getUser().getUsername(),posts.getPostId(), posts.getPostText(), posts.getCreateDate());
    }
}
