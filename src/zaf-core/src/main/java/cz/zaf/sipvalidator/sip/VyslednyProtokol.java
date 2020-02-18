package cz.zaf.sipvalidator.sip;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import cz.zaf.schema.kontrolasip.KontrolaSIP;
import cz.zaf.schema.kontrolasip.ObjectFactory;
import cz.zaf.schema.kontrolasip.TPouzitiKontroly;
import cz.zaf.schema.kontrolasip.TPravidlo;
import cz.zaf.schema.kontrolasip.TSip;
import cz.zaf.schema.kontrolasip.TTyp;
import cz.zaf.schema.kontrolasip.TVysledekKontroly;
import cz.zaf.schema.kontrolasip.TVysledekPravidla;
import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;

/**
 * Vysledny protokol o provedene kontrole
 *
 */
public class VyslednyProtokol {

    final static ObjectFactory objectFactory = new ObjectFactory();
    KontrolaSIP kontrolaSIP = objectFactory.createKontrolaSIP();

    static Schema schema;
    {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try (InputStream is = VyslednyProtokol.class.getClassLoader().getResourceAsStream("schema/kontrolasip.xsd")) {
            schema = sf.newSchema(new StreamSource(is));
        } catch (IOException | SAXException e) {
            throw new RuntimeException("Failed to load internal XSD", e);
        }
    }

    public VyslednyProtokol() {

        try {
            pripravAppInfo();
            pripravCas();

            kontrolaSIP.setKontrolaID(UUID.randomUUID().toString());

        } catch (Exception e) {
            throw new IllegalStateException("Failed to prepare VyslednyProtokol", e);
        }
    }

    private void pripravCas() throws DatatypeConfigurationException {
        Instant now = Instant.now();
        String dateTimeString = now.toString();
        XMLGregorianCalendar xcal;
        xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTimeString);
        kontrolaSIP.setDatumKontroly(xcal);

    }

    private void pripravAppInfo() throws IOException {
        // read default version from app
        final Properties properties = new Properties();
        try (InputStream r = getClass().getClassLoader().getResourceAsStream("zaf-core.properties")) {
            properties.load(r);
        }
        String verzeApp = properties.getProperty("version");
        String artifactId = properties.getProperty("artifactId");

        kontrolaSIP.setNazevAplikace(artifactId);
        kontrolaSIP.setVerzeAplikace(verzeApp);
    }

    public KontrolaSIP getKontrolaSIP() {
        return kontrolaSIP;
    }

    public TSip addSipInfo(SipInfo sipInfo) {
        TSip sipNode = objectFactory.createTSip();
        prepareSipInfo(sipNode, sipInfo);
        kontrolaSIP.getSip().add(sipNode);
        return sipNode;
    }

    static private void prepareSipInfo(TSip sipNode, SipInfo sipInfo) {

        writeSipInfo(sipNode, sipInfo);

        List<VysledekKontroly> kontroly = sipInfo.getSeznamKontrol();
        for (VysledekKontroly vysl : kontroly) {
            TTyp typKontrolyNode = convert(vysl);
            sipNode.getTyp().add(typKontrolyNode);
        }
    }

    private static TTyp convert(VysledekKontroly vysl) {
        TTyp typ = objectFactory.createTTyp();
        typ.setNazev(vysl.getKontrola_nazev());
        typ.setStav(convert(vysl.getStavKontroly()));

        // prevod pravidel
        for (PravidloKontroly pravidlo : vysl.getPravidla()) {
            TPravidlo pravNode = convert(pravidlo);
            typ.getPravidlo().add(pravNode);
        }
        return typ;
    }

    /**
     * Konverze jednotliveho pravidla
     * 
     * @param pravidlo
     *            vysledek pravidla
     * @return Prevedene pravidlo
     */
    private static TPravidlo convert(PravidloKontroly pravidlo) {
        TPravidlo pravNode = objectFactory.createTPravidlo();
        pravNode.setId(pravidlo.getId());
        pravNode.setText(pravidlo.getTextPravidla());
        pravNode.setZdroj(pravidlo.getZdroj());
        if (!pravidlo.getStav()) {
            pravNode.setStav(TVysledekPravidla.CHYBA);
            pravNode.setPopisChyby(pravidlo.getPopisChybyObecny());
            pravNode.setVypisChyby(pravidlo.getVypis_chyby());
            pravNode.setMistoChyby(pravidlo.getMisto_chyby());
        } else {
            pravNode.setStav(TVysledekPravidla.OK);
        }
        return pravNode;
    }

    private static TVysledekKontroly convert(StavKontroly stavKontroly) {
        switch (stavKontroly) {
        case CHYBA:
            return TVysledekKontroly.CHYBA;
        case NESPUSTENA:
            return TVysledekKontroly.NESPUSTENA;
        case OK:
            return TVysledekKontroly.OK;
        }
        throw new IllegalStateException("Failed to convert :" + stavKontroly);
    }

    private static void writeSipInfo(TSip sipNode, SipInfo sipInfo) {
        String metsObjId = sipInfo.getMetsObjId();
        if (StringUtils.isNotEmpty(metsObjId)) {
            sipNode.setSipID(metsObjId);
        }

        String g = sipInfo.getNameZip();
        if (g == null) {
            g = sipInfo.getName();
        }
        sipNode.setNazevSouboru(g);

    }

    public void setProfilValidace(ProfilValidace profilValidace) {
        String nazev = profilValidace.getNazev();
        TPouzitiKontroly pouzitiKontroly = TPouzitiKontroly.fromValue(nazev);
        kontrolaSIP.setPouzitiKontroly(pouzitiKontroly);

    }

    public void save(Path outputPath) throws IOException, JAXBException {
        try (OutputStream os = Files.newOutputStream(outputPath);) {
            save(os);
        }
    }

    public void save(OutputStream os) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(KontrolaSIP.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setSchema(schema);
        marshaller.marshal(kontrolaSIP, os);
    }
}
