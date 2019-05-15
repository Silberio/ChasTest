package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		// Marshalls order. Includes a method to create a mock order with hardcoded values
		OrderCreator oc = OrderCreator.getInstance();
		

		// xml file where our sample marshalled xml will be written to
		OutputStream os = new FileOutputStream("order.xml");
		
		oc.runOrderCreator(os);

		// order created from XML will be unmarshalled
		// then print a representation (OrderPerson, item qt. and name)
		// Here, a sample xml is provided
		/*
		 * IDEA FOR UNMARSHALL: 
		 * 	T - user input to unmarshall 
		 * If user wants to unmarshall xml, then it will prompt for the file URL and set an internal file
		 */
		// oc.unmarshallXML(new File("ford.xml"));

	}



}
