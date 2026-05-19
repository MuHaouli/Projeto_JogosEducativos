package com.unifil.jogosEducativos.models;

import java.util.ArrayList;
import java.util.List;

public abstract class Person {
    private String nome;
    private String cpf;

    private List<String> hand = new ArrayList<>();

    public Person(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void grabCard(String card){
        hand.add(card);
    }

    public List<String> getHand() {
        return hand;
    }

    public int getHandValue(Cards cards){

        int total = 0;

        for(String card : hand){
            total += cards.getCardValue(card);
        }

        return total;
    }
}
