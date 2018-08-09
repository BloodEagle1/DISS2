package com.company.Udalosti;

import com.company.Generatory.GenRovnomerneho;
import com.company.Minibus;
import com.company.Pozicovna;
import com.company.Zakaznik;

public class UdalostZacVyzdvihnutia2 extends Udalost {

    private Minibus minibus;
    private GenRovnomerneho genNastupu;

    public UdalostZacVyzdvihnutia2(double casVykonania, Pozicovna simJadro, Minibus minibus) {
        super(casVykonania, simJadro);
        this.minibus = minibus;
        this.genNastupu = simJadro.getGenNastupu();
    }

    @Override
    public void vykonaj() {
        minibus.setAktualnaZastavka("Terminal2");
        if (minibus.getPriorFrontCestujuci().size() != minibus.getPocetMiest() && !getSimJadro().getPriorFrontZakTerminal2().isEmpty()) {
            Zakaznik zakaznik = getSimJadro().vyberZakaznikaZRadu(getSimJadro().getPriorFrontZakTerminal2());
            getSimJadro().zvysCasVRadeTerminal2(getCasVykonania() - zakaznik.getVstupDORaduTerminal2());
            getSimJadro().zvysPocetZakVystupZRaduTerminal2();
            minibus.pridajZakaznika(zakaznik);
            double zakNastup = genNastupu.dajCislo();
            getSimJadro().planUdalosti(new UdalostZacVyzdvihnutia2(getCasVykonania() + zakNastup, getSimJadro(), minibus));
        } else {
            getSimJadro().planUdalosti(new UdalostKonVyzdvihnutia2(getCasVykonania(), getSimJadro(), minibus));
        }
    }
}
