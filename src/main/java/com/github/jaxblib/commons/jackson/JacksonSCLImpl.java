package com.github.jaxblib.commons.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.github.jaxblib.commons.JAXBUtil;
import com.github.jaxblib.commons.utils.ResourceUtils;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

/**
 * @author samirromdhani
 */
public class JacksonSCLImpl implements JAXBUtil<SCL> {

    private static XmlMapper xmlMapper;
    static{
        try {
            JacksonXmlModule module = new JacksonXmlModule();
            module.setDefaultUseWrapper(false);
            XmlMapper xmlMapper = new XmlMapper(module);
            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // JAXB annotations
            xmlMapper.registerModule(new JaxbAnnotationModule());
            //xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
           // xmlMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
           // xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            //xmlMapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
            //xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            //xmlMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
            // Indentation
            //xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            //xmlMapper.setPropertyNamingStrategy(new AtSymbolPropertyNamingStrategy());
            //xmlMapper.enable(MapperFeature.ALLOW_EXPLICIT_PROPERTY_RENAMING);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings("serial")
    static class AtSymbolPropertyNamingStrategy extends PropertyNamingStrategy {

        private String fieldName(AnnotatedMember member, String defaultName) {
            XmlAttribute xmlAttributeAnnotation = member.getAllAnnotations().get(XmlAttribute.class);

            if (xmlAttributeAnnotation != null) {
                return "@" + xmlAttributeAnnotation.name();
            }

            return defaultName;
        }

        @Override
        public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
            return fieldName(method, defaultName);
        }

        @Override
        public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
            return fieldName(field, defaultName);
        }

        @Override
        public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
            return fieldName(method, defaultName);
        }
    }

    @Override
    public byte[] marshal(SCL scl) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        xmlMapper.writeValue(byteArrayOutputStream, scl);
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(baos)){
            return baos.toByteArray();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public SCL unmarshal(String xml) throws JsonProcessingException {
        return xmlMapper.readValue(xml, SCL.class);
    }

    @Override
    public SCL unmarshal(InputStream xml) throws IOException {
        String inputStreamToString = inputStreamToString(xml);
        SCL itemInfo = xmlMapper.readValue(inputStreamToString, SCL.class);
        System.out.println(itemInfo);
        return xmlMapper.readValue(inputStreamToString, SCL.class);
    }

    @Override
    public SCL unmarshal(byte[] bytes) throws IOException {
        return xmlMapper.readValue(bytes, SCL.class);
    }

    @Override
    public SCL unmarshalWithSAX(InputSource inputSource) throws ParserConfigurationException, SAXException, JAXBException, jakarta.xml.bind.JAXBException, IOException {
        return null ;//xmlMapper.readValue(inputSource, SCL.class);
    }

    @Override
    public SCL unmarshalWithSAX(InputStream inputStream) throws JAXBException, ParserConfigurationException, SAXException, jakarta.xml.bind.JAXBException, IOException {
        return null;
    }

    private static String inputStreamToString(InputStream is) throws IOException {
        BufferedReader br;
        StringBuilder sb = new StringBuilder();

        String line;
        br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    @Override
    public String getName() {
        return "Jackson";
    }
}
