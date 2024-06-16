package io.getint.recruitment_task.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CommentBody {

    @JsonProperty("content")
    private List<CommentBodyContent> commentBodyContent;
    private String type;
    private int version;

    public CommentBody() {
    }

    public List<CommentBodyContent> getCommentBodyContent() {
        return commentBodyContent;
    }

    public void setCommentBodyContent(List<CommentBodyContent> commentBodyContent) {
        this.commentBodyContent = commentBodyContent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
