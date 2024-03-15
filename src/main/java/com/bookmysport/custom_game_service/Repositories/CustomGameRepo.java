package com.bookmysport.custom_game_service.Repositories;

import java.sql.Date;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookmysport.custom_game_service.Models.CustomGameModel;

import jakarta.transaction.Transactional;

public interface CustomGameRepo extends JpaRepository<CustomGameModel, UUID> {
    @Transactional
    @Query(value = "SELECT * FROM custom_games WHERE arena_id = :arenaId AND sport_id= :sportId AND date_of_booking = :dateOfBooking AND court_number LIKE CONCAT('%', :courtNumber, '%') AND (start_time = :startTime OR stop_time = :stopTime) ", nativeQuery = true)
    CustomGameModel findSlotExists(@Param("arenaId") UUID arenaId, @Param("sportId") UUID sportId,
            @Param("dateOfBooking") Date dateOfBooking, @Param("startTime") int startTime,
            @Param("stopTime") int stopTime, @Param("courtNumber") String courtNumber);
}
