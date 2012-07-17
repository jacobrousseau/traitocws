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

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.openclinica.ws.beans.EventType;

/**
 * Simple representation of a Scheduled Event
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl)
 */
public class ScheduledEvent extends Event {

	/** XML Data type factory */
	private DatatypeFactory dataTypeFactory;
	/** event start date */
	private XMLGregorianCalendar startDate;
	/** event end date */
	private XMLGregorianCalendar endDate;

	/**
	 * create a new event
	 * @throws DatatypeConfigurationException
	 */
	public ScheduledEvent() throws DatatypeConfigurationException {
		super();
		dataTypeFactory = DatatypeFactory.newInstance();
		startDate = dataTypeFactory.newXMLGregorianCalendar(new GregorianCalendar());
		startDate.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
	}

	/**
	 * create a new event from an Event (not scheduled).
	 * @param event event
	 * @throws DatatypeConfigurationException
	 */
	public ScheduledEvent(Event event) throws DatatypeConfigurationException {
		this();
		this.eventOID = event.getEventOID();
		this.eventName = event.getEventName();
	}
	
	/**
	 * Create a new event from an OC EventType event
	 * @param event the OC event to initialize from
	 * @throws DatatypeConfigurationException
	 */
	public ScheduledEvent(EventType event) throws DatatypeConfigurationException {
		this();
		setStartDate(event.getStartDate());
		setEndDate(event.getEndDate());
		setEventOID(event.getEventDefinitionOID());
	}

	/**
	 * Get event start date
	 * @return event start date
	 */
	public XMLGregorianCalendar getStartDate() {
		return startDate;
	}

	/**
	 * Set event start date
	 * @param startDate event start date
	 */
	public void setStartDate(XMLGregorianCalendar startDate) {
		this.startDate = startDate;
		if (this.startDate != null) {
			this.startDate.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		}
	}

	/**
	 * Set event start date from String
	 * @param startDate event start date as String (yyyy-mm-dd)
	 */
	public void setStartDate(String startDate) {
		setStartDate(dataTypeFactory.newXMLGregorianCalendar(startDate));
	}

	/**
	 * Get event end date
	 * @return end date
	 */
	public XMLGregorianCalendar getEndDate() {
		return endDate;
	}

	/**
	 * Set event end date
	 * @param endDate event end date
	 */
	public void setEndDate(XMLGregorianCalendar endDate) {
		this.endDate = endDate;
		if (this.endDate != null) {
			this.endDate.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		}
	}

	/**
	 * Set event end date from String
	 * @param endDate event end date as String (yyyy-mm-dd)
	 */
	public void setEndDate(String endDate) {
		setEndDate(dataTypeFactory.newXMLGregorianCalendar(endDate));
	}

	@Override
	public String toString() {
		return "ScheduledEvent: eventName: " + eventName + ", eventOID: " + eventOID + ", startDate: " + startDate
				+ ", endDate: " + endDate;
	}

}
