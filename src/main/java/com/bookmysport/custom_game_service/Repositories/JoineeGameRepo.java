package com.bookmysport.custom_game_service.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookmysport.custom_game_service.Models.JoineeGameModel;
import java.util.List;


@Repository
public interface JoineeGameRepo extends JpaRepository<JoineeGameModel,UUID> {
    List<JoineeGameModel> findByGameId(UUID gameId);
    JoineeGameModel findByUserId(UUID userId);
}
