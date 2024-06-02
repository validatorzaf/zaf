package cz.zaf.eadvalidator.ap2023.layers.enc.enc00_09;

import java.io.InputStream;
import java.nio.file.Files;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.io.input.BOMInputStream;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;

public class Rule01 extends EadRule {
    static final public String CODE = "kod1";

    public Rule01() {
        super(CODE,
                "Znakovou sadou souboru je Unicode/UCS v kódování UTF-8 bez BOM (Byte Order Mark).",
                "Znaková sada datového balíčku SIP není Unicode/UCS v kódování UTF-8 bez BOM (Byte order mark).",
                "Část 1.1.5 profilu EAD3 MV ČR");
    }

    @Override
    protected void evalImpl() {
        String deklarace = nactiKodovaniVDeklaraci();
    }

    private String nactiKodovaniVDeklaraci() throws ZafException {
        try (InputStream is = Files.newInputStream(ctx.getLoader().getFilePath())) {
            // lib commons-io-2.4
            BOMInputStream bomIn = BOMInputStream.builder().setInputStream(is).get();
            if (bomIn.hasBOM()) {
                String chybaKodovani = "Soubor obsahuje chybně BOM prefix.";
                throw new ZafException(BaseCode.CHYBA, chybaKodovani);
            }

            final XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(bomIn);

            return xmlStreamReader.getCharacterEncodingScheme();
        } catch (Exception e) {
            ZafException ze;
            if (e instanceof ZafException) {
                // pass ZafException
                ze = (ZafException) e;
            } else {
                String chybaKodovani = "Chyba při detekci kódování: " + e.toString();
                ze = new ZafException(BaseCode.CHYBA, chybaKodovani, e);
            }
            throw ze;
        }

    }

}
