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

package nl.vumc.trait.oc.connect;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.parsers.ParserConfigurationException;

import nl.vumc.trait.oc.soap.EventDefListAllHandler;
import nl.vumc.trait.oc.soap.ImportRequestHandler;
import nl.vumc.trait.oc.soap.LoggingHandler;
import nl.vumc.trait.oc.soap.StudyListAllHandler;
import nl.vumc.trait.oc.soap.StudySubjectHandler;
import nl.vumc.trait.oc.soap.Util;
import nl.vumc.trait.oc.soap.WsseSecurityHandler;
import nl.vumc.trait.oc.ws.DataWsService;
import nl.vumc.trait.oc.ws.EventWsService;
import nl.vumc.trait.oc.ws.StudyEventDefinitionWsService;
import nl.vumc.trait.oc.ws.StudySubjectWsService;
import nl.vumc.trait.oc.ws.StudyWsService;


/**
 * Supports "low-level" interaction with OpenClinica web services such
 * as exception handling, credentials and soap handler setup.
 * 
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl)
 */
public class OCConnector {

	/** Status message for failure (should correspond to OpenClinica "result" value */
	public static final String STATUS_FAIL = "Fail";
	/** Status message for success (should correspond to OpenClinica "result" value */
	public static final String STATUS_SUCCESS = "Success";
	/** return status of last web service call */
	private String status;
	/** messages returned during web services calls */
	private ArrayList<String> messages;
	/** base url for OpenClinica WS instance */
	private String baseURL;
	/** data wsdl location, relative to baseURL */
	public static final String URL_DATA = "ws/data/v1/dataWsdl.wsdl";
	/** event wsdl location, relative to baseURL */
	public static final String URL_EVENT = "ws/event/v1/eventWsdl.wsdl";
	/** study wsdl location, relative to baseURL */
	public static final String URL_STUDY = "ws/study/v1/studyWsdl.wsdl";
	/** studyEventDefinition wsdl location, relative to baseURL */
	public static final String URL_STUDYEVENTDEF = "ws/studyEventDefinition/v1/studyEventDefinitionWsdl.wsdl";
	/** studySubject wsdl location, relative to baseURL */
	public static final String URL_STUDYSUBJECT = "ws/studySubject/v1/studySubjectWsdl.wsdl";
	/** Flag to turn on/off logging */
	private boolean logging;
	/** This class is a singleton, so keep a static instance */
	private static OCConnector instance;
	/** WS binding: Study */
	protected org.openclinica.ws.study.v1.Ws studyBinding;
	/** WS binding: Study Subject */
	protected org.openclinica.ws.studysubject.v1.Ws studySubjectBinding;
	/** WS binding: Event */
	protected org.openclinica.ws.event.v1.Ws eventBinding;
	/** WS binding: Data */
	protected org.openclinica.ws.data.v1.Ws dataBinding;
	/** WS binding: Study Event Definition */
	protected org.openclinica.ws.studyeventdefinition.v1.Ws studyEventDefinitionBinding;
	/** SOAP Message Handler for WSSE Security */
	private WsseSecurityHandler wsseHandler;
	/** Data type factory for XML */
	protected DatatypeFactory dataTypeFactory;

	/**
	 * get current instance or create new if not yes instantiated.
	 * @param connectInfo OpenClinica connection info and credentials.
	 * @return returns OCConnector instance
	 * @throws MalformedURLException 
	 * @throws ParserConfigurationException 
	 * @throws DatatypeConfigurationException 
	 */
	public static OCConnector getInstance(ConnectInfo connectInfo)
			throws MalformedURLException, ParserConfigurationException, DatatypeConfigurationException {
		return getInstance(connectInfo, false, false);
	}

	/**
	 * get current instance or create new if not yet instantiated. if logging
	 * option differs from logging option in existing instance, a new one will
	 * be created.
	 * @param connectInfo OpenClinica connection info and credentials.
	 * @param logging toggle logging
	 * @return returns OCConnector instance
	 * @throws MalformedURLException 
	 * @throws ParserConfigurationException 
	 * @throws DatatypeConfigurationException 
	 */
	public static OCConnector getInstance(ConnectInfo connectInfo, boolean logging)
			throws MalformedURLException, ParserConfigurationException, DatatypeConfigurationException {
		return getInstance(connectInfo, logging, false);
	}

	/**
	 * get current instance or create new if not yet instantiated. if logging
	 * option differs from logging option in existing instance or if forceInstantiation
	 * is set, a new one instance be created.
	 * @param connectInfo OpenClinica connection info and credentials.
	 * @param logging toggle logging
	 * @param forceInstantiation if set force the creation of a new instance
	 * @return returns OCConnector instance
	 * @throws MalformedURLException 
	 * @throws ParserConfigurationException 
	 * @throws DatatypeConfigurationException 
	 */
	public static OCConnector getInstance(ConnectInfo connectInfo, boolean logging,
			boolean forceInstantiation) throws MalformedURLException, ParserConfigurationException,
			DatatypeConfigurationException {
		if (forceInstantiation || instance == null || instance.isLogging() != logging) {
			instance = new OCConnector(connectInfo, logging);
		}
		return instance;
	}

