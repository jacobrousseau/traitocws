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
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * This handler restructures the buggy isSubjectResponse from OC 3.1.2. See
 * issue report 13411 (https://issuetracker.openclinica.com/view.php?id=13411).
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl)
 */
public class StudySubjectHandler implements SOAPHandler<SOAPMessageContext> {

	/** StubjectSubject faulty createResponse namespace */
	private final String STUDYSUBJECT_NS = "http://openclinica.org/ws/studySubject/v1";
	/** StubjectSubject faulty createResponse tag */
	private final String STUDYSUBJECT_CREATE_RESPONSE = "createResponse";
	/** subjectOID */
	private final String SUBJECTOID = "subjectOID";
	/** subjectOID */
	private final String STUDYSUBJECTOID = "studySubjectOID";
	/** target name */
	private final String STUDYSUBJECT_ISSSRESPONSE = "isStudySubjectResponse";

	/**
	 * Messages
	 */
	private ArrayList<String> messages;

	/**
	 * Create a StudyListAllHandler
	 * @throws ParserConfigurationException 
	 */
	public StudySubjectHandler() throws ParserConfigurationException {
		this(new ArrayList<String>());
	}

	/**
	 * Create a StudyListAllHandler passing a custom message list
	 * @param messages arraylist to capture any messages
	 * @throws ParserConfigurationException 
	 */
	public StudySubjectHandler(ArrayList<String> messages) throws ParserConfigurationException {
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

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (!isRequest) { // only incoming messages
			try {
				SOAPMessage soapMsg = context.getMessage();
				SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();
				SOAPBody soapBody = soapEnv.getBody();
				if (soapBody != null) {
					// Should have a SOAPBody and the targeted tags...
					NodeList responseNodes = soapBody.getElementsByTagNameNS(STUDYSUBJECT_NS,
							STUDYSUBJECT_CREATE_RESPONSE);
					NodeList oidNodes = soapBody.getElementsByTagNameNS(STUDYSUBJECT_NS, SUBJECTOID);
					if (responseNodes.getLength() > 0 && oidNodes.getLength() > 0) {
						Document me = responseNodes.item(0).getOwnerDocument();
						try {
							for (int i = 0; i < responseNodes.getLength(); ++i) {
								me.renameNode(responseNodes.item(i), STUDYSUBJECT_NS, STUDYSUBJECT_ISSSRESPONSE);
							}
							for (int i = 0; i < oidNodes.getLength(); ++i) {
								me.renameNode(oidNodes.item(i), STUDYSUBJECT_NS, STUDYSUBJECTOID);
							}
							soapMsg.saveChanges();
						} catch (Exception e) {
							catchMessages(e);
						}
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
