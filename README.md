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

Training 1

_API Version_ | Type | File size | File size
--- | --- | --- | ---
---  |   ---   | 7 MB   | 70 MB
_jaxb/v0_ | *JAXB2* | ~2.2 s | ~22.5 s
_jaxb/v1_ | *JAXB with SAX* | ~155 ms | ~1.5 s
_jaxb/v2_ | *JAXB without SAX* | ~100 ms | ~1 s
_jaxb/v3_ | *JAXB CoMPAS sct* | ~2.5 s | ~23 s

Training 2

_API Version_ | Type | File size | File size
--- | --- | --- | ---
---  |   ---   | 7 MB   | 70 MB
_jaxb/v0_ | *JAXB2* | ~3.9 s  | ~42.5 s
_jaxb/v1_ | *JAXB with SAX* | ~600 ms | ~6 s
_jaxb/v2_ | *JAXB without SAX* | TODO | TODO
_jaxb/v3_ | *JAXB CoMPAS sct* | TODO | TODO


2. _jaxb/v4_ : TODO with jaxb jakarta 3.0.1

## Performance Test Related request type 
**Client:** `Firefox` `Edge`

Scenario : add file `~6MB` in file `[7MB, 13,5MB, 26.6MB, 52.8MB, 70MB]`
### Request Type
1. **FormData**

_API Version_ | Type | File size | File size | File size | File size | File size
--- | --- | --- | --- | --- | --- | ---
---  |   ---  |  7 MB  | 13.5 MB | 26.6 MB | 52.8MB | 70 MB
_jaxb/v0_ | *JAXB2* | ~5500 ms (5.5s) |  ~7s |  ~13000ms (13s) |  ~19s |  ~23s
_jaxb/v1_ | *JAXB with SAX* |~500ms - 1500ms | ~600ms - 700ms  | ~1400ms |  TODO |  TODO
_jaxb/v2_ | *JAXB without SAX* | TODO | TODO | TODO | TODO | TODO 
_jaxb/v3_ | *JAXB CoMPAS sct* | TODO | TODO | TODO | TODO | TODO

Sometime in case of file > 50 MB we have this kind of exception
```bash
MultipartException

[http-nio-8088-exec-4] ERROR o.a.c.c.C.[.[.[.[dispatcherServlet] - Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.springframework.web.multipart.MultipartException: Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException] with root cause
java.io.EOFException: null
```
Try this solution (The default is 10MB)

```bash
servlet:
    multipart:
        max-file-size: 100MB
        max-request-size: 100MB
```

2. **XML object**

    Consuming XML in Spring Boot Instead of MultipartFile

_API Version_ | Type | File size | File size | File size | File size | File size
--- | --- | --- | --- | --- | --- | ---
---  |   ---  |  7 MB  | 13.5 MB | 26.6 MB | 52.8MB | 70 MB
_jaxb/v0_ | *JAXB2* | ~2500 ms (2.5s) |  ~4500 ms ~4.5s |  ~10s |  ~18s |  ~22s
_jaxb/v1_ | *JAXB with SAX* |~500ms - 1500ms | TODO  | TODO |  TODO |  TODO
_jaxb/v2_ | *JAXB without SAX* | TODO | TODO | TODO | TODO | TODO
_jaxb/v3_ | *JAXB CoMPAS sct* | TODO | TODO | TODO | TODO | TODO




