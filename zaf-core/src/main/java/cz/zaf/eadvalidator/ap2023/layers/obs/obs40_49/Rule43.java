package cz.zaf.eadvalidator.ap2023.layers.obs.obs40_49;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Control;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Langmaterial;
import cz.zaf.schema.ead3.Language;
import cz.zaf.schema.ead3.Languagedeclaration;
import cz.zaf.schema.ead3.Scopecontent;
import cz.zaf.schema.ead3.Unittitle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Rule43 extends EadRule {

    static final public String CODE = "obs43";
    static final public String RULE_TEXT = "Každý element <ead:language> má atribut \"langcode\". Hodnota tohoto atributu stejně jako hodnota atributu \"lang\" u elementů <ead:unittitle> a <ead:scopecontent> odpovídá rozšířené podobě tří písmenného ISO kódu zapsaného malými písmeny. Hodnota elementu <ead:language> odpovídá hodnotě podle číselníku atributu \"langcode\" tohoto elementu.";
    static final public String RULE_ERROR = "Element <ead:language> nemá atribut \"langcode\" a/nebo hodnota tohoto atributu nebo atributu \"lang\" u elementů <ead:unittitle> nebo <ead:scopecontent> neodpovídá  rozšířené podobě tří písmenného ISO kódu zapsaného malými písmeny a/nebo hodnota elementu <ead:language> neodpovídá podle číselníku hodnotě atributu \"langcode\" tohoto elementu.";
    static final public String RULE_SOURCE = "Část 3.7 a 6.14 profilu EAD3 MV ČR";
    private Map<String, String> languageMap;

    public Rule43() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        languageMap = getLanguageMap();
        Archdesc archDesc = ctx.getEad().getArchdesc();
        Did didA = archDesc.getDid();
        validateUnittitleAndLangMaterial(didA);
        List<Object> listA = archDesc.getAccessrestrictOrAccrualsOrAcqinfo();
        validateScopecontent(listA);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            Did didC = c.getDid();
            validateUnittitleAndLangMaterial(didC);
            List<Object> listC = c.getMDescBase();
            validateScopecontent(listC);
        });

        Control control = ctx.getEad().getControl();
        validateLanguage(control);
    }

    private void validateUnittitleAndLangMaterial(Did did) {
        List<Object> list = did.getMDid();
        for (Object object : list) {
            if (object instanceof Unittitle unittitle) {
                String lang = unittitle.getLang();
                if (lang != null) {
                    validateLang(lang, object);
                }
            }
            if (object instanceof Langmaterial langmaterial) {
                List<Object> languageOrLanguageset = langmaterial.getLanguageOrLanguageset();
                for (Object objLang : languageOrLanguageset) {
                    if (objLang instanceof Language language) {
                        validateLanguage(language);
                    }
                }
            }
        }
    }

    private void validateScopecontent(List<Object> list) {
        for (Object object : list) {
            if (object instanceof Scopecontent scopecontent) {
                String lang = scopecontent.getLang();
                if (lang != null) {
                    validateLang(lang, object);
                }
            }
        }
    }

    private void validateLanguage(Control control) {
        List<Languagedeclaration> languagedeclarationList = control.getLanguagedeclaration();
        for (Languagedeclaration languagedeclaration : languagedeclarationList) {
            Language language = languagedeclaration.getLanguage();
            validateLanguage(language);
        }
    }

    private void validateLang(String lang, Object object) {
        if (StringUtils.isEmpty(lang)) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Chybí hodnota atributu lang.", ctx.formatEadPosition(object));
        }
        String languageName = languageMap.get(lang);
        if (languageName == null) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Chybná hodnota atributu lang: " + lang + ".", ctx.formatEadPosition(object));
        }
    }

    private void validateLanguage(Language language) {
        String langcode = language.getLangcode();

        if (StringUtils.isEmpty(langcode)) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Chybí hodnota atributu langcode.", ctx.formatEadPosition(language));
        }
        String languageName = languageMap.get(langcode);
        if (languageName == null) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Chybná hodnota atributu langcode: " + langcode + ".", ctx.formatEadPosition(language));
        }
        String languageContent = language.getContent();
        if (!StringUtils.equals(languageContent, languageName)) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybná hodnota elementu language: " + languageContent + ".", ctx.formatEadPosition(language));
        }
    }

    private Map<String, String> getLanguageMap() {
        languageMap = new HashMap<>();
        String url = "/jazykyzp.csv";
        String spliter = ",";
        InputStream inputStr = getClass().getResourceAsStream(url);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStr, "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(spliter);
                String languageName = values[0];
                String languageShort = values[1];
                if (languageShort.startsWith("LNG_")) {
                    String modify = languageShort.replaceFirst("LNG_", "");
                    languageShort = modify;
                }
                languageMap.put(languageShort, languageName);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EadRule.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EadRule.class.getName()).log(Level.SEVERE, null, ex);
        }

        return languageMap;
    }

}
