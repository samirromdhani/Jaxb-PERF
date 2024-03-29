# Jaxb-PERF

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Notes](#notes)
* [Links](#links)
* [Performance Test Report](#performance-test-report)

## General info
**Evaluating jaxb performance (jaxb2) by comparing it with others technologies**

## Technologies
#### Java API
* JAXB: (jaxb-api 2.3.1)
* JAXB: (jakarta.xml.bind-api 3.0.0)
#### CoMPAS SCT Commons
* JAXB: (jaxb-impl 2.3.6) 
#### Spring (current)
* JAXB2: OXM (Object XML Mapping) 
#### TODO
* Jackson
* Jaxb-V2
#### Others
* JAXB-STREAM 
* JIBX 

## Notes

_**MarshallerJaxb2Wrapper**_  : Jaxb2Marshaller (org.springframework.oxm.jaxb.Jaxb2Marshaller)

_**JavaSCLJaxbImpl**_ : (javax.xml.bind.*)

_**MarshallerWrapper**_  : CoMPAS sct (javax.xml.bind.*)

_**JakartaSCLJaxbImpl**_  : (jakarta.xml.bind.*)
<!--
```bash
JavaSCLJaxbImpl implements JAXBUtil<SCL>
```
```bash
JakartaSCLJaxbImpl implements JAXBUtil<SCL>
```
```bash
public interface JAXBUtil<T extends Object> {
    byte[] marshal(T element);
    T unmarshal(String xml);
    T unmarshal(InputStream xml) throws IOException;
    T unmarshal(byte[] bytes) throws IOException;

    // SAX Utils
    String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
    T unmarshalWithSAX(InputSource inputSource) throws ParserConfigurationException, SAXException, JAXBException, jakarta.xml.bind.JAXBException;
    T unmarshalWithSAX(InputStream inputStream) throws JAXBException, ParserConfigurationException, SAXException, jakarta.xml.bind.JAXBException;
    
    String getName();
}
```
-->

## Links
##### JAXB on Java 9, 10, 11 and beyond :
https://jesperdj.com/2018/09/30/jaxb-on-java-9-10-11-and-beyond/
##### JAXB V2 : Partial Unmarshalling
https://github.com/javaee/jaxb-v2/tree/master/jaxb-ri/samples/src/main/samples/partial-unmarshalling

##### Others
https://stackoverflow.com/questions/65805250/hows-the-performance-of-jackson-compared-to-jaxb-in-creating-xml

https://github.com/beanit/iec61850bean/blob/master/src/main/java/com/beanit/iec61850bean/SclParser.java

## Performance Test Report

### **jaxb2** (spring)

_jaxb/v0_

### **jaxb 2.3.1** (java)

_jaxb/v1_ , _jaxb/v2_ , _jaxb/v3_  :

* Using default package based javax api : org.lfenergy.compas.scl2007b4.model.SCL

### **jaxb 3.0.0** (java, jakarta)

_jaxb/v4_ , _jaxb/v5_ :

* Using custom package based jakarta api : com.github.jaxblib.xsd.jakarta.model.SCL

    instead of org.lfenergy.compas.scl2007b4.model.SCL
### Test unmarshalling
* convert file to Java objects (SCL)

### #1


_API Version_ | Type                    | File size  | File size  | File size
---           | ---                     |    ---     | ---        | ---
---           | ---                     |  7 MB      | 70 MB      | 328 MB
_jaxb/v0_ | *JAXB2*                     | 2056 ms    | 18.7 s     | 1m 35s
_jaxb/v1_ | *JAXB*                      | 1843 ms    | 17.6 s     | 1m 35s
_jaxb/v2_ | *JAXB with SAX*             | 1936 ms    | 19 s       | 1m 48s
_jaxb/v3_ | *JAXB CoMPAS sct*           | 1857 ms    | 17.6 s     | 1m 45s
_jaxb/v4_ | *JAXB Jakarta*              | 1749 ms    | 17.5 s     | 1m 43s
_jaxb/v5_ | *JAXB Jakarta with SAX*     | 1930 ms    | 20.5 s     | 1m 44s

### #2

_API Version_ | Type                    | File size  | File size  | File size
---           | ---                     |    ---     | ---        | ---
---           | ---                     |  7 MB      | 70 MB      | 328 MB
_jaxb/v0_ | *JAXB2*                     | 2079 ms    | 18.3 s     | 1m 43s
_jaxb/v1_ | *JAXB*                      | 1963 ms   | 19 s       | 1m 35s
_jaxb/v2_ | *JAXB with SAX*             | 1916 ms    | 20.2 s     | 1m 50s
_jaxb/v3_ | *JAXB CoMPAS sct*           | 1810 ms    | 18.8 s     | 1m 48s
_jaxb/v4_ | *JAXB Jakarta*  | 1727 ms   | 19.4 s     | 1m 41s
_jaxb/v5_ | *JAXB Jakarta with SAX*     | 1917 ms    | 19.2 s     | 1m 47s 


### Test marshalling TODO
* convert Java objects (SCL) to string or bytes

### #1


_API Version_ | Type                    | File size  | File size  | File size
---           | ---                     |    ---     | ---        | ---
---           | ---                     |  7 MB      | 70 MB      | 328 MB
_jaxb/v0_ | *JAXB2*                     |   ----     |   ----     |   ----
_jaxb/v1_ | *JAXB*                      |   ----     |   ----     |   ----
_jaxb/v2_ | *JAXB with SAX*             |   ----     |   ----     |   ----
_jaxb/v3_ | *JAXB CoMPAS sct*           |   ----     |   ----     |   ----
_jaxb/v4_ | *JAXB Jakarta*              |   ----     |   ----     |   ----
_jaxb/v5_ | *JAXB Jakarta with SAX*     |   ----     |   ----     |   ----

###### For memory usage, take a look at tests, github action report or gatling report

