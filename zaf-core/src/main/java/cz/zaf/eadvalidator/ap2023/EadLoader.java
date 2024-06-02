package cz.zaf.eadvalidator.ap2023;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import cz.zaf.common.result.ValidationLayerResult;
import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.validation.ValidationInput;
import cz.zaf.common.xml.PositionalXMLReader;

public class EadLoader implements Closeable, ValidationInput, ValidationResult {

    private final Path filePath;
    private Exception parserError;

    private Document document;

    final protected List<ValidationLayerResult> validationResults = new ArrayList<>();

    public EadLoader(final Path filePath) {
        Objects.requireNonNull(filePath);

        this.filePath = filePath;
    }

    public Path getFilePath() {
        return filePath;
    }

    public Exception getParserError() {
        return parserError;
    }

    public Document getDocument() {
        return document;
    }

    /**
     * Load EAD from file
     */
    public void load() {
        try (InputStream is = Files.newInputStream(filePath)) {
            PositionalXMLReader xmlReader = new PositionalXMLReader();
            document = xmlReader.readXML(is);

            // vlastni nacteni dat
            readDocument();
        } catch (SAXException e) {
            parserError = e;
        } catch (IOException e) {
            parserError = e;
        }
    }

    private void readDocument() {
        Node element = this.document.getFirstChild();
        while (element != null) {
            element = element.getNextSibling();
        }
    }

    @Override
    public void close() throws IOException {
        document = null;
        parserError = null;
    }

    public ValidationResult getResult() {
        return this;
    }

    @Override
    public String getValidatedObjectId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getValidatedObjectName() {
        return filePath.getFileName().toString();
    }

    @Override
    public List<ValidationLayerResult> getValidationLayerResults() {
        return validationResults;
    }

}
