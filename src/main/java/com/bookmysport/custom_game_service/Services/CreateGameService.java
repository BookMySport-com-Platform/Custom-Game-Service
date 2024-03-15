package com.bookmysport.custom_game_service.Services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookmysport.custom_game_service.Middlewares.GetPriceOfSport;
import com.bookmysport.custom_game_service.Middlewares.GetSlotState;
import com.bookmysport.custom_game_service.Middlewares.GetUserDetailsMW;
import com.bookmysport.custom_game_service.Models.CustomGameModel;
import com.bookmysport.custom_game_service.Models.ResponseMessage;
import com.bookmysport.custom_game_service.Repositories.CustomGameRepo;

@Service
public class CreateGameService {

    @Autowired
    private GetSlotState getSlotState;

    @Autowired
    private GetUserDetailsMW getSUserDetailsMW;

    @Autowired
    private GetPriceOfSport getPriceOfSport;

    @Autowired
    private CustomGameRepo customGameRepo;

    @Autowired
    private ResponseMessage responseMessage;

    public ResponseEntity<ResponseMessage> createGameService(String token, String role, CustomGameModel game) {
        try {
            boolean slotCondition = getSlotState
                    .getSlotStateMW(game.getArenaId().toString(), game.getDateOfBooking(), game.getStartTime(),
                            game.getStopTime(), game.getCourtNumber(), game.getSportId().toString())
                    .getBody().getSuccess();

            CustomGameModel gameExistence = customGameRepo.findSlotExists(game.getArenaId(), game.getSportId(),
                    game.getDateOfBooking(), game.getStartTime(), game.getStopTime(), game.getCourtNumber());

            if (slotCondition && gameExistence == null) {
                String userId = getSUserDetailsMW.getUserDetailsByToken(token, role).getBody().get("id").toString();
                game.setRole("host");
                game.setUserId(UUID.fromString(userId));

                int priceOfSportPerHour = (int) getPriceOfSport
                        .getPriceOfSportMW(game.getSportId().toString(), game.getArenaId().toString()).getBody()
                        .get("message");

                int totalPrice = priceOfSportPerHour * (game.getStopTime() - game.getStartTime())
                        * game.getCourtNumber().split(",").length;

                game.setPricePaid(totalPrice);

                customGameRepo.save(game);

                responseMessage.setSuccess(true);
                responseMessage.setMessage("Custom game booked");
                responseMessage.setUserDetails(null);
                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);

            } else {
                responseMessage.setSuccess(false);
                responseMessage.setMessage("Slot full");
                responseMessage.setUserDetails(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
            }
        } catch (Exception e) {
            responseMessage.setSuccess(false);
            responseMessage.setMessage("Internal Server Error in createGameService. Reason: " + e.getMessage());
            responseMessage.setUserDetails(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }

    }
}
