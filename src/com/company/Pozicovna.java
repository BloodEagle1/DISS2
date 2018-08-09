package com.company;

import com.company.Generatory.GenExponencialne;
import com.company.Generatory.GenRovnomerneho;
import com.company.Udalosti.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;

public class Pozicovna extends SimJadro {

    private PriorityQueue<Zakaznik> priorFrontZakTerminal1;
    private PriorityQueue<Zakaznik> priorFrontZakTerminal2;
    private PriorityQueue<Zakaznik> priorFrontZakPozicovna;
    private GenExponencialne genPrichodovTerminal1;
    private GenExponencialne genPrichodovTerminal2;
    private GenRovnomerneho genCasObsluhy;
    private GenRovnomerneho genNastupu;
    private GenRovnomerneho genVystupu;
    private int pracovnici;
    private int minibusy;
    private int vypusteneMinibusy;
    private int volnyPracovnici;
    private int zakSystemu;
    private double casVSysteme;
    private int zakVstup;
    private double repCasVSysteme;
    private double pocetReplikacii;
    private double pocetPridaniDoSumZakVRadeObsluha;
    private double casPoslednejZmenyFrontuObsluha;
    private double sumZakVRadeObsluha;
    private double repSumZakVRadeObsluha;
    private double casVRadeObsluha;
    private double pocetZakVystupZRaduObsluha;
    private double repCasVRadeObsluha;
    private double sumObsadPrac;
    private double casPoslednejZmenyPrac;
    private double pocetPridaniDoSumObsadPrac;
    private double repSumObsadPrac;
    private Minibus[] minibusyArray;
    private double sumZakVRadeTerminal1;
    private double pocetPridaniDoSumZakVRadeTerminal1;
    private double sumZakVRadeTerminal2;
    private double pocetPridaniDoSumZakVRadeTerminal2;
    private double casPoslednejZmenyFrontuTerminal1;
    private double casPoslednejZmenyFrontuTerminal2;
    private double repSumZakVRadeTerminal1;
    private double repSumZakVRadeTerminal2;
    private double rychlost;
    private double frekvencia;
    private double repCasVSystemeSquared;
    private double casVRadeTerminal1;
    private double casVRadeTerminal2;
    private double pocetZakVystupZRaduTerminal1;
    private double pocetZakVystupZRaduTerminal2;
    private double repCasVRadeTerminal1;
    private double repCasVRadeTerminal2;

    public Pozicovna(double maxCas, int pracovnici, int minibusy) {
        super(maxCas);
        this.rychlost = 0;
        this.frekvencia = 0;
        this.minibusyArray = new Minibus[minibusy];
        this.pocetReplikacii = 0;
        this.pracovnici = pracovnici;
        this.minibusy = minibusy;
        this.volnyPracovnici = pracovnici;
        this.vypusteneMinibusy = 0;
        Random genNasada = new Random();
        this.genPrichodovTerminal1 = new GenExponencialne(genNasada.nextLong(), 1.0 / ((60.0 * 60.0 / 43.0)));
        this.genPrichodovTerminal2 = new GenExponencialne(genNasada.nextLong(), 1.0 / ((60.0 * 60.0 / 19.0)));
        this.genCasObsluhy = new GenRovnomerneho(genNasada.nextLong(), 6 * 60, 4 * 60);
        this.genNastupu = new GenRovnomerneho(genNasada.nextLong(), 12, 2);
        this.genVystupu = new GenRovnomerneho(genNasada.nextLong(), 8, 4);
        this.casPoslednejZmenyFrontuObsluha = 0;
        this.priorFrontZakPozicovna = new PriorityQueue<>((z1, z2) -> {
            if (z1.getVstupDoSystemu() < z2.getVstupDoSystemu()) {
                return -1;
            }
            if (z1.getVstupDoSystemu() > z2.getVstupDoSystemu()) {
                return 1;
            }
            return 0;
        });

        this.priorFrontZakTerminal1 = new PriorityQueue<>((z1, z2) -> {
            if (z1.getVstupDoSystemu() < z2.getVstupDoSystemu()) {
                return -1;
            }
            if (z1.getVstupDoSystemu() > z2.getVstupDoSystemu()) {
                return 1;
            }
            return 0;
        });

        this.priorFrontZakTerminal2 = new PriorityQueue<>((z1, z2) -> {
            if (z1.getVstupDoSystemu() < z2.getVstupDoSystemu()) {
                return -1;
            }
            if (z1.getVstupDoSystemu() > z2.getVstupDoSystemu()) {
                return 1;
            }
            return 0;
        });

        Udalost u1 = new UdalostPrichZakTerminal1(0, this);
        planUdalosti(u1);
        Udalost u2 = new UdalostPrichZakTerminal2(0, this);
        planUdalosti(u2);
        Udalost u3 = new UdalostMinibusStart(30 * 60, this);
        planUdalosti(u3);
        if (maxCas > (31 * 24 * 60 * 60)) {
            Udalost u4 = new UdalostKoniecReplikacie(31 * 24 * 60 * 60, this);
            planUdalosti(u4);
        }
        Udalost u5 = new UdalostKoniecZahrievania(24 * 60 * 60, this);
        planUdalosti(u5);
        if (maxCas <= 31 * 24 * 60 * 60) {
            Udalost u6 = new UdalostSystemova(24 * 60 * 60, this);
            planUdalosti(u6);
        }
    }

