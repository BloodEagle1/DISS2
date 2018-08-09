package com.company.Udalosti;

import com.company.Pozicovna;

public class UdalostKoniecReplikacie extends Udalost{
    public UdalostKoniecReplikacie(double casVykonania, Pozicovna simJadro) {
        super(casVykonania, simJadro);
    }

    @Override
    public void vykonaj() {
        getSimJadro().zvysHodnoty();
        getSimJadro().vynuluj();
        getSimJadro().vyprazdniFronty();
        getSimJadro().nastavCasPoslZmien(getCasVykonania());
        getSimJadro().planUdalosti(new UdalostKoniecReplikacie(getCasVykonania() + 31 * 24 * 60 * 60, getSimJadro()));
        getSimJadro().planUdalosti(new UdalostPrichZakTerminal1(getCasVykonania(), getSimJadro()));
        getSimJadro().planUdalosti(new UdalostPrichZakTerminal2(getCasVykonania(), getSimJadro()));
        getSimJadro().planUdalosti(new UdalostMinibusStart(getCasVykonania() + 10*60, getSimJadro()));
        getSimJadro().planUdalosti(new UdalostKoniecZahrievania(getCasVykonania() + 24*60*60, getSimJadro()));
    }
}
