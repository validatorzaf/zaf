package cz.zaf.sipvalidator.nsesss2017.pravidla06;

import cz.zaf.common.validation.Rule;

/**
 * Obsahové pravidlo
 * 
 * Obsahové pravidlo může být používáno jen v rámci jednoho vlákna. 
 * V průběhu kontroly má uložen svůj vnitřní stav.
 * 
 */
public interface ObsahovePravidlo extends Rule<K06KontrolaContext> {
}
