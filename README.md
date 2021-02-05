# sgb-decoder
Java library that does the following:

* Decodes Second Generation Beacon (SGB) detection messages (250bit) to xml/json
* Extracts Beacon 23 Hex Id from an SGB detection message
* Decodes Beacon 23 Hex Id to xml/json

Status: *pre-alpha* (in development, not production ready)

## How to build
```bash
mvn clean install
```
The library jar will be in `target` directory.

## Usage

The most likely form that you will encounter a beacon detection message is in the hex encoded Cospas-Sarsat Ground Segment Representation. Here's an example:

```java
Detection d = Detection.fromHexGroundSegmentRepresentation("0039823D32618658622811F0000000000003FFF004030680258");
``` 
At that point you can browse the object representation of the message or dump a simple text representation to stdout.