    public double getRychlost() {
        return rychlost;
    }

    public double getFrekvencia() {
        return frekvencia;
    }

    public PriorityQueue<Zakaznik> getPriorFrontZakTerminal1() {
        return priorFrontZakTerminal1;
    }

    public PriorityQueue<Zakaznik> getPriorFrontZakTerminal2() {
        return priorFrontZakTerminal2;
    }

    public PriorityQueue<Zakaznik> getPriorFrontZakPozicovna() {
        return priorFrontZakPozicovna;
    }

    public GenExponencialne getGenPrichodovTerminal1() {
        return genPrichodovTerminal1;
    }

    public GenExponencialne getGenPrichodovTerminal2() {
        return genPrichodovTerminal2;
    }

    public GenRovnomerneho getGenCasObsluhy() {
        return genCasObsluhy;
    }

    public GenRovnomerneho getGenNastupu() {
        return genNastupu;
    }

    public GenRovnomerneho getGenVystupu() {
        return genVystupu;
    }

    public int getPracovnici() {
        return pracovnici;
    }

    public int getMinibusy() {
        return minibusy;
    }

    public int getVypusteneMinibusy() {
        return vypusteneMinibusy;
    }

    public int getVolnyPracovnici() {
        return volnyPracovnici;
    }

    public int getZakVstup() {
        return zakVstup;
    }

    public int getZakSystemu() {
        return zakSystemu;
    }

    public Minibus[] getMinibusyArray() {
        return minibusyArray;
    }

    public double getRepCasVSysteme() {
        return repCasVSysteme;
    }

    public double getRepCasVSystemeSquared() {
        return repCasVSystemeSquared;
    }

    public void setRychlost(double rychlost) {
        this.rychlost = rychlost;
    }

    public void setFrekvencia(double frekvencia) {
        this.frekvencia = frekvencia;
    }

    public double getPocetReplikacii() {
        return pocetReplikacii;
    }

    public void pridajZakaznika(Zakaznik zakaznik, PriorityQueue priorFront) {
        if (priorFront == priorFrontZakPozicovna) {
            sumZakVRadeObsluha += (getAktualnyCas() - casPoslednejZmenyFrontuObsluha) * priorFront.size();
            pocetPridaniDoSumZakVRadeObsluha += getAktualnyCas() - casPoslednejZmenyFrontuObsluha;
            casPoslednejZmenyFrontuObsluha = getAktualnyCas();
            zakaznik.setVstupDoRaduObsluha(getAktualnyCas());
        } else if (priorFront == priorFrontZakTerminal1) {
            sumZakVRadeTerminal1 += (getAktualnyCas() - casPoslednejZmenyFrontuTerminal1) * priorFront.size();
            pocetPridaniDoSumZakVRadeTerminal1 += getAktualnyCas() - casPoslednejZmenyFrontuTerminal1;
            casPoslednejZmenyFrontuTerminal1 = getAktualnyCas();
            zakaznik.setVstupDORaduTerminal1(getAktualnyCas());
        } else if (priorFront == priorFrontZakTerminal2) {
            sumZakVRadeTerminal2 += (getAktualnyCas() - casPoslednejZmenyFrontuTerminal2) * priorFront.size();
            pocetPridaniDoSumZakVRadeTerminal2 += getAktualnyCas() - casPoslednejZmenyFrontuTerminal2;
            casPoslednejZmenyFrontuTerminal2 = getAktualnyCas();
            zakaznik.setVstupDORaduTerminal2(getAktualnyCas());
        }
        priorFront.add(zakaznik);
    }

