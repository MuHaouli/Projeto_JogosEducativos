package com.unifil.jogosEducativos.models;

import java.util.ArrayList;
import java.util.List;

public abstract class Person {

    private final String name;
    private final List<Card> hand;

    protected Person(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public int calculateScore() {
        int score = hand.stream().mapToInt(Card::getValue).sum();
        long aceCount = hand.stream().filter(card -> "A".equals(card.getRank())).count();

        while (score > 21 && aceCount > 0) {
            score -= 10;
            aceCount--;
        }

        return score;
    }
}
