package io.getint.recruitment_task.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Transition {
    private String id;
    private String name;
    private To to;

    public Transition() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public String getName() {
        return name;
    }

    @JsonProperty
    public void setName(String name) {
        this.name = name;
    }
    @JsonIgnore
    public To getTo() {
        return to;
    }

    @JsonProperty
    public void setTo(To to) {
        this.to = to;
    }
}
