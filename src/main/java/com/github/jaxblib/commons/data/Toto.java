package com.github.jaxblib.commons.data;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Toto {
    @XmlAttribute
    private int id;
    @XmlElement
    private String field;
}
