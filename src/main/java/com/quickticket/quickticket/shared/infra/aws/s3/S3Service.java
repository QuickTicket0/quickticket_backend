package com.quickticket.quickticket.shared.infra.aws.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

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

    /**
     * 전달받은 ID를 파일명으로 사용하여 S3에 업로드
     * 확장자는 원본 파일에서 추출하여 자동으로 붙여주고있음
     * @param file      업로드할 실제 이미지 파일
     * @param directory S3 내에 저장될 폴더 경로
     * @param id        파일명으로 사용할 고유 ID
     * @return          S3에 저장된 최종 객체 키
     */
    public String uploadId(MultipartFile file, String directory, String id) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // ID 뒤에 확장자를 붙여서 저장
        String key = directory + "/" + id + extension;

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return key; 

        } catch (IOException e) {
            throw new RuntimeException("S3 업로드 실패", e);
        }
    }
}
