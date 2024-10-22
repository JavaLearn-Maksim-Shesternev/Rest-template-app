package com.template.resttemplate.service;

import com.template.resttemplate.model.User;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private final String BASE_URL = "http://94.198.50.185:7081/api/users";
    private final RestTemplate restTemplate = new RestTemplate();
    private String sessionId;

    public List<User> getAllUsers() {
        ResponseEntity<User[]> response = restTemplate.getForEntity(BASE_URL, User[].class);
        sessionId = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        return Arrays.asList(response.getBody());
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionId);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public String createUser(User user) {
        HttpEntity<User> request = new HttpEntity<>(user, createHeaders());
        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL, request, String.class);
        return response.getBody();
    }

    public String updateUser(User user) {
        HttpEntity<User> request = new HttpEntity<>(user, createHeaders());
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT, request, String.class);
        return response.getBody();
    }

    public String deleteUser(Long userId) {
        HttpEntity<String> request = new HttpEntity<>(createHeaders());
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/"
                + userId, HttpMethod.DELETE, request, String.class);
        return response.getBody();
    }
}