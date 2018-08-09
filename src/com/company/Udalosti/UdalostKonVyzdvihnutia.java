package com.company.Udalosti;

import com.company.Minibus;
import com.company.Pozicovna;

public class UdalostKonVyzdvihnutia extends Udalost {

    private Minibus minibus;

    public UdalostKonVyzdvihnutia(double casVykonania, Pozicovna simJadro, Minibus minibus) {
        super(casVykonania, simJadro);
        this.minibus = minibus;
    }

    @Override
    public void vykonaj() {
        minibus.setPredoslaZastavka(minibus.getAktualnaZastavka());
        minibus.setAktualnaZastavka("");
        getSimJadro().planUdalosti(new UdalostZacVyzdvihnutia2(getCasVykonania() + 51.4285714, getSimJadro(), minibus));
    }
}