    public Zakaznik vyberZakaznikaZRadu(PriorityQueue<Zakaznik> priorFront) {
        if (priorFront == priorFrontZakPozicovna) {
            sumZakVRadeObsluha += (getAktualnyCas() - casPoslednejZmenyFrontuObsluha) * priorFront.size();
            pocetPridaniDoSumZakVRadeObsluha += getAktualnyCas() - casPoslednejZmenyFrontuObsluha;
            casPoslednejZmenyFrontuObsluha = getAktualnyCas();
        } else if (priorFront == priorFrontZakTerminal1) {
            sumZakVRadeTerminal1 += (getAktualnyCas() - casPoslednejZmenyFrontuTerminal1) * priorFront.size();
            pocetPridaniDoSumZakVRadeTerminal1 += getAktualnyCas() - casPoslednejZmenyFrontuTerminal1;
            casPoslednejZmenyFrontuTerminal1 = getAktualnyCas();
        } else if (priorFront == priorFrontZakTerminal2) {
            sumZakVRadeTerminal2 += (getAktualnyCas() - casPoslednejZmenyFrontuTerminal2) * priorFront.size();
            pocetPridaniDoSumZakVRadeTerminal2 += getAktualnyCas() - casPoslednejZmenyFrontuTerminal2;
            casPoslednejZmenyFrontuTerminal2 = getAktualnyCas();
        }

        return priorFront.poll();
    }

    public void zvysVypusteneMinibusy(Minibus minibus) {
        minibusyArray[vypusteneMinibusy] = minibus;
        minibus.setNazov("Minibus" + vypusteneMinibusy);
        vypusteneMinibusy++;
    }

    public void znizVolnychPracovnikov() {
        sumObsadPrac += (getAktualnyCas() - casPoslednejZmenyPrac) * (pracovnici - volnyPracovnici);
        pocetPridaniDoSumObsadPrac += (getAktualnyCas() - casPoslednejZmenyPrac);
        casPoslednejZmenyPrac = getAktualnyCas();
        volnyPracovnici--;
    }

    public void zvysVolnychPracovnikov() {
        sumObsadPrac += (getAktualnyCas() - casPoslednejZmenyPrac) * (pracovnici - volnyPracovnici);
        pocetPridaniDoSumObsadPrac += (getAktualnyCas() - casPoslednejZmenyPrac);
        casPoslednejZmenyPrac = getAktualnyCas();
        volnyPracovnici++;
    }

    public void zvysZakVstup() {
        zakVstup++;
    }

    public void zvysZakaznikovSystemu() {
        zakSystemu++;
    }

    public void zvysCasVSysteme(double cas) {
        casVSysteme += cas;
    }

    public void zvysPocetZakVystupZRaduObsluha() {
        pocetZakVystupZRaduObsluha++;
    }

    public void zvysCasVRadeObsluha(double cas) {
        casVRadeObsluha += cas;
    }

    public void zvysCasVRadeTerminal1(double cas) {
        casVRadeTerminal1 += cas;
    }

    public void zvysPocetZakVystupZRaduTerminal1() {
        pocetZakVystupZRaduTerminal1++;
    }

    public void zvysCasVRadeTerminal2(double cas) {
        casVRadeTerminal2 += cas;
    }

    public void zvysPocetZakVystupZRaduTerminal2() {
        pocetZakVystupZRaduTerminal2++;
    }

    public double statCasuVSysteme() {
        return casVSysteme / (zakSystemu * 60.0);
    }

    public double statZakVRadeObsluha() {
        return sumZakVRadeObsluha / pocetPridaniDoSumZakVRadeObsluha;
    }

    public double statCasuVRadeObsluha() {
        return casVRadeObsluha / (pocetZakVystupZRaduObsluha * 60.0);
    }

    public double statObsadenostPrac() {
        return sumObsadPrac / pocetPridaniDoSumObsadPrac;
    }

    public double statZakVRadeTerminal1() {
        return sumZakVRadeTerminal1 / pocetPridaniDoSumZakVRadeTerminal1;
    }

