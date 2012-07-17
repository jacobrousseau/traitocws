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

import java.util.Collection;
import java.util.HashMap;

import javax.xml.datatype.DatatypeConfigurationException;

import nl.vumc.trait.oc.connect.OCConnectorException;
import nl.vumc.trait.oc.connect.OCWebServices;
import nl.vumc.trait.oc.types.ScheduledEvent;
import nl.vumc.trait.oc.types.Study;
import nl.vumc.trait.oc.types.StudySubject;

import org.openclinica.ws.study.v1.ListAllResponse;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Clinical ODM with functionality to resolve non-OID ids from OpenClinica IDs
 * 
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl)
 * 
 */
public class ClinicalODMResolver extends ClinicalODM {
	
	// TODO: too many constructors here :S

	/** Attribute: Mirth:TranslateOID, indicates whether or not to translate a certain element */
	private final String ATTR_MIRTH_TRANSLATEOID = "Mirth:TranslateOID";
	/** Attribute: Mirth:Create, indicates whether or not to create a certain element if non-existent */
	private final String ATTR_MIRTH_CREATE = "Mirth:Create";
	/** Attribute: OpenClinica, data of birth */
	private final String ATTR_OC_DATEOFBIRTH = "OpenClinica:DateOfBirth";
	/** Attribute: OpenClinica, registration date */
	private final String ATTR_OC_DATEOFREGISTRATION = "OpenClinica:DateOfRegistration";
	/** Attribute: OpenClinica, gender */
	private final String ATTR_OC_SEX = "OpenClinica:Sex";
	/** Attribute: SubjectKey (can be OID or label depending on Mirth:TranslateOID) */
	private final String ATTR_OC_SUBJECTKEY = "SubjectKey";
	/** Attribute: OpenClinica, unique identifier: Person ID */
	private final String ATTR_OC_PERSONID = "OpenClinica:UniqueIdentifier";
	/** Attribute: ODM, Study Event OID */
	private final String ATTR_STUDY_EVENT_OID = "StudyEventOID";
	/** Attribute: OpenClinica, start date (of event) */
	private final String ATTR_OC_START_DATE = "OpenClinica:StartDate";
	/** Attribute: ODM, Study OID or label (can be OID or label depending on Mirth:TranslateOID) */
	private final String ATTR_STUDYOID = "StudyOID";
	// TODO: private final String ATTR_STUDY_EVENT_REPEAT_KEY = "StudyEventRepeatKey";
	/** XPath Query: ClinicalData element, the root of OpenClinica loadable data */
	private final String XPATH_CLINICAL_DATA = "/ODM/ClinicalData";
	/** XPath Query: Subject Data -- Child(ren) of ClinicalData */
	private final String XPATH_SUBJECT_DATA = "./SubjectData";
	/** XPath Query: Study Event Data -- event related data, child of SubjectData */
	private final String XPATH_STUDYEVENTDATA = "./StudyEventData";
	/** XPath Query: All attributes that do not have their value set to '<VALUE>' */
	private final String XPATH_SUBJECT_ATTRS = "./@*[.!='<VALUE>']";

	/** studie index. Study objects, keyed by studyname + sitename */
	protected HashMap<String, Study> studies; // // <unique id (study name + sitename), study object>
	
	/** OC web services connector */
	private OCWebServices connector;

	/**
	 * Init ClinicalODMResolver.
	 * @param ocConnector OpenClinica web services
	 * @throws ODMException
	 */
	public ClinicalODMResolver(OCWebServices ocConnector) throws ODMException {
		super();
		this.connector = ocConnector;
		studies = new HashMap<String, Study>();
	}

