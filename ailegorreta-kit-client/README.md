# <img height="25" src="./images/AILLogoSmall.png" width="40"/> AILegorreta-kit-client

<a href="https://www.legosoft.com.mx"><img height="150px" src="./images/ClientIcon.png" alt="AI Legorreta" align="left"/></a>
These repositories contains the source code for all libraries (java packages) that are imported from UI microservices.
`ailegorreta-kit-client` has packages for any front-end microservice in order to avoid repeating code and
also to simplify maintenance.

The purpose of these java packages are to minimize development time, simplify maintenance for the Ai marketplace by
LegoSoft Soluciones, S.C. These are generic packages that also can be imported by any Clients system but the
©Copyright it is still owned by LegoSoft Soluciones, S.C.. The Customer can use these packages and copy them
as many times as he(she) likes, inside his(her) Company only.
## What is it?

The `ailegorreta-kit-clients` is a set of generic classes that can be utilized for a different front-end microservices.
All these microservice uses Vaadin flow or Vaadin Hilla. In a future they will use Vaadin Hilla React.

## List of packages:

### AILegorreta Kit Client

Packages to be used by the front microservice. The subprojects are:

* `ailegorreta-kit-client-components`: Vaadin flow extra components (see next section of additional components).
* `ailegorreta-kit-client-blockly`: Package for Google Blockly wrapper to be used in Vaadin Flow.
* `ailegorreta-kit-client-bpm` : Dinamic generation for Camunda BPM User Tasks.
* `ailegorreta-kit-client-crudui` : Classes to generate a dynamic form for CRUD operations. It is based in the Vaadin CRUD addon. It
includes a demonstration application called `ailegorreta-kit-client-crudui-demo`.
* `ailegorreta-kit-client-dataproviders`:  Vaadin flow data providers that support paging.
* `ailegorreta-kit-client-exporter`: Package to export a Vaadin Grid content to an excel file. note: it needs the newer version.
* `ailegorreta-kit-client-security`: Package to handle all Spring Authorization Server client-security: login pages, logout, etc.
* `ailegorreta-kit-client-mobile-security`: Package to handle all Spring Authorization Server client-security but for mobile PWA apps.
* `ailegorreta-kit-client-navigation`: Package to generate al menu, header & footer for Vaadin flow applications.
* `ailegorreta-kit-client-preference`: Additional UI components for User Preference administration.
* `org-vaadin-addon-visjs-network`: Wrapper for javascript library visjs-network. note: the copyright is not Legosoft´s copyright.
* `vaadin-litelement-ckeditor`: Wrapper for javascript CKEditor editor. note: the copyright is not LegoSoft's copyright

For more documentation see de documentation directory.

## List of additional UI components

`SimpleDialogBox` -> Support for different format buttons.

## User preference

UI components for user preference administration. All acomponents are now for Vaadin Flow only.

This library depends on `ailegorreta-kit-client-components` so it has to be compiled after.

## Security

Libary classes to create Spring Security client that works with Spring Authorization Server that is implemented in `auth-service` 
microservice.

## BPM

Library to generate the Vaadin flow interface for User Tasks according to Camunda Json format. It generates view for:

- Camunda forms
- Custom forms, and optional it can be included inside a Camunda form.
- Custom task form

This package follows the Camunda standar form defined in :

https://docs.camunda.io/docs/apis-clients/tasklist-api/objects/form/

It is a render that where developed in previous versions for OSGi jBPM but with a much simpler scheme.


## History of ailegorreta-kit-clients

### Version 1.X

For this version we use everything for docker desktop environment.

### Version 2.X

The main functionality was kept the same. The main difference is to migrate the Desktop on-premise
platform to a Cloud Native Kubernetes platform.

## Future improvements:

Since this is continuous work the following development activities are in process (June 2023):


| Activity               | Comment                                             |
|------------------------|-----------------------------------------------------|
| `documentation`        | Extends all components documentation.               |


### Contact AI Legorreta

Feel free to reach out to AI Legorreta on [web page](https://legosoft.com.mx).


Version: 2.0
©LegoSoft Soluciones, S.C., 2023
