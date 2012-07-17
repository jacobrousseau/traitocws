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

import java.io.InputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * ODM Clinical Data
 * 
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl)
 * 
 */
public class ClinicalODM extends AbstractODM {

	/** clean on instantiation or not */
	protected static final boolean DEFAULT_CLEANING = true;

	/**
	 * Location of the ODM cleaning XSL template
	 */
	private static final String ODM_XSLT = "/nl/vumc/trait/oc/transformations/CleanODMImport.xslt";

	/**
	 * Limit public access to constructor
	 * @throws ODMException
	 */
	protected ClinicalODM() throws ODMException {
		// to facilitate default constructor in sub classes
		super();
	}
	
	/**
	 * Construct an ODM Object from a DOM Document
	 * @param odm ODM DOM Document
	 * @param clean to clean or not to clean ODM
	 * @throws ODMException 
	 */
	public ClinicalODM(Document odm, boolean clean) throws ODMException {
		super(odm);
		if (clean) {
			clean();
		}
	}

	/**
	 * Construct an ODM Object from a DOM Document
	 * @param odm ODM DOM Document
	 * @throws ODMException 
	 */
	public ClinicalODM(Document odm) throws ODMException {
		this(odm, DEFAULT_CLEANING);
	}

	/**
	 * Construct an ODM Object from a XML String representation
	 * @param odm ODM XML String
	 * @param clean to clean or not to clean ODM
	 * @throws ODMException 
	 */
	public ClinicalODM(String odm, boolean clean) throws ODMException {
		super(odm);
		if (clean) {
			setOdm(cleaningTransformation(this.odm));
		}
	}

	/**
	 * Construct an ODM Object from a XML String representation
	 * @param odm ODM XML String
	 * @throws ODMException 
	 */
	public ClinicalODM(String odm) throws ODMException {
		this(odm, DEFAULT_CLEANING);
	}

	/**
	 * Clean Clinical ODM removing all empty template slots
	 * @param odm Clinical ODM DOM Document
	 * @return A cleaned version of odm
	 * @throws ODMException 
	 */
	private Document cleaningTransformation(Document odm) throws ODMException {
		try {
			InputStream xslt = getClass().getResourceAsStream(ODM_XSLT);
			Document result = documentBuilder.newDocument();
			Transformer transformer = transformerFactory.newTransformer(new StreamSource(xslt));
			transformer.transform(new DOMSource(odm), new DOMResult(result));
			return result;
		} catch (Exception e) {
			throw new ODMException("Cannot clean ODM.", e);
		}
	}

	/**
	 * Returns cleaned clinical ODM with all empty template slots removed.
	 * @return a cleaned version of this clinical odm
	 * @throws ODMException 
	 */
	public ClinicalODM getClean() throws ODMException {
		try {
			return new ClinicalODM(cleaningTransformation(odm));
		} catch (Exception e) {
			throw new ODMException(e);
		}
	}

	/**
	 * Clean this ClinicalODM -- perform cleaning transformation.
	 * @return reference to "this" for convenience
	 * @throws ODMException 
	 */
	public ClinicalODM clean() throws ODMException {
		setOdm(cleaningTransformation(this.odm));
		removeAttributes(this.odm, "//@OpenClinica:*[.='<VALUE>']");
		return this;
	}

