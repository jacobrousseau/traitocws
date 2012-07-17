
package org.openclinica.ws.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for studyEventDefinitionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="studyEventDefinitionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oid" type="{http://openclinica.org/ws/beans}customStringType"/>
 *         &lt;element name="name" type="{http://openclinica.org/ws/beans}customStringType"/>
 *         &lt;element name="eventDefinitionCrfs" type="{http://openclinica.org/ws/beans}crfsType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "studyEventDefinitionType", propOrder = {
    "oid",
    "name",
    "eventDefinitionCrfs"
})
public class StudyEventDefinitionType {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String oid;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String name;
    @XmlElement(required = true)
    protected CrfsType eventDefinitionCrfs;

    /**
     * Gets the value of the oid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOid() {
        return oid;
    }

    /**
     * Sets the value of the oid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOid(String value) {
        this.oid = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the eventDefinitionCrfs property.
     * 
     * @return
     *     possible object is
     *     {@link CrfsType }
     *     
     */
    public CrfsType getEventDefinitionCrfs() {
        return eventDefinitionCrfs;
    }

    /**
     * Sets the value of the eventDefinitionCrfs property.
     * 
     * @param value
     *     allowed object is
     *     {@link CrfsType }
     *     
     */
    public void setEventDefinitionCrfs(CrfsType value) {
        this.eventDefinitionCrfs = value;
    }

}
