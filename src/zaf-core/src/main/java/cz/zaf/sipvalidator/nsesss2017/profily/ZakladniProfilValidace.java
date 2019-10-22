package cz.zaf.sipvalidator.nsesss2017.profily;

public class ZakladniProfilValidace implements ProfilValidace {

    final String nazev;

    final int[] obsahoveKontroly;

    ZakladniProfilValidace(final String nazev,
                           final int[] obsahoveKontroly) {
        this.nazev = nazev;
        this.obsahoveKontroly = obsahoveKontroly;
    }

    @Override
    public String getNazev() {
        return nazev;
    }

    @Override
    public int[] getObsahoveKontroly() {
        return obsahoveKontroly;
    }

}
