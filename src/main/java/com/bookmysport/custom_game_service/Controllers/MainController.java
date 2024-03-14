package com.bookmysport.custom_game_service.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmysport.custom_game_service.Models.CustomGameModel;
import com.bookmysport.custom_game_service.Models.ResponseMessage;
import com.bookmysport.custom_game_service.Services.CreateGameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("api")
public class MainController {

    @Autowired
    private CreateGameService createGameService;

    @PostMapping("bookcustomgame")
    public ResponseEntity<ResponseMessage> bookCustomGame(@RequestHeader String token, @RequestHeader String role,
            @RequestBody CustomGameModel game) {
        return createGameService.createGameService(token, role, game);
    }
}
