package com.quickticket.quickticket.domain.event.entity;

import com.quickticket.quickticket.domain.category.entity.Category;
import com.quickticket.quickticket.domain.location.entity.Location;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigInteger;
import java.sql.Blob;
import java.util.Map;

@Entity
@Table(name = "EVENT")
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(nullable = false)
    private Long eventId;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private Location location;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private Category category1;

    @ManyToOne
    @JoinColumn
    private Category category2;

    @NotNull
    @Column(length = 100, nullable = false)
    private String name;

    @NotNull
    @Column(length = 8000, nullable = false)
    private String description;

    @NotNull
    @Column(nullable = false)
    private AgeRating ageRating;

    @NotNull
    @Column(nullable = false)
    private Long userRatingSum;

    @NotNull
    @Column(nullable = false)
    private Blob thumbnailImage;

    @NotNull
    @Column(length = 30, nullable = false)
    private String agentName;

    @NotNull
    @Column(length = 30, nullable = false)
    private String hostName;

    @Column(length = 30)
    private String contactData;

    @JdbcTypeCode(SqlTypes.JSON)
    @NotNull
    @Column(nullable = false)
    private Map<Long, Object> seatData;
}
