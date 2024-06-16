package io.getint.recruitment_task.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.getint.recruitment_task.entity.JiraConnection;
import io.getint.recruitment_task.entity.Project;

import java.util.List;
import java.util.Optional;

import static io.getint.recruitment_task.RestClient.getResponseFromJira;
import static io.getint.recruitment_task.service.ObjectMapperService.createObjectMapper;

public class ProjectService {
    public static Project getProjectObjectByName(JiraConnection jiraConnection, String name) {
        String projectAsJson = getResponseFromJira(jiraConnection, "project/search");
        ObjectMapper objectMapper = createObjectMapper();
        String substring = projectAsJson.substring(projectAsJson.indexOf("values") + 8, projectAsJson.indexOf("}]}") + 2);
        try {
            List<Project> projectList = objectMapper.readValue(substring, new TypeReference<List<Project>>() {});
            Optional<Project> optionalProject = projectList.stream().filter(e -> name.equals(e.getKey())).findFirst();
            return optionalProject.orElse(null);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
