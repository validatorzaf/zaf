package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs100_109;

import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.NsesssV4;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;
import org.apache.commons.collections4.CollectionUtils;

public class Pravidlo111 extends K06PravidloBase {

    private static final DatatypeFactory datatypeFactory;

    static {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String OBS111 = "obs111";

    public Pravidlo111() {
        super(OBS111,
                "Pokud existuje jakýkoli element <nsesss:Komponenta>, který obsahuje atribut forma_uchovani s hodnotou kontejner, potom v rodičovském elementu <nsesss:Komponenty> existuje alespoň jeden další element <nsesss:Komponenta>, který obsahuje atribut forma_uchovani s odlišnou hodnotou, než je kontejner.",
                "Chybí uvedení komponent z kontejneru.",
                "Požadavek 2.4.3 NSESSS; příloha č. 2 NSESSS, nsesss-common.xsd, ř. 119.");
    }

    @Override
    protected void kontrola() {
        List<Element> listKomponenty = metsParser.getElements(NsesssV4.KOMPONENTY);

        if (!CollectionUtils.isEmpty(listKomponenty)) {
            for (Element elkomponenty : listKomponenty) {
                List<Element> listKomponenta = ValuesGetter.getChildNodes(elkomponenty, NsesssV4.KOMPONENTA);
                Element elKomponentaKontejner = null;
                Element elKomponentaNeKontejner = null;
                for (Element elKomponenta : listKomponenta) {
                    String atrFormaUchovani = elKomponenta.getAttribute("forma_uchovani");
                    if (!StringUtils.isEmpty(atrFormaUchovani)) {
                        if (atrFormaUchovani.equals("kontejner")) {
                            elKomponentaKontejner = elKomponenta;
                        } else {
                            elKomponentaNeKontejner = elKomponenta;
                        }
                    }
                }
                if (elKomponentaKontejner != null) {
                    if (elKomponentaNeKontejner == null) {
                        nastavChybu(BaseCode.CHYBI_ELEMENT, "Existuje element <nsesss:Komponenta> s atributem forma_uchovani s hodnotou kontejner, ale neexistuje element <nsesss:Komponenta> s jinou hodnotou v atributu forma_uchovani.",
                                getMistoChyby(elKomponentaKontejner));

                    }
                }
            }
        }

    }

}
