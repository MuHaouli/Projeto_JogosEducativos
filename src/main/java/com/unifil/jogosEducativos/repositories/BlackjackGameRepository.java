package com.unifil.jogosEducativos.repositories;

import com.unifil.jogosEducativos.entities.BlackjackGameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlackjackGameRepository extends JpaRepository<BlackjackGameEntity, Long> {
    Optional<BlackjackGameEntity> findByGameId(String gameId);
}

