package com.bookmysport.custom_game_service.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmysport.custom_game_service.Models.CustomGameModel;

public interface CustomGameRepo extends JpaRepository<CustomGameModel,UUID>
{

}
