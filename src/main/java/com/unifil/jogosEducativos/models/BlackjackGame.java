package com.unifil.jogosEducativos.models;

import java.util.List;

public class BlackjackGame {

    private final String gameId;
    private final Player player;
    private final Dealer dealer;
    private final Cards deck;
    private final int betAmount;
    private boolean finished;

    public BlackjackGame(String gameId, String playerName, int betAmount) {
        this.gameId = gameId;
        this.player = new Player(playerName);
        this.dealer = new Dealer();
        this.deck = new Cards();
        this.betAmount = betAmount;
        this.finished = false;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public void startRound() {
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());

        if (player.calculateScore() == 21 || dealer.calculateScore() == 21) {
            finished = true;
        }
    }

    public void hitPlayer() {
        if (finished) {
            throw new IllegalStateException("Rodada encerrada.");
        }

        player.addCard(deck.drawCard());
        if (player.calculateScore() > 21) {
            finished = true;
        }
    }

    public void standPlayer() {
        if (finished) {
            throw new IllegalStateException("Rodada encerrada.");
        }

        while (dealer.calculateScore() < 17) {
            dealer.addCard(deck.drawCard());
        }
        finished = true;
    }

    public String gameResult() {
        int playerScore = player.calculateScore();
        int dealerScore = dealer.calculateScore();

        if (playerScore > 21) {
            return "DEALER_WIN";
        }

        if (dealerScore > 21) {
            return "PLAYER_WIN";
        }

        if (playerScore == dealerScore) {
            return "DRAW";
        }

        return playerScore > dealerScore ? "PLAYER_WIN" : "DEALER_WIN";
    }

    public String getGameId() {
        return gameId;
    }

    public Player getPlayer() {
        return player;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public boolean isFinished() {
        return finished;
    }

    public List<String> getPlayerCards() {
        return player.getHand().stream().map(Card::getLabel).toList();
    }

    public List<String> getDealerCards() {
        return dealer.getHand().stream().map(Card::getLabel).toList();
    }
}