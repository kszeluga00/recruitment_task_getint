package io.getint.recruitment_task.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

public class ContentsContent {
    private String text;
    private String type;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private List<Mark> marks;

    public ContentsContent() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }
}
