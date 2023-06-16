# <img height="25" src="./images/AILLogoSmall.png" width="40"/> AILegorreta-kit

<a href="https://www.legosoft.com.mx"><img height="150px" src="./images/AILLogoBig.png" alt="AI Legorreta" align="left"/></a>
This repository contains the source code for all libraries (java packages) that are imported from any microservice. 
`ailegorreta-kit` has packages for security, common, events, etc in order to avoid repeating code and also
to simplify maintenance.

The purpose of these java packages are to minimize development time, simplify maintenance for the Ai marketplace by 
LegoSoft Soluciones, S.C. These are generic packages that also can be imported by any Clients system but the
©Copyright it is still owned by LegoSoft Soluciones, S.C.. The Customer can use these packages and copy them
as many times as he(she) likes, inside his(her) Company only.
## What is it?

The `ailegorreta-kit` is a set of generic classes that can be utilized for a different microservices. 
The objective is to reduce code repetition.

## List of packages:

### AILegorreta Kit Commons

Common package to be used by the front or back-end microservice. The subprojects are:

* `ailegorreta-kit-commons-utils`: Java and Kotlin utilities common classes and methods.
* `ailegorreta-kit-commons-event`: DTOs to send and receive kafka messages.
* `ailegorreta-kit-commons-cmis` : General classes to interfaces with Alfresco via CMIS [1.1] standard.
* `ailegorreta-kit-commons-preference` : Classes and methods to support User preferences.

### AILegorreta Client packages

Package for UI microservices. Includes documentation in how to use these Vaadin client libraries:

* `ai-legorreta-kit-client-blockly`: wrapper for blockly.js.
* `ai-legorreta-kit-client-bpm`: Camunda BPM manual tasks are displayed as Vaadin views.
* `ai-legorreta-kit-client-components`: extra Vaadin components.
* `ai-legorreta-kit-client-crud-ui`: Vaadin CRUD ui wrapper.
* `ai.legorreta-kit-client-dataproviers`: Vaadin data providers for Neo4j, MongoDb and other databases. Supports
    paging.
* `ai-legorreta-kit-client-exporter`: Vaadin grid Excel component wrapper exporter.
* `ai-legorreta-kit-client-mobile-security`: To handle PWA security inside an iPhone or Android.
* `ai-legorreta-kit-client-navigation`: Vaadin flow menu navigator.
* `ai-legorreta-kit-client-preferences`: Vaadin flow components to add or delete user preferences.
* `ai-legorreta-kit-client-security`: Common web (ui) security filters.
* `ai-legorreta-kit-client-visjs-network`: Visjs-network wrapper to display visjs graphs in Vaadin.
* `ai-legorreta-kit-client-litelement-ckeditor`: Vaadin wrapper for the CKEditor.js library.

### AILegorreta Kit for data manipulation packages

Spring Data enhancement packages:

* `ai-legorreta-kit-data-jpa`: Utilities for Spring Data JPA.
* `ai-legorreta-kit-data-mongodb`: Utilities for Spring Data MongoDB.
* `ai-legorreta-kit-data-neo4j`: Utilities for Spring Data Neo4j graph database.

### AILegorreta Kit fore back-end microservices

These projects are for bak-end microservices.

* `ai-legorreta-kit-resource-security`: Spring security utilities for back-end microservices.


## History of ailegorreta-kit

### Version 1.X

For this version we use everything for docker desktop environment.

### Version 2.X

The main functionality was kept the same. The main difference is to migrate the Desktop on-premise
platform to a Cloud Native Kubernetes platform.

## Future improvements:

Since this is continuous work the following development activities are in process (June 2023):


| Activity               | Comment                                             |
|------------------------|-----------------------------------------------------|
| `none`                 | None.                                               |


### Contact AI Legorreta

Feel free to reach out to AI Legorreta on [web page](https://legosoft.com.mx).


Version: 2.0
©LegoSoft Soluciones, S.C., 2023
