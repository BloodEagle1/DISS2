package com.company.Udalosti;

import com.company.Generatory.GenRovnomerneho;
import com.company.Minibus;
import com.company.Pozicovna;
import com.company.Zakaznik;

public class UdalostZacVyzdvihnutia extends Udalost {

    private Minibus minibus;
    private GenRovnomerneho genNastupu;

    public UdalostZacVyzdvihnutia(double casVykonania, Pozicovna simJadro, Minibus minibus) {
        super(casVykonania, simJadro);
        this.minibus = minibus;
        this.genNastupu = simJadro.getGenNastupu();
    }

    @Override
    public void vykonaj() {
        minibus.setAktualnaZastavka("Terminal1");
        if (minibus.getPriorFrontCestujuci().size() != minibus.getPocetMiest() && !getSimJadro().getPriorFrontZakTerminal1().isEmpty()) {
            Zakaznik zakaznik = getSimJadro().vyberZakaznikaZRadu(getSimJadro().getPriorFrontZakTerminal1());
            getSimJadro().zvysCasVRadeTerminal1(getCasVykonania() - zakaznik.getVstupDORaduTerminal1());
            getSimJadro().zvysPocetZakVystupZRaduTerminal1();
            minibus.pridajZakaznika(zakaznik);
            double zakNastup = genNastupu.dajCislo();
            getSimJadro().planUdalosti(new UdalostZacVyzdvihnutia(getCasVykonania() + zakNastup, getSimJadro(), minibus));
        } else {
            getSimJadro().planUdalosti(new UdalostKonVyzdvihnutia(getCasVykonania(), getSimJadro(), minibus));
        }
    }
}
