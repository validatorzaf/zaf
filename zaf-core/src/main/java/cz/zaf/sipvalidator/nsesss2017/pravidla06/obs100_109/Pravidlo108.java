package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

public class Pravidlo108 extends K06PravidloBase {

    private static final DatatypeFactory datatypeFactory;
    static {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String OBS108 = "obs108";

    public Pravidlo108() {
        super(OBS108,
                "Element s atributem datum obsahuje hodnotu, která po interpretaci denního data je rovna hodnotě elementu.",
                "Chybně je vyplněn časový údaj.",
                "-");
    }

    @Override
    protected void kontrola() {
        List<Element> zaklEntity = this.predpokladZakladniEntity();
        LinkedList<Element> elements = new LinkedList<>(zaklEntity);
        while (elements.size() > 0) {
            Element el = elements.pop();
            Attr attrDatum = el.getAttributeNode("datum");
            if (attrDatum != null) {
                // get content
                String cnt = el.getTextContent();
                if (StringUtils.isNotEmpty(cnt)) {
                    kontrolaPlatnostiDatum(el, attrDatum.getNodeValue(), cnt);
                }
            }
            // append child elements
            NodeList nl = el.getChildNodes();
            if (nl != null) {
                for (int i = nl.getLength() - 1; i >= 0; i--) {
                    Node n = nl.item(i);
                    if (n.getNodeType() == Node.ELEMENT_NODE) {
                        Element e = (Element) n;
                        elements.push(e);
                    }
                }
            }
        }
    }

    private void kontrolaPlatnostiDatum(Element el, String attrValue, String contentValue) {
        XMLGregorianCalendar xmlCal;
        try {
            xmlCal = datatypeFactory.newXMLGregorianCalendar(attrValue);
        } catch (Exception e) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                        "Chybný formát v atributu datum, hodnota: " + attrValue + ", chyba: " + e.toString(), el);
            return;
        }
        LocalDate cntDate;
        try {
            cntDate = LocalDate.parse(contentValue);
        } catch (Exception e) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                        "Chybný formát datace, hodnota: " + contentValue + ", chyba: " + e.toString(), el);
            return;
        }
        int tz = xmlCal.getTimezone();
        if (tz == DatatypeConstants.FIELD_UNDEFINED) {
            // no time zone, simply check values
            if (xmlCal.getYear() != cntDate.getYear() ||
                    xmlCal.getMonth() != cntDate.getMonth().getValue() ||
                    xmlCal.getDay() != cntDate.getDayOfMonth()) {
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                            "Nesoulad hodnot datace, hodnota atributu: " + attrValue + ", hodnota elementu: "
                                    + contentValue, el);
            }
        } else {
            // time including time zone
            // TODO: use time-zone related shift?
            if (xmlCal.getYear() != cntDate.getYear() ||
                    xmlCal.getMonth() != cntDate.getMonth().getValue() ||
                    xmlCal.getDay() != cntDate.getDayOfMonth()) {
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                            "Nesoulad hodnot datace, hodnota atributu: " + attrValue + ", hodnota elementu: "
                                    + contentValue, el);
            }
        }
    }

}
