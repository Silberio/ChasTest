package control;

import java.io.File;
import java.io.OutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
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
 * This class contains methods for creation of a new order. Either thru user input or hardcoded.
 * Unmarshalling of a XML is also provided. 
 * @author silberio_stalone
 *
 */
public class OrderCreator {
	
	private static OrderCreator instance = null;
	
	public static OrderCreator getInstance() {
		if(instance==null) {
			instance = new OrderCreator();
		} return instance;
	}
	
	
	/**
	 * Marshalls (creates XML from POJO) Shiporder object and formats the document. 
	 * Also prints out the XML in pretty format to console using a StringWriter.
	 * 
	 * @param order <i>The Shiporder object to be marshalled into xml</i>
	 * @param os <i>The physical .xml file to be written to</i>
	 */
	public void marshallOrder(Shiporder order, OutputStream os) {
		
		try {
			JAXBContext context = JAXBContext.newInstance(Shiporder.class);
			Marshaller marshall = context.createMarshaller();
			marshall.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshall.marshal(order, os);
			
			StringWriter sw = new StringWriter();
			marshall.marshal(order, sw);
			
			System.out.println("PRINTING XML...:");
			System.out.println(sw);
			
		} catch (JAXBException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * Unmarshalls (does the XML to POJO thing) a document
	 */
	public void unmarshallXML(File file) {
		Shiporder order;
		
		try {
			JAXBContext context = JAXBContext.newInstance(Shiporder.class);
			Unmarshaller unmarshall = context.createUnmarshaller();
			
			order = (Shiporder) unmarshall.unmarshal(file);

			
			System.out.println("run succesful");
			System.out.println(
			"Order by: " +
			order.getOrderperson() + "\nof: " + 
			order.getItem().iterator().next().getQuantity() + " "+ 
			order.getItem().iterator().next().getTitle());
			
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * CREATE ORDERS
	 */
	
	private ObjectFactory factory = new ObjectFactory();
	
	private Shiporder order = factory.createShiporder();
	private Shipto shipto = factory.createShiporderShipto();
	private Item item = factory.createShiporderItem();
	private Scanner sc = new Scanner(System.in);
	
/**
 * Creates a sample order for the Kaiser of Prussia
 * @return
 */
	public Shiporder sampleOrder() {
				
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
	//	This piece here should add a second item to the list
	//	however, I'm missing how to add these dynamically; say
	//	the user wants to add more than one  item.
		
		item.setTitle("Kaiser's Sabre");
		item.setQuantity(new BigInteger("1"));
		item.setPrice(new BigDecimal("1200"));
		item.setNote("Blunt sabre");
		order.getItem();
	*/
		
		UUID id = UUID.randomUUID();
		order.setOrderid(id.toString());
		order.setOrderperson("Kaiser Willhelm");
		order.setShipto(shipto);

		
		return order;
	}
	
	/**
	 * Create an order by asking for input. Lots of syso's in this one.
	 * Only meant to be used for test purposes. 
	 * 
	 * @return a fully instantiated Shiporder object with user input
	 */
	public Shiporder createOrderByUserInput() {
		
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
		System.out.println("(Optional) enter note: ");
		if(sc.nextLine().equals("")) {
			item.setNote(" ");			
		} item.setNote(sc.nextLine());
		
		order.getItem().add(item);
		
		return order;
	}
	
	
}
