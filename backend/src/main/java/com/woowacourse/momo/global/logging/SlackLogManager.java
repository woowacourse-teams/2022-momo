package com.woowacourse.momo.global.logging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Primary
public class SlackLogManager implements LogManager {

    private static final String SLACK_MESSAGE_REQUEST_URL = "https://slack.com/api/chat.postMessage";
    private static final RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${slack.token}")
    private String token;

    @Value("${slack.channel-id")
    private String channelId;

    @Override
    public void writeMessage(String message) {
        HttpEntity<String> request = generateHttpEntity(message);
        restTemplate.postForObject(SLACK_MESSAGE_REQUEST_URL, request, String.class);
    }

    @Override
    public void writeException(Exception exception) {
        HttpEntity<String> request = generateHttpEntity(TraceExtractor.getStackTrace(exception));

        restTemplate.postForObject(SLACK_MESSAGE_REQUEST_URL, request, String.class);
    }

    private HttpEntity<String> generateHttpEntity(String message) {
        SlackMessage slackMessage = new SlackMessage(channelId, message);
        HttpHeaders httpHeaders = headerConfigure();
        String body = bodyConfigure(slackMessage);

        return new HttpEntity<>(body, httpHeaders);
    }

    private String bodyConfigure(SlackMessage slackMessage) {
        String jsonData = null;
        try {
            jsonData = objectMapper.writeValueAsString(slackMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json 파싱 에러입니다.");
        }
        return jsonData;
    }

    private HttpHeaders headerConfigure() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return httpHeaders;
    }
}
