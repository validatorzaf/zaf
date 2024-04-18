package cz.zaf.sipvalidator.sip;

import javax.xml.bind.JAXBException;

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
