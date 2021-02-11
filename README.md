# sgb-decoder
Java library that decodes [Cospas-Sarsat](https://en.wikipedia.org/wiki/International_Cospas-Sarsat_Programme) Second Generation Beacon (SGB) detection messages and is compliant with the C/S T.018 Rev. 6 [specification](https://vnmcc.vishipel.vn/images/uploads/attach/T018-MAY-2020.pdf).

**Features**

* Decodes Second Generation Beacon (SGB) detection messages (202 bits) to Java objects
* Extracts Beacon 23 Hex Id from an SGB detection message
* Provides a JSON form of the SGB detection message
* Provides a JSON Schema document for the JSON form
* 100% unit test coverage (enforced)
* *spotbugs* maximum effort static analysis enforced 
* *pmd* static analysis enforced 

Status: *pre-alpha* (in development, **NOT** production ready yet)

## How to build
```bash
mvn clean install
```
The library jar will be in `target` directory.

## Getting started

Add this dependency to your pom.xml:

```xml
<dependency>
  <groupId>au.gov.amsa</groupId>
  <artifactId>sgb-decoder</artifactId>
  <version>VERSION_HERE</version>
</dependency>
```
Note that this library is not stand-alone and has a number of runtime dependencies. Use Maven (or Gradle etc)! 

## Usage
The most likely form that consumers will encounter a beacon detection message is in the hex encoded Cospas-Sarsat Ground Segment Representation (202 bits hex-encoded to 51 chars using left padded zero bits) Here's an example:

```java
import sgb.decoder.Detection;

String hex = "0039823D32618658622811F0000000000003FFF004030680258";
Detection d = Detection.fromHexGroundSegmentRepresentation(hex);
``` 
At that point you can browse the object representation of the message or dump a JSON text representation to stdout.

```java
System.out.println(d.toJson());
```
Output is [here](src/test/resources/compliance-kit/detection-specification-example.json).

The JSON Schema for the above is [here](src/main/resources/detection-schema.json).

You can also decode the raw bits (as a bit string) using `Detection.fromBitString("1010000..")`.

Note that a unit [test](src/test/java/au/gov/amsa/sgb/decoder/internal/json/JsonSchemaTest.java) ensures that the abovementioned sample json complies with the JSON Schema.

## BCH Error Correction
A beacon transmits the 202 bit SGB detection message followed by a 48 bit BCH error correction code. You can calculate the code expected from the 202 bit SGB detection message like this:

```java
Bits bch = detection.calculateBchErrorCorrectionCode();
System.out.println(bch.toBitString());
```
Application of error corrections (when required) is presumed to happen upstream of consumers so this library does not support it.

## Performance
Quick and dirty performance testing (without JMH) indicates that the the library can decode about 140,000 beacon detection messages a second. If you need faster performance than this raise an issue.

## Semantic Versioning
This project follows [Semantic Versioning 2.0](https://semver.org/).

## Compliance Kit
With the arrival of second generation Beacons on the market sometime from July 2021, many of the National Rescue Coordination Centres (RCCs) throughout the world will want to be able to decode SGB hex detection messages into a human readable form. This might be simply a web page that performs the decode but could equally be a programming library that developers might use to customize their use of the hex detection message.

Producing a programming library that decodes an SGB detection message is a non-trivial task that has one important risk: **correctness**. As a developer how do I confirm that my code correctly decodes all variations of SGB detection messages? Writing unit tests still has the risk that my *interpretation* of the specification might not match the intent of the specification.

A suggestion for the creators of the SGB encoding specification is that they help to build a Compliance Kit which is a list of beacon detection messages in hex form together with the corresponding decoded human readable version of the detection message in some *canonical form*. If this were the case then no matter what language a decoder was written in full test coverage of that decoder would be guaranteed by consuming the (comprehensive) Compliance Kit test data. 

An example of a test kit is [here](src/test/resources/compliance-kit) and comprises:
* [`tests.csv`](src/test/resources/compliance-kit/tests.csv) file with columns *TITLE*, *HEX*, *JSON*
* json files

A consumer of the Compliance Kit would decode the given hex and generate the JSON canonical form string and compare it to the given JSON file (using JSON equivalence rathen exact string equality).

Clearly one test in the kit does not cut it. There are many variations on field values, some are derived from special binary codes, some field values are optional.

Given that a service implementation of the decoder would probably serialize the decoded structure into JSON or XML, it probably makes sense to use one of those text formats to hold the canonical decoded form so that the implementer can reuse the canonical form work.

If JSON or XML was used for the canonical form then it should also be described by a schema document (JSON Schema or XSD). This library provides a JSON form and a JSON Schema document and the author suggests that **JSON format is used for the *canonical form***. 

Note that the canonical form in JSON would not have to be exactly matched as a string during a test for compliance. We don't care about whitespace outside of expressions (new lines, indents) and even field order so the match would be based on JSON equality. Every major programming language has support for this sort of equality match (either in an open-source library or in the base platform).

## TODO
* will consumers need to apply BCH error code correction (which will correct up to 6 bit errors in the first 202 bits of the 250 bit SGB detection message) or is it normally done upstream?
* create a set of test messages for the Compliance Kit
* discuss Compliance Kit with the specification authors
* report error in example in specification to authors
* how to handle invalid message bit sequences (might only affect one field). How to represent this in canonical form?
