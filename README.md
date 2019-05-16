# ChasTest

### What the app does

* Marshalls XML from either sample data, which is hardcoded in *OrderCreator* class, or through user input.
* Given an external XML file, it'll unmarshall it into a POJO and will display some info from it on the console

This was created using JAXB

### How to run

The program is compiled into a runnable jar using Maven and can be run using the java -jar command.

### Details

The classes *Shipmentorder* and *ObjectFactory* were created using xjc, from a given xsd. Business logic resides within *OrderCreator* class, where marshalling and unmarshalling takes place

#### Marshalling an xml
 
This can be done two ways, either through sample method done by hardcoded data where 
