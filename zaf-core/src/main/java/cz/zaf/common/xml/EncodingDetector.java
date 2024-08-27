package cz.zaf.common.xml;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.lang.StringUtils;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;

public class EncodingDetector {
	
	public static class Result {
		
		boolean bom = false;
		private String schemaEncoding;
		private String detectedEncoding;

		public boolean hasBom() {
			return bom;
		}

		public void setBOM(final boolean bom) {
			this.bom = bom;
		}

		public void setSchemaEncoding(final String encoding) {
			this.schemaEncoding = encoding;
		}
		
		public String getSchemaEncoding() {
			return schemaEncoding;
		}

		public void setDetectedEncoding(final String detectedEncoding) {
			this.detectedEncoding = detectedEncoding;
			
		}

		public String getDetectedEncoding() {
			return detectedEncoding;
		}
	};

	public static Result detect(Path inputFile, boolean allowBom) throws IOException, XMLStreamException, FactoryConfigurationError {
		Result result = new Result();
		
		try (InputStream is = Files.newInputStream(inputFile)) {

			// lib commons-io-2.4
			BOMInputStream bomIn = BOMInputStream.builder().setInputStream(is).get();
			result.setBOM(bomIn.hasBOM());
			if(!allowBom&&bomIn.hasBOM()) {
				return result;
			}
			
			// detection if not failed
			final XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(bomIn);
			result.setSchemaEncoding(xmlStreamReader.getCharacterEncodingScheme());
		}
		
		// detect encoding from text
        // lib ucu4j-56.jar
        CharsetDetector det = new CharsetDetector();
        try (InputStream is = Files.newInputStream(inputFile)) {

            BufferedInputStream buf = new BufferedInputStream(is);
            CharsetDetector setText = det.setText(buf);

            // lib ucu4j-56.jar
            CharsetMatch matchedCharsets[] = setText.detectAll();
            
            if(matchedCharsets!=null&&matchedCharsets.length>0) {
            	var detected = matchedCharsets[0];
            	// overeni, zda je mozne najit deklarovane schema
            	if(StringUtils.isNotBlank(result.getSchemaEncoding())) {
            		var declared = result.getSchemaEncoding().toLowerCase();
            		for(var d: matchedCharsets) {
            			if(d.getName().toLowerCase().equals(declared)) {
            				detected = d; 
            				break;
            			}
            		}
            	}

            	// kvůli upřesnění
            	result.setDetectedEncoding(detected.getName().toLowerCase());
            }
        }
		
        return result;		
	}

}
