
package org.openclinica.ws.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for eventType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eventType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="studySubjectRef" type="{http://openclinica.org/ws/beans}studySubjectRefType"/>
 *         &lt;element name="studyRef" type="{http://openclinica.org/ws/beans}studyRefType"/>
 *         &lt;element name="eventDefinitionOID" type="{http://openclinica.org/ws/beans}customStringType"/>
 *         &lt;element name="location" type="{http://openclinica.org/ws/beans}customStringType"/>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}time" minOccurs="0"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="endTime" type="{http://www.w3.org/2001/XMLSchema}time" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eventType", propOrder = {
    "studySubjectRef",
    "studyRef",
    "eventDefinitionOID",
    "location",
    "startDate",
    "startTime",
    "endDate",
    "endTime"
})
public class EventType {

    @XmlElement(required = true)
    protected StudySubjectRefType studySubjectRef;
    @XmlElement(required = true)
    protected StudyRefType studyRef;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String eventDefinitionOID;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String location;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDate;
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar startTime;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar endTime;

    /**
     * Gets the value of the studySubjectRef property.
     * 
     * @return
     *     possible object is
     *     {@link StudySubjectRefType }
     *     
     */
    public StudySubjectRefType getStudySubjectRef() {
        return studySubjectRef;
    }

    /**
     * Sets the value of the studySubjectRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudySubjectRefType }
     *     
     */
    public void setStudySubjectRef(StudySubjectRefType value) {
        this.studySubjectRef = value;
    }

    /**
     * Gets the value of the studyRef property.
     * 
     * @return
     *     possible object is
     *     {@link StudyRefType }
     *     
     */
    public StudyRefType getStudyRef() {
        return studyRef;
    }

    /**
     * Sets the value of the studyRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudyRefType }
     *     
     */
    public void setStudyRef(StudyRefType value) {
        this.studyRef = value;
    }

    /**
     * Gets the value of the eventDefinitionOID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventDefinitionOID() {
        return eventDefinitionOID;
    }

    /**
     * Sets the value of the eventDefinitionOID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventDefinitionOID(String value) {
        this.eventDefinitionOID = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartTime(XMLGregorianCalendar value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndTime(XMLGregorianCalendar value) {
        this.endTime = value;
    }

}
