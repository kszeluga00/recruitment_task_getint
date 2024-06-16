package io.getint.recruitment_task.service;

import java.util.Base64;

public class UrlService {
    public static final String REST_API = "/rest/api/3/";

    public static String jiraUrlCreator(String jiraUrl, String key) {
        return jiraUrl + REST_API + key;
    }

    public static String jiraAuthorizationKey(String email, String apiKey) {
        String auth = String.format("%s:%s", email, apiKey);
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }
}
