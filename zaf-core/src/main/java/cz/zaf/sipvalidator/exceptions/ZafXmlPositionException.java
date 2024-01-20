package cz.zaf.sipvalidator.exceptions;

import org.w3c.dom.Node;

import cz.zaf.common.xml.PositionalXMLReader;
import cz.zaf.sipvalidator.exceptions.codes.ErrorCode;

public class ZafXmlPositionException extends ZafException {

    public ZafXmlPositionException(ErrorCode errorCode, String detailChyby, Node node) {
        super(errorCode, detailChyby, PositionalXMLReader.formatPosition(node));
    }

}
