package com.woowacourse.momo.support.logging.manager;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.woowacourse.momo.support.logging.manager.dto.SlackMessageRequest;
import com.woowacourse.momo.support.logging.TraceExtractor;
import com.woowacourse.momo.support.logging.manager.dto.SlackThreadRequest;


public class SlackLogManager implements LogManager {

    private static final String SLACK_MESSAGE_REQUEST_URL = "https://slack.com/api/chat.postMessage";
    private static final RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${slack.token}")
    private String token;

    @Value("${slack.channel-id}")
    private String channelId;

    @Value("${momo-log.slack}")
    private boolean used;

    @Override
    public void writeMessage(String message) {
        if (!used) {
            return;
        }

        HttpEntity<String> request = generateSlackMessageHttpEntity(message);
        restTemplate.postForObject(SLACK_MESSAGE_REQUEST_URL, request, String.class);
    }

    @Override
    public void writeException(Exception exception) {
        if (!used) {
            return;
        }

        HttpEntity<String> request = generateSlackMessageHttpEntity("에러가 발생했습니다.");

        String response = restTemplate.postForObject(SLACK_MESSAGE_REQUEST_URL, request, String.class);
        String ts = extractCommentNumber(response);

        HttpEntity<String> threadRequest = generateSlackThreadHttpEntity(ts,
                TraceExtractor.getStackTrace(exception));
        restTemplate.postForObject(SLACK_MESSAGE_REQUEST_URL, threadRequest, String.class);
    }

    private String extractCommentNumber(String response) {
        try {
            HashMap<String, Object> hashMap = objectMapper.readValue(response, HashMap.class);
            return (String) hashMap.get("ts");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpEntity<String> generateSlackMessageHttpEntity(String message) {
        SlackMessageRequest slackMessage = new SlackMessageRequest(channelId, message);
        HttpHeaders httpHeaders = configureHeader();
        String body = configureBody(slackMessage);

        return new HttpEntity<>(body, httpHeaders);
    }

    private HttpEntity<String> generateSlackThreadHttpEntity(String ts, String message) {
        SlackThreadRequest threadRequest = new SlackThreadRequest(channelId, ts, message);
        HttpHeaders httpHeaders = configureHeader();
        String body = configureBody(threadRequest);

        return new HttpEntity<>(body, httpHeaders);
    }

    private String configureBody(Object body) {
        String jsonData = null;
        try {
            jsonData = objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json 파싱 에러입니다.");
        }
        return jsonData;
    }

    private HttpHeaders configureHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return httpHeaders;
    }
}
