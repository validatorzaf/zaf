/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidui.panels;

import cz.zaf.sipvalidator.sip.VysledekPravidla;

/**
 *
 * @author m000xz006159
 */
public class EventListenerJFmainTableSeSipSoubory_object {
    final VysledekPravidla pravidlo;

    public EventListenerJFmainTableSeSipSoubory_object(final VysledekPravidla pravidlo) {
        this.pravidlo = pravidlo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        if (pravidlo.getStav()) {
            sb.append("OK");
        } else {
            sb.append("NO");
        }
        sb.append(" ").append(pravidlo.getId()).append(". ").append("PRAVIDLO");
        
        return sb.toString();
    }

    public VysledekPravidla getPravidlo() {
        return pravidlo;
    }
}