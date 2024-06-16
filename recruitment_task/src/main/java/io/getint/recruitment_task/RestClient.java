package io.getint.recruitment_task;

import io.getint.recruitment_task.entity.JiraConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static io.getint.recruitment_task.service.UrlService.jiraUrlCreator;

public class RestClient {

    public static String getResponseFromJira(JiraConnection jiraConnection, String key) {
        String result = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(jiraUrlCreator(jiraConnection.getJiraUrl(), key));
            setUpHttpRequest(jiraConnection, request);
            result = getResponseFromRequest(httpClient.execute(request), result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        verifyResult(result);
        return result;
    }

    public static void deleteResponseFromJira(JiraConnection jiraConnection, String key) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpDelete request = new HttpDelete(jiraUrlCreator(jiraConnection.getJiraUrl(), key));
            setUpHttpRequest(jiraConnection, request);
            httpClient.execute(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static String postToJira(JiraConnection jiraConnection, String key, String json) {
        String result = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(jiraUrlCreator(jiraConnection.getJiraUrl(), key));
            StringEntity stringEntity = new StringEntity(json);
            request.setEntity(stringEntity);
            setUpHttpRequest(jiraConnection, request);
            result = getResponseFromRequest(httpClient.execute(request), result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        verifyResult(result);
        return result;

    }

    private static void verifyResult(String result) {
        if (result.contains("errorMessage")) {
            throw new RuntimeException(result);
        }
    }

    private static String getResponseFromRequest(CloseableHttpResponse httpClient, String result) throws IOException {
        try (CloseableHttpResponse response = httpClient) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        }
        return result;
    }

    private static void setUpHttpRequest(JiraConnection jiraConnection, HttpRequestBase request) {
        request.addHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.addHeader("Authorization", jiraConnection.getAuth());
    }
}
