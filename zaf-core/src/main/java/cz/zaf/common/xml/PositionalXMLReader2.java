/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.common.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang3.StringUtils;
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
public class PositionalXMLReader2 {
    final static SAXParserFactory factory = SAXParserFactory.newInstance();
    final static DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    {
    	// Namespaces are used
    	factory.setNamespaceAware(true);
    	factory.setValidating(false);
    	docBuilderFactory.setNamespaceAware(true);
    	docBuilderFactory.setValidating(false);
    }

    Document doc;
    SAXParser parser;
    final private Map<String, String> nsMapping = new HashMap<>();
    // Explicitly applied NS
    final private Set<String> appliedNs = new HashSet<>();

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
            final Element el = doc.createElementNS(uri, qName);
            addNsIfNeeded(el);
            if(attributes!=null) {
            	var attrCnt = attributes.getLength(); 
				for (int i = 0; i < attrCnt; i++) {
					String attrNs = attributes.getURI(i);
					String attrQName = attributes.getQName(i);					
					String attrValue = attributes.getValue(i);
					el.setAttributeNS(attrNs, attrQName, attrValue);
				}
            }
            el.setUserData(PositionalXMLReader.LINE_NUMBER_KEY_NAME, this.locator.getLineNumber(), null);
            el.setUserData(PositionalXMLReader.COLUMN_NUMBER, this.locator.getColumnNumber(), null);
            if(nsMapping.size()>0) {
            	// copy current namespace mapping
            	el.setUserData(PositionalXMLReader.NS_MAPPING, new HashMap<>(nsMapping), null);
            }
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
        
        @Override
        public void startPrefixMapping (String prefix, String uri) {
        	nsMapping.put(prefix, uri);
        }
        
        @Override
        public void endPrefixMapping(String prefix) {
        	String ns = nsMapping.remove(prefix);
        	appliedNs.remove(prefix);
        	Objects.nonNull(ns);
        }
    };

    public PositionalXMLReader2() {

    }

    public Document readXML(final InputStream is) throws IOException, SAXException {
        try {
            parser = factory.newSAXParser();
            final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
            parser.parse(is, handler);
            return doc;
        } catch (final ParserConfigurationException e) {
            throw new RuntimeException("Can't create SAX parser / DOM builder.", e);
        }
    }

    private void addNsIfNeeded(Element el) {
    	if(appliedNs.size()==nsMapping.size()) {
        	return;
    	}
    	for(var ns: nsMapping.keySet()) {
	    	if(appliedNs.contains(ns)) {
	    		continue;
	    	}
	    	String nsUri = nsMapping.get(ns);
	    	if(StringUtils.isEmpty(ns)) {
	    		el.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", nsUri);
	    	} else {
	    		el.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:"+ns, nsUri);
	    	}
	    	appliedNs.add(ns);
    	}
	}
}
