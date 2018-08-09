package com.company;

import com.company.Udalosti.Udalost;

import javax.swing.*;
import java.util.*;

public class SimJadro {

    private final double maxCas;
    private double aktualnyCas;
    private PriorityQueue<Udalost> priorFrontCasovaOs;
    private List<ISimDelegat> delegati;
    private boolean breakpoint = false;

    public SimJadro(double maxCas) {
        this.delegati = new ArrayList<>();
        this.maxCas = maxCas;
        this.aktualnyCas = 0;
        this.priorFrontCasovaOs = new PriorityQueue<>((u1, u2) -> {
            if (u1.getCasVykonania() < u2.getCasVykonania()) {
                return -1;
            }
            if (u1.getCasVykonania() > u2.getCasVykonania()) {
                return 1;
            }
            return 0;
        });

    }

    public void spust() {
        SwingWorker<Void, Integer> sw = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() {
                try {
                    while (aktualnyCas <= maxCas && !priorFrontCasovaOs.isEmpty()) {
                        while (breakpoint) {
                            Thread.sleep(10);
                        }
                        Udalost pomUdalost = priorFrontCasovaOs.poll();
                        aktualnyCas = pomUdalost.getCasVykonania();
                        pomUdalost.vykonaj();
                        if (maxCas <= 31*24*60*60 && aktualnyCas >= 24*60*60)
                            refreshGUI();
                    }
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void done() {
                dajVysledky();
            }
        };

        sw.execute();
    }

    public void dajVysledky() {
        refreshGUI();
    }

    public void planUdalosti(Udalost udalost) {
        if (udalost.getCasVykonania() <= maxCas) {
            priorFrontCasovaOs.add(udalost);
        }
    }

    public double getAktualnyCas() {
        return aktualnyCas;
    }

    public PriorityQueue<Udalost> getPriorFrontCasovaOs() {
        return priorFrontCasovaOs;
    }

    public void registrujDelegata(ISimDelegat delegat)
    {
        delegati.add(delegat);
    }

    private void refreshGUI()
    {
        for (ISimDelegat delegat : delegati)
        {
            delegat.refresh(this);
        }
    }

    public void zastav(){
        this.breakpoint = true;
    }
    public void pokracuj() {
        this.breakpoint = false;
    }
}
