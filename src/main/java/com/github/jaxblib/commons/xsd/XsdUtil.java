package com.github.jaxblib.commons.xsd;

import com.github.jaxblib.commons.jaxb.JaxbContextHolder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class XsdUtil<T> {
    private static final Map<Class, String> SCHEMA_MAP = new ConcurrentHashMap<>();
    
    public String getXsd(Class<T> c){
        if(SCHEMA_MAP.containsKey(c))return SCHEMA_MAP.get(c);
        
        JAXBContext jaxbContext = JaxbContextHolder.getJAXBContext(c);
        try {
            MemorySchemaOutputResolver resolver = new MemorySchemaOutputResolver();
            jaxbContext.generateSchema(resolver);
            String schema = resolver.getXsd();
            SCHEMA_MAP.put(c, schema);
            return schema;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    class MemorySchemaOutputResolver extends SchemaOutputResolver {
        private final ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        @Override
        public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
            StreamResult streamResult = new StreamResult(out);
            streamResult.setSystemId(EMPTY);
            return streamResult;  
        }
        
        public String getXsd(){
            byte[] bytes = out.toByteArray();
            out.reset();
            
            return new String(bytes);
        }
        
        private static final String EMPTY = "";
    }
    
}