# Jaxb-PERF

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Notes](#notes)
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

## Performance Test Report

Type / File size | 7 MB | 70 MB
--- | --- | ---
*JAXB2* (/v0) | ~2.2 s | ~22.5 s
*JAXB with SAX* (/v1) | ~300 ms | ~2 s
*JAXB CoMPAS sct* | TODO | TODO


```bash
jakarta.xml.bind.UnmarshalException: élément inattendu (URI : "http://www.iec.ch/61850/2003/SCL", local : "SCL"). Les éléments attendus sont (none)
```
