/*
	
	Copyright 2012 VU Medical Center Amsterdam
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	    http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
	
*/

package nl.vumc.trait.oc.odm;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import nl.vumc.trait.oc.util.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * ODM
 * 
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl)
 * 
 */
public abstract class AbstractODM {

	/** The ODM DOM Document */
	protected Document odm;
	/** Logger to be used for logging */
	protected Logger logger;
	
	/**
	 * Document Builder Factory, set to be namespace aware in default
	 * constructor
	 */
	protected DocumentBuilderFactory documentBuilderFactory;
	/** Document Builder */
	protected DocumentBuilder documentBuilder;
	/** Transformer Factory */
	protected TransformerFactory transformerFactory;
	/** xpath factory */
	protected XPathFactory xPathFactory;
	/** xpath */
	protected XPath xPath;

	/**
	 * Initialize an AbstractODM Object
	 * @throws ODMException 
	 */
	protected AbstractODM() throws ODMException {
		try {
			documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setValidating(false);
			documentBuilderFactory.setNamespaceAware(true); // <- important!
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			transformerFactory = TransformerFactory.newInstance();
			xPathFactory = XPathFactory.newInstance();
			xPath = xPathFactory.newXPath();
			xPath.setNamespaceContext(new NSContext()); // <- important too!
		} catch (Exception e) {
			throw new ODMException("Cannot instantiate ODM object.", e);
		}
		logger = Logger.getInstance();
	}

	/**
	 * Construct an ODM Object from a DOM Document
	 * @param odm ODM DOM Document
	 * @throws ODMException 
	 */
	public AbstractODM(Document odm) throws ODMException {
		this();
		setOdm(odm);
	}

	/**
	 * Construct an ODM Object from a XML String representation
	 * @param odm ODM XML String
	 * @throws ODMException 
	 */
	public AbstractODM(String odm) throws ODMException {
		this();
		try {
			setOdm(stringToDocument(odm));
		} catch (Exception e) {
			throw new ODMException("Cannot instantiate ODM object.", e);
		}
	}

	/**
	 * Get the ODM
	 * @return ODM DOM Document
	 */
	public Document getOdm() {
		return odm;
	}

	/**
	 * Set ODM
	 * @param odm ODM DOM Document
	 * @throws ODMException 
	 */
	public void setOdm(Document odm) throws ODMException {
		this.odm = odm;
	}

	/**
	 * Create a DOM Document from an XML String
	 * @param s The String XML representation
	 * @return W3C Document based on string
	 * @throws SAXException 
	 * @throws IOException 
	 */
	protected Document stringToDocument(String s) throws SAXException, IOException {
		StringReader stringReader = new StringReader(s);
		Document result = documentBuilder.parse(new InputSource(stringReader));
		return result;
	}

	/**
	 * Convert an XML DOM Document to a String representation
	 * @param d DOM Document
	 * @return String representation of DOM Document d
	 * @throws TransformerException 
	 */
	protected String documentToString(Document d) throws TransformerException {
		StringWriter s = new StringWriter();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		transformer.transform(new DOMSource(d), new StreamResult(s));
		return s.toString();
	}

	@Override
	public String toString() {
		try {
			return documentToString(odm);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns the study OIDs in this ODM
	 * @return NodeList of StudyOIDs
	 * @throws ODMException 
	 */
	public abstract NodeList getStudyOID() throws ODMException;

}
