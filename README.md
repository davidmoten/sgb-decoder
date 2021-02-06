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
