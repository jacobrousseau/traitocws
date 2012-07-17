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

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;

import nl.vumc.trait.oc.odm.MetadataODM;
import nl.vumc.trait.oc.odm.ODMException;
import nl.vumc.trait.oc.types.Event;
import nl.vumc.trait.oc.types.ScheduledEvent;
import nl.vumc.trait.oc.types.Study;
import nl.vumc.trait.oc.types.StudySubject;

import org.openclinica.ws.beans.EventType;
import org.openclinica.ws.beans.GenderType;
import org.openclinica.ws.beans.ListStudySubjectsInStudyType;
import org.openclinica.ws.beans.SiteRefType;
import org.openclinica.ws.beans.SiteType;
import org.openclinica.ws.beans.StudyEventDefinitionListAllType;
import org.openclinica.ws.beans.StudyEventDefinitionType;
import org.openclinica.ws.beans.StudyRefType;
import org.openclinica.ws.beans.StudySubjectRefType;
import org.openclinica.ws.beans.StudySubjectType;
import org.openclinica.ws.beans.StudySubjectWithEventsType;
import org.openclinica.ws.beans.StudyType;
import org.openclinica.ws.beans.SubjectType;
import org.openclinica.ws.data.v1.ImportResponse;
import org.openclinica.ws.event.v1.ScheduleRequest;
import org.openclinica.ws.event.v1.ScheduleResponse;
import org.openclinica.ws.study.v1.GetMetadataRequest;
import org.openclinica.ws.study.v1.GetMetadataResponse;
import org.openclinica.ws.study.v1.ListAllResponse;
import org.openclinica.ws.studyeventdefinition.v1.ListAllRequest;
import org.openclinica.ws.studysubject.v1.CreateRequest;
import org.openclinica.ws.studysubject.v1.CreateResponse;
import org.openclinica.ws.studysubject.v1.IsStudySubjectRequest;
import org.openclinica.ws.studysubject.v1.IsStudySubjectResponse;
import org.openclinica.ws.studysubject.v1.ListAllByStudyResponse;
import org.xml.sax.SAXException;

/**
 * Contains basic OpenClinica operations.
 * 
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl)
 */
public class OCWebServices extends OCConnector {

	// ================================================================================================================

	// we are a connector. implement the obliged stuff here...

	/**
	 * static OCWebServices instance. We are a singleton.
	 */
	private static OCWebServices instance;

	/**
	 * Disables public access to default constructor.
	 * @throws DatatypeConfigurationException
	 */
	protected OCWebServices() throws DatatypeConfigurationException {
		super();
	}

	/**
	 * Setup a OCWebServices instance.
	 * @param connectInfo credentials
	 * @param logging logging yes or no
	 * @throws MalformedURLException
	 * @throws ParserConfigurationException
	 * @throws DatatypeConfigurationException
	 */
	protected OCWebServices(ConnectInfo connectInfo, boolean logging) throws MalformedURLException,
			ParserConfigurationException, DatatypeConfigurationException {
		super(connectInfo, logging);
	}

	/**
	 * Get a OCWebServices instance.
	 * @param connectInfo credentials
	 * @return OCWebServices instance
	 * @throws MalformedURLException
	 * @throws ParserConfigurationException
	 * @throws DatatypeConfigurationException
	 */
	public static OCWebServices getInstance(ConnectInfo connectInfo) throws MalformedURLException,
			ParserConfigurationException, DatatypeConfigurationException {
		return getInstance(connectInfo, false, false);
	}

	/**
	 * Get a OCWebServices instance.
	 * @param connectInfo credentials
	 * @param logging logging yes or no
	 * @return OCWebServices instance
	 * @throws MalformedURLException
	 * @throws ParserConfigurationException
	 * @throws DatatypeConfigurationException
	 */
	public static OCWebServices getInstance(ConnectInfo connectInfo, boolean logging) throws MalformedURLException,
			ParserConfigurationException, DatatypeConfigurationException {
		return getInstance(connectInfo, logging, false);
	}

	/**
	 * Get a OCWebServices instance.
	 * @param connectInfo credentials
	 * @param logging logging yes or no
	 * @param forceInstantiation flag to force creation of a new instance
	 * @return OCWebServices instance
	 * @throws MalformedURLException
	 * @throws ParserConfigurationException
	 * @throws DatatypeConfigurationException
	 */
	public static OCWebServices getInstance(ConnectInfo connectInfo, boolean logging, boolean forceInstantiation)
			throws MalformedURLException, ParserConfigurationException, DatatypeConfigurationException {
		if (forceInstantiation || instance == null || instance.isLogging() != logging) {
			instance = new OCWebServices(connectInfo, logging);
		}
		return instance;
	}

