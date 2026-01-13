package com.quickticket.quickticket.domain.location.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "LOCATION")
@Getter
@Setter
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private Long locationId;

    @Column(columnDefinition = "VARCHAR(5)", length = 5, nullable = false)
    private String zipNumber;

    @Column(columnDefinition = "VARCHAR(20)", length = 20)
    private String sido;

    @Column(columnDefinition = "VARCHAR(20)", length = 20)
    private String sigungu;

    @Column(columnDefinition = "VARCHAR(20)", length = 20)
    private String eupmyun;

    @Column(columnDefinition = "VARCHAR(12)", length = 12)
    private String doroCode;

    @Column(columnDefinition = "VARCHAR(80)", length = 80)
    private String doro;

    @Column(columnDefinition = "VARCHAR(50)", length = 50)
    private String locationName;

    @Column(columnDefinition = "VARCHAR(20)", length = 20)
    private String phone;
}
