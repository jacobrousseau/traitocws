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

import java.util.ArrayList;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This handler renames nodes that belong to the beans
 * http://openclinica.org/ws/beans but that are incorrectly returned in the
 * wrong namespace by OpenClinica 3.1.2-community. See issue report 13333
 * (https://issuetracker.openclinica.com/view.php?id=13333).
 * 
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl)
 */
public class EventDefListAllHandler implements SOAPHandler<SOAPMessageContext> {

	/** OpenClinica namespace for study event def */
	private final String EVENTDEF_NS = "http://openclinica.org/ws/studyEventDefinition/v1";
	/** Target namespace -- the correct beans namespace */
	private final String EVENTDEF_TARGET_NS = "http://openclinica.org/ws/beans";
	/** The "root" element for renaming... */
	private final String EVENTDEF_ELEMENT = "studyEventDefinition";
	/** ListAllResponse */
	private final String EVENTDEF_LIST_ALL_RESPONSE = "listAllResponse";

	/**
	 * Messages
	 */
	private ArrayList<String> messages;

	/**
	 * Create a EventDefListAllHandler
	 * @throws ParserConfigurationException 
	 */
	public EventDefListAllHandler() throws ParserConfigurationException {
		this(new ArrayList<String>());
	}

	/**
	 * Create a EventDefListAllHandler passing a custom message list
	 * @param messages arraylist to capture any messages
	 * @throws ParserConfigurationException 
	 */
	public EventDefListAllHandler(ArrayList<String> messages) throws ParserConfigurationException {
		super();
		setMessages(messages);
	}

	/**
	 * Get messages
	 * @return list of messages
	 */
	public ArrayList<String> getMessages() {
		return messages;
	}

	/**
	 * Set message list
	 * @param messages message list
	 */
	public void setMessages(ArrayList<String> messages) {
		this.messages = messages;
	}

	/**
	 * Recursively set the target namespace on nodes in a NodeList
	 * @param n The NodeList
	 * @throws TransformerException 
	 */
	public void recursiveRenamespace(NodeList n) throws TransformerException {
		if (n.getLength() > 0) {
			Document d = n.item(0).getOwnerDocument();
			for (int i = 0; i < n.getLength(); ++i) {
				recursiveRenamespace(n.item(i).getChildNodes());
				if (n.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Node node = n.item(i);
					d.renameNode(node, EVENTDEF_TARGET_NS, node.getNodeName());
				}
			}
		}
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (!isRequest) { // only incoming messages
			try {
				SOAPMessage soapMsg = context.getMessage();
				SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();
				SOAPBody soapBody = soapEnv.getBody();
				if (soapBody != null) {
					// Should have a SOAPBody and a listall response...
					NodeList nodeList = soapBody.getElementsByTagNameNS(EVENTDEF_NS, EVENTDEF_LIST_ALL_RESPONSE);
					if (nodeList.getLength() > 0) { // check for listAllResponse
													// tag first!
						nodeList = soapBody.getElementsByTagNameNS(EVENTDEF_NS, EVENTDEF_ELEMENT);
						recursiveRenamespace(nodeList); // renamespace...
						soapMsg.saveChanges();
					}
				}
			} catch (Exception e) {
				catchMessages(e);
			}
		}
		return true;
	}

	/**
	 * Collects messages from Exceptions
	 * 
	 * @param e
	 */
	private void catchMessages(Exception e) {
		messages.add(e.getMessage());
		messages.add("");
		for (StackTraceElement element : e.getStackTrace()) {
			messages.add(element.toString());
		}
	}

	@Override
	public void close(MessageContext context) {
		// nothing required here.
	}

	@Override
	public boolean handleFault(SOAPMessageContext arg0) {
		return false;
	}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}

}
