package com.unifil.jogosEducativos.models;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class Cards {

    private final Deque<Card> deck;

    public Cards() {
        List<Card> cards = createDeck();
        Collections.shuffle(cards);
        this.deck = new ArrayDeque<>(cards);
    }

    public Card drawCard() {
        if (deck.isEmpty()) {
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