package com.company.Udalosti;

import com.company.Generatory.GenRovnomerneho;
import com.company.Minibus;
import com.company.Pozicovna;
import com.company.Zakaznik;

public class UdalostZacVystupuPozicovna extends Udalost{

    private Minibus minibus;
    private GenRovnomerneho genVystupu;

    public UdalostZacVystupuPozicovna(double casVykonania, Pozicovna simJadro, Minibus minibus) {
        super(casVykonania, simJadro);
        this.minibus = minibus;
        this.genVystupu = simJadro.getGenVystupu();
    }

    @Override
    public void vykonaj() {
        if (minibus.getAktualnaZastavka().equals(""))
            minibus.setAktualnaZastavka("Pozicovna");
        if (!minibus.getPriorFrontCestujuci().isEmpty()) {
            double zakVystup = genVystupu.dajCislo();
            if (getSimJadro().getVolnyPracovnici() != 0){
                getSimJadro().znizVolnychPracovnikov();
                getSimJadro().planUdalosti(new UdalostZacObsluhy(getCasVykonania() + zakVystup, getSimJadro(), minibus.vyberZakaznikaZRadu()));
            }else{
                getSimJadro().pridajZakaznika(minibus.vyberZakaznikaZRadu(), getSimJadro().getPriorFrontZakPozicovna());
            }
            getSimJadro().planUdalosti(new UdalostZacVystupuPozicovna(getCasVykonania() + zakVystup, getSimJadro(), minibus));
        } else {
            getSimJadro().planUdalosti(new UdalostKonVystupuPozicovna(getCasVykonania(), getSimJadro(), minibus));
        }
    }
}
