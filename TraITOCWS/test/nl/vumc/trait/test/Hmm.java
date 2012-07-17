package nl.vumc.trait.test;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import nl.vumc.trait.oc.connect.ConnectInfo;
import nl.vumc.trait.oc.connect.OCConnectorException;
import nl.vumc.trait.oc.connect.OCWebServices;
import nl.vumc.trait.oc.odm.ClinicalODM;
import nl.vumc.trait.oc.odm.ClinicalODMResolver;
import nl.vumc.trait.oc.odm.NSContext;

import org.w3c.dom.Document;

public class Hmm {

	protected DocumentBuilderFactory documentBuilderFactory;
	/** Document Builder */
	protected DocumentBuilder documentBuilder;
	/** Transformer Factory */
	protected TransformerFactory transformerFactory;
	/** xpath factory */
	protected XPathFactory xPathFactory;
	/** xpath */
	protected XPath xPath;

	private final String ODM_INPUT1 = "/nl/vumc/trait/test/odm.txt";
	private final String ODM_INPUT2 = "/nl/vumc/trait/test/myfile.txt";

	/**
	 * Convert an XML DOM Document to a String representation
	 * 
	 * @param d
	 *            DOM Document
	 * @return String representation of DOM Document d
	 */
	protected String documentToString(Document d) throws TransformerException {
		StringWriter s = new StringWriter();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		transformer.transform(new DOMSource(d), new StreamResult(s));
		return s.toString();
	}

	public Hmm() throws ParserConfigurationException, MalformedURLException, DatatypeConfigurationException,
			OCConnectorException {
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setValidating(false);
		documentBuilderFactory.setNamespaceAware(true); // <- important!
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
		transformerFactory = TransformerFactory.newInstance();
		xPathFactory = XPathFactory.newInstance();
		xPath = xPathFactory.newXPath();
		xPath.setNamespaceContext(new NSContext()); // <- important too!
	}

	public void test() throws Exception {
		ConnectInfo connectInfo = new ConnectInfo("https://appserv2.ctms/OpenClinica-ws", "arjan",
				"8070379c3a8c300c71a50fefa07c68d49c416edd");
		OCWebServices webservices = OCWebServices.getInstance(connectInfo, true, false);
		InputStream odmInput = getClass().getResourceAsStream(ODM_INPUT1);
		ClinicalODM odm = new ClinicalODMResolver(documentBuilder.parse(odmInput), webservices, true);

		odmInput = getClass().getResourceAsStream(ODM_INPUT2);
		Document doc = documentBuilder.parse(odmInput);
		System.out.println("----------------------------------------------------------");
		System.out.println(documentToString(doc));
		System.out.println("----------------------------------------------------------");
//		System.out.println(documentToString(odm.resolveDocument(doc)));
//		System.out.println(documentToString(odm.resolveDocument(doc)));
//		System.out.println(documentToString(odm.resolveDocument(doc)));
//		System.out.println(documentToString(odm.resolveDocument(doc)));
//		System.out.println(documentToString(odm.resolveDocument(doc)));
//		System.out.println(documentToString(odm.resolveDocument(doc)));
//		System.out.println(documentToString(odm.resolveDocument(doc)));
//		System.out.println(documentToString(odm.resolveDocument(doc)));
//		System.out.println(documentToString(odm.resolveDocument(doc)));
//		System.out.println(documentToString(odm.resolveDocument(doc)));
//		System.out.println(documentToString(odm.resolveDocument(doc)));
//		System.out.println(documentToString(odm.resolveDocument(doc)));
//		System.out.println(documentToString(odm.resolveDocument(doc)));
//		System.out.println(documentToString(odm.resolveDocument(doc)));
//		System.out.println(documentToString(odm.resolveDocument(doc)));
//		System.out.println(documentToString(odm.resolveDocument(doc)));
//		System.out.println(documentToString(odm.resolveDocument(doc)));
//		System.out.println(documentToString(odm.resolveDocument(doc)));
//		System.out.println(documentToString(odm.resolveDocument(doc)));
//		System.out.println(documentToString(odm.resolveDocument(doc)));
		System.out.println("----------------------------------------------------------");

		System.out.println(odm);

	}

	public static void main(String[] args) throws Exception {
		Hmm bla = new Hmm();
		bla.test();
	}

}
