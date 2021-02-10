# sgb-decoder
Java library that decodes Second Generation Beacon (SGB) detection messages and is compliant with C/S T.018 Rev. 6 specification.

**Features**

* Decodes Second Generation Beacon (SGB) detection messages (250bit) to Java objects
* Extracts Beacon 23 Hex Id from an SGB detection message
* Provides a JSON form of the SGB detection message
* Provides a JSON Schema document for the JSON form
* 100% unit test coverage (enforced)
* *spotbugs* maximum effort static analysis enforced 
* *pmd* static analysis enforced 

Status: *pre-alpha* (in development, not production ready)

## How to build
```bash
mvn clean install
```
The library jar will be in `target` directory.

## Usage

Add this dependency to your pom.xml:

```xml
<dependency>
  <groupId>au.gov.amsa</groupId>
  <artifactId>sgb-decoder</artifactId>
  <version>VERSION_HERE</version>
</dependency>
```

The most likely form that you will encounter a beacon detection message is in the hex encoded Cospas-Sarsat Ground Segment Representation. Here's an example:

```java
import sgb.decoder.Detection;

String hex = "0039823D32618658622811F0000000000003FFF004030680258";
Detection d = Detection.fromHexGroundSegmentRepresentation(hex);
``` 
At that point you can browse the object representation of the message or dump a JSON text representation to stdout.

```java
System.out.println(d.toJson());
```
Output is [here](src/docs/detection.json).

The JSON Schema for the above is [here](src/main/json-schema/schema.json).

## Compliance Kit
With the arrival of second generation Beacons on the market sometime from July 2021, many of the National Rescue Coordination Centres (RCCs) throughout the world will want to be able to decode the hex detection messages into a human readable form. This might be simply a web page that performs the decode but could equally be a programming library that developers might use to customize their use of the hex detection message.

Producing a programming library that decodes an SGB detection message is a non-trivial task that has one important risk: **correctness**. As a developer how do I confirm that my code correctly decodes all variations of SGB detection messages? Writing unit tests still has the risk that my *interpretation* of the specification might not match the intent of the specification.

A suggestion for the creators of the SGB encoding specification is that they share a Compliance Kit which is a list of beacon detection messages in hex form together with the corresponding decoded human readable version of the detection message in some *canonical form*. If this were the case then no matter what language a decoder was written in full test coverage of that decoder would be guaranteed by consuming the (comprehensive) Compliance Kit test data. An example of one test in the Compliance Kit would be the pair below:

Ground Segment Representation Hex
```
0039823D32618658622811F0000000000003FFF004030680258
```

[detection.json](src/docs/detection.json)

A consumer of the Compliance Kit would consume decode the given hex and generate the JSON canonical form string and compare it to the given JSON file (using JSON equivalence rathen exact string equality).

Clearly one test in the kit does not cut it. There are many variations on field values, some are derived from special binary codes, some field values are optional.

Given that a service implementation of the decoder would probably serialize the decoded structure into JSON or XML, it probably makes sense to use one of those text formats to hold the canonical decoded form so that the implementer can reuse the canonical form work.

If JSON or XML was used for the canonical form then it should also be described by a schema document (JSON Schema or XSD). This library provides a JSON form and a JSON Schema document and the author suggests that **JSON format is used for the *canonical form***. 

Note that the canonical form in JSON would not have to be exactly matched as a string during a test for compliance. We don't care about whitespace outside of expressions (new lines, indents) and even field order so the match would be based on JSON equality. Every major programming language has support for this sort of equality match (either in an open-source library or in the base platform).
