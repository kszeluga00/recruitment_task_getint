package io.getint.recruitment_task.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.getint.recruitment_task.entity.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.getint.recruitment_task.RestClient.getResponseFromJira;
import static io.getint.recruitment_task.RestClient.postToJira;
import static io.getint.recruitment_task.service.ObjectMapperService.createObjectMapper;

public class TaskService {
    public static List<Issue> changeSourceProjectToTargetProjectForAllTasks(List<Issue> issueList, Project targetProject) {
        return issueList.stream().peek(issue -> issue.getFields().setProject(targetProject)).collect(Collectors.toList());
    }

    public static void copyCommentsFromSourceTaskToTargetTask(Issue sourceTask, Issue targetTask, JiraConnection jiraConnection) {
        List<Comment> commentList = getCommentsFromTask(sourceTask, jiraConnection);
        commentList.forEach(comment -> {
            try {
                addCommentsToATask(jiraConnection, comment, targetTask);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public static void addCommentsToATask(JiraConnection jiraConnection, Comment comment, Issue targetTask) throws JsonProcessingException {
        //adds all comments, but author based on email and api owner
        ObjectMapper objectMapper = createObjectMapper();
        String targetTaskKey = String.format("issue/%s/comment", targetTask.getKey());
        postToJira(jiraConnection, targetTaskKey, objectMapper.writeValueAsString(comment));
    }

    public static Issue createTask(Issue sourceTask, JiraConnection jiraConnection) {
        ObjectMapper objectMapper = createObjectMapper();
        try {
            String issue = postToJira(jiraConnection, "issue", objectMapper.writeValueAsString(sourceTask));
            return objectMapper.readValue(issue, Issue.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Comment> getCommentsFromTask(Issue sourceTask, JiraConnection jiraConnection) {
        ObjectMapper objectMapper = createObjectMapper();
        String sourceTaskKey = String.format("issue/%s/comment", sourceTask.getKey());
        String getSourceCommentResponse = getResponseFromJira(jiraConnection, sourceTaskKey);
        String subs = getSourceCommentResponse.substring(getSourceCommentResponse.indexOf("comments") + 10,
                getSourceCommentResponse.length() - 1);
        try {
            return objectMapper.readValue(subs, new TypeReference<List<Comment>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setTargetTaskStatusFromSourceTask(Issue sourceTask, Issue targetTask, JiraConnection jiraConnection) {
        ObjectMapper objectMapper = createObjectMapper();
        String sourceTaskKey = String.format("issue/%s/transitions", targetTask.getKey());
        String getTransitionsResponse = getResponseFromJira(jiraConnection, sourceTaskKey);
        String subs = getTransitionsResponse.substring(getTransitionsResponse.indexOf("transitions\":[") + 13, getTransitionsResponse.length() - 1);
        try {
            List<Transition> transitions = objectMapper.readValue(subs, new TypeReference<List<Transition>>() {});
            Optional<Transition> first = transitions.stream()
                    .filter(transition -> transition.getTo().getStatusCategory().getId() ==
                            sourceTask.getFields().getStatus().getStatusCategory().getId()).findFirst();
            Transition transition = first.orElseGet(null);
            String json = String.format("{\"transition\":%s}", objectMapper.writeValueAsString(transition));
            postToJira(jiraConnection, "issue/" + targetTask.getKey() + "/transitions", json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Issue> getListOfIssues(JiraConnection jiraConnection, String sourceProjectName) {
        String responseFromJira = getResponseFromJira(jiraConnection, getTasksFromProjectPath(sourceProjectName));
        String listOfIssuesAsJson = "";
        try {
            listOfIssuesAsJson = responseFromJira.substring(responseFromJira.indexOf("issues\":") + 8, responseFromJira.indexOf("}}}]}") + 4);
            return createObjectMapper().readValue(listOfIssuesAsJson, new TypeReference<List<Issue>>() {});
        } catch (StringIndexOutOfBoundsException | JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static String getTasksFromProjectPath(String projectName) {
        return "search?jql=project%20%3D%20" + projectName + "&maxResults=5";
    }
}
