package io.getint.recruitment_task.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Comment {
    @JsonProperty("body")
    private CommentBody commentBody;

    public Comment() {
    }

    public CommentBody getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(CommentBody commentBody) {
        this.commentBody = commentBody;
    }
}
