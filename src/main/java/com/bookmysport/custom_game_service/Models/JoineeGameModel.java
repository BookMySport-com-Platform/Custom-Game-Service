package com.bookmysport.custom_game_service.Models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class JoineeGameModel {
    
    @Id
    private UUID gameId;

    private String role;

    private UUID userId;
}
