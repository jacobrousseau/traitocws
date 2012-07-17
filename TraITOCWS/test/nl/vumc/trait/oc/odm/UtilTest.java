package nl.vumc.trait.oc.odm;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UtilTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDocumentToString() {
		fail("Not yet implemented");
	}

}



//package nl.vumc.trait.oc.odm;
//
//import static org.junit.Assert.*;
//
//import java.io.IOException;
//import java.io.StringReader;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//
//import org.junit.Test;
//import org.w3c.dom.Document;
//import org.xml.sax.InputSource;
//import org.xml.sax.SAXException;
//
//public class UtilTest {
//	protected DocumentBuilderFactory documentBuilderFactory;
//	/** Document Builder */
//	protected DocumentBuilder documentBuilder;
//
//	documentBuilderFactory = DocumentBuilderFactory.newInstance();
//	documentBuilderFactory.setValidating(false);
//	documentBuilderFactory.setNamespaceAware(true); // <- important!
//	documentBuilder = documentBuilderFactory.newDocumentBuilder();
//
//	protected Document stringToDocument(String s) throws SAXException, IOException {
//		StringReader stringReader = new StringReader(s);
//		Document result = documentBuilder.parse(new InputSource(stringReader));
//		return result;
//	}
//
//
//	@Test
//	public void testDocumentToString() {
//		fail("Not yet implemented");
//	}
//
//}