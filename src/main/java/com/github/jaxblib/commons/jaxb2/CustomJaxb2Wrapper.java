package com.github.jaxblib.commons.jaxb2;

import com.github.jaxblib.commons.JAXB2Util;
import com.github.jaxblib.commons.xsd.XSDFileProperties;
import org.apache.commons.io.input.BOMInputStream;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author samirromdhani
 */
@Component
public class CustomJaxb2Wrapper implements JAXB2Util<SCL> {

    @Autowired
    private ResourceLoader resourceLoader ;

    @Autowired
    private XSDFileProperties xsdFileProperties;

    @Autowired
    @Qualifier("customJaxb2Marshaller")
    private Jaxb2Marshaller marshaller;

    @Override
    public String marshal(SCL obj, boolean validate) throws Exception {
        if(validate) validate();
        StringWriter sw = new StringWriter();
        Result result = new StreamResult(sw);
        marshaller.marshal(obj, result);
        return sw.toString();
    }

    @Override
    public SCL unmarshal(InputStream xml, boolean validate) throws Exception {
        Reader reader = new InputStreamReader(new BOMInputStream(xml), "UTF-8");
        if(validate) validate();
        return (SCL)marshaller.unmarshal(new StreamSource(reader));
    }


    @Override
    public SCL unmarshal(byte[] bytes, boolean validate) throws Exception {
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        return unmarshal(input, validate);
    }

    private void validate() throws Exception {
        List<Resource> resources = new ArrayList<>();
        for (Map.Entry<String, String> path : xsdFileProperties.getPaths().entrySet()) {
            String filePath = path.getValue();
            resources.add(resourceLoader.getResource(filePath));
        }
        marshaller.setSchemas(resources.toArray(Resource[]::new));
        marshaller.afterPropertiesSet();
    }

}
