package com.company.Udalosti;

import com.company.Pozicovna;

public class UdalostSystemova extends Udalost {
    public UdalostSystemova(double casVykonania, Pozicovna simJadro) {
        super(casVykonania, simJadro);
    }

    @Override
    public void vykonaj() {

        try {
            Thread.sleep((long)getSimJadro().getRychlost()*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        getSimJadro().planUdalosti(new UdalostSystemova(getCasVykonania() + getSimJadro().getFrekvencia(), getSimJadro()));
    }
}
