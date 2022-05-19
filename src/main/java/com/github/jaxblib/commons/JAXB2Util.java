package com.github.jaxblib.commons;
import java.io.InputStream;


/**
 * @author samirromdhani
 */
public interface JAXB2Util<T extends Object> {
    String marshal(T element, boolean validate) throws Exception;
    T unmarshal(InputStream xml, boolean validate) throws Exception;
    T unmarshal(byte[] bytes, boolean validate) throws Exception;
}
