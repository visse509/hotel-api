package com.vincent.hotelapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomResponse {
    private int premiumRooms;
    private int premiumAmount;
    private int economyRooms;
    private int economyAmount;
}
