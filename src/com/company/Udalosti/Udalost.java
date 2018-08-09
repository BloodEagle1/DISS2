package com.company.Udalosti;

import com.company.Pozicovna;

public class Udalost {

    private double casVykonania;
    private Pozicovna simJadro;

    public Udalost(double casVykonania, Pozicovna simJadro) {
        this.casVykonania = casVykonania;
        this.simJadro = simJadro;
    }

    public void vykonaj() {
    }

    public Pozicovna getSimJadro() {
        return simJadro;
    }

    public double getCasVykonania() {
        return casVykonania;
    }
}
