package com.bookmysport.custom_game_service.Services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookmysport.custom_game_service.Middlewares.GetUserDetailsMW;
import com.bookmysport.custom_game_service.Models.JoineeGameModel;
import com.bookmysport.custom_game_service.Models.ResponseMessage;
import com.bookmysport.custom_game_service.Repositories.CustomGameRepo;
import com.bookmysport.custom_game_service.Repositories.JoineeGameRepo;

@Service
public class JoinTheGameService {

    @Autowired
    private GetUserDetailsMW getUserDetailsMW;

    @Autowired
    private JoineeGameRepo joineeGameRepo;

    @Autowired
    private ResponseMessage responseMessage;

    @Autowired
    private CustomGameRepo customGameRepo;

    public ResponseEntity<ResponseMessage> joinTheGameService(String token, String role, String gameId) {
        try {
            String userId = getUserDetailsMW.getUserDetailsByToken(token, role).getBody().get("id").toString();
            if (userId != null) {
                if (customGameRepo.findByGameId(UUID.fromString(gameId)) != null) {

                    if (joineeGameRepo.findByUserIdAndGameId(UUID.fromString(userId),UUID.fromString(gameId)) == null) {
                        int numberOfPlayersEnteredByHost = customGameRepo.findByGameId(UUID.fromString(gameId))
                                .getNumberOfPlayers();
                        int playersJoinedToGame = joineeGameRepo.findByGameId(UUID.fromString(gameId)).size();

                        if (numberOfPlayersEnteredByHost != playersJoinedToGame) {
                            JoineeGameModel joinGame = new JoineeGameModel();

                            joinGame.setGameId(UUID.fromString(gameId));
                            joinGame.setRole("joinee");
                            joinGame.setUserId(UUID.fromString(userId));

                            joineeGameRepo.save(joinGame);

                            responseMessage.setSuccess(true);
                            responseMessage.setMessage("Joined the game successfully");
                            responseMessage.setDetails(null);
                            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                        } else {
                            responseMessage.setSuccess(false);
                            responseMessage.setMessage("Custom game has been filled");
                            responseMessage.setDetails(null);
                            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                        }
                    } else {
                        responseMessage.setSuccess(false);
                        responseMessage.setMessage("You have already joined this game.");
                        responseMessage.setDetails(null);
                        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                    }

                } else {
                    responseMessage.setSuccess(false);
                    responseMessage.setMessage("Custom game doesn't exists");
                    responseMessage.setDetails(null);
                    return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                }
            } else {
                responseMessage.setSuccess(false);
                responseMessage.setMessage("User dosn't exists");
                responseMessage.setDetails(null);
                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            }

        } catch (Exception e) {
            responseMessage.setSuccess(false);
            responseMessage.setMessage("Internal Server Error in joinTheGameService. Reason: " + e.getMessage());
            responseMessage.setDetails(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }

    }
}