	/**
	 * Create a ClinicalODMResolver, initializing its content from a Document.
	 * @param odm ODM XML document
	 * @param ocConnector OpenClinica web services
	 * @param clean Clean the ODM used for init or not
	 * @throws ODMException 
	 * @throws OCConnectorException 
	 * @throws DatatypeConfigurationException 
	 */
	public ClinicalODMResolver(Document odm, OCWebServices ocConnector, boolean clean) throws ODMException,
			OCConnectorException, DatatypeConfigurationException {
		super(odm, clean);
		this.connector = ocConnector;
		studies = new HashMap<String, Study>();
	}

	/**
	 * Create a ClinicalODMResolver, initializing its content from a String.
	 * @param odm ODM XML as String
	 * @param ocConnector OpenClinica web services
	 * @param clean Clean the ODM used for init or not
	 * @throws ODMException 
	 * @throws OCConnectorException 
	 * @throws DatatypeConfigurationException 
	 */
	public ClinicalODMResolver(String odm, OCWebServices ocConnector, boolean clean) throws ODMException,
			OCConnectorException, DatatypeConfigurationException {
		super(odm, clean);
		studies = new HashMap<String, Study>();
	}

	/**
	 * Create a ClinicalODMResolver, initializing its content from a Document,
	 * with cleaning set to DEFAULT_CLEANING.
	 * @param odm ODM XML document
	 * @param ocConnector OpenClinica web services
	 * @throws ODMException 
	 * @throws OCConnectorException 
	 * @throws DatatypeConfigurationException 
	 */
	public ClinicalODMResolver(Document odm, OCWebServices ocConnector) throws ODMException, OCConnectorException,
			DatatypeConfigurationException {
		this(odm, ocConnector, DEFAULT_CLEANING);
		studies = new HashMap<String, Study>();
	}

	/**
	 * Create a ClinicalODMResolver, initializing its content from a String,
	 * with cleaning set to DEFAULT_CLEANING.
	 * @param odm ODM XML as String
	 * @param ocConnector OpenClinica web services
	 * @throws ODMException 
	 * @throws OCConnectorException 
	 * @throws DatatypeConfigurationException 
	 */
	public ClinicalODMResolver(String odm, OCWebServices ocConnector) throws ODMException, OCConnectorException,
			DatatypeConfigurationException {
		this(odm, ocConnector, DEFAULT_CLEANING);
		studies = new HashMap<String, Study>();
	}

	/**
	 * Return boolean attribute ATTR_MIRTH_TRANSLATEOID as bool for a certain node.
	 * @param node the nodes that supposedly has the ATTR_MIRTH_TRANSLATEOID attribute
	 * @return the boolean value as bool
	 */
	private boolean translateOrNot(Node node) {
		return checkBooleanAttribute(node, ATTR_MIRTH_TRANSLATEOID);
	}

	/**
	 * Return boolean attribute ATTR_MIRTH_CREATE as bool for a certain node.
	 * @param node the nodes that supposedly has the ATTR_MIRTH_CREATE attribute
	 * @return the boolean value as bool
	 */
	private boolean createOrNot(Node node) {
		return checkBooleanAttribute(node, ATTR_MIRTH_CREATE);
	}

	/**
	 * Creatr StudySubject from SubjectData ODM element.
	 * @param study study the subject belongs to
	 * @param subjectDataNode the node to be translated
	 * @return a StudySubject object based on the SubjectData element supplied
	 * @throws ODMException
	 * @throws DatatypeConfigurationException
	 */
	private StudySubject createStudySubject(Study study, Node subjectDataNode) throws ODMException,
			DatatypeConfigurationException {
		NodeList attributes = xPath(subjectDataNode, XPATH_SUBJECT_ATTRS);
		StudySubject studySubject = new StudySubject(study);
		String subjectHandle = "";
		for (int i = 0; i < attributes.getLength(); ++i) {
			Node node = attributes.item(i);
			if (node.getNodeName().equals(ATTR_OC_DATEOFBIRTH)) {
				studySubject.setDateOfBirth(node.getNodeValue());
			} else if (node.getNodeName().equals(ATTR_OC_DATEOFREGISTRATION)) {
				studySubject.setDateOfRegistration(node.getNodeValue());
			} else if (node.getNodeName().equals(ATTR_OC_SEX)) {
				studySubject.setSex(node.getNodeValue());
			} else if (node.getNodeName().equals(ATTR_OC_SUBJECTKEY)) {
				subjectHandle = node.getNodeValue();
			} else if (node.getNodeName().equals(ATTR_OC_PERSONID)) {
				studySubject.setPersonID(node.getNodeValue());
			}
			if (translateOrNot(subjectDataNode)) {
				studySubject.setStudySubjectLabel(subjectHandle);
			} else {
				studySubject.setStudySubjectOID(subjectHandle);
			}
		}
		return studySubject;
	}

