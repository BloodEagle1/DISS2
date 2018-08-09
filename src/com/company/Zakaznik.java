package com.company;

public class Zakaznik {
    private double vstupDoSystemu;
    private double vstupDoRaduObsluha;
    private double vstupDORaduTerminal1;
    private double vstupDORaduTerminal2;

    public Zakaznik(double vstupDoSystemu) {
        this.vstupDoSystemu = vstupDoSystemu;
    }

    public double getVstupDoSystemu() {
        return vstupDoSystemu;
    }

    public double getVstupDoRaduObsluha() {
        return vstupDoRaduObsluha;
    }

    public double getVstupDORaduTerminal1()
    {
        return vstupDORaduTerminal1;
    }

    public double getVstupDORaduTerminal2() {
        return vstupDORaduTerminal2;
    }

    public void setVstupDoRaduObsluha(double vstupDoRaduObsluha) {
        this.vstupDoRaduObsluha = vstupDoRaduObsluha;
    }

    public void setVstupDORaduTerminal1(double vstupDORaduTerminal1) {
        this.vstupDORaduTerminal1 = vstupDORaduTerminal1;
    }

    public void setVstupDORaduTerminal2(double vstupDORaduTerminal2) {
        this.vstupDORaduTerminal2 = vstupDORaduTerminal2;
    }
}
