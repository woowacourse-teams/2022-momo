package com.woowacourse.momo.support.logging.manager;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.woowacourse.momo.support.logging.TraceExtractor;
import com.woowacourse.momo.support.logging.exception.LogException;
import com.woowacourse.momo.support.logging.manager.dto.SlackMessageRequest;
import com.woowacourse.momo.support.logging.manager.dto.SlackThreadRequest;

public class SlackLogManager implements LogManager {

    private static final String SLACK_MESSAGE_REQUEST_URL = "https://slack.com/api/chat.postMessage";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String COMMENT_ID = "ts";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${slack.token}")
    private String token;

    @Value("${slack.channel-id}")
    private String channelId;

    @Value("${momo-log.slack}")
    private boolean used;

    @Override
    public void writeMessage(String message) {
        HttpEntity<String> request = generateSlackMessageHttpEntity(message);
        restTemplate.postForObject(SLACK_MESSAGE_REQUEST_URL, request, String.class);
    }

    @Override
    public void writeException(Exception exception) {
        HttpEntity<String> request = generateSlackMessageHttpEntity(exception.getMessage());

        String response = restTemplate.postForObject(SLACK_MESSAGE_REQUEST_URL, request, String.class);
        String ts = extractCommentNumber(response);

        HttpEntity<String> threadRequest = generateSlackThreadHttpEntity(ts,
                TraceExtractor.getStackTrace(exception));
        restTemplate.postForObject(SLACK_MESSAGE_REQUEST_URL, threadRequest, String.class);
    }

    @Override
    public boolean isNotUsed() {
        return !used;
    }

    private String extractCommentNumber(String response) {
        try {
            HashMap<String, Object> hashMap = objectMapper.readValue(response, HashMap.class);
            return (String) hashMap.get(COMMENT_ID);
        } catch (JsonProcessingException e) {
            throw new LogException("json 변환 에러입니다.");
        }
    }

    private HttpEntity<String> generateSlackMessageHttpEntity(String message) {
        SlackMessageRequest slackMessage = new SlackMessageRequest(channelId, message);
        HttpHeaders httpHeaders = configureHeader();
        String body = configureBody(slackMessage);

        return new HttpEntity<>(body, httpHeaders);
    }

    private HttpEntity<String> generateSlackThreadHttpEntity(String commentID, String message) {
        SlackThreadRequest threadRequest = new SlackThreadRequest(channelId, commentID, message);
        HttpHeaders httpHeaders = configureHeader();
        String body = configureBody(threadRequest);

        return new HttpEntity<>(body, httpHeaders);
    }

    private String configureBody(Object body) {
        try {
            return objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new LogException("json 파싱 에러입니다.");
        }
    }

    private HttpHeaders configureHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return httpHeaders;
    }
}
