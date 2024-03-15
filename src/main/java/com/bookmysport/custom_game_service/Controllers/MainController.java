package com.bookmysport.custom_game_service.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmysport.custom_game_service.Models.CustomGameModel;
import com.bookmysport.custom_game_service.Models.ResponseMessage;
import com.bookmysport.custom_game_service.Repositories.CustomGameRepo;
import com.bookmysport.custom_game_service.Services.CreateGameService;
import com.bookmysport.custom_game_service.Services.FetchGamesService;
import com.bookmysport.custom_game_service.Services.JoinTheGameService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("api")
public class MainController {

    @Autowired
    private CreateGameService createGameService;

    @Autowired
    private JoinTheGameService joinTheGameService;

    @Autowired
    private CustomGameRepo customGameRepo;

    @Autowired
    private FetchGamesService fetchGamesService;

    @PostMapping("bookcustomgame")
    public ResponseEntity<ResponseMessage> bookCustomGame(@RequestHeader String token, @RequestHeader String role,
            @RequestBody CustomGameModel game) {
        return createGameService.createGameService(token, role, game);
    }

    @PostMapping("joingame")
    public ResponseEntity<ResponseMessage> joinTheGame(@RequestHeader String token, @RequestHeader String role,
            @RequestHeader String gameId) {
        return joinTheGameService.joinTheGameService(token, role, gameId);
    }

    // This gives all the custom games irrespective to the arena ID
    @GetMapping("getcustomgames")
    public List<CustomGameModel> getCustomGames() {
        return customGameRepo.findAll();
    }

    // This returns all the games created by the user with role host
    @GetMapping("getgamescreatedbyhost")
    public ResponseEntity<Map<String, Object>> getGamesCreatedByHost(@RequestHeader String token, @RequestHeader String role) {
        return fetchGamesService.fetchAllCustomGamesByHost(token, role);
    }

    //This returns the game by taking the id as input
    @GetMapping("getcustomgamebygameid")
    public CustomGameModel getCustomGameByGameId(String gameId)
    {
        return customGameRepo.findByGameId(UUID.fromString(gameId));
    }

}
