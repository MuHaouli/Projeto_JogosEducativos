package com.unifil.jogosEducativos.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "blackjack_games")
public class BlackjackGameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String playerName;
    private Integer playerScore;
    private Integer dealerScore;
    private String status;

    public Long getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(Integer playerScore) {
        this.playerScore = playerScore;
    }

    public Integer getDealerScore() {
        return dealerScore;
    }

    public void setDealerScore(Integer dealerScore) {
        this.dealerScore = dealerScore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
