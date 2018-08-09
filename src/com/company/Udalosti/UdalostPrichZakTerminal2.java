package com.company.Udalosti;

import com.company.Generatory.GenExponencialne;
import com.company.Pozicovna;
import com.company.Zakaznik;

public class UdalostPrichZakTerminal2 extends  Udalost {

    private Zakaznik zakaznik;
    private GenExponencialne genPrichodovTerminal2;

    public UdalostPrichZakTerminal2(double casVykonania, Pozicovna simJadro) {
        super(casVykonania, simJadro);
        this.zakaznik = new Zakaznik(casVykonania);
        this.genPrichodovTerminal2 = simJadro.getGenPrichodovTerminal2();
    }

    @Override
    public void vykonaj() {
        getSimJadro().zvysZakVstup();
        getSimJadro().pridajZakaznika(zakaznik, getSimJadro().getPriorFrontZakTerminal2());
        getSimJadro().planUdalosti(new UdalostPrichZakTerminal2(getCasVykonania() + genPrichodovTerminal2.dajCislo(), getSimJadro()));
    }
}