	/**
	 * Fetch all studies and resolve. See overloaded resolveMe(). This method
	 * make a OpenClinica web service call.
	 * @return a collection of studies
	 * @throws ODMException
	 * @throws OCConnectorException
	 * @throws DatatypeConfigurationException
	 */
	private Collection<Study> resolveMe() throws ODMException, OCConnectorException, DatatypeConfigurationException {
		ListAllResponse allStudies = connector.listAllStudies(); // fetch available studies
		return resolveMe(studies, allStudies);
	}
	
	/**
	 * This is where all the logic sits for resolving ODM data. ODM data is processed
	 * here and unresolved attributes are resolved, subjects are created, events are
	 * scheduled... This method operated on a pre-fetched LastAllResponse object.
	 * @param studies internal study data (our cache)
	 * @param allStudies all studies from listAll OpenClinica method.
	 * @return A collection of studies.
	 * @throws ODMException
	 * @throws OCConnectorException
	 * @throws DatatypeConfigurationException
	 */
	private Collection<Study> resolveMe(HashMap<String, Study> studies, ListAllResponse allStudies)
			throws ODMException, OCConnectorException, DatatypeConfigurationException {
		this.clean();
		
		// TODO: Change the order of things in here slightly
		// so as to make this a bit more of a "transaction". A subject
		// should not be created if we know beforehand that the event
		// data is rubbish.

		// for each ClinicalData i
		NodeList clinicalDatas = xPath(XPATH_CLINICAL_DATA);
		for (int i = 0; i < clinicalDatas.getLength(); ++i) {
			Node clinicalData = clinicalDatas.item(i); // ---- ClinicalData i ----
			Attr studyOID = getAttribute(clinicalData, ATTR_STUDYOID);
			Study study = connector.findStudy(allStudies, studyOID.getNodeValue(), !translateOrNot(clinicalData));
			studyOID.setNodeValue(study.getStudyOID()); // update OID field (it may have been translated)
			String studyHashKey = "Study: " + study.getStudyName() + ", Site: " + study.getSiteName();
			if (studies.containsKey(studyHashKey)) {
				study = studies.get(studyHashKey);
			} else {
				studies.put(studyHashKey, study);
				logger.debug("resolveMe() calling connector.populateStudy()");
				connector.populateStudy(study); // fetch study from OC
			}
			logger.debug("studies: " + studies.keySet());
			// for each SubjectData j
			NodeList subjectDatas = xPath(clinicalData, XPATH_SUBJECT_DATA);
			for (int j = 0; j < subjectDatas.getLength(); ++j) {
				Node subjectData = subjectDatas.item(j); // ---- SubjectData j ----
				StudySubject subject = createStudySubject(study, subjectData);
				for (StudySubject s : study.getStudySubjects()) { // make sure we reuse existing subjects...
					if (s.getStudySubjectLabel().equals(subject.getStudySubjectLabel())) {
						subject = s;
						break;
					}
				}
				handleSubjectDataNode(subjectData, subject);
				try {
					connector.updateOID(subject); // if this works it must be in the study (and in our model)
				} catch (OCConnectorException e) {
					if (createOrNot(subjectData)) {
						logger.info("Creating study subject...");
						connector.createStudySubject(subject);
						connector.updateOID(subject);
						study.getStudySubjects().add(subject); // update model
					} else {
						throw e;
					}
				}
				getAttribute(subjectData, "SubjectKey").setNodeValue(subject.getStudySubjectOID());

				// for each EventData k
				NodeList eventDatas = xPath(subjectData, XPATH_STUDYEVENTDATA);
				for (int k = 0; k < eventDatas.getLength(); ++k) {
					String eventOID;
					Node eventData = eventDatas.item(k); // ---- EventData k ----
					eventOID = getAttribute(eventData, ATTR_STUDY_EVENT_OID).getNodeValue();
					boolean hasEvent = false;
					for (ScheduledEvent scheduledEvent : subject.getScheduledEvents()) {
						if (scheduledEvent.getEventOID().equals(eventOID)) {
							hasEvent = true;
							break;
						}
					}
					if (!hasEvent) { // no scheduled event
						if (createOrNot(eventData)) { // schedule one
							ScheduledEvent scheduledEvent = new ScheduledEvent(study.getEventDefinition(eventOID));
							// scheduling happens here...
							try {
								scheduledEvent.setStartDate(getAttribute(eventData, ATTR_OC_START_DATE).getNodeValue());
							} catch (ODMException e1) {
								// Ignore odm exception here as we can do
								// without a start date
							}
							logger.info("Scheduling event...");
							connector.scheduleEvent(subject, scheduledEvent);
							subject.getScheduledEvents().add(scheduledEvent); // update model
						} else { // event not found (not scheduled)
							throw new ODMException("Event with OID '" + eventOID + "' not found!");
						}
					}
				}
			}
		}
		removeAttributes(this.odm, "//@Mirth:*");
		removeAttributes(this.odm, "//@OpenClinica:*[.='<VALUE>']");
		return studies.values();
	}

