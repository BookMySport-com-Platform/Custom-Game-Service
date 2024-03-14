package com.bookmysport.custom_game_service.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmysport.custom_game_service.Models.CustomGameModel;
import com.bookmysport.custom_game_service.Services.CreateGameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api")
public class MainController {

    @Autowired
    private CreateGameService createGameService;

    @GetMapping("slotcheck")
    public String getMethodName(@RequestBody CustomGameModel game) {
        return createGameService.createGameService(null, null, game.getArenaId().toString(), game.getDateOfBooking(),
                game.getSportId().toString(), game.getStartTime(), game.getStopTime(),game.getCourtNumber());
    }
}
