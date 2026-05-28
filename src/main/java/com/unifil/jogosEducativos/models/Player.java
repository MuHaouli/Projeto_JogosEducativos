package com.unifil.jogosEducativos.models;

public class Player extends Person {

    private boolean stopped;

    public Player(String name) {
        super(name);
        this.stopped = false;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void stop() {
        this.stopped = true;
    }
}