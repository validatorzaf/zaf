package cz.zaf.sipvalidator.nsesss2017.profily;

/**
 * Rozhraní pro validační profily
 *
 */
public interface ProfilValidace {

    /**
     * Název validačního profilu
     * 
     * @return
     */
    public String getNazev();

    /**
     * Seznam obsahových kontrol
     * 
     * @return
     */
    public int[] getObsahoveKontroly();
}
