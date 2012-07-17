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

package nl.vumc.trait.oc.types;

import java.util.ArrayList;

import nl.vumc.trait.oc.odm.ODMException;

import org.openclinica.ws.beans.StudyType;

/**
 * Simple representation of a Study
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl)
 */
public class Study {

	/** site name (unique protocol ident) */
	private String siteName;
	/** study name */
	private String studyName;
	/** study oid */
	private String studyOID;
	/** events defined for this study */
	private ArrayList<Event> events;
	/** subjects in this study */
	private ArrayList<StudySubject> studySubjects;

	/** create an empty study */
	public Study() {
	}

	/**
	 * create a study from an OC StudyType object
	 * @param ocStudy 
	 */
	public Study(StudyType ocStudy) {
		studyName = ocStudy.getName();
		studyOID = ocStudy.getOid();
	}

	/**
	 * Get siteName
	 * @return siteName
	 */
	public String getSiteName() {
		return siteName;
	}

	/**
	 * Set site name
	 * @param siteName site name
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	/**
	 * Get study name (unique identifier kind of name)
	 * @return study name
	 */
	public String getStudyName() {
		return studyName;
	}

	/**
	 * Set study name (unique identifier kind of name)
	 * @param studyName study name
	 */
	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	/**
	 * Get study OID
	 * @return study OID
	 */
	public String getStudyOID() {
		return studyOID;
	}

	/**
	 * Set study OID
	 * @param studyOID studyOID
	 */
	public void setStudyOID(String studyOID) {
		this.studyOID = studyOID;
	}

	/**
	 * Get events defined for this study
	 * @return events defined for this study
	 */
	public ArrayList<Event> getEvents() {
		if (events == null) {
			events = new ArrayList<Event>();
		}
		return events;
	}

	/**
	 * Set events defined for this study
	 * @param events events for this study
	 */
	public void setEvents(ArrayList<Event> events) {
		this.events = events;
	}

	/**
	 * Get subjects in this study
	 * @return list of subjects
	 */
	public ArrayList<StudySubject> getStudySubjects() {
		if (studySubjects == null) {
			studySubjects = new ArrayList<StudySubject>();
		}
		return studySubjects;
	}

	/**
	 * Set list of subjects in this study
	 * @param studySubjects subjects in this study
	 */
	public void setStudySubjects(ArrayList<StudySubject> studySubjects) {
		this.studySubjects = studySubjects;
	}

	/**
	 * Check whether or not site name is defined
	 * @return has sitename or not (bool)
	 */
	public boolean hasSiteName() {
		return !(siteName == null || siteName.equals(""));
	}

	/**
	 * Unset sitename
	 */
	public void removeSiteName() {
		siteName = "";
	}
	
	/**
	 * Return a specific event (by OID) defined in this study
	 * @param eventDefOID
	 * @return the event definition for the specified event
	 * @throws ODMException if the event specified does not exist
	 */
	public Event getEventDefinition(String eventDefOID) throws ODMException {
		for (Event e : getEvents()) { // is it defined in study?
			if (e.getEventOID().equals(eventDefOID)) {
				return e;
			}
		}
		throw new ODMException("No event definition with OID '" + eventDefOID + "' defined for this study!");
	}

	@Override
	public String toString() {
		return "Study: studyName: " + studyName + ", siteName: " + siteName + ", studyOID: " + studyOID
				+ ", Defined events: " + events + ", Study Subjects: " + studySubjects;
	}

	@Override
	public int hashCode() {
		return (studyName + studyOID).hashCode();
	}

	
}
