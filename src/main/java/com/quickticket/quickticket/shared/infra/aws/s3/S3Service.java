package com.quickticket.quickticket.shared.infra.aws.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

@Service
@RequiredArgsConstructor
public class S3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final S3Client s3Client;

    private GetObjectRequest getObjectRequest(String key) {
        return GetObjectRequest.builder()
                .bucket(this.bucketName)
                .key(key)
                .build();
    }

    public ResponseInputStream<GetObjectResponse> getObjectResponseStream(String key) {
        var getObjectRequest = this.getObjectRequest(key);

        var inputStream = s3Client.getObject(getObjectRequest);
        return inputStream;
    }

    public ResponseEntity<Resource> getObjectResponseEntity(String key) {
        ResponseInputStream<GetObjectResponse> responseStream;

        try {
            responseStream = this.getObjectResponseStream(key);
        } catch (NoSuchKeyException e) {
            return ResponseEntity.notFound().build();
        }

        var response = responseStream.response();
        var streamResource = new InputStreamResource(responseStream);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(response.contentType()))
                .contentLength(response.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .body(streamResource);
    }
}
