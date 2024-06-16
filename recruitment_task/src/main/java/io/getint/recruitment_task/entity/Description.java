package io.getint.recruitment_task.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Description {
    @JsonProperty("content")
    private List<DescriptionContent> content;
    private String type;
    private int version;
    public Description() {
    }

    public List<DescriptionContent> getContent() {
        return content;
    }

    public void setContent(List<DescriptionContent> content) {
        this.content = content;
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
