package com.quickticket.quickticket.domain.seatClass.entity;


import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class SeatClassId implements Serializable{
    private Long event;
}