	/**
	 * Hook for additional node processing at subject data level
	 * @param subjectData SubjectData node 
	 * @param subject study subject
	 * @throws ODMException
	 */
	protected void handleSubjectDataNode(Node subjectData, StudySubject subject) throws ODMException {
	}

	/**
	 * Hook for additional node processing at EventData level
	 * @param eventData event data node
	 * @param event the event
	 * @throws ODMException
	 */
	protected void handleEventDataNode(Node eventData, ScheduledEvent event) throws ODMException {
	}

	/**
	 * Resolve clinical OMD ("this")
	 * @return ref to clean "this" as a shortcut
	 * @throws OCConnectorException
	 * @throws ODMException
	 * @throws DatatypeConfigurationException
	 */
	public ClinicalODM resolveOdmDocument() throws OCConnectorException, ODMException,
			DatatypeConfigurationException {
		resolveMe();
		return this;
	}

	// TODO: The cleaning part needs to be cleaned up and perhaps remodeled.
	/**
	 * Clean "this" but more than with clean(), hence the name...
	 * @return clean version of "this" as a shortcut.
	 * @throws ODMException
	 */
	public ClinicalODM extraClean() throws ODMException {
		this.clean();
		logger.debug("extraClean() (before): " + this);
		removeAttributes(this.odm, "//@Mirth:*");
		removeAttributes(this.odm, "//@OpenClinica:*[.='<VALUE>']");
		removeAttributes(this.odm, "//@StudyEventRepeatKey[.='<VALUE>']");
		removeAttributes(this.odm, "//@ItemGroupRepeatKey[.='<VALUE>']");
		logger.debug("extraClean() (after): " + this);
		return this;		
	}

	/**
	 * Clear all study data
	 */
	public void clearCache() {
		studies = new HashMap<String, Study>(); // <unique id (study name + sitename), study object>
	}

	/**
	 * Return web service connector
	 * @return web service connector
	 */
	public OCWebServices getConnector() {
		return connector;
	}

	/**
	 * Set web service connector
	 * @param connector web service connector
	 */
	public void setConnector(OCWebServices connector) {
		this.connector = connector;
	}


}
