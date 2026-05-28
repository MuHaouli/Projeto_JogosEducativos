package com.unifil.jogosEducativos.models;
import java.util.Map;
import java.util.HashMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cards {

    private final List<Card> deck;

    public Cards() {
        this.deck = createDeck();
        // TODO permitir seed no embaralhamento para testes deterministas
        Collections.shuffle(this.deck);
    }

    public Card drawCard() {
        if (deck.isEmpty()) {
            // TODO decidir estrategia quando acabar baralho (reshuffle ou encerrar jogo)
            throw new IllegalStateException("Baralho vazio. Reinicie a rodada.");
        }
        return deck.removeFirst();
    }

    private List<Card> createDeck() {
        List<Card> cards = new ArrayList<>();
        String[] suits = {"S", "H", "D", "C"};

        addNumberCards(cards, suits);
        addFaceCards(cards, suits);
        addAceCards(cards, suits);

        return cards;
    }

    private void addNumberCards(List<Card> cards, String[] suits) {
        for (String suit : suits) {
            for (int value = 2; value <= 10; value++) {
                cards.add(new Card(String.valueOf(value), suit, value));
            }
        }
    }

    private void addFaceCards(List<Card> cards, String[] suits) {
        String[] ranks = {"J", "Q", "K"};
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit, 10));
            }
        }
    }

    private void addAceCards(List<Card> cards, String[] suits) {
        for (String suit : suits) {
            cards.add(new Card("A", suit, 11));
        }
    }
}
