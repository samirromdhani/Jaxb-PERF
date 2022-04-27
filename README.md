# Jaxb-PERF

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Notes](#notes)
* [Links](#links)
* [Performance Test Report](#performance-test-report)
* [Performance Test Related request type](#performance-test-related-request-type)

## General info
**Evaluating jaxb performance (jaxb2) by comparing it with others technologies**

## Technologies
### Java API
* JAXB: default
* JAXB: with SAX
### CoMPAS SCT Commons
* JAXB: MarshallerWrapper
### Spring Feature
* JAXB2: MarshallerJaxb2Wrapper (current) 
### Others
* JAXB-STREAM 
* JIBX 

## Notes

_**MarshallerWrapper**_  : CoMPAS SCT Commons Implementation -> unmarshalDefault (javax.xml.bind.*)

_**GoodJAXBUtilGeneric**_ : unmarshalWithSAX else unmarshalDefault (javax.xml.bind.*)

_**GoodJAXBUtilWithoutSAX**_ : unmarshalDefault (javax.xml.bind.*)

_**MarshallerJaxb2Wrapper**_  : Jaxb2Marshaller (org.springframework.oxm.jaxb.Jaxb2Marshaller)

## Links
https://mkyong.com/java/jaxb-hello-world-example/

## Performance Test Report
1. **jaxb 2.3.1** 

**unmarshall** convert file to Java objects (SCL)

Training 1

_API Version_ | Type             | File size | File size
---           | ---              |    ---    | ---
---           | ---              |  7 MB   | 70 MB
_jaxb/v0_ | *JAXB2*              | ~2.2 s    | ~22.5 s
_jaxb/v1_ | *JAXB with SAX*      | ~155 ms   | ~1.5 s
_jaxb/v2_ | *JAXB without SAX*   | ~100 ms   | ~1 s
_jaxb/v3_ | *JAXB CoMPAS sct*    | ~2.5 s    | ~23 s

Training 2

_API Version_ | Type             | File size | File size
---           | ---              |    ---    | ---
---           | ---              |  7 MB     | 70 MB
_jaxb/v0_ | *JAXB2*              | ~3.9 s    | ~42.5 s
_jaxb/v1_ | *JAXB with SAX*      | ~600 ms   | ~6 s
_jaxb/v2_ | *JAXB without SAX*   | TODO      | TODO
_jaxb/v3_ | *JAXB CoMPAS sct*    | TODO      | TODO

2. _jaxb/v4_ : TODO with jaxb jakarta 3.0.1

## Performance Test Related request type 
**Client:** `Firefox` `Edge`

Scenario : add file `~6MB` in file `[7MB, 13,5MB, 26.6MB, 52.8MB, 70MB]`

### Request Type
1. **FormData**

1. unmarshall `[7MB, 13,5MB, 26.6MB, 52.8MB, 70MB]`
2. unmarshall `~6MB`
3. marshall result

_API Version_ | Type | File size | File size | File size | File size | File size
--- | --- | --- | --- | --- | --- | ---
---  |   ---                    |  7 MB  |~13.5 MB | ~26.6 MB | 52.8MB | 70 MB
_jaxb/v0_ | *JAXB2*             | ~9.3s  |  ~14s   | ~24s     | ~43s   | ~51s
_jaxb/v1_ | *JAXB with SAX*     | ~500ms | ~700ms  | ~1.2s    | ~2.2s  | ~3s
_jaxb/v2_ | *JAXB without SAX*  | ~400ms | ~600ms  | ~900ms   | ~1.6s  | ~2s 
_jaxb/v3_ | *JAXB CoMPAS sct*   | ~9.3s  | ~14s    | ~23s     | ~41s   | ~51s

Sometime in case of file > 50 MB we have this kind of exceptions **MultipartException** and **FileSizeLimitExceededException**
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

2. **XML object** 

Consuming XML in Spring Boot Instead of MultipartFile

1. unmarshall `[7MB, 13,5MB, 26.6MB, 52.8MB, 70MB]`
2. ~~unmarshall~~ ~6MB
3. marshall result


_API Version_ | Type | File size | File size | File size | File size | File size
--- | --- | --- | --- | --- | --- | ---
---  |   ---                    |  7 MB  |~13.5 MB | ~26.6 MB | 52.8MB | 70 MB
_jaxb/v0_ | *JAXB2*             |  ~7s   |  ~13s   |  ------  |  ~40s  |  ~50s
_jaxb/v1_ | *JAXB with SAX*     | ~450ms | ~600ms  |  ------  |  ~2s   | ~2.5s
_jaxb/v2_ | *JAXB without SAX*  | ~400ms | ~500ms  |  ------  |  ~1.6s | ~2s
_jaxb/v3_ | *JAXB CoMPAS sct*   |   ~7s  |  ~12s   |  ------  |  ~40s  | ~50s




