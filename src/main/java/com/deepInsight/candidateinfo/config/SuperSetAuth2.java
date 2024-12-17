package com.deepInsight.candidateinfo.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SuperSetAuth2 {
    private final RestTemplate restTemplate = new RestTemplate();
    String superSetUrl = "http://192.168.0.133:8089";
    String supersetApiUrl = superSetUrl + "/api/v1/security";
    String dashBoardId = "7246192d-81ee-453b-ba48-3b0f59b908f2";
    private String guestToken;

    public String loginToSuperset() throws JsonProcessingException {
        String loginUrl = supersetApiUrl + "/login";
        String csrfUrl = supersetApiUrl + "/csrf_token/";
        String guestTokenUrl = supersetApiUrl + "/guest_token/";

        String adminUser = "ppl-pcl";
        String pass = "123456";


        // Step 1: Authenticate and get the access token
        String loginPayload = "{"
                + "\"username\":\"" + adminUser + "\","
                + "\"password\":\"" + pass + "\","
                + "\"provider\":\"db\","
                + "\"refresh\":true"
                + "}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> loginRequest = new HttpEntity<>(loginPayload, headers);

        ResponseEntity<String> loginResponse = restTemplate.postForEntity(loginUrl, loginRequest, String.class);
        String accessToken = getAccessToken(loginResponse,0);

        // Step 2: Fetch the CSRF token
        HttpHeaders csrfHeaders = new HttpHeaders();
        csrfHeaders.setBearerAuth(accessToken);
        HttpEntity<String> csrfRequest = new HttpEntity<>(null, csrfHeaders);

        ResponseEntity<String> csrfResponse = restTemplate.exchange(csrfUrl, HttpMethod.GET, csrfRequest, String.class);
        String csrfToken = getCsrfToken(csrfResponse);

        // Step 3: Get the Guest Token
        HttpHeaders guestHeaders = new HttpHeaders();
        guestHeaders.setContentType(MediaType.APPLICATION_JSON);
        guestHeaders.setBearerAuth(accessToken);
        guestHeaders.set("X-CSRF-TOKEN", csrfToken);  // Include the CSRF Token

        String requestBody = "{"
                + "\"resources\": ["
                + "  {\"type\": \"dashboard\", \"id\": \"" + dashBoardId + "\"}"
                + "],"
                +   "\"rls\": [],"
                + "\"user\": {"
                + "  \"username\": \"report-viewer\","
                + "  \"first_name\": \"report-viewer\","
                + "  \"last_name\": \"report-viewer\""
                + "}"
                + "}";

            HttpEntity<String> guestRequest = new HttpEntity<>(requestBody, guestHeaders);
        ResponseEntity<String> guestResponse = restTemplate.postForEntity(guestTokenUrl, guestRequest, String.class);

        guestToken = getAccessToken(guestResponse,1);

        // Step 4: Build the iframe URL with the guest token
        // String iframeUrl = superSetUrl + "/superset/dashboard/"
        //         + dashBoardId + "/?standalone=1&token=" + guestToken;

        // return iframeUrl;
return guestToken;

    }

    // Utility method to extract access token
    private String getAccessToken(ResponseEntity<String> response,int ind) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.getBody());
        String text;
        if(ind==0)
        {
            text= rootNode.path("access_token").asText();

        }else
        {
            text= rootNode.path("token").asText();
        }
        return text;//rootNode.path("access_token").asText();
    }

    // Utility method to extract CSRF token
    private String getCsrfToken(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.getBody());
        String text = rootNode.path("result").asText();
        return text;//rootNode.path("result").path("csrf_token").asText();
    }


}
