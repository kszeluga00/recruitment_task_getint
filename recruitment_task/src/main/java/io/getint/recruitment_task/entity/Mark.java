package io.getint.recruitment_task.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Mark {
    private String type;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Attrs attrs;

    public Mark() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Attrs getAttrs() {
        return attrs;
    }

    public void setAttrs(Attrs attrs) {
        this.attrs = attrs;
    }
}
