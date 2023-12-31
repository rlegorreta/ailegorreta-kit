# <img height="25" src="./images/AILLogoSmall.png" width="40"/> AILegorreta-kit-data-mongo

<a href="https://www.legosoft.com.mx"><img height="150px" src="./images/SpringDataMongoDB.png" alt="AI Legorreta" align="left"/></a>
This repository contains the source code for all libraries (java packages) that are imported from any microservice that
needs some data persistence access to a Mongo database (imperative)
`ailegorreta-kit-data-mongo` has packages for access for MongoDB database in
order to avoid repeating code and also to simplify maintenance.

The purpose of these java packages are to minimize development time, simplify maintenance for the Ai marketplace by
LegoSoft Soluciones, S.C. These are generic packages that also can be imported by any Clients system but the
©Copyright it is still owned by LegoSoft Soluciones, S.C.. The Customer can use these packages and copy them
as many times as he(she) likes, inside his(her) Company only.
## What is it?

The `ailegorreta-kit-data-mongo` are a set of generic classes that can be utilized for a different microservices.
The objective is to reduce code repetition.

- Classes needed for `Querydsl`framework.
- Classes needed for `GraphQL`framework.

note: These classes are for Mongo DB Spring Data specification, that is for the imperative
stack. For the reactive stack (i.e., `R2DBC`) see the `ailegorreta-kit-data-reactive-mongo`.


## History of ailegorreta-kit-data-mongo

### Version 1.X

For this version we use everything for docker desktop environment.

### Version 2.X

The main functionality was kept the same. The main difference is to migrate the Desktop on-premise
platform to a Cloud Native Kubernetes platform.

## Future improvements:

Since this is continuous work the following development activities are in process (June 2023):


| Activity | Comment                                                      |
|----------|--------------------------------------------------------------|
| `kotlin` | Upgrade to gradle & Kotlin. Need more `graphql` custom types |


### Contact AI Legorreta

Feel free to reach out to AI Legorreta on [web page](https://legosoft.com.mx).


Version: 2.0
©LegoSoft Soluciones, S.C., 2023
