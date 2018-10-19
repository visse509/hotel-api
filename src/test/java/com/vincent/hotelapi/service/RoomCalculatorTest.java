package com.vincent.hotelapi.service;

import com.vincent.hotelapi.adapters.GuestsSource;
import com.vincent.hotelapi.dto.RoomResponse;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RoomCalculatorTest {

    private static List<Integer> mockGuests;
    RoomCalculator roomCalculator;

    static {
        mockGuests = Arrays.asList(
                23,
                45,
                155,
                374,
                22,
                99,
                100,
                101,
                115,
                209);
    }

    @Before
    public void setup() {
        GuestsSource guestsSourceMock = mock(GuestsSource.class);
        when(guestsSourceMock.getGuests()).thenReturn(mockGuests);
        roomCalculator = new RoomCalculator(guestsSourceMock);
    }

    @Test
    public void test_threePremium_threeEcon() {
        RoomResponse rooms = roomCalculator.getRooms(3, 3);
        Assertions.assertThat(rooms.getPremiumRooms()).isEqualTo(3);
        Assertions.assertThat(rooms.getEconomyRooms()).isEqualTo(3);
        Assertions.assertThat(rooms.getPremiumAmount()).isEqualTo(738);
        Assertions.assertThat(rooms.getEconomyAmount()).isEqualTo(167);
    }

    @Test
    public void test_sevenPremium_fiveEcon() {
        RoomResponse rooms = roomCalculator.getRooms(7, 5);
        Assertions.assertThat(rooms.getPremiumRooms()).isEqualTo(6);
        Assertions.assertThat(rooms.getEconomyRooms()).isEqualTo(4);
        Assertions.assertThat(rooms.getPremiumAmount()).isEqualTo(1054);
        Assertions.assertThat(rooms.getEconomyAmount()).isEqualTo(189);
    }

    @Test
    public void test_twoPremium_sevenEcon() {
        RoomResponse rooms = roomCalculator.getRooms(2, 7);
        Assertions.assertThat(rooms.getPremiumRooms()).isEqualTo(2);
        Assertions.assertThat(rooms.getEconomyRooms()).isEqualTo(4);
        Assertions.assertThat(rooms.getPremiumAmount()).isEqualTo(583);
        Assertions.assertThat(rooms.getEconomyAmount()).isEqualTo(189);
    }

    @Test
    public void test_sevenPremium_oneEcon() {
        RoomResponse rooms = roomCalculator.getRooms(7, 1);
        Assertions.assertThat(rooms.getPremiumRooms()).isEqualTo(7);
        Assertions.assertThat(rooms.getEconomyRooms()).isEqualTo(1);
        Assertions.assertThat(rooms.getPremiumAmount()).isEqualTo(1153);
        Assertions.assertThat(rooms.getEconomyAmount()).isEqualTo(45);
    }
}