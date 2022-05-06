package com.github.jaxblib.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

@Configuration
@ConfigurationProperties(prefix = "xsd")
@PropertySource(value = "classpath:config.yml", factory = CustomPropertySourceFactory.class)
public class ConfigProperties {

    @Value("classpath:/SCL2007B4/SCL.xsd")
    private Resource resource;

    private String scl;

    public String getScl() {
        return scl;
    }

    public void setScl(String scl) {
        this.scl = scl;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
