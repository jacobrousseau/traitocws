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

package nl.vumc.trait.oc.soap;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * This handler processes ODM data in dataImport requests. ODM is passed as a
 * String and should be transformed to proper XML before it can be sent off to
 * the OpenClinica web service. This handler is used since there is no formal
 * definition of the contents of the importRequest. This handler handles
 * outgoing messages having a importRequest tag. In the process of transforming
 * ODM to XML it will also cleansed so as to filter out missing template values.
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl)
 */
public class ImportRequestHandler implements SOAPHandler<SOAPMessageContext> {

	/**
	 * Default ODM cleaning.
	 */
	public final static boolean DEFAULT_CLEAN_ODM = true;

	/**
	 * Namespace of the importRequest element
	 */
	private final String IMPORT_ELEMENT_NS = "http://openclinica.org/ws/data/v1";

	/**
	 * Name of the importRequest element
	 */
	private final String IMPORT_ELEMENT = "importRequest";

	/**
	 * Location of the ODM cleaning XSL template
	 */
	private final String ODM_XSLT = "/nl/vumc/trait/oc/transformations/CleanODMImport.xslt";

	/**
	 * ODM Cleaning option
	 */
	private boolean cleanODM;

	/**
	 * Messages
	 */
	private ArrayList<String> messages;

	/**
	 * Create a handler that creates its own messages ArrayList. Sets ODM
	 * cleaning to DEFAULT_CLEAN_ODM.
	 */
	public ImportRequestHandler() {
		this(new ArrayList<String>(), DEFAULT_CLEAN_ODM);
	}

	/**
	 * Create a handler setting the messages ArrayList and telling it whether or
	 * not to clean ODM.
	 * @param messages messages ArrayList
	 * @param cleanODM clean ODM or not
	 */
	public ImportRequestHandler(ArrayList<String> messages, boolean cleanODM) {
		super();
		setCleanODM(cleanODM);
		setMessages(messages);
	}

	/**
	 * Create a handler setting the messages ArrayList and telling it whether or
	 * not to clean ODM.
	 * @param cleanODM clean ODM or not
	 * @param messages messages ArrayList
	 */
	public ImportRequestHandler(boolean cleanODM, ArrayList<String> messages) {
		super();
		setCleanODM(cleanODM);
		setMessages(messages);
	}

	/**
	 * Create a handler setting the messages ArrayList. Sets ODM cleaning to
	 * DEFAULT_CLEAN_ODM.
	 * @param messages messages ArrayList
	 */
	public ImportRequestHandler(ArrayList<String> messages) {
		super();
		setCleanODM(DEFAULT_CLEAN_ODM);
		setMessages(messages);
	}

	/**
	 * Create a handler that creates its own messages ArrayList and telling it
	 * whether or not to clean ODM.
	 * @param cleanODM clean ODM or not
	 */
	public ImportRequestHandler(boolean cleanODM) {
		super();
		setCleanODM(cleanODM);
		setMessages(new ArrayList<String>());
	}

	/**
	 * Get messages generated due to exceptions
	 * @return messages
	 */
	public ArrayList<String> getMessages() {
		return messages;
	}

	/**
	 * Set messages ArrayList.
	 * @param messages messages ArrayList
	 */
	public void setMessages(ArrayList<String> messages) {
		this.messages = messages;
	}

	/**
	 * Get ODM cleaning option
	 * @return ODM cleaning option
	 */
	public boolean isCleanODM() {
		return cleanODM;
	}

	/**
	 * Set ODM cleaning option
	 * @param cleanODM ODM cleaning option
	 */
	public void setCleanODM(boolean cleanODM) {
		this.cleanODM = cleanODM;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		// if this is a request, true for outbound messages, false for inbound
		if (isRequest) {
			try {
				SOAPMessage soapMsg = context.getMessage();
				SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();
				SOAPBody soapBody = soapEnv.getBody();

				if (soapBody != null) {
					// Should have a SOAPBody
					NodeList nodeList = soapBody.getElementsByTagNameNS(IMPORT_ELEMENT_NS, IMPORT_ELEMENT);
					if (nodeList.getLength() != 0) {
						// Should have "importRequest" element
						Node importRequest = nodeList.item(0);
						String textContent;
						if (cleanODM) {
							textContent = cleanODM(importRequest.getTextContent());
						} else {
							textContent = importRequest.getTextContent();
						}
						// Marshal importRequest content
						DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
						f.setValidating(false);
						Document d = f.newDocumentBuilder().parse(new InputSource(new StringReader(textContent)));
						Node rootNode = d.getFirstChild();
						// Have the SOAP message adopt our newly created tree...
						importRequest.setTextContent(null);
						// importRequest.getOwnerDocument().adoptNode(rootNode);
						importRequest.appendChild(importRequest.getOwnerDocument().importNode(rootNode, true));
						soapMsg.saveChanges();
					}
				}
			} catch (SOAPException e) {
				catchMessages(e);
			} catch (SAXException e) {
				catchMessages(e);
			} catch (IOException e) {
				catchMessages(e);
			} catch (ParserConfigurationException e) {
				catchMessages(e);
			} catch (DOMException e) {
				catchMessages(e);
			} catch (TransformerException e) {
				catchMessages(e);
			}
		}
		return true;
	}

	/**
	 * Collects messages from Exceptions
	 * @param e
	 */
	private void catchMessages(Exception e) {
		messages.add(e.getMessage());
		messages.add("");
		for (StackTraceElement element : e.getStackTrace()) {
			messages.add(element.toString());
		}
	}

	/**
	 * Cleans ODM using XSL template
	 * @param odm ODM to be transformed
	 * @return Transformed ODM In case the ODM cannot be transformed
	 * @throws TransformerException 
	 */
	private String cleanODM(String odm) throws TransformerException {
		StringWriter s = new StringWriter();
		InputStream xslt = getClass().getResourceAsStream(ODM_XSLT);
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer(new StreamSource(xslt));
		transformer.transform(new StreamSource(new StringReader(odm)), new StreamResult(s));
		return s.toString();
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		// Nothing required here...
		return true;
	}

	@Override
	public void close(MessageContext context) {
		// Nothing required here...
	}

	@Override
	public Set<QName> getHeaders() {
		// Nothing required here...
		return null;
	}

}
