
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
 * <p>Java class for studySubjectWithEventsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="studySubjectWithEventsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="label" type="{http://openclinica.org/ws/beans}customStringType" minOccurs="0"/>
 *         &lt;element name="secondaryLabel" type="{http://openclinica.org/ws/beans}customStringType" minOccurs="0"/>
 *         &lt;element name="enrollmentDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="subject" type="{http://openclinica.org/ws/beans}subjectType"/>
 *         &lt;element name="studyRef" type="{http://openclinica.org/ws/beans}studyRefType"/>
 *         &lt;element name="events" type="{http://openclinica.org/ws/beans}eventsType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "studySubjectWithEventsType", propOrder = {
    "label",
    "secondaryLabel",
    "enrollmentDate",
    "subject",
    "studyRef",
    "events"
})
public class StudySubjectWithEventsType {

    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String label;
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String secondaryLabel;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar enrollmentDate;
    @XmlElement(required = true)
    protected SubjectType subject;
    @XmlElement(required = true)
    protected StudyRefType studyRef;
    @XmlElement(required = true)
    protected EventsType events;

    /**
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabel(String value) {
        this.label = value;
    }

    /**
     * Gets the value of the secondaryLabel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondaryLabel() {
        return secondaryLabel;
    }

    /**
     * Sets the value of the secondaryLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondaryLabel(String value) {
        this.secondaryLabel = value;
    }

    /**
     * Gets the value of the enrollmentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEnrollmentDate() {
        return enrollmentDate;
    }

    /**
     * Sets the value of the enrollmentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEnrollmentDate(XMLGregorianCalendar value) {
        this.enrollmentDate = value;
    }

    /**
     * Gets the value of the subject property.
     * 
     * @return
     *     possible object is
     *     {@link SubjectType }
     *     
     */
    public SubjectType getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubjectType }
     *     
     */
    public void setSubject(SubjectType value) {
        this.subject = value;
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
     * Gets the value of the events property.
     * 
     * @return
     *     possible object is
     *     {@link EventsType }
     *     
     */
    public EventsType getEvents() {
        return events;
    }

    /**
     * Sets the value of the events property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventsType }
     *     
     */
    public void setEvents(EventsType value) {
        this.events = value;
    }

}
