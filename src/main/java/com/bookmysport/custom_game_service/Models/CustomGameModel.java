package com.bookmysport.custom_game_service.Models;

import java.sql.Date;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Component
@Table(name = "custom_games")
public class CustomGameModel {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID gameId;

    private String role;

    private UUID userId;

    private UUID arenaId;

    private Date dateOfBooking;

    private UUID sportId;

    private int startTime;

    private int stopTime;

    private int numberOfPlayers;

    private int pricePaid;

    private String courtNumber;
}
