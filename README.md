# sgb-decoder
Java library that decodes Second Generation Beacon (SGB) detection messages and is compliant with C/S T.018 Rev. 6 specification.

**Features**

* Decodes Second Generation Beacon (SGB) detection messages (250bit) to Java objects
* Extracts Beacon 23 Hex Id from an SGB detection message
* Provides a plain text view of resultant Java objects
* Provides a JSON form of the SGB detection message (TODO)
* 100% unit test coverage

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
At that point you can browse the object representation of the message or dump a simple (but comprehensive) text representation to stdout.

```java
System.out.println(d);
```
Output:
```
Detection
  tac = 230
  serial number = 573
  country code = 201
  has at least one enabled homing signal = true
  has enabled RLS = false
  is test protocol message = false
  encoded GNSS position = 
    lat = 48.79315185546875
    long = 69.00875854492188
  vessel ID = 
  beacon type = ELT_NOT_DT
  rotating field = 
      rotating field type = Objective Requirements
      elapsed time since activation (hours) = 1
      time since last encoded location (minutes) = 6
      altitude of encoded location (metres) = 432
      dilution precision HDOP = 0
      dilution precision DOP = >1 and <= 2
      activation method = MANUAL_ACTIVATION_BY_USER
      remaining battery capacity percent = >75 and <= 100
      GNSS status = LOCATION_3D
  beacon 23 hex ID = 9934039823d000000000000
  beacon 15 hex ID = 9934039823d0000
```

## Compliance Kit
A suggestion for the creators of the SGB encoding specification is that they share a Compliance Kit which is a list of beacon detection messages in hex form together with the corresponding decoded human readable version of the detection message in some *canonical form*. An example of the canonical form is the plain text version of the detection above. Might equally be JSON, XML or something else. If this were the case then no matter what language a decoder was written in full test coverage of that decoder would be guaranteed by consuming the (comprehensive) Compliance Kit test data. An example of one item in the Compliance Kit would be a text file containing:

```
0039823D32618658622811F0000000000003FFF004030680258
Detection
  tac = 230
  serial number = 573
  country code = 201
  has at least one enabled homing signal = true
  has enabled RLS = false
  is test protocol message = false
  encoded GNSS position = 
    lat = 48.79315185546875
    long = 69.00875854492188
  vessel ID = 
  beacon type = ELT_NOT_DT
  rotating field = 
      rotating field type = Objective Requirements
      elapsed time since activation (hours) = 1
      time since last encoded location (minutes) = 6
      altitude of encoded location (metres) = 432
      dilution precision HDOP = 0
      dilution precision DOP = >1 and <= 2
      activation method = MANUAL_ACTIVATION_BY_USER
      remaining battery capacity percent = >75 and <= 100
      GNSS status = LOCATION_3D
  beacon 23 hex ID = 9934039823d000000000000
  beacon 15 hex ID = 9934039823d0000
```
A consumer of the Compliance Kit would consume the first line of that file, decode it, generate the canonical form string and compare it to the rest of the file.

Given that a service implementation of the decoder would probably serialize the decoded structure into JSON or XML, it probably makes sense to use one of those text formats to hold the canonical decoded form so that the implementer can reuse the canonical form work.

If JSON or XML was used for the canonical format then it should also be described by a schema document (JSON Schema or XSD).

