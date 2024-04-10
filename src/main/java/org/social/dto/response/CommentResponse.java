package org.social.dto.response;

import java.time.LocalDateTime;

public record CommentResponse(String username,
                              Long postId,
                              String commentText,
                              LocalDateTime createdDate) {
}
