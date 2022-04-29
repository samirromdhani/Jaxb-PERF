# Jaxb-PERF

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Notes](#notes)
* [Links](#links)
* [Performance Test Report](#performance-test-report)
* [Performance Test Related request type](#performance-test-related-request-type-FormData)

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
JAXB on Java 9, 10, 11 and beyond :
https://jesperdj.com/2018/09/30/jaxb-on-java-9-10-11-and-beyond/

## Performance Test Report

### **jaxb2** (spring)

_jaxb/v0_

### **jaxb 2.3.1** (java)

_jaxb/v1_ , _jaxb/v2_ , _jaxb/v3_  :

* Use default package based javax api : org.lfenergy.compas.scl2007b4.model.SCL

### **jaxb 3.0.0** (java, jakarta)

_jaxb/v4_ , _jaxb/v5_ :

* Use custom package based jakarta api : com.github.jaxblib.xsd.jakarta.model.SCL

    instead of org.lfenergy.compas.scl2007b4.model.SCL
### Test unmarshalling
* convert file to Java objects (SCL)

Training 1

_API Version_ | Type                    | File size | File size
---           | ---                     |    ---    | ---
---           | ---                     |  7 MB   | 70 MB
_jaxb/v0_ | *JAXB2*                     | ~2.2 s    | ~22.5 s
_jaxb/v1_ | *JAXB without SAX*          | ~2.2 s    | ~1s without XSD validation
_jaxb/v2_ | *JAXB with SAX*             | ~2.2 s    | ~1.5s without XSD validation
_jaxb/v3_ | *JAXB CoMPAS sct*           | ~2.5 s    | ~23 s
_jaxb/v4_ | *JAXB Jakarta without SAX*  | ~2.2 s    | ~21 s
_jaxb/v5_ | *JAXB Jakarta with SAX*     | ~2.2 s    | ~23 s


## Performance Test Related request type FormData
**Client:** `Firefox` `Edge`

Scenario : add file `~6MB` in file `[7MB, 13,5MB, 26.6MB, 52.8MB, 70MB]`

1. unmarshall `[7MB, 13,5MB, 26.6MB, 52.8MB, 70MB]`
2. unmarshall `~6MB`
3. marshall result

In case of file > 50 MB we have this kind of exceptions **MultipartException** and **FileSizeLimitExceededException**
```bash
MultipartException

[dispatcherServlet] - Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.springframework.web.multipart.MultipartException: Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException] with root cause
java.io.EOFException: null
```

```bash
FileSizeLimitExceededException

[dispatcherServlet].log - Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.springframework.web.multipart.MaxUploadSizeExceededException: Maximum upload size exceeded; nested exception is java.lang.IllegalStateException: org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException: The field file exceeds its maximum permitted size of 1048576 bytes.] with root cause
 The field file exceeds its maximum permitted size of 1048576 bytes.
```
Try this solution (the default is 10MB)

```bash
servlet:
    multipart:
        max-file-size: 300MB
        max-request-size: 300MB
```
or
```bash
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.ofMegabytes(300));
		factory.setMaxRequestSize(DataSize.ofMegabytes(300));
		return factory.createMultipartConfig();
	}
```
