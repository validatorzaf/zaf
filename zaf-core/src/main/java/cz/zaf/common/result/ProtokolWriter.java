package cz.zaf.common.result;

import cz.zaf.sipvalidator.sip.SipInfo;
import jakarta.xml.bind.JAXBException;

/**
 * Protokol writter
 * 
 *
 */
public interface ProtokolWriter extends AutoCloseable {
    
    /**
     * Append result of SIP validation
     * 
     * @param sipInfo
     *            SIP info
     * @throws JAXBException
     *             Výjimka v případě chyby serializace XML
     */
    void writeVysledek(SipInfo sipInfo) throws JAXBException;
}
