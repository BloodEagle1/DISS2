package com.company.Udalosti;

import com.company.Pozicovna;
import com.company.Zakaznik;

public class UdalostKonObsluhy extends Udalost {

    private Zakaznik zakaznik;

    public UdalostKonObsluhy(double casVykonania, Pozicovna simJadro, Zakaznik zakaznik) {
        super(casVykonania, simJadro);
        this.zakaznik = zakaznik;
    }

    @Override
    public void vykonaj() {
        if (!getSimJadro().getPriorFrontZakPozicovna().isEmpty()){
            getSimJadro().planUdalosti(new UdalostZacObsluhy(getCasVykonania(), getSimJadro(), getSimJadro().vyberZakaznikaZRadu(getSimJadro().getPriorFrontZakPozicovna())));
        }else{
            getSimJadro().zvysVolnychPracovnikov();
        }

        getSimJadro().zvysZakaznikovSystemu();
        getSimJadro().zvysCasVSysteme(getCasVykonania() - zakaznik.getVstupDoSystemu());
    }
}
