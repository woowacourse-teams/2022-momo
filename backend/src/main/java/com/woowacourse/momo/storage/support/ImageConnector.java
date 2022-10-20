package com.woowacourse.momo.storage.support;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.momo.storage.exception.GroupImageErrorCode;
import com.woowacourse.momo.storage.exception.GroupImageException;

@Component
public class ImageConnector {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    private final String address;
    private final String requestUrl;
    private final String pathKey;
    private final String fileKey;

    public ImageConnector(@Value("${image.server.address}") String address,
                          @Value("${image.api.request-url}") String requestUrl,
                          @Value("${image.api.path-key}") String pathKey,
                          @Value("${image.api.file-key}") String fileKey) {
        this.address = address;
        this.requestUrl = requestUrl;
        this.pathKey = pathKey;
        this.fileKey = fileKey;
    }

    public String requestImageSave(String path, MultipartFile multipartFile) {
        HttpEntity<MultiValueMap<String, Object>> request = generateRequest(path, multipartFile);
        ResponseEntity<Void> response = saveImage(request);

        URI uri = response.getHeaders().getLocation();
        validateUriIsNotNull(uri);
        return uri.getPath();
    }

    private HttpEntity<MultiValueMap<String, Object>> generateRequest(String path, MultipartFile multipartFile) {
        return new HttpEntity<>(
                generateBody(path, multipartFile),
                generateHeader()
        );
    }

    private MultiValueMap<String, String> generateHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return headers;
    }

    private MultiValueMap<String, Object> generateBody(String path, MultipartFile multipartFile) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add(pathKey, path);
        body.add(fileKey, multipartFile.getResource());
        return body;
    }

    private ResponseEntity<Void> saveImage(HttpEntity<MultiValueMap<String, Object>> request) {
        try {
            return REST_TEMPLATE.postForEntity(address + requestUrl, request, Void.class);
        } catch (HttpClientErrorException e) {
            throw new GroupImageException(GroupImageErrorCode.RESPONSE_IS_4XX);
        } catch (HttpServerErrorException e) {
            throw new GroupImageException(GroupImageErrorCode.RESPONSE_IS_5XX);
        }
    }

    private void validateUriIsNotNull(URI uri) {
        if (uri == null) {
            throw new GroupImageException(GroupImageErrorCode.RESPONSE_IS_NULL);
        }
    }
}
