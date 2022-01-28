package cz.zaf.sipvalidator.nsesss2017;

/**
 * Zakladni implementace pravidel pred zavedenim vyjimek
 *
 * Nove se prepracovava na vyjimky, tj. pravidla jsou odvozena
 * primo od K06PravidloBase
 */
public abstract class K06PravidloBaseOld extends K06PravidloBase {

    public K06PravidloBaseOld(final String kodPravidla,
                           final String textPravidla,
                           final String obecnyPopisChyby,
                           final String zdrojChyby) {        
        super(kodPravidla, textPravidla, obecnyPopisChyby, zdrojChyby);
    }

    protected abstract boolean kontrolaPravidla();

    @Override
    protected void kontrola() {
        kontrolaPravidla();
    }
}
