package com.quickticket.quickticket.domain.performance.controller;

import com.quickticket.quickticket.shared.infra.aws.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class PerformanceController {
    private final S3Service s3Service;

    @GetMapping("/api/files/seatMap/{performanceId}")
    public ResponseEntity<Resource> getSeatMap(@PathVariable Long performanceId) {
        return s3Service.getObjectResponseEntity("files/seatMap/" + performanceId + ".svg");
    }
}
