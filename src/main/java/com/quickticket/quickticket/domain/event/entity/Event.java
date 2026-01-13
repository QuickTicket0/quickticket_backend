package com.quickticket.quickticket.domain.event.entity;

import com.quickticket.quickticket.domain.category.entity.Category;
import com.quickticket.quickticket.domain.location.entity.Location;
import jakarta.persistence.*;
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
    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private Long eventId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Location location;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category1;

    @ManyToOne
    @JoinColumn
    private Category category2;

    @Column(columnDefinition = "VARCHAR(100)", length = 100, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(columnDefinition = "TINYINT UNSIGNED", nullable = false)
    private AgeRating ageRating;

    @Column(columnDefinition = "LONG UNSIGNED", nullable = false)
    private BigInteger userRatingSum;

    @Column(nullable = false)
    private Blob thumbnailImage;

    @Column(columnDefinition = "VARCHAR(30)", length = 30, nullable = false)
    private String agentName;

    @Column(columnDefinition = "VARCHAR(30)", length = 30, nullable = false)
    private String hostName;

    @Column(columnDefinition = "VARCHAR(30)", length = 30)
    private String contactData;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON", nullable = false)
    private Map<Long, Object> seatData;
}
