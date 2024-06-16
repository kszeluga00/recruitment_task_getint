package io.getint.recruitment_task.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CommentBodyContent {
    @JsonProperty("content")
    private List<CommentBodyContentsContent> contentsContent;
    private String type;

    public CommentBodyContent() {
    }

    public List<CommentBodyContentsContent> getContentsContent() {
        return contentsContent;
    }

    public void setContentsContent(List<CommentBodyContentsContent> contentsContent) {
        this.contentsContent = contentsContent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
