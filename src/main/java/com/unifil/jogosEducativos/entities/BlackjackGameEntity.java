package com.unifil.jogosEducativos.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "blackjack_games")
public class BlackjackGameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String gameId;

    @Column(nullable = false)
    private String playerName;

    @Column(nullable = false)
    private Integer playerScore;

    @Column(nullable = false)
    private Integer dealerScore;

    @Column(nullable = false)
    private String status;

    public Long getId() {
        return id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
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