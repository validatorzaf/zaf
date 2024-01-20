/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.common.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Nacteni XML, vytvoreni DOM a obohaceni o pozici
 * 
 *
 */
public class PositionalXMLReader {
    public final static String LINE_NUMBER_KEY_NAME = "lineNumber";
    public final static String COLUMN_NUMBER = "columnNumber";

    final static SAXParserFactory factory = SAXParserFactory.newInstance();

    Document doc;
    SAXParser parser;

    final Stack<Element> elementStack = new Stack<Element>();
    final StringBuilder textBuffer = new StringBuilder();
    final DefaultHandler handler = new DefaultHandler() {
        private Locator locator;

        @Override
        public void setDocumentLocator(final Locator locator) {
            this.locator = locator; // Save the locator, so that it can be used later for line tracking when traversing nodes.
        }

        @Override
        public void startElement(final String uri, final String localName, final String qName,
                                 final Attributes attributes)
                throws SAXException {
            addTextIfNeeded();
            final Element el = doc.createElement(qName);
            for (int i = 0; i < attributes.getLength(); i++) {
                el.setAttribute(attributes.getQName(i), attributes.getValue(i));
            }
            el.setUserData(LINE_NUMBER_KEY_NAME, this.locator.getLineNumber(), null);
            el.setUserData(COLUMN_NUMBER, this.locator.getColumnNumber(), null);
            elementStack.push(el);
        }

        @Override
        public void endElement(final String uri, final String localName, final String qName) {
            addTextIfNeeded();
            final Element closedEl = elementStack.pop();
            if (elementStack.isEmpty()) { // Is this the root element?
                doc.appendChild(closedEl);
            } else {
                final Element parentEl = elementStack.peek();
                parentEl.appendChild(closedEl);
            }
        }

        @Override
        public void characters(final char ch[], final int start, final int length) throws SAXException {
            textBuffer.append(ch, start, length);
        }

        // Outputs text accumulated under the current node
        private void addTextIfNeeded() {
            if (textBuffer.length() > 0) {
                final Element el = elementStack.peek();
                final Node textNode = doc.createTextNode(textBuffer.toString());
                el.appendChild(textNode);
                textBuffer.delete(0, textBuffer.length());
            }
        }
    };

    public PositionalXMLReader() {

    }

    public Document readXML(final InputStream is) throws IOException, SAXException {
        try {
            parser = factory.newSAXParser();
            final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
            parser.parse(is, handler);
            return doc;
        } catch (final ParserConfigurationException e) {
            throw new RuntimeException("Can't create SAX parser / DOM builder.", e);
        }
    }

    /**
     * Return formatted position of node in XML
     * 
     * @param node
     * @return
     */
    static public String formatPosition(Node node) {
        if (node == null) {
            return null;
        }
        Object lineNumber = node.getUserData(LINE_NUMBER_KEY_NAME);
        if (lineNumber == null) {
            return null;
        }
        Object colNumber = node.getUserData(COLUMN_NUMBER);
        StringBuilder sb = new StringBuilder();
        sb.append("Řádek ").append(lineNumber);
        if (colNumber != null) {
            sb.append(":").append(colNumber);
        }
        sb.append(", element <").append(node.getNodeName()).append(">.");
        return sb.toString();
    }
}
