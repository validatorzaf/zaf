package cz.zaf.sipvalidator.sip;

import javax.xml.bind.JAXBException;

/**
 * Protokol writter
 * 
 *
 */
public interface ProtokolWriter {
    
    /**
     * Append result of SIP validation
     * 
     * @param sipInfo SIP info
     * @throws JAXBException 
     */
    void writeVysledek(SipInfo sipInfo) throws JAXBException;
}
