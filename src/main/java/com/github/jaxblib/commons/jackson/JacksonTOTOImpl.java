package com.github.jaxblib.commons.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.jaxblib.commons.JAXBUtil;
import com.github.jaxblib.commons.data.Toto;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

/**
 * @author samirromdhani
 */
public class JacksonTOTOImpl implements JAXBUtil<Toto> {

    private static XmlMapper xmlMapper;
    static{
        try {
            xmlMapper = new XmlMapper();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public byte[] marshal(Toto toto) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        xmlMapper.writeValue(byteArrayOutputStream, toto);
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(baos)){
            return baos.toByteArray();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Toto unmarshal(String xml) throws JsonProcessingException {
        return xmlMapper.readValue(xml, Toto.class);
    }

    @Override
    public Toto unmarshal(InputStream xml) throws IOException {
        String inputStreamToString = inputStreamToString(xml);
        return xmlMapper.readValue(inputStreamToString, Toto.class);
    }

    @Override
    public Toto unmarshal(byte[] bytes) throws IOException {
        return xmlMapper.readValue(bytes, Toto.class);
    }

    @Override
    public Toto unmarshalWithSAX(InputSource inputSource) throws ParserConfigurationException, SAXException, JAXBException, jakarta.xml.bind.JAXBException, IOException {
        return null ;//xmlMapper.readValue(inputSource, Toto.class);
    }

    @Override
    public Toto unmarshalWithSAX(InputStream inputStream) throws JAXBException, ParserConfigurationException, SAXException, jakarta.xml.bind.JAXBException, IOException {
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