	/**
	 * create instance (we are a singleton, so, protected)
	 * @throws DatatypeConfigurationException 
	 */
	protected OCConnector() throws DatatypeConfigurationException {
		dataTypeFactory = DatatypeFactory.newInstance();
		status = STATUS_FAIL;
		messages = new ArrayList<String>();
		Util.installPermissiveTrustManager();
		wsseHandler = new WsseSecurityHandler();
	}

	/**
	 * create instance (we are a singleton, so, protected)
	 * @param connectInfo OpenClinica connection info and credentials.
	 * @param logging toggle logging
	 * @throws MalformedURLException 
	 * @throws ParserConfigurationException 
	 * @throws DatatypeConfigurationException 
	 */
	protected OCConnector(ConnectInfo connectInfo, boolean logging)
			throws MalformedURLException, ParserConfigurationException, DatatypeConfigurationException {
		this();
		String baseURL = connectInfo.getBaseURL();
		if (baseURL.charAt(baseURL.length() - 1) != '/') {
			this.baseURL = baseURL + "/";
		} else {
			this.baseURL = baseURL;
		}
		setCredentials(connectInfo);
		this.logging = logging;
		setupBindings();
	}

	/**
	 * get logging flag
	 * @return logging
	 */
	public boolean isLogging() {
		return logging;
	}

	/**
	 * get baseURL
	 * @return baseURL
	 */
	public String getBaseURL() {
		return baseURL;
	}

	/**
	 * get current status
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * check whether last action was successful (status == STATUS_SUCCESS)
	 * @return successful or not
	 */
	public boolean isSuccess() {
		return status.equals(STATUS_SUCCESS);
	}

	/**
	 * get messages
	 * @return messages
	 */
	public ArrayList<String> getMessages() {
		return messages;
	}

	/**
	 * set credentials for web service
	 * @param connectInfo user, password hash and url
	 */
	public void setCredentials(ConnectInfo connectInfo) {
		wsseHandler.setUsername(connectInfo.getUserName());
		wsseHandler.setPassword(connectInfo.getPasswordHash());
		baseURL = connectInfo.getBaseURL();
	}

	/**
	 * process status of last action and throw exception if appropriate
	 * @param status status
	 * @param errors list of error messages
	 * @throws OCConnectorException 
	 */
	protected void checkResponseExceptions(String status, List<String> errors) throws OCConnectorException {
		if (status.equals(STATUS_FAIL)) {
			StringBuffer msg = new StringBuffer();
			for (String error : errors) {
				msg.append(error + "\n");
			}
			throw new OCConnectorException("An error was returned by OpenClinica web services:\n" + msg.toString());
		}
	}

	/**
	 * Configure all WS bindings
	 * @throws MalformedURLException 
	 * @throws ParserConfigurationException 
	 */
	private void setupBindings() throws MalformedURLException, ParserConfigurationException {
		// get bindings
		studyBinding = new StudyWsService(baseURL + URL_STUDY).getWsSoap11();
		studySubjectBinding = new StudySubjectWsService(baseURL + URL_STUDYSUBJECT).getWsSoap11();
		eventBinding = new EventWsService(baseURL + URL_EVENT).getWsSoap11();
		dataBinding = new DataWsService(baseURL + URL_DATA).getWsSoap11();
		studyEventDefinitionBinding = new StudyEventDefinitionWsService(baseURL + URL_STUDYEVENTDEF).getWsSoap11();
		// setup handlers 'n stuff
		setupDefaultHandlers(studyBinding);
		setupDefaultHandlers(studySubjectBinding);
		setupDefaultHandlers(eventBinding);
		setupDefaultHandlers(dataBinding);
		setupDefaultHandlers(studyEventDefinitionBinding);
		ImportRequestHandler importRequestHandler = new ImportRequestHandler(messages, true);
		Util.addMessageHandler(dataBinding, 0, importRequestHandler);
		StudyListAllHandler studyListAllHandler = new StudyListAllHandler(messages);
		Util.addMessageHandler(studyBinding, 0, studyListAllHandler);
		StudySubjectHandler studySubjectHandler = new StudySubjectHandler(messages);
		Util.addMessageHandler(studySubjectBinding, 0, studySubjectHandler);
		EventDefListAllHandler eventDefListAllHandler = new EventDefListAllHandler(messages);
		Util.addMessageHandler(studyEventDefinitionBinding, 0, eventDefListAllHandler);
	}

	/**
	 * Install default SOAP message handlers
	 * @param binding
	 */
	private void setupDefaultHandlers(Object binding) {
		if (logging) {
			Util.addMessageHandler(binding, new LoggingHandler());
		}
		Util.addMessageHandler(binding, 0, wsseHandler.newInstance());
	}

	/**
	 * Clears messages
	 */
	public void clearMessages() {
		messages = new ArrayList<String>();
	}

}
