package com.company.Generatory;

import java.util.Random;

public class GenExponencialne implements IGenerator {

    private Random rand;
    private double lambda;

    public GenExponencialne(long nasada, double lambda) {
        this.rand = new Random(nasada);
        this.lambda = lambda;
    }

    @Override
    public double dajCislo() {
        return Math.log(1 - rand.nextDouble()) / (-lambda);
    }

}
