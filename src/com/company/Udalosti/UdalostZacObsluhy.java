package com.company.Udalosti;

import com.company.Generatory.GenRovnomerneho;
import com.company.Pozicovna;
import com.company.Zakaznik;

public class UdalostZacObsluhy extends Udalost {

    private Zakaznik zakaznik;
    private GenRovnomerneho genCasObsluhy;

    public UdalostZacObsluhy(double casVykonania, Pozicovna simJadro, Zakaznik zakaznik) {
        super(casVykonania, simJadro);
        this.zakaznik = zakaznik;
        this.genCasObsluhy = simJadro.getGenCasObsluhy();
    }

    @Override
    public void vykonaj() {
        if (zakaznik.getVstupDoRaduObsluha() != 0.0){
            getSimJadro().zvysCasVRadeObsluha(getCasVykonania() - zakaznik.getVstupDoRaduObsluha());
            getSimJadro().zvysPocetZakVystupZRaduObsluha();
        }
        getSimJadro().planUdalosti(new UdalostKonObsluhy(getCasVykonania() + genCasObsluhy.dajCislo(), getSimJadro(), zakaznik));
    }
}
