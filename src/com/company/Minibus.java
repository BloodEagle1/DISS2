package com.company;

import java.util.PriorityQueue;

public class Minibus {

    private PriorityQueue<Zakaznik> priorFrontCestujuci;
    private int pocetMiest;
    private String predoslaZastavka;
    private String aktualnaZastavka;
    private double casPoslednejZastavky;
    private String nazov;

    public Minibus() {
        this.pocetMiest = 12;
        this.predoslaZastavka = "";
        this.aktualnaZastavka = "";
        this.priorFrontCestujuci = new PriorityQueue<>((z1, z2) -> {
            if (z1.getVstupDoSystemu() < z2.getVstupDoSystemu()) {
                return -1;
            }
            if (z1.getVstupDoSystemu() > z2.getVstupDoSystemu()) {
                return 1;
            }
            return 0;
        });
    }

    public int getPocetMiest() {
        return pocetMiest;
    }

    public PriorityQueue<Zakaznik> getPriorFrontCestujuci() {
        return priorFrontCestujuci;
    }

    public String getPredoslaZastavka() {
        return predoslaZastavka;
    }

    public String getAktualnaZastavka() {
        return aktualnaZastavka;
    }

    public double getCasPoslednejZastavky() {
        return casPoslednejZastavky;
    }

    public String getNazov() {
        return nazov;
    }

    public void setPredoslaZastavka(String predoslaZastavka) {
        this.predoslaZastavka = predoslaZastavka;
    }

    public void setAktualnaZastavka(String aktualnaZastavka) {
        this.aktualnaZastavka = aktualnaZastavka;
    }

    public void setCasPoslednejZastavky(double casPoslednejZastavky) {
        this.casPoslednejZastavky = casPoslednejZastavky;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public void pridajZakaznika(Zakaznik zakaznik) {
        priorFrontCestujuci.add(zakaznik);
    }

    public Zakaznik vyberZakaznikaZRadu() {
        return priorFrontCestujuci.poll();
    }


}
