package com.quickticket.quickticket.domain.event.dto;

import com.quickticket.quickticket.domain.event.domain.AgeRating;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.sql.Blob;

public class EventRequest {
    @Builder
    public record Create(
        @NotBlank
        @Size(max = 50)
        String name,

        @Size(max = 8000)
        String description,

        @NotNull
        @Min(0)
        Long category1Id,

        @Min(0)
        Long category2Id,

        @NotNull
        AgeRating ageRating,

        Blob thumbnailImage,

        @Size(max = 30)
        String agentName,

        @Size(max = 30)
        String hostName,

        @Size(max = 50)
        String contactData,

        @NotNull
        @Min(0)
        Long locationId
    ) {}

    @Builder
    public record Edit(
        @NotNull
        @Min(0)
        Long id,

        @Size(max = 50)
        String name,

        @Size(max = 8000)
        String description,

        @Min(0)
        Long category1Id,

        @Min(0)
        Long category2Id,

        AgeRating ageRating,

        Blob thumbnailImage,

        @Size(max = 30)
        String agentName,

        @Size(max = 30)
        String hostName,

        @Size(max = 60)
        String contactData,

        @Min(0)
        Long locationId
    ) {}

    @Builder
    public record Delete(
        @NotNull
        @Min(0)
        Long id
    ) {}
}