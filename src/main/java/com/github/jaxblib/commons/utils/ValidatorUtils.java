package com.github.jaxblib.commons.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Service
public class ValidatorUtils {

    private List<SchemaConfig> schemaConfigs = new ArrayList<>();

    public ValidatorUtils() {
        SchemaConfig schemaConfig1 = new SchemaConfig(
                "target/xsd/SCL2007B4/SCL.xsd",
                "http://www.iec.ch/61850/2003/SCL",
                "org.lfenergy.compas.scl2007b4.model");
        schemaConfigs.add(schemaConfig1);
    }
    private SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    private String schema1 = "<xs:import namespace=\"" + "http://www.iec.ch/61850/2003/SCL"
            + "\" schemaLocation=\""
            + "target/xsd/SCL2007B4/SCL.xsd" + "\"/>\n";
    private String combinedXsdSchema =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                    + "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" "
                    + "elementFormDefault=\"qualified\">\n"
                    + schema1
                    + "</xs:schema>";

    public Schema getSchema() throws SAXException {
        return factory.newSchema(
                new StreamSource(new StringReader(combinedXsdSchema), "topSchema")
        );
    }

    private URL getResource(String filePath) throws IOException {
        final String clsPathIndicator = "classpath:";
        if(filePath.startsWith(clsPathIndicator)){
            String classPathRes = filePath.substring(clsPathIndicator.length());
            return IOUtils.resourceToURL("/" + classPathRes);
        }
        Path path = Paths.get(filePath).toAbsolutePath();
        if(!Files.exists(path)) {
            throw new IOException(path + ": No such file or directory");
        }
        return path.toUri().toURL();
    }

    private class SchemaConfig {
        private final String xsdPath;
        private final String namespace;
        private final String contextPath;

        public SchemaConfig(String xsdPath, String namespace, String contextPath) {
            // Convert the XSD Path to a URL, that will be used in the import statement.
            // If the XSD Path is not correct loading will fail.
            this.xsdPath = xsdPath;
            this.namespace = namespace;
            this.contextPath = contextPath;
        }

        public String getXsdPath() {
                return xsdPath;
        }

        public String getNamespace() {
            return namespace;
        }
    }
}
