package com.unifil.jogosEducativos.models;
import java.util.Map;
import java.util.HashMap;

public class Cards {

    private Map<String, Integer> cards = new HashMap<>();

    public Cards(int handValue){
        this.cards.put("A", getAValue(handValue));
        this.cards.put("2", 2);
        this.cards.put("3", 3);
        this.cards.put("4", 4);
        this.cards.put("5", 5);
        this.cards.put("6", 6);
        this.cards.put("7", 7);
        this.cards.put("8", 8);
        this.cards.put("9", 9);
        this.cards.put("10", 10);
        this.cards.put("J", 10);
        this.cards.put("Q", 10);
        this.cards.put("K", 10);
    }
    private int getAValue(int handValue){
        return handValue>10 ? 1:11;
    }

    public int getCardValue(String card){
        return cards.get(card);
    }

    public Map<String, Integer> getCards(){
        return cards;
    }

}
