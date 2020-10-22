package cz.zaf.sipvalidator.pdfa;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xeustechnologies.jcl.JarClassLoader;

public class VeraValidatorProxy {

    static Logger log = LoggerFactory.getLogger(VeraValidatorProxy.class);

    private ClassLoader veraClsLoader;

    private Class<?> clsFoundries;

    private Class<?> clsVeraPDFAFlavour;

    private Class<?> clsVeraPDFFoundry;

    private Class<?> clsPDFAParser;

    private Class<?> clsPDFAValidator;

    private Class<?> clsValidationResult;

    public VeraValidatorProxy(final ClassLoader clsLoader) throws ClassNotFoundException {
        this.veraClsLoader = clsLoader;

        // Init classes
        this.clsFoundries = veraClsLoader.loadClass("org.verapdf.pdfa.Foundries");
        this.clsVeraPDFAFlavour = veraClsLoader.loadClass("org.verapdf.pdfa.flavours.PDFAFlavour");
        this.clsVeraPDFFoundry = veraClsLoader.loadClass("org.verapdf.pdfa.VeraPDFFoundry");
        this.clsPDFAParser = veraClsLoader.loadClass("org.verapdf.pdfa.PDFAParser");
        this.clsPDFAValidator = veraClsLoader.loadClass("org.verapdf.pdfa.PDFAValidator");
        this.clsValidationResult = veraClsLoader.loadClass("org.verapdf.pdfa.results.ValidationResult");

    }

    static String jars[] = {
            "/verapdf/rhino.jar",
            "/verapdf/javax-activation.jar",
            "/verapdf/javax-activation-api.jar",
            "/verapdf/stax-utils.jar",
            "/verapdf/jaxb-api.jar",
            "/verapdf/jaxb-impl.jar",
            "/verapdf/jaxb-core.jar",
            "/verapdf/validation-model.jar",
            "/verapdf/core.jar",
            "/verapdf/feature-reporting.jar",
            "/verapdf/parser.jar",
            "/verapdf/metadata-fixer.jar",
            "/verapdf/verapdf-xmp-core.jar"
    };

    static public VeraValidatorProxy init() throws MalformedURLException,
            ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        /*URL veraAppUrl = VeraValidatorProxy.class.getResource("/verapdf/greenfield-apps.jar");
        if (veraAppUrl == null) {
            log.error("Failed to load verapdf from resources");
            throw new RuntimeException("Failed to load verapdf from resources");
        }*/

        JarClassLoader clsLoader = new JarClassLoader();
        // load jars
        for (String jar : jars) {
            loadJar(clsLoader, jar);
        }

        /*
        URLClassLoader clsLoader = URLClassLoader.newInstance(new URL[] {
                veraAppUrl
        });
        */
        Class<?> cls = clsLoader.loadClass("org.verapdf.pdfa.VeraGreenfieldFoundryProvider");
        Method initMethod = cls.getMethod("initialise");
        initMethod.invoke(null);

        return new VeraValidatorProxy(clsLoader);
    }

    private static void loadJar(JarClassLoader clsLoader, String jarPath) {
        URL jarUrl = VeraValidatorProxy.class.getResource(jarPath);
        if (jarUrl == null) {
            log.error("Failed to load " + jarPath + " from resources");
            throw new RuntimeException("Failed to load " + jarPath + " from resources");
        }
        clsLoader.add(jarUrl);
    }

    public boolean validate(FileInputStream fis) throws Exception {

        ClassLoader currentThreadClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(veraClsLoader);

        try {
        Method defaultInstanceMethod = clsFoundries.getMethod("defaultInstance");
        Object objVeraPdfFoundry = defaultInstanceMethod.invoke(null);

        Method createParserMethod = clsVeraPDFFoundry.getMethod("createParser", InputStream.class);
        Method createValidatorMethod = clsVeraPDFFoundry.getMethod("createValidator", clsVeraPDFAFlavour,
                                                                   boolean.class);
        Method getFlavourMethod = clsPDFAParser.getMethod("getFlavour");
        Method validateMethod = clsPDFAValidator.getMethod("validate", clsPDFAParser);
        Method isCompliantMethod = clsValidationResult.getMethod("isCompliant");

        try (Closeable objParser = (Closeable) createParserMethod.invoke(objVeraPdfFoundry, fis);) {

            // PDFAFlavour flavour = parser.getFlavour()
            Object flavour = getFlavourMethod.invoke(objParser);

            // PDFAValidator validator = veraPdfFoundry.createValidator(flavour, false);
            Object validator = createValidatorMethod.invoke(objVeraPdfFoundry, flavour, false);

            // ValidationResult result = validator.validate(parser);
            Object result = validateMethod.invoke(validator, objParser);

            // return result.isCompliant();
            Object value = isCompliantMethod.invoke(result);

            Boolean b = (Boolean) value;
            return b.booleanValue();
        }
    } finally {
        Thread.currentThread().setContextClassLoader(currentThreadClassLoader);
    }

        //Method createValidatorMethod = clsVeraPDFFoundry.getMethod("createValidator");

        /*
        try (PDFAParser parser = veraPdfFoundry.createParser(fis)) {
            PDFAValidator validator = veraPdfFoundry.createValidator(parser.getFlavour(), false);
            return validator.validate(parser);
        }
        */
    }

}
