package com.company.Udalosti;

import com.company.Generatory.GenExponencialne;
import com.company.Pozicovna;
import com.company.Zakaznik;

public class UdalostPrichZakTerminal1 extends Udalost {

    private Zakaznik zakaznik;
    private GenExponencialne genPrichodovTerminal1;

    public UdalostPrichZakTerminal1(double casVykonania, Pozicovna simJadro) {
        super(casVykonania, simJadro);
        this.zakaznik = new Zakaznik(casVykonania);
        this.genPrichodovTerminal1 = simJadro.getGenPrichodovTerminal1();
    }

    @Override
    public void vykonaj() {
        getSimJadro().zvysZakVstup();
        getSimJadro().pridajZakaznika(zakaznik, getSimJadro().getPriorFrontZakTerminal1());
        getSimJadro().planUdalosti(new UdalostPrichZakTerminal1(getCasVykonania() + genPrichodovTerminal1.dajCislo(), getSimJadro()));
    }
}
