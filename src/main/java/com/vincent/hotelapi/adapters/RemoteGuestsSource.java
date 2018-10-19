package com.vincent.hotelapi.adapters;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class RemoteGuestsSource implements GuestsSource {

    private JSONParser parser = new JSONParser();

    @Override
    public List<Integer> getGuests() {
        List<Integer> resultList = new ArrayList<>();
        try {
            RestTemplate restTemplate = new RestTemplate();
            String guestUrl = "https://gist.githubusercontent.com/fjahr/b164a446db285e393d8e4b36d17f4143/raw/75108c09a72a001a985d27b968a0ac5a867e830b/smarthost_hotel_guests.json";
            ResponseEntity<String> response = restTemplate.getForEntity(guestUrl, String.class);
            Object object = parser.parse(response.getBody());
            JSONArray jsonArray = (JSONArray) object;
            jsonArray.forEach(guest -> resultList.add(((Long)guest).intValue()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
