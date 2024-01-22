package cz.zaf.sipvalidator.nsesss2017.profily;

import cz.zaf.sipvalidator.nsesss2017.pravidla06.ObsahovePravidlo;
import cz.zaf.sipvalidator.nsesss2017.pravidla07.K07PravidloBase;

/**
 * Rozhraní pro validační profily
 *
 */
public interface ProfilValidace {

    /**
     * Název validačního profilu
     * 
     * @return Jméno profilu
     */
    public String getNazev();
    
    /**
     * Vrátí seznam připravených obsahových kontrol
     * 
     * Obsahová pravidla mohou být používána
     * jen v rámci jednoho vlákna. Mají svůj vnitřní stav
     * při běhu kontroly.
     * 
     * @return Pole pravidel
     */
    public ObsahovePravidlo[] createObsahovaPravidla();

    /**
     * Vrátí seznam připravených obsahových kontrol
     * 
     * Obsahová pravidla mohou být používána
     * jen v rámci jednoho vlákna. Mají svůj vnitřní stav
     * při běhu kontroly.
     * 
     * @return Pole pravidel
     */
    public K07PravidloBase[] createFormatovaPravidla();
}
