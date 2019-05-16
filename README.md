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
 
This can be done two ways, either through sample method done by hardcoded data residing in the *createOrderWithSampleData()* function and by user input through the *createOrderByUserInput()* method, which will prompt the user for input on every line. There are currently no exception handlers or validators for the input. 

#### Unmarshalling an xml

Unmarshalling (porting data from XML to a POJO) is done primarily with the *selectUnmarshall()* method, which will prompt the user for the file location. Location must be given with an absolute filepath. File path will then be stored locally and then given to a *new File()* object , which in turn will be passed on to *unmarshallXML* for unmarshalling. A few lines from the data will be printed on the console.

#### points that could be improved

Currently, all the logic is held in the *OrderCreator* class, including UI methods. This technically doesn't not follow the SOLID principles, where OrderCreator should **only** have to deal with creating the xml methods. However, every function is decoupled. One could, theoretically, only use the marshaller/unmarshall methods by themselves in any other UI.
