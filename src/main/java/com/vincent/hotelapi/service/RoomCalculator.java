package com.vincent.hotelapi.service;

import com.vincent.hotelapi.adapters.GuestsSource;
import com.vincent.hotelapi.dto.RoomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.*;

@Component
public class RoomCalculator {

    @Autowired
    private
    GuestsSource guestsSource;

    RoomCalculator(GuestsSource guestsSource) {
        this.guestsSource = guestsSource;
    }

    public RoomResponse getRooms(int availablePremiumRooms, int availableEconomyRooms) {
        return calculateAvailableRooms(availablePremiumRooms, availableEconomyRooms);
    }

    private RoomResponse calculateAvailableRooms(int availablePremiumRooms, int availableEconomyRooms) {
        List<Integer> guests = guestsSource.getGuests();

        //Get list of premium guests(>=100), ordered by amount, DESC.
        List<Integer> premiumGuests = guests.stream()
                .filter(guest -> guest >= 100)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        //Get list of economy guests (<100), ordered by amount, DESC.
        List<Integer> economyGuests = guests.stream()
                .filter(guest -> guest < 100)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        //Get List of guests that should be upgraded to premium
        List<Integer> eligibleForPremium = getEligibleForPremium(availableEconomyRooms, economyGuests, availablePremiumRooms - premiumGuests.size());
        premiumGuests.addAll(eligibleForPremium);

        //Get the number of premium rooms booked, and amount for the premium rooms
        int filledPremiumRooms = min(premiumGuests.size(), availablePremiumRooms);
        int premiumAmount = premiumGuests.subList(0, filledPremiumRooms)
                .stream()
                .reduce(0, Integer::sum);

        //Get the number of economy rooms booked, and amount for the economy rooms
        int filledEconomyRooms = min(economyGuests.size(), availableEconomyRooms);
        int economyAmount = economyGuests.subList(eligibleForPremium.size(), eligibleForPremium.size() + filledEconomyRooms)
                .stream()
                .reduce(0, Integer::sum);

        //Return the response
        return RoomResponse.builder()
                .premiumRooms(filledPremiumRooms)
                .premiumAmount(premiumAmount)
                .economyRooms(filledEconomyRooms)
                .economyAmount(economyAmount)
                .build();
    }

    //Gets List of economy guests that are eligible to be upgraded to a premium room.
    private List<Integer> getEligibleForPremium(int economyRooms, List<Integer> economyGuests, int surplusPremiumRooms) {
        if (economyRooms < economyGuests.size() && surplusPremiumRooms > 0) {
            return economyGuests.subList(0, min(surplusPremiumRooms,economyGuests.size() - economyRooms));
        } else {
            return Collections.emptyList();
        }
    }
}
