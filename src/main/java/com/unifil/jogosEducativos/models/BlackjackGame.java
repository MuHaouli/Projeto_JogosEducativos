package com.unifil.jogosEducativos.models;

import java.util.List;

public class BlackjackGame {

    private final String gameId;
    private final Player player;
    private final Dealer dealer;
    private final Cards deck;
    private boolean finished;

    public BlackjackGame(String gameId, String playerName) {
        this.gameId = gameId;
        this.player = new Player(playerName);
        this.dealer = new Dealer();
        this.deck = new Cards();
        this.finished = false;
    }

    public void startRound() {
        // TODO validar blackjack natural logo apos distribuicao inicial
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
    }

    public void hitPlayer() {
        // TODO impedir hit quando player ja escolheu stand
        if (finished) {
            throw new IllegalStateException("Rodada encerrada.");
        }

        player.addCard(deck.drawCard());
        if (player.calculateScore() > 21) {
            finished = true;
        }
    }

    public void standPlayer() {
        player.stop();
        while(dealer.calculateScore() < 17){
            dealer.addCard(deck.drawCard());
        }

        finished = true;
    }


    //  Se jogador passou de 21 -> DEALER_WIN
//Se dealer passou de 21 -> PLAYER_WIN
//Se jogador > dealer -> PLAYER_WIN
//Se jogador == dealer -> DRAW
//Caso contrario -> DEALER_WIN
    public String gameResult() {
        // TODO implementar regras completas (blackjack natural, push de blackjack e estados finais detalhados)


        int playerScore = player.calculateScore();
        int dealerScore = dealer.calculateScore();

        if (playerScore > 21) {
            return "DEALER_WIN";
        }

        if (dealerScore > 21 ) {
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