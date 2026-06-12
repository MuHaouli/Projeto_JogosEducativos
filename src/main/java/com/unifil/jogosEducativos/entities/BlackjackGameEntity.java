package com.unifil.jogosEducativos.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private Integer betAmount;

    @Column(nullable = false)
    private Integer balance;

    @ElementCollection
    @CollectionTable(name = "blackjack_player_cards", joinColumns = @JoinColumn(name = "blackjack_game_id"))
    @Column(name = "card", nullable = false)
    private List<String> playerCards = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "blackjack_dealer_cards", joinColumns = @JoinColumn(name = "blackjack_game_id"))
    @Column(name = "card", nullable = false)
    private List<String> dealerCards = new ArrayList<>();

    public Long getId() { return id; }

    public String getGameId() { return gameId; }
    public void setGameId(String gameId) { this.gameId = gameId; }

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public Integer getPlayerScore() { return playerScore; }
    public void setPlayerScore(Integer playerScore) { this.playerScore = playerScore; }

    public Integer getDealerScore() { return dealerScore; }
    public void setDealerScore(Integer dealerScore) { this.dealerScore = dealerScore; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getBetAmount() { return betAmount; }
    public void setBetAmount(Integer betAmount) { this.betAmount = betAmount; }

    public Integer getBalance() { return balance; }
    public void setBalance(Integer balance) { this.balance = balance; }

    public List<String> getPlayerCards() { return playerCards; }
    public void setPlayerCards(List<String> playerCards) { this.playerCards = playerCards; }

    public List<String> getDealerCards() { return dealerCards; }
    public void setDealerCards(List<String> dealerCards) { this.dealerCards = dealerCards; }
}