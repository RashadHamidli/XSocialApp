package org.social.dto.request;

import org.social.entities.Comment;

import java.time.LocalDateTime;

public record CommentRequest(String commentText) {
    public static Comment convertCommentResponseToComment(CommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setCommentText(commentRequest.commentText);
        comment.setCreatedDate(LocalDateTime.now());
        return comment;
    }

}
