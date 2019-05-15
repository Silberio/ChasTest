package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		// xml file where our sample marshalled xml will be written to
		OutputStream os = new FileOutputStream("kaiser.xml");
		
		// Marshalls order. Includes a method to create a mock order with hardcoded values
		OrderCreator oc = OrderCreator.getInstance();
		

		// mock order will be created, marshalled and printed to console
		oc.marshallOrder(oc.createOrderByUserInput(), os);
		
		// order created from XML will be unmarshalled
		// then print a representation (OrderPerson, item qt. and name)
		// Here, a sample xml is provided
		oc.unmarshallXML(new File("ford.xml"));

	}



}
