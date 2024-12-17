package com.deepInsight.candidateinfo.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SuperSetAuth {

    private final RestTemplate restTemplate = new RestTemplate();
    String superSetUrl = "http://192.168.0.133:8089";
    String dashBoardId = "ca622c12-86cf-43a8-8182-2a12f0f1386b";

    public String loginAsGbfUserAndGetDashboardUrl() throws JsonProcessingException {
        // Step 1: Log in as gbf-user and get access token
        String accessToken = loginAsGbfUser();

        // Step 2: Construct iframe URL
        String dashboardIframeUrl = superSetUrl
                + "/superset/dashboard/" + dashBoardId
                + "/?standalone=1&access_token=" + accessToken;

        return dashboardIframeUrl;
    }

    private String loginAsGbfUser() throws JsonProcessingException {
        String loginUrl = superSetUrl + "/api/v1/security/login";
        String username = "gbf-user";
        String password = "123456";

        // Prepare login payload
        String loginPayload = "{"
                + "\"username\":\"" + username + "\","
                + "\"password\":\"" + password + "\","
                + "\"provider\":\"db\","
                + "\"refresh\":true"
                + "}";

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Make POST request to login
        HttpEntity<String> request = new HttpEntity<>(loginPayload, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(loginUrl, request, String.class);

        // Extract access token
        return getAccessToken(response);
    }

    private String getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.getBody());
        return rootNode.path("access_token").asText();
    }

  /*  private final RestTemplate restTemplate = new RestTemplate();
    String superSetUrl ="http://192.168.0.133:8089";
    String supersetApiUrl = superSetUrl + "/api/v1/security";

    String dashBoardId ="ca622c12-86cf-43a8-8182-2a12f0f1386b";

    public String loginToSuperset() throws JsonProcessingException {
        String loginUrl = supersetApiUrl+"/login";
        String adminuser ="gbf-user";
        String pass ="123456";
        // Prepare the payload
        String loginPayload = "{"
                + "\"username\":\"" + adminuser + "\","
                + "\"password\":\"" + pass + "\","
                + "\"provider\":\"db\","
                + "\"refresh\":true"
                + "}";

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request entity
        HttpEntity<String> request = new HttpEntity<>(loginPayload, headers);

        // Make the POST request
        ResponseEntity<String> response = restTemplate.postForEntity(loginUrl, request, String.class);
        String accessToken = getAccessToken(response);

        HttpHeaders guestheaders = new HttpHeaders();
        guestheaders.setContentType(MediaType.APPLICATION_JSON);
        guestheaders.setBearerAuth(accessToken);

        String requestBody = "{"
                + "\"resources\": ["
                + "  {"
                + "    \"type\": \"dashboard\","
                + "    \"id\": \"" + 12 + "\""
                + "  }"
                + "],"
                + "\"rls\": ["
                + "  {"
                + "    \"clause\": \"org_id = 64\""
                + "  }"
                + "],"
                + "\"user\": {"
                + "  \"username\": \"gbf-user\","
                + "  \"first_name\": \"gbf-user\","
                + "  \"last_name\": \"gbf-user\""
                + "}"
                + "}";


//        String requestBody = "{"
//                + "\"resources\": ["
//                + "  {"
//                + "    \"type\": \"dashboard\","
//                + "    \"id\": \"dashboardId\""
//                + "  }"
//                + "],"
//                + "\"rls\": [],"
//                + "\"user\": {"
//                + "  \"username\": \"report-viewer\","
//                + "  \"first_name\": \"report-viewer\","
//                + "  \"last_name\": \"report-viewer\""
//                + "}"
//                + "}";
        HttpEntity<String> guestHttpEntity = new HttpEntity<>( requestBody,guestheaders);

        ResponseEntity<String> guistAuth = restTemplate.postForEntity(supersetApiUrl+"/guest_token",guestHttpEntity, String.class);
        String guestAccessToken = getAccessToken(response);
//
//        String dashboardId = "ca622c12-86cf-43a8-8182-2a12f0f1386b";  // Dashboard ID
        String iframeUrl = superSetUrl+"/superset/dashboard/"
                + 12 + "/?standalone=2&expand_filters=0&token=" + guestAccessToken;

        return iframeUrl;
    }


    private String getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.getBody());
        String accessToken = rootNode.path("access_token").asText();
        return accessToken;
    }
    */

}
