package com.bookmysport.custom_game_service.Middlewares;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.bookmysport.custom_game_service.Models.ResponseMessage;

import reactor.core.publisher.Mono;

@Service
public class GetSlotState {

    @Autowired
    private WebClient webClient;

    @Autowired
    private ResponseMessage responseMessage;

    public ResponseEntity<ResponseMessage> getSlotStateMW(String arenaId,
            Date dateOfBooking, int startTime, int stopTime, String courtNumber, String sportId) {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("spId", arenaId);
            requestBody.put("sportId", sportId);
            requestBody.put("dateOfBooking", dateOfBooking);
            requestBody.put("startTime", startTime);
            requestBody.put("stopTime", stopTime);
            requestBody.put("courtNumber", courtNumber);

            Mono<Map<String, Object>> slotState = webClient.post()
                    .uri(System.getenv("URL_FOR_CHECK_SLOT"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(requestBody))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                    });

            Map<String, Object> slot = slotState.block();
            if (slot != null && Boolean.TRUE.equals(slot.get("success"))) {
                responseMessage.setSuccess(true);
                responseMessage.setMessage("Slot empty");
                return ResponseEntity.ok().body(responseMessage);

            } else {
                responseMessage.setSuccess(false);
                responseMessage.setMessage("Slot full inside middleware");
                return ResponseEntity.badRequest().body(responseMessage);
            }

        } catch (Exception e) {
            responseMessage.setSuccess(false);
            responseMessage.setMessage("Internal Server Error " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}
