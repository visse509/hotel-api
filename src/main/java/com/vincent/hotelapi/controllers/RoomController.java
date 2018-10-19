package com.vincent.hotelapi.controllers;

import com.vincent.hotelapi.dto.RoomResponse;
import com.vincent.hotelapi.service.RoomCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoomController {

    @Autowired
    private RoomCalculator roomCalculator;

    @RequestMapping(value = "/rooms", method= RequestMethod.GET)
    @ResponseBody
    RoomResponse rooms(@RequestParam(value = "premiumRooms") Integer premiumRooms, @RequestParam(value = "economyRooms") Integer economyRooms) {
        return roomCalculator.getRooms(premiumRooms, economyRooms);
    }
}
