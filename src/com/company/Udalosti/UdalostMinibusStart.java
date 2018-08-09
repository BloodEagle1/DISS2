package com.company.Udalosti;

import com.company.Minibus;
import com.company.Pozicovna;

public class UdalostMinibusStart extends Udalost {

    private Minibus minibus;

    public UdalostMinibusStart(double casVykonania, Pozicovna simJadro) {
        super(casVykonania, simJadro);
        this.minibus = new Minibus();
    }

    @Override
    public void vykonaj() {
        if (getSimJadro().getVypusteneMinibusy() < getSimJadro().getMinibusy()){
            getSimJadro().planUdalosti(new UdalostMinibusStart(getCasVykonania() + 30*60, getSimJadro()));
            getSimJadro().zvysVypusteneMinibusy(minibus);
            getSimJadro().planUdalosti(new UdalostZacVyzdvihnutia(getCasVykonania(), getSimJadro(), minibus));
        }

    }
}
