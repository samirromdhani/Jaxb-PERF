package com.github.jaxblib.commons.xsd;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author samirromdhani
 */
@Getter
@Setter
@NoArgsConstructor
@Configuration
@Primary
@Qualifier("xsdFileProperties")
@ConfigurationProperties("compas.scl.schema")
public class XSDFileProperties {

    @Autowired
    private ResourceLoader resourceLoader ;

    private Map<String, String> paths;
    List<Resource> resources = new ArrayList<>();

    @PostConstruct
    public void init() {
        for (Map.Entry<String, String> path : paths.entrySet()) {
            String filePath = path.getValue();
            resources.add(resourceLoader.getResource(filePath));
        }
    }
}