    public double statZakVRadeTerminal2() {
        return sumZakVRadeTerminal2 / pocetPridaniDoSumZakVRadeTerminal2;
    }

    public double statCasuVRadeTerminal1() {
        return casVRadeTerminal1 / (pocetZakVystupZRaduTerminal1 * 60.0);
    }

    public double statCasuVRadeTerminal2() {
        return casVRadeTerminal2 / (pocetZakVystupZRaduTerminal2 * 60.0);
    }

    public double repStatCasuVSysteme() {
        return repCasVSysteme / (pocetReplikacii);
    }

    public double repStatZakVRadeNaObsluhu() {
        return repSumZakVRadeObsluha / (pocetReplikacii);
    }

    public double repStatCasVRadeObsluha() {
        return repCasVRadeObsluha / (pocetReplikacii);
    }

    public double repStatObsadenostPrac() {
        return repSumObsadPrac / (pocetReplikacii);
    }

    public double repStatZakVRadeTerminal1() {
        return repSumZakVRadeTerminal1 / (pocetReplikacii);
    }

    public double repStatZakVRadeTerminal2() {
        return repSumZakVRadeTerminal2 / (pocetReplikacii);
    }

    public double repStatCasVRadeTerminal1() {
        return repCasVRadeTerminal1 / (pocetReplikacii);
    }

    public double repStatCasVRadeTerminal2() {
        return repCasVRadeTerminal2 / (pocetReplikacii);
    }

    public void zvysHodnoty() {
        pocetReplikacii++;
        repCasVSysteme += statCasuVSysteme();
        repSumZakVRadeObsluha += statZakVRadeObsluha();
        repCasVRadeObsluha += statCasuVRadeObsluha();
        repSumObsadPrac += statObsadenostPrac();
        repSumZakVRadeTerminal1 += statZakVRadeTerminal1();
        repSumZakVRadeTerminal2 += statZakVRadeTerminal2();
        repCasVSystemeSquared += Math.pow(statCasuVSysteme(), 2);
        repCasVRadeTerminal1 +=statCasuVRadeTerminal1();
        repCasVRadeTerminal2 +=statCasuVRadeTerminal2();
    }

    public void vynuluj() {
        vypusteneMinibusy = 0;
        volnyPracovnici = pracovnici;
        vynulujStatistiky();
    }

    public void vyprazdniFronty() {
        getPriorFrontZakTerminal2().clear();
        getPriorFrontZakTerminal1().clear();
        getPriorFrontZakPozicovna().clear();
        super.getPriorFrontCasovaOs().clear();
    }

    public void nastavCasPoslZmien(double casVykonania) {
        casPoslednejZmenyFrontuObsluha = casVykonania;
        casPoslednejZmenyPrac = casVykonania;
        casPoslednejZmenyFrontuTerminal1 = casVykonania;
        casPoslednejZmenyFrontuTerminal2 = casVykonania;
    }


    public void vynulujStatistiky() {
        zakSystemu = 0;
        casVSysteme = 0;
        zakVstup = 0;
        sumZakVRadeObsluha = 0;
        pocetPridaniDoSumZakVRadeObsluha = 0;
        casVRadeObsluha = 0;
        pocetZakVystupZRaduObsluha = 0;
        sumObsadPrac = 0;
        pocetPridaniDoSumObsadPrac = 0;
        sumZakVRadeTerminal1 = 0;
        sumZakVRadeTerminal2 = 0;
        pocetPridaniDoSumZakVRadeTerminal1 = 0;
        pocetPridaniDoSumZakVRadeTerminal2 = 0;
        casVRadeTerminal1 = 0;
        pocetZakVystupZRaduTerminal1 = 0;
        casVRadeTerminal2 = 0;
        pocetZakVystupZRaduTerminal2 = 0;
    }

    public String vypocitajInterval() {
        double rozptylSquared = ((1 / pocetReplikacii) * getRepCasVSystemeSquared()) - Math.pow((1 / pocetReplikacii) * getRepCasVSysteme(), 2);
        double rozptyl = Math.sqrt(rozptylSquared);

        double dolny = repStatCasuVSysteme() - ((1.645 * rozptyl) / (Math.sqrt(pocetReplikacii - 1)));
        double horny = repStatCasuVSysteme() + ((1.645 * rozptyl) / (Math.sqrt(pocetReplikacii - 1)));
        return "< " + dolny + " , " + horny + " >";
    }
}
