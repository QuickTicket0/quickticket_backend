package com.quickticket.quickticket.domain.seat.dto;

import java.util.List;

public class SeatAllocationResponse {
    public record SeatClass(
        String name,
        Integer price,
        List<Integer> wishingSeatsId
    ) {}
}
