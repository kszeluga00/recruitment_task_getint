package io.getint.recruitment_task.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DescriptionContent {
    @JsonProperty("content")
    private List<ContentsContent> contentsContent;
    private String type;

    public DescriptionContent() {
    }

    public List<ContentsContent> getContentsContent() {
        return contentsContent;
    }

    public void setContentsContent(List<ContentsContent> contentsContent) {
        this.contentsContent = contentsContent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
