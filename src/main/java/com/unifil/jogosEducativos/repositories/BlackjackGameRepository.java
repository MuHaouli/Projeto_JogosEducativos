package com.unifil.jogoseducativos.repositories;

import com.unifil.jogosEducativos.entities.BlackjackGameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackjackGameRepository extends JpaRepository<BlackjackGameEntity, Long> {
}
