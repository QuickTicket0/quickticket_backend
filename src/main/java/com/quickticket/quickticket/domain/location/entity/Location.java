package com.quickticket.quickticket.domain.location.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "LOCATION")
@Getter
@Setter
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(nullable = false)
    private Long locationId;

    @NotNull
    @Column(length = 5, nullable = false)
    private String zipNumber;

    @Column(length = 20)
    private String sido;

    @Column(length = 20)
    private String sigungu;

    @Column(length = 20)
    private String eupmyun;

    @Column(length = 12)
    private String doroCode;

    @Column(length = 80)
    private String doro;

    @Column(length = 50)
    private String locationName;

    @Column(length = 20)
    private String phone;
}
