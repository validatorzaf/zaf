package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

// Obsahova 101
// Pokud existuje jakýkoli element nsesss:Komponenty, všechny dětské elementy
// nsesss:Komponenta obsahují
// v atributu poradi hodnotu, která společně tvoří vzestupnou a souvislou řadu
// přirozených čísel počínaje 1,
// přičemž čísla se mohou opakovat a úvodní nuly se ignorují.
public class Pravidlo100 extends K06PravidloBase {

    static Logger log = LoggerFactory.getLogger(Pravidlo100.class);

    public static final String OBS100 = "obs100";

    public Pravidlo100() {
        super(OBS100, "Pokud existuje jakýkoli element nsesss:Komponenty, " +
                "všechny dětské elementy nsesss:Komponenta obsahují v atributu poradi hodnotu, " +
                "která společně tvoří vzestupnou a souvislou řadu přirozených čísel počínaje 1, " +
                "přičemž čísla se mohou opakovat a úvodní nuly se ignorují.",
                "Komponenta má chybné pořadí.",
                "");
    }

    @Override
    protected boolean kontrolaPravidla() {
        // získání všech komponent ve výstupním datovém formátu
        List<Node> komponenty = metsParser.getNodes(NsessV3.KOMPONENTA);
        if (CollectionUtils.isEmpty(komponenty)) {
            return true;
        }
        // sort by parent
        Node parentNode = null;
        int lastValue = 0;
        for (Node komponenta : komponenty) {
            Node parent = komponenta.getParentNode();
            if (parent != parentNode) {
                // reset parent
                lastValue = 0;
                parentNode = parent;
            }

            Integer nextPoradi = ValuesGetter.getAttribute(komponenta, "poradi");
            if (nextPoradi == null || !nextPoradi.equals(lastValue + 1)) {
                return nastavChybu("Komponenta má chybné pořadí, poslední zjištěné pořadí je: " +
                        lastValue +
                        ", pořadí následující komponenty není větší a má hodnotu: " +
                        nextPoradi, komponenta);
            }
            lastValue++;
        }

        return true;
    }

}
