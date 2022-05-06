package com.github.jaxblib.conf;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ConfigPropertiesTest {

    @Autowired
    private ConfigProperties configProperties;

    @Test
    public void whenFactoryProvidedThenYamlPropertiesInjected() {
        Assertions.assertEquals("classpath:/xsd/SCL2007B4/SCL.xsd", configProperties.getScl());
    }
}