	// ...end connector stuff.

	// ================================================================================================================

	/**
	 * List all accessible studies
	 *
	 * @return all accessible studies
	 *         (org.openclinica.ws.study.v1.ListAllResponse)
	 * @throws OCConnectorException 
	 */
	public ListAllResponse listAllStudies() throws OCConnectorException {
		// TODO: catch errors (see listAllByStudy)
		ListAllResponse response = studyBinding.listAll(null);
		checkResponseExceptions(response.getResult(), response.getError());
		return response;
	}

	/**
	 * List all study subjects for a given study.
	 *
	 * @param study OpenClinica study
	 * @return list of study subjects
	 *         (org.openclinica.ws.studysubject.v1.ListAllByStudyResponse)
	 * @throws OCConnectorException 
	 */
	public ListAllByStudyResponse listAllByStudy(Study study) throws OCConnectorException {
		// TODO: copy this type of error handling to all ws calling methods...
		ListStudySubjectsInStudyType request = new ListStudySubjectsInStudyType();
		StudyRefType studyRef = new StudyRefType();
		studyRef.setIdentifier(study.getStudyName());
		request.setStudyRef(studyRef);
		if (study.hasSiteName()) {
			SiteRefType siteref = new SiteRefType();
			siteref.setIdentifier(study.getSiteName());
			studyRef.setSiteRef(siteref);
		}
		ListAllByStudyResponse response;
		try {
			response = studySubjectBinding.listAllByStudy(request);
		} catch (Exception e) {
			throw new OCConnectorException("Exception while calling OpenClinica web service\n", e);
		}
		checkResponseExceptions(response.getResult(), response.getError());
		return response;
	}

	/**
	 * Call isStudySubject() OC method.
	 * @param studySubject study subject
	 * @param submitDate control whether registration date is passed or not
	 * @return IsStudySubjectResponse object (possibly containing OID)
	 * @throws DatatypeConfigurationException
	 * @throws OCConnectorException
	 */
	public IsStudySubjectResponse isStudySubject(StudySubject studySubject, boolean submitDate)
			throws DatatypeConfigurationException, OCConnectorException {
		// TODO: catch errors (see listAllByStudy)
		Study study = studySubject.getStudy();
		IsStudySubjectRequest request = new IsStudySubjectRequest();
		StudyRefType studyRef = new StudyRefType();
		StudySubjectType ocSubject = new StudySubjectType();
		studyRef.setIdentifier(study.getStudyName());
		ocSubject.setLabel(studySubject.getStudySubjectLabel());
		ocSubject.setStudyRef(studyRef);
		if (submitDate) {
			ocSubject.setEnrollmentDate(studySubject.getDateOfRegistration());
		}
		if (study.hasSiteName()) {
			SiteRefType siteref = new SiteRefType();
			siteref.setIdentifier(study.getSiteName());
			studyRef.setSiteRef(siteref);
		}
		request.setStudySubject(ocSubject);
		IsStudySubjectResponse response = studySubjectBinding.isStudySubject(request);
		checkResponseExceptions(response.getResult(), response.getError());
		return response;
	}

	/**
	 * Call isStudySubject() OpenClinica method (passed registration date)
	 * @param studySubject study subject
	 * @return IsStudySubjectResponse object (possibly containing OID)
	 * @throws DatatypeConfigurationException
	 * @throws OCConnectorException
	 */
	public IsStudySubjectResponse isStudySubject(StudySubject studySubject) throws DatatypeConfigurationException,
			OCConnectorException {
		return isStudySubject(studySubject, true);
	}

