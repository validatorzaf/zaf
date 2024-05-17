package cz.zaf.common.exceptions;

import org.w3c.dom.Node;

import cz.zaf.common.exceptions.codes.ErrorCode;
import cz.zaf.common.xml.PositionalXMLReader;

public class ZafXmlPositionException extends ZafException {

    public ZafXmlPositionException(ErrorCode errorCode, String detailChyby, Node node) {
        super(errorCode, detailChyby, PositionalXMLReader.formatPosition(node));
    }

}
