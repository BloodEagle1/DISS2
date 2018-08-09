package com.company.Udalosti;

import com.company.Minibus;
import com.company.Pozicovna;

public class UdalostKonVyzdvihnutia2 extends Udalost {

    private Minibus minibus;

    public UdalostKonVyzdvihnutia2(double casVykonania, Pozicovna simJadro, Minibus minibus) {
        super(casVykonania, simJadro);
        this.minibus = minibus;
    }

    @Override
    public void vykonaj() {
        minibus.setPredoslaZastavka(minibus.getAktualnaZastavka());
        minibus.setAktualnaZastavka("");
        getSimJadro().planUdalosti(new UdalostZacVystupuPozicovna(getCasVykonania() + 257.142857, getSimJadro(), minibus));
    }
}