	/**
	 * Schedule an event for a study subject
	 * @param studySubject the study subject to schedule the event for
	 * @param scheduledEvent event to be scheduled
	 * @return ScheduleResponse containing success or fail
	 * @throws OCConnectorException
	 */
	public ScheduleResponse scheduleEvent(StudySubject studySubject, ScheduledEvent scheduledEvent)
			throws OCConnectorException {
		// TODO: catch errors (see listAllByStudy)
		// set event date
		Study study = studySubject.getStudy();
		StudyRefType studyRef = new StudyRefType();
		studyRef.setIdentifier(study.getStudyName());
		StudySubjectRefType studySubjectRef = new StudySubjectRefType();
		studySubjectRef.setLabel(studySubject.getStudySubjectLabel());
		EventType event = new EventType();
		event.setStartDate(scheduledEvent.getStartDate());
		event.setStudyRef(studyRef);
		event.setStudySubjectRef(studySubjectRef);
		event.setEventDefinitionOID(scheduledEvent.getEventOID());
		event.setLocation("N/A"); // hmm, why is this required? the UI does not
									// enforce it.
		ScheduleRequest scheduleRequest = new ScheduleRequest();
		scheduleRequest.getEvent().add(event);
		if (study.hasSiteName()) {
			SiteRefType siteref = new SiteRefType();
			siteref.setIdentifier(study.getSiteName());
			studyRef.setSiteRef(siteref);
		}
		ScheduleResponse scheduleResponse = eventBinding.schedule(scheduleRequest);
		checkResponseExceptions(scheduleResponse.getResult(), scheduleResponse.getError());
		return scheduleResponse;
	}

	/**
	 * Create subject, update subject label if generated by OpenClinica.
	 * @param studySubject the study subject to create
	 * @return CreateResponse response from OpenClinica
	 * @throws DatatypeConfigurationException
	 * @throws OCConnectorException
	 */
	public CreateResponse createStudySubject(StudySubject studySubject) throws DatatypeConfigurationException,
			OCConnectorException {
		// TODO: catch errors (see listAllByStudy)

		Study study = studySubject.getStudy();

		// subject
		SubjectType subject = new SubjectType();
		subject.setDateOfBirth(studySubject.getDateOfBirth());
		if (studySubject.getSex()!= null) {
			subject.setGender(GenderType.fromValue(studySubject.getSex()));
		}
		subject.setUniqueIdentifier(studySubject.getPersonID());

		// reference to study
		StudyRefType studyRef = new StudyRefType();
		studyRef.setIdentifier(study.getStudyName());
		if (study.hasSiteName()) {
			SiteRefType siteref = new SiteRefType();
			siteref.setIdentifier(study.getSiteName());
			studyRef.setSiteRef(siteref);
		}
		// combine to create studysubject
		StudySubjectType newStudySubject = new StudySubjectType();
		newStudySubject.setEnrollmentDate(studySubject.getDateOfRegistration());
		newStudySubject.setLabel(studySubject.getStudySubjectLabel());
		newStudySubject.setStudyRef(studyRef);
		newStudySubject.setSubject(subject);

		// create a create-request
		CreateRequest create = new CreateRequest();
		create.getStudySubject().add(newStudySubject);
		CreateResponse createResponse = studySubjectBinding.create(create);
		checkResponseExceptions(createResponse.getResult(), createResponse.getError());
		studySubject.setStudySubjectLabel(createResponse.getLabel());
		return createResponse;
	}

	/**
	 * Call the data import OpenClinica method dataImport() submitting ODM as a string. 
	 * @param odm The ODM to be loaded. NB: OpenClinica has limited support for direct data loading.
	 * @return OpenClinica reponse.
	 * @throws OCConnectorException
	 */
	public ImportResponse importODM(String odm) throws OCConnectorException {
		// TODO: catch errors (see listAllByStudy) (also, this method looks a
		// bit short...)
		// TODO: See OC manual on limitations which are the main motivation
		// for this code in the first place
		ImportResponse response = dataBinding.dataImport(odm);
		checkResponseExceptions(response.getResult(), response.getError());
		return response;
	}

	/**
	 * Check whether a specific event has been scheduled for a given study subject based on the event's OID.
	 * @param studySubject the study subject to check for. an exception is raised in case the subject does not exist.
	 * @param queryEvent the event to check for (only the event OID will be used in the comparison).
	 * @return true is the query event is scheduled for the study subject
	 * @throws OCConnectorException
	 */
	public boolean studySubjectHasEvent(StudySubject studySubject, Event queryEvent)
			throws OCConnectorException {
		// TODO: catch errors (see listAllByStudy)
		Study study = studySubject.getStudy();
		ListAllByStudyResponse subjects = listAllByStudy(study);
		boolean isSubject = false;
		if (subjects.getStudySubjects().getStudySubject() != null) {
			for (StudySubjectWithEventsType subject : subjects.getStudySubjects().getStudySubject()) {
				if (subject.getLabel().equals(studySubject.getStudySubjectLabel())
						&& subject.getEnrollmentDate().equals(studySubject.getDateOfRegistration())) {
					isSubject = true;
					if (subject.getEvents().getEvent() != null) {
						for (EventType event : subject.getEvents().getEvent()) {
							if (event.getEventDefinitionOID().equals(queryEvent.getEventOID())) {
								return true;
							}
						}
					}
				}
			}
		}
		if (!isSubject) {
			throw new OCConnectorException("Subject '" + studySubject.getStudySubjectLabel() + "' enrolled on '"
					+ studySubject.getDateOfRegistration() + "' does not exist in study '" + study.getStudyName()
					+ "'!");
		}
		return false;
	}

