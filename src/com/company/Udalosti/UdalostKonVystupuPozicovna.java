package com.company.Udalosti;

import com.company.Minibus;
import com.company.Pozicovna;

public class UdalostKonVystupuPozicovna extends Udalost{

    private Minibus minibus;

    public UdalostKonVystupuPozicovna(double casVykonania, Pozicovna simJadro, Minibus minibus) {
        super(casVykonania, simJadro);
        this.minibus = minibus;
    }

    @Override
    public void vykonaj() {
        minibus.setPredoslaZastavka(minibus.getAktualnaZastavka());
        minibus.setAktualnaZastavka("");
        getSimJadro().planUdalosti(new UdalostZacVyzdvihnutia(getCasVykonania() + 658.285714, getSimJadro(), minibus));
    }
}
