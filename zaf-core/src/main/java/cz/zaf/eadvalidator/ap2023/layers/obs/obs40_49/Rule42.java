package cz.zaf.eadvalidator.ap2023.layers.obs.obs40_49;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Accruals;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Didnote;
import cz.zaf.schema.ead3.Scopecontent;
import cz.zaf.schema.ead3.Unittitle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Rule42 extends EadRule {

    static final public String CODE = "obs42";
    static final public String RULE_TEXT = "Elementy <unittitle>, <scopecontent>, <accruals> nebo <didnote> se mohou vyskytovat vícekrát v rámci rodičovského elementu, pokud se liší hodnotou atributu \"lang\", existencí atributu \"audience\" nebo hodnotou atributu \"audience\".";
    static final public String RULE_ERROR = "Dvojice elementů  <unittitle>, <scopecontent>, <relation>, <accruals> nebo <didnote> na stejné úrovni má špatně uvedené nebo vyplněné atributy \"audience\".";
    static final public String RULE_SOURCE = "Část 3.6 profilu EAD3 MV ČR";

    static List<ComparedObect> siblingsList;

    public Rule42() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();

        Did didA = archDesc.getDid();
        siblingsList = new ArrayList<>();
        getFromDid(didA);
        compare();

        List<Object> listA = archDesc.getAccessrestrictOrAccrualsOrAcqinfo();
        siblingsList = new ArrayList<>();
        getFromMain(listA);
        compare();

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            Did didC = c.getDid();
            siblingsList = new ArrayList<>();
            getFromDid(didC);
            compare();

            List<Object> listC = c.getMDescBase();
            siblingsList = new ArrayList<>();
            getFromMain(listC);
            compare();
        });
    }

    private void getFromDid(Did did) {
        List<Object> mDid = did.getMDid();
        for (Object object : mDid) {
            if (object instanceof Unittitle unittitle) {
                String audience = unittitle.getAudience();
                String lang = unittitle.getLang();
                siblingsList.add(new ComparedObect("unittitle", audience, lang, unittitle));
            }
            if (object instanceof Didnote didnote) {
                String audience = didnote.getAudience();
                String lang = didnote.getLang();
                siblingsList.add(new ComparedObect("didnote", audience, lang, didnote));
            }
        }
    }

    private void getFromMain(List<Object> list) {
        for (Object object : list) {
            if (object instanceof Scopecontent scopecontent) {
                String audience = scopecontent.getAudience();
                String lang = scopecontent.getLang();
                siblingsList.add(new ComparedObect("scopecontent", audience, lang, scopecontent));
            }
            if (object instanceof Accruals accruals) {
                String audience = accruals.getAudience();
                String lang = accruals.getLang();
                siblingsList.add(new ComparedObect("accruals", audience, lang, accruals));
            }
        }
    }

    private void compare() {
        Set<List<String>> seen = new HashSet<>();

        for (ComparedObect co : siblingsList) {
            List<String> key = List.of(co.getName(), co.getAudience(), co.getLang());
            if (!seen.add(key)) {
                throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nepovolený výskyt elementu.", ctx.formatEadPosition(co.getObject()));
            }
        }
    }

    private class ComparedObect {

        String name, audience, lang;
        Object object;

        public ComparedObect(String name, String audience, String lang, Object object) {
            if (name == null) {
                name = "$null";
            }
            this.name = name;

            if (audience == null) {
                audience = "$null";
            }
            this.audience = audience;

            if (lang == null) {
                lang = "$null";
            }
            this.lang = lang;

            if (object == null) {
                object = "$null";
            }
            this.object = object;
        }

        public String getAudience() {
            return audience;
        }

        public String getLang() {
            return lang;
        }

        public String getName() {
            return name;
        }

        public Object getObject() {
            return object;
        }
    }

}
