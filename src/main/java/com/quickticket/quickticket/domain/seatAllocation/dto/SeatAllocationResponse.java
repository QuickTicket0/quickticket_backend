package com.quickticket.quickticket.domain.seatAllocation.dto;

import java.util.List;

public class SeatAllocationResponse {
    public record SeatClass(
        String name,
        Integer price,
        List<Integer> wishingSeatsId
    ) {}
}
