package io.getint.recruitment_task;

import io.getint.recruitment_task.entity.Issue;
import io.getint.recruitment_task.entity.JiraConnection;
import io.getint.recruitment_task.entity.Project;

import java.util.List;
import java.util.Objects;

import static io.getint.recruitment_task.RestClient.*;
import static io.getint.recruitment_task.service.ProjectService.getProjectObjectByName;
import static io.getint.recruitment_task.service.TaskService.*;
import static io.getint.recruitment_task.service.UrlService.jiraAuthorizationKey;

public class JiraSynchronizer {


    /**
     * Search for 5 tickets in one project, and move them
     * to the other project within same Jira instance.
     * When moving tickets, please move following fields:
     * - summary (title)
     * - description
     * - priority
     * Bonus points for syncing comments.
     */

    public void moveTasksToOtherProject() throws Exception {

        //These should be parameters, but I guess Im not supposed to change the method parameters
        String sourceProjectName = "";
        String targetProjectName = "";
        String apiKey = "";
        String jiraUrl = "";
        String email = "";
        JiraConnection jiraConnection = new JiraConnection(jiraAuthorizationKey(email, apiKey), jiraUrl);

        Project targetProject = getProjectObjectByName(jiraConnection, targetProjectName);
        if (Objects.isNull(targetProject)) {
            return;
        }
        List<Issue> sourceProjectIssueList = getListOfIssues(jiraConnection, sourceProjectName);
        sourceProjectIssueList = changeSourceProjectToTargetProjectForAllTasks(sourceProjectIssueList, targetProject);
        sourceProjectIssueList.forEach(sourceTask -> {
            Thread createThread = new Thread(() -> {
                Issue targetTask = createTask(sourceTask, jiraConnection);
                copyCommentsFromSourceTaskToTargetTask(sourceTask, targetTask, jiraConnection);
                setTargetTaskStatusFromSourceTask(sourceTask, targetTask, jiraConnection);
                deleteResponseFromJira(jiraConnection, "issue/" + sourceTask.getKey());
            });
            createThread.start();
        });
    }


}
