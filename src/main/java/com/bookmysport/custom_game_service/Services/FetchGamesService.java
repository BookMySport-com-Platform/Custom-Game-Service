package com.bookmysport.custom_game_service.Services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookmysport.custom_game_service.Middlewares.GetUserDetailsMW;
import com.bookmysport.custom_game_service.Models.CustomGameModel;
import com.bookmysport.custom_game_service.Models.ResponseMessage;
import com.bookmysport.custom_game_service.Repositories.CustomGameRepo;

@Service
public class FetchGamesService {

    @Autowired
    private GetUserDetailsMW getUserDetailsMW;

    @Autowired
    private CustomGameRepo customGameRepo;

    @Autowired
    private ResponseMessage responseMessage;

    public ResponseEntity<Map<String, Object>> fetchAllCustomGamesByHost(String token, String role) {
        Map<String, Object> response = new HashMap<>();
        try {
            String userId = getUserDetailsMW.getUserDetailsByToken(token, role).getBody().get("id").toString();

            if (userId != null) {
                List<CustomGameModel> games = customGameRepo.findByUserId(UUID.fromString(userId));
                if (games.size() != 0) {
                    response.put("success", true);
                    response.put("message", games);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    response.put("success", false);
                    response.put("message", "No games exists");
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }

            } else {
                response.put("success", false);
                response.put("message", "User doesn't exists");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Internal server error in fetchAllCustomGamesByHost. Reason: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

    }

    public ResponseEntity<ResponseMessage> checkSlot(CustomGameModel slotToBeChecked) {
        try {
            CustomGameModel gameExistence = customGameRepo.findSlotExists(slotToBeChecked.getArenaId(),
                    slotToBeChecked.getSportId(),
                    slotToBeChecked.getDateOfBooking(), slotToBeChecked.getStartTime(), slotToBeChecked.getStopTime(),
                    slotToBeChecked.getCourtNumber());

            if (gameExistence == null) {
                responseMessage.setSuccess(true);
                responseMessage.setMessage("Slot is empty");
                responseMessage.setDetails(null);
                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            } else {
                responseMessage.setSuccess(false);
                responseMessage.setMessage("Slot is full");
                responseMessage.setDetails(null);
                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            }
        } catch (Exception e) {
            responseMessage.setSuccess(false);
            responseMessage.setMessage("Internal Server Error in checkSlot. Reason: " + e.getMessage());
            responseMessage.setDetails(null);
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        }
    }
}
