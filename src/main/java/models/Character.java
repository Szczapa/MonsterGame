package models;

import java.io.Serializable;

public class Character implements Serializable {
    private String name;
    private int pv;
    private int force;

    public Character(String name, int pv, int force) {
        this.name = name;
        this.pv = pv;
        this.force = force;
    }

    public String getName() {
        return name;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public int getForce() {
        return force;
    }
}
