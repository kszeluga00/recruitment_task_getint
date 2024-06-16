package io.getint.recruitment_task.entity;

public class JiraConnection {
    private final String auth;
    private final String jiraUrl;

    public JiraConnection(String auth, String jiraUrl) {
        this.auth = auth;
        this.jiraUrl = jiraUrl;
    }

    public String getAuth() {
        return auth;
    }

    public String getJiraUrl() {
        return jiraUrl;
    }
}

