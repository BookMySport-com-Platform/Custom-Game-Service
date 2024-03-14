package com.bookmysport.custom_game_service.Services;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmysport.custom_game_service.Middlewares.GetSlotState;

@Service
public class CreateGameService {

    @Autowired
    private GetSlotState getSlotState;

    public String createGameService(String token, String role, String areanId, Date dateOfBooking, String sportId,
            int startTime,
            int stopTime, String courtNumber) {

        System.out.println(areanId);
        String msg = getSlotState.getSlotStateMW(areanId, dateOfBooking, startTime, stopTime, courtNumber, sportId)
                .getBody().getMessage();

        return msg;

    }
}
