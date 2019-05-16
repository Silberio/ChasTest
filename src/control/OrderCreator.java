package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import objects.generated.ObjectFactory;
import objects.generated.Shiporder;
import objects.generated.Shiporder.Item;
import objects.generated.Shiporder.Shipto;

/**
 * This class contains methods for creation of a new order. Either thru user
 * input or hardcoded. Unmarshalling of a XML is also provided.
 * 
 * @author silberio_stalone
 *
 */
public class OrderCreator {

	/**
	 * Class Constructor
	 */
	public OrderCreator() {
		
	}
	
	// VARIABLES FOR ORDER CREATION
	private ObjectFactory factory = new ObjectFactory();
	private Shiporder order = factory.createShiporder();
	private Shipto shipto = factory.createShiporderShipto();
	private Item item = factory.createShiporderItem();
	private Scanner sc = new Scanner(System.in);

	// MARSHALLER AND UNMARSHALLER VARIABLES
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
	private JAXBContext context;
	private OutputStream os;

	/**
	 * Runs the order creation interface.
	 * 
	 * First a JAXBContext instance is initialized. This also initializes the
	 * marshaller and the unmarshaller. Then it sets a local OutputStream object
	 * that will be accesed by the marshaller and unmarshaller.
	 * 
	 * Then a menu is initialized that prompts the user for input, then it either
	 * creates an order with <i>user input</i> or with <i>sample input</i> which is
	 * hardcoded in this class.
	 * 
	 * <b>WARNING User input is not validated.</b> It's expected that the user will
	 * be nice until input validators and exception handlers are in place
	 * 
	 * @param os output .xml file to which the order object will be marshalled to
	 */
	public void runOrderCreator(OutputStream outputstream) {
		try {
			createJAXBContext();
			this.os = outputstream;

			orderCreationSelection();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * "Main Menu", prompts user to pick between <i>sample created</i> order or
	 * <i>user input created</i> order
	 */
	private void orderCreationSelection() {
		System.out.println(
				"Create an order (Marshall XML) \n" + "For sample data, enter S\n" + "For User created, enter U\n" + "For unmarshalling an XML, press T");
		switch (sc.nextLine().toUpperCase()) {
		case "S":
			marshallOrder(createOrderWithSampleData());
			break;
		case "U":
			marshallOrder(createOrderByUserInput());
			break;
		case "T":
			selectUnmarshall();
		}
	}

	/*
	 * SAMPLE CREATIONS
	 * 
	 */

	/**
	 * Creates a sample order for the Kaiser of Prussia
	 * 
	 * @return
	 */
	private Shiporder createOrderWithSampleData() {

		shipto.setAddress("Strasse Street 123");
		shipto.setCity("Berlin");
		shipto.setCountry("Prussia");
		shipto.setName("The Kaiser");

		item.setTitle("Kaiser's Horse");
		item.setQuantity(new BigInteger("1"));
		item.setPrice(new BigDecimal("18000"));
		item.setNote("Make sure to feed");
		order.getItem().add(item);

		/*
		 * // This piece here should add a second item to the list // however, I'm
		 * missing how to add these dynamically; say // the user wants to add more than
		 * one item.
		 * 
		 * item.setTitle("Kaiser's Sabre"); item.setQuantity(new BigInteger("1"));
		 * item.setPrice(new BigDecimal("1200")); item.setNote("Blunt sabre");
		 * order.getItem();
		 */

		UUID id = UUID.randomUUID();
		order.setOrderid(id.toString());
		order.setOrderperson("Kaiser Willhelm");
		order.setShipto(shipto);

		return order;
	}

	/**
	 * Create an order by asking for input. Lots of syso's in this one. Only meant
	 * to be used for test purposes.
	 * 
	 * @return a fully instantiated Shiporder object with user input
	 */
	private Shiporder createOrderByUserInput() {

		UUID id = UUID.randomUUID();
		order.setOrderid(id.toString());
		System.out.println("Input order person: ");
		order.setOrderperson(sc.nextLine());

		System.out.println("Enter name of customer: ");
		shipto.setName(sc.nextLine());
		System.out.println("Enter Address Line 1: ");
		shipto.setAddress(sc.nextLine());
		System.out.println("Enter city: ");
		shipto.setCity(sc.nextLine());
		System.out.println("Enter country");
		shipto.setCountry(sc.nextLine());

		order.setShipto(shipto);
		System.out.println("Recipient added. \nEnter order item: ");
		item.setTitle(sc.nextLine());
		System.out.println("Enter quantity: ");
		item.setQuantity(new BigInteger(sc.nextLine()));
		System.out.println("Enter price: ");
		item.setPrice(new BigDecimal(sc.nextLine()));
		System.out.println("Leave a note: ");
		item.setNote(sc.nextLine());

		order.getItem().add(item);

		return order;
	}

	/**
	 * Creates an instance of the JAXB Api and instantiates marshaller and
	 * unmarshaller for XML stuff
	 * 
	 * @throws JAXBException
	 */
	private void createJAXBContext() throws JAXBException {
		context = JAXBContext.newInstance(Shiporder.class);
		marshaller = context.createMarshaller();
		unmarshaller = context.createUnmarshaller();

	}

	/**
	 * Marshalls (creates XML from POJO) Shiporder object and formats the document.
	 * Also prints out the XML in pretty format to console using a StringWriter.
	 * 
	 * @param order <i>The Shiporder object to be marshalled into xml</i>
	 * @param os    <i>The physical .xml file to be written to</i>
	 */
	private void marshallOrder(Shiporder order) {

		try {
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(order, os);

			StringWriter sw = new StringWriter();
			marshaller.marshal(order, sw);

			System.out.println("PRINTING XML...:");
			System.out.println(sw);

		} catch (JAXBException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Used to unmarshall an XML. When calling this method, user will be prompted
	 * for a path the xml to marshall. Currently only been tested with files in the
	 * main repository folder. It has not yet been tested for files elsewhere in the
	 * user system
	 * </hr>
	 * This method is kept public for abstraction.
	 * </hr>
	 * <b>note:</b> As of this 0.2.1 version, if the input file is null, the user will get a
	 * "File not found" message and the program should, <i>in theory</i> return to
	 * the selection menu, but has yet to be tested.
	 */
	public void selectUnmarshall() {
		File file;

		System.out.println("Input absolute file path");
		String input = sc.nextLine();
		try {
			file = new File(input);
			unmarshallXML(file);
		} catch (NullPointerException e) {
			System.out.println("File not found or path corrupt");
			orderCreationSelection();
		}

	}

	/**
	 * Unmarshal an XML file and then prints some of the details to the console
	 * 
	 * @param file .xml file to be unmarshalled by JAXB
	 */
	private void unmarshallXML(File file) {
		Shiporder order;

		try {

			order = (Shiporder) unmarshaller.unmarshal(file);

			System.out.println("Unmarshall succesful...");
			System.out.println(
					"Order by: " + order.getOrderperson() + "\nof: " + order.getItem().iterator().next().getQuantity()
							+ " " + order.getItem().iterator().next().getTitle());

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
