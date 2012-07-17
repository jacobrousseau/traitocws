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
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * ODM Metadata
 * 
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl)
 * 
 */
public class MetadataODM extends AbstractODM {

	/**
	 * Location of the ODM cleaning XSL template
	 */
	private final String ODM_XSLT = "/nl/vumc/trait/oc/transformations/MetaODMToTemplate.xslt";

	/**
	 * Construct a new MetadataODM from a DOM Document
	 * 
	 * @param odm The XML document
	 * @throws ODMException 
	 * @throws ParserConfigurationException 
	 */
	public MetadataODM(Document odm) throws ODMException, ParserConfigurationException {
		super(odm);
	}

	/**
	 * Construct a new MetadataODM from String XML representation
	 * 
	 * @param odm The String representation of ODM metadata XML
	 * @throws ODMException 
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 */
	public MetadataODM(String odm) throws ODMException, SAXException, IOException, ParserConfigurationException {
		super(odm);
	}

	/**
	 * Transform ODM metadata into a template for ODM data loading
	 * @return the resulting template DOM Document
	 * @throws ODMException 
	 */
	public ClinicalODM getClinicalTemplate() throws ODMException {
		InputStream xslt = getClass().getResourceAsStream(ODM_XSLT);
		Document result = documentBuilder.newDocument();
		try {
			Transformer transformer = transformerFactory.newTransformer(new StreamSource(xslt));
			transformer.transform(new DOMSource(this.odm), new DOMResult(result));
			return new ClinicalODM(result, false);
		} catch (Exception e) {
			throw new ODMException(e);
		}
	}

	@Override
	public NodeList getStudyOID() throws ODMException {
		try {
			return (NodeList) xPath.evaluate("/cdisc:ODM/cdisc:Study/@OID", odm, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			throw new ODMException(e);
		}
	}

}
