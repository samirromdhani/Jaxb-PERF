@XmlSchema(
        namespace = "http://www.iec.ch/61850/2003/SCL",
        xmlns = {
                @XmlNs(namespaceURI = "http://www.rte-france.com", prefix = "rte"),
                @XmlNs(namespaceURI = "https://www.lfenergy.org/compas/extension/v1", prefix = "compas"),
                @XmlNs(namespaceURI = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi"),
                @XmlNs(namespaceURI = "http://www.iec.ch/61850/2003/SCL", prefix = ""),
                @XmlNs(namespaceURI = "http://www.w3.org/2001/XMLSchema", prefix = "xs")
        },
        elementFormDefault = XmlNsForm.QUALIFIED)

package com.github.samirromdhani.jaxblib.commons;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;