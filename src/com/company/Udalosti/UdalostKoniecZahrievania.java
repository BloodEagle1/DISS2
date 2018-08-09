package com.company.Udalosti;

import com.company.Pozicovna;

public class UdalostKoniecZahrievania extends Udalost {

    public UdalostKoniecZahrievania(double casVykonania, Pozicovna simJadro) {
        super(casVykonania, simJadro);
    }

    @Override
    public void vykonaj() {
        getSimJadro().vynulujStatistiky();
    }
}