	@Override
	public NodeList getStudyOID() throws ODMException {
		try {
			return (NodeList) xPath.evaluate("/ODM/ClinicalData/@StudyOID", odm, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			throw new ODMException(e);
		}
	}

	/**
	 * Perform an xPath query on a node and return a nodelist yielded.
	 * @param node the node to query on 
	 * @param query the xPath query
	 * @param failIfNoneFound if set to true ODMException will be raised if the result is empty
	 * @return yield of the query
	 * @throws ODMException
	 */
	public NodeList xPath(Node node, String query, boolean failIfNoneFound) throws ODMException {
		NodeList list;
		try {
			list = (NodeList) xPath.evaluate(query, node, XPathConstants.NODESET);
			logger.debug("Node: " + node.getNodeName() + ", query: " + query + ", length: " + list.getLength());
			if (failIfNoneFound && list.getLength() == 0) {
				throw new ODMException("Error resolving ODM (xPath '" + query + "' on '" + node.getNodeName()
						+ "' returns empty nodelist!)");
			}
		} catch (XPathExpressionException e) {
			throw new ODMException("Error resolving ODM!", e);
		}
		return list;
	}

	/**
	 * See xPath(Node node, String query, boolean failIfNoneFound), same but the root node is ODM.
	 * @param query the xPath query
	 * @param failIfNoneFound if set to true ODMException will be raised if the result is empty
	 * @return yield of the query
	 * @throws ODMException
	 */
	public NodeList xPath(String query, boolean failIfNoneFound) throws ODMException {
		return xPath(odm, query, failIfNoneFound);
	}

	/**
	 * See xPath(Node node, String query, boolean failIfNoneFound), same but no exception is raised on empty result.
	 * @param node the node to query on 
	 * @param query the xPath query
	 * @return yield of the query
	 * @throws ODMException
	 */
	public NodeList xPath(Node node, String query) throws ODMException {
		return xPath(node, query, false);
	}

	/**
	 * See xPath(Node node, String query), same but root node is ODM
	 * @param query the xPath query
	 * @return yield of the query
	 * @throws ODMException
	 */
	public NodeList xPath(String query) throws ODMException {
		return xPath(odm, query, false);
	}

	/**
	 * Fetch a specific attribute of a given node
	 * @param node the node who's attribute is to be fetched
	 * @param attribute the attribute name
	 * @return attribute requested
	 * @throws ODMException if attribute does not exist
	 */
	public Attr getAttribute(Node node, String attribute) throws ODMException {
		return getAttribute(node, attribute, true);
	}

	/**
	 * Fetch a specific attribute of a given node
	 * @param node the node who's attribute is to be fetched
	 * @param attribute the attribute name
	 * @param failIfNoneFound if set to false no exception is raised if attribute non-existent
	 * @return attribute requested
	 * @throws ODMException if attribute does not exist
	 */
	public Attr getAttribute(Node node, String attribute, boolean failIfNoneFound) throws ODMException {
		Attr attr = (Attr) node.getAttributes().getNamedItem(attribute);
		if (attr != null) {
			return attr;
		} else {
			if (failIfNoneFound) {
				throw new ODMException("Attribute '" + attribute + "' not found in node '" + node.getNodeName() + "'");
			} else {
				return null; // silently return null
			}
		}
	}

	/**
	 * Return named boolean attribute as bool for a certain node.
	 * @param node the nodes that supposedly has the named attribute
	 * @param attributeName name of the attribute
	 * @return the boolean value as bool
	 */
	public boolean checkBooleanAttribute(Node node, String attributeName) {
		Node attr = node.getAttributes().getNamedItem(attributeName);
		return (attr != null && attr.getNodeValue().toLowerCase().equals("true"));
	}

	/**
	 * Remove attributes from a Node based on an xPAth query
	 * @param node root node to operate on 
	 * @param query query selecting the attributes to be removed
	 * @return the number of nodes removed
	 * @throws ODMException
	 */
	protected int removeAttributes(Node node, String query) throws ODMException {
		return removeAttributes(node, query, false);
	}
	
	/**
	 * Remove attributes from a Node based on an xPAth query
	 * @param node root node to operate on 
	 * @param query query selecting the attributes to be removed
	 * @param failIfNotFound if set to true ODMException is raised if no attributes match the query
	 * @return the number of nodes removed
	 * @throws ODMException
	 */
	protected int removeAttributes(Node node, String query, boolean failIfNotFound) throws ODMException {
		int count = 0;
		NodeList mirthAttr = xPath(node, query, failIfNotFound);
		for (int l = 0; l < mirthAttr.getLength(); ++l) {
			((Attr) mirthAttr.item(l)).getOwnerElement().removeAttributeNode((Attr) mirthAttr.item(l));
			count++;
		}
		return count;
	}

}
