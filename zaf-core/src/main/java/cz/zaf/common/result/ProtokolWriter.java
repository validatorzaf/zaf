package cz.zaf.common.result;

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
     * @param validationResult
     *            Výsledek validace
     * @throws JAXBException
     *             Výjimka v případě chyby serializace XML
     */
    void writeVysledek(ValidationResult validationResult) throws JAXBException;
}
