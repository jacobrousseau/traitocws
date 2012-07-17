package nl.vumc.trait.test;

import java.io.InputStream;
import java.net.MalformedURLException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import nl.vumc.trait.oc.connect.ConnectInfo;
import nl.vumc.trait.oc.connect.OCConnectorException;
import nl.vumc.trait.oc.connect.OCWebServices;
import nl.vumc.trait.oc.odm.ClinicalODM;
import nl.vumc.trait.oc.odm.ClinicalODMResolver;

public class ClinicalODMResolverTest {

	/**
	 * Document Builder Factory, set to be namespace aware in default
	 * constructor
	 */
	private DocumentBuilderFactory documentBuilderFactory;
	/** Document Builder */
	private DocumentBuilder documentBuilder;

	private final String ODM_INPUT = "/nl/vumc/trait/test/odm.txt";

	public ClinicalODMResolverTest() throws ParserConfigurationException, MalformedURLException,
			DatatypeConfigurationException, OCConnectorException {
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setValidating(false);
		documentBuilderFactory.setNamespaceAware(true); // <- important!
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
	}

	public void test() throws Exception {
		ConnectInfo connectInfo = new ConnectInfo("https://appserv2.ctms/OpenClinica-ws", "arjan",
				"8070379c3a8c300c71a50fefa07c68d49c416edd");
		OCWebServices webservices = OCWebServices.getInstance(connectInfo, true, false);
		InputStream odmInput = getClass().getResourceAsStream(ODM_INPUT);
		ClinicalODM odm = new ClinicalODMResolver(documentBuilder.parse(odmInput), webservices, true);
		
		System.out.println(odm);
	}

	public static void main(String[] args) throws Exception {
		ClinicalODMResolverTest bla = new ClinicalODMResolverTest();
		bla.test();
	}
}
