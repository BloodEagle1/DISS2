package com.company.Generatory;

import java.util.Random;

public class GenRovnomerneho implements IGenerator {

    private Random rand;
    private int minimum;
    private int maximum;
    private int rozsah;

    public GenRovnomerneho(long nasada, int priemer, int rozsah) {
        this.rand = new Random(nasada);
        this.minimum = priemer - rozsah;
        this.maximum = priemer + rozsah;
        this.rozsah = maximum - minimum;
    }

    @Override
    public double dajCislo() {
        return rand.nextDouble() * (rozsah) + minimum;
    }

}