	/**
	 * Retrieve the metadata ODM for a given study.
	 * @param study the study to retrieve metadata for.
	 * @return Metadata ODM for the study
	 * @throws OCConnectorException
	 * @throws ODMException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public MetadataODM fetchStudyMetadata(Study study) throws OCConnectorException, ODMException, SAXException,
			IOException, ParserConfigurationException {
		// TODO: catch errors (see listAllByStudy)
		SiteRefType siteRef = new SiteRefType();
		GetMetadataRequest request = new GetMetadataRequest();
		siteRef.setIdentifier(study.getStudyName());
		request.setStudyMetadata(siteRef);
		GetMetadataResponse response = studyBinding.getMetadata(request);
		checkResponseExceptions(response.getResult(), response.getError());
		return new MetadataODM(response.getOdm());
	}

	/**
	 * Retrieve event definitions for a given study.
	 * @param study the study to query for.
	 * @return a list of Event objects based on the study provided.
	 * @throws OCConnectorException
	 * @throws DatatypeConfigurationException
	 */
	public ArrayList<Event> fetchEventDefinitions(Study study) throws OCConnectorException,
			DatatypeConfigurationException {
		// TODO: catch errors (see listAllByStudy)
		ArrayList<Event> result = new ArrayList<Event>();
		StudyEventDefinitionListAllType studyEventDefinitionListAllType = new StudyEventDefinitionListAllType();
		StudyRefType studyRef = new StudyRefType();
		studyRef.setIdentifier(study.getStudyName());
		studyEventDefinitionListAllType.setStudyRef(studyRef);
		ListAllRequest listAllRequest = new ListAllRequest();
		listAllRequest.setStudyEventDefinitionListAll(studyEventDefinitionListAllType);
		org.openclinica.ws.studyeventdefinition.v1.ListAllResponse listAllResponse = studyEventDefinitionBinding
				.listAll(listAllRequest);
		checkResponseExceptions(listAllResponse.getResult(), listAllResponse.getError());
		if (listAllResponse.getStudyEventDefinitions().getStudyEventDefinition() == null) {
			throw new OCConnectorException("Cannot retreive event data or no events defined.");
		}
		for (StudyEventDefinitionType d : listAllResponse.getStudyEventDefinitions().getStudyEventDefinition()) {
			Event newEvent = new Event();
			newEvent.setEventOID(d.getOid());
			newEvent.setEventName(d.getName());
			result.add(newEvent);
		}
		return result;
	}

	/**
	 * Find a study based on either a OID or a study name by calling the OpenClinica
	 * listAllStudies() method. (so, this method performs a WS call. Used the overloaded
	 * findStudy() to use an existing ListAllResponse object to search in.
	 * @param studyIdentifier the string to look for
	 * @param byOID set to true if studyIdentifier is an OID
	 * @return a Study object containing the information returned by listAllStudies()
	 * for the given study.
	 * @throws OCConnectorException
	 */
	public Study findStudy(String studyIdentifier, boolean byOID) throws OCConnectorException {
		ListAllResponse allStudies = listAllStudies();
		return findStudy(allStudies, studyIdentifier, byOID);
	}

	/**
	 * Find a study based on either a OID or a study name by in a ListAllResponse object.
	 * (so, this method does NOT performs a WS call. Used the overloaded findStudy() to
	 * call the listAllStudies() OpenClinica method instead of using an existing
	 * ListAllResponse object to search in.
	 * @param allStudies A ListAllResponse to search in.
	 * @param studyIdentifier the string to look for
	 * @param byOID set to true if studyIdentifier is an OID
	 * @return a Study object containing the information returned by listAllStudies()
	 * for the given study.
	 * @throws OCConnectorException
	 */
	public Study findStudy(ListAllResponse allStudies, String studyIdentifier, boolean byOID)
			throws OCConnectorException {
		Study result = new Study();
		boolean found = false;
		if (allStudies.getStudies() != null) {
			if (allStudies.getStudies().getStudy() != null) {
				List<StudyType> studies = allStudies.getStudies().getStudy();
				for (StudyType s : studies) { // look for study
					// iterate studies
					if (byOID && s.getOid().equals(studyIdentifier) || !byOID
							&& s.getIdentifier().equals(studyIdentifier)) {
						found = true;
						result.setStudyName(s.getIdentifier());
						result.setStudyOID(s.getOid());
						break;
					} else {
						if (s.getSites() != null) { // is it a site?
							if (s.getSites().getSite() != null) {
								for (SiteType site : s.getSites().getSite()) {
									// iterate sites
									if (byOID && site.getOid().equals(studyIdentifier) || !byOID
											&& site.getIdentifier().equals(studyIdentifier)) {
										found = true;
										result.setStudyName(s.getIdentifier());
										result.setSiteName(site.getIdentifier());
										result.setStudyOID(site.getOid());
										break;
									}
								}
							}
						}
					}
					if (found) {
						break;
					}
				}
			}
		}
		// not found
		if (!found) {
			throw new OCConnectorException("Study '" + studyIdentifier + "' does not exist.");
		} else {
			return result;
		}
	}

