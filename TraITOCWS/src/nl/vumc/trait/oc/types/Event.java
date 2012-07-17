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


import javax.xml.datatype.DatatypeConfigurationException;

import org.openclinica.ws.beans.EventType;

/**
 * Simple representation of an Event
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl)
 */
public class Event {

	/** name of the event */
	protected String eventName;
	/** event OID */
	protected String eventOID;

	/**
	 * create a new event
	 * @throws DatatypeConfigurationException 
	 */
	public Event() throws DatatypeConfigurationException {
	}

	/**
	 * create a new event from an OC EventType object
	 * @param event OC event to initialize from
	 * @throws DatatypeConfigurationException 
	 */
	public Event(EventType event) throws DatatypeConfigurationException {
		this();
		setEventOID(event.getEventDefinitionOID());
	}

	/**
	 * Get the event name
	 * @return event name
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * Set the event name
	 * @param eventName event name
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * Get the event OID
	 * @return event OID
	 */
	public String getEventOID() {
		return eventOID;
	}

	/**
	 * Set the event OID
	 * @param eventOID event OID
	 */
	public void setEventOID(String eventOID) {
		this.eventOID = eventOID;
	}

	@Override
	public String toString() {
		return "Event: eventName: " + eventName + ", eventOID: " + eventOID;
	}

}
