package com.bookmysport.custom_game_service.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookmysport.custom_game_service.Models.JoineeGameModel;

import jakarta.transaction.Transactional;

import java.util.List;


@Repository
public interface JoineeGameRepo extends JpaRepository<JoineeGameModel,UUID> {
    List<JoineeGameModel> findByGameId(UUID gameId);
    JoineeGameModel findByUserId(UUID userId);


    @Transactional
    @Query(value="SELECT * FROM joinee_game_model where user_id=:userId AND game_id=:gameId",nativeQuery = true)
    JoineeGameModel findByUserIdAndGameId(@Param("userId") UUID userId,@Param("gameId") UUID gameId);
}