	/**
	 * Retrieve a study's related information such as study subjects, events and scheduled events
	 * for subjects. The Study object will be augmented with information fetched from OpenClinica
	 * web services. All subjects in the given study will be cleared and reloaded from OpenClinica and
	 * OIDs are NOT updated. (see overloaded populateStudy()).
	 * @param study The study to be populated.
	 * @throws OCConnectorException
	 * @throws DatatypeConfigurationException
	 */
	public void populateStudy(Study study) throws OCConnectorException, DatatypeConfigurationException {
		populateStudy(study, false, false);
	}

	/**
	 * Retrieve a study's related information such as study subjects, events and scheduled events
	 * for subjects. The Study object will be augmented with information fetched from OpenClinica
	 * web services.
	 * @param study The study to be populated
	 * @param fetchOIDs Fetch OIDs for all subjects in the study
	 * @param updateExistingSubjects update existing subjects yes or no. If set to false, all subjects
	 * will be removed and retrieved from OpenClinica. If set to true, an attempt will be made to load
	 * only subjects not in the study object yet. OIDs will be updated for all if fetchOID is set to true.
	 * @throws OCConnectorException
	 * @throws DatatypeConfigurationException
	 */
	public void populateStudy(Study study, boolean fetchOIDs, boolean updateExistingSubjects)
			throws OCConnectorException, DatatypeConfigurationException {
		HashMap<String, StudySubject> subjectLabels = new HashMap<String, StudySubject>();
		study.setEvents(null);
		if (!updateExistingSubjects) {
			study.setStudySubjects(null);
		} else {
			// create a set of all subjects for easy lookup
			for (StudySubject s : study.getStudySubjects()) {
				subjectLabels.put(s.getStudySubjectLabel(), s);
			}
		}
		study.setEvents(fetchEventDefinitions(study)); // get events
		ListAllByStudyResponse subjectsByStudy = listAllByStudy(study); // get all subjects
		if (subjectsByStudy.getStudySubjects() != null) {
			if (subjectsByStudy.getStudySubjects().getStudySubject() != null) {
				for (StudySubjectWithEventsType s : subjectsByStudy.getStudySubjects().getStudySubject()) { // for each subject
					StudySubject newSubject;
					if (subjectLabels.containsKey(s.getLabel())) { // update existing
						newSubject = subjectLabels.get(s.getLabel());
						newSubject.updateStudySubject(s);
					} else { // create new
						newSubject = new StudySubject(study, s);
					}
					study.getStudySubjects().add(newSubject);
					if (fetchOIDs) {
						updateOID(newSubject);
					}
					if (s.getEvents() != null) {
						if (s.getEvents().getEvent() != null) { // get all scheduled events
							newSubject.setScheduledEvents(null); // clear events for the subject (lest we wrongly update a subject's events)
							for (EventType event : s.getEvents().getEvent()) {
								ScheduledEvent newEvent = new ScheduledEvent(event);
								newSubject.getScheduledEvents().add(newEvent);
								for (Event e : study.getEvents()) { // find event name
									if (e.getEventOID().equals(newEvent.getEventOID())) {
										newEvent.setEventName(e.getEventName());
										break;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Call the isStudySubject() OpenClinica method in order to fetch and update the OID
	 * of a given StudySubject.
	 * @param subject The study subject to be updated
	 * @throws DatatypeConfigurationException
	 * @throws OCConnectorException
	 */
	public void updateOID(StudySubject subject) throws DatatypeConfigurationException, OCConnectorException   {
		IsStudySubjectResponse response = isStudySubject(subject);
		subject.setStudySubjectOID(response.getStudySubjectOID());
	}

}
