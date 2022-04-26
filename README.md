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

_**GoodJAXBUtilGeneric**_ : unmarshalWithSAX ->  unmarshalDefault (javax.xml.bind.*)

_**MarshallerJaxb2Wrapper**_  : Jaxb2Marshaller (org.springframework.oxm.jaxb.Jaxb2Marshaller)

## Links
https://mkyong.com/java/jaxb-hello-world-example/

## Performance Test Report

1. **jaxb 2.3.1**

Type / File size | 7 MB | 70 MB
--- | --- | ---
*JAXB2* (jaxb/v0) | ~2.2 s | ~22.5 s
*JAXB with SAX* (jaxb/v1) | ~300 ms | ~2 s
*JAXB CoMPAS sct* | TODO | TODO


Type / File size | 7 MB    | 70 MB
--- |---------| ---
*JAXB2* (jaxb/v0) | ~3.9 s  | ~42.5 s
*JAXB with SAX* (jaxb/v1) | ~600 ms | ~6 s
*JAXB CoMPAS sct* | TODO    | TODO

2. **jaxb jakarta 3.0.1** (TODO)


## Performance Test Related request Type 
**Client:** `Firefox` `Edge`

**Server:** :
```bash
servlet:
    multipart:
        max-file-size: 50MB
        max-request-size: 50MB
```
Scenario : Add file `~6MB` in file `[7MB, 13,5MB, 26.6MB, 52.8MB, 70MB]`
### Request Type
1. **FormData**

Type / File size | 7 MB  | 13.5 MB | 26.6 MB | 52.8MB | 70 MB
--- |---------| --- | --- | --- | ---
*JAXB2* (jaxb/v0) | ~5500 ms (5.5s) |  ~7s |  ~13000ms (13s) |  ~19s |  ~23s
*JAXB with SAX* (jaxb/v1) |~500ms - 1500ms | ~600ms - 700ms  | ~1400ms |  TODO |  TODO

**Sometime in case of file > 50 MB**
```bash
[http-nio-8088-exec-4] ERROR o.a.c.c.C.[.[.[.[dispatcherServlet] - Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.springframework.web.multipart.MultipartException: Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException] with root cause
java.io.EOFException: null
```

2. **Consuming XML in Spring Boot REST Instead of MultipartFile**

Type / File size | 7 MB  | 13.5 MB | 26.6 MB | 52.8MB | 70 MB
--- |---------| --- | --- | --- | ---
*JAXB2* (jaxb/v0) | ~2500 ms (2.5s) |  ~4500 ms ~4.5s |  ~10s |  ~18s |  ~22s
*JAXB with SAX* (jaxb/v1) |~500ms - 1500ms | TODO  | TODO |  TODO |  TODO




