package com.bookmysport.custom_game_service.Middlewares;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class GetPriceOfSport {

    @Autowired
    private WebClient webClient;

    public ResponseEntity<Map<String, Object>> getPriceOfSportMW(String sportId, String arenaId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Mono<Map<String, Object>> priceOfSport = webClient.get()
                    .uri(System.getenv("GETSPORTBYSPORTIDANDSPID_URL"))
                    .headers(headers -> {
                        headers.set("Content-Type", "application/json");
                        headers.set("spId", arenaId);
                        headers.set("sportId", sportId);
                    })
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                    });
            Map<String, Object> priceDetails = priceOfSport.block();
            
            if (priceDetails != null) {
                response.put("success", true);
                response.put("message", (Integer) priceDetails.get("pricePerHour"));
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response.put("success", false);
                response.put("message", "No sport exists");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Internal Server Error in getPriceOfSportMW. Reason: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
