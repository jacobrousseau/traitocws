
package org.openclinica.ws.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for eventDefinitionCrfType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eventDefinitionCrfType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="required" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="doubleDataEntry" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="passwordRequired" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hideCrf" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="sourceDataVerificaiton" type="{http://openclinica.org/ws/beans}customStringType"/>
 *         &lt;element name="crf" type="{http://openclinica.org/ws/beans}crfObjType"/>
 *         &lt;element name="defaultCrfVersion" type="{http://openclinica.org/ws/beans}crfVersionType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eventDefinitionCrfType", propOrder = {
    "required",
    "doubleDataEntry",
    "passwordRequired",
    "hideCrf",
    "sourceDataVerificaiton",
    "crf",
    "defaultCrfVersion"
})
@SuppressWarnings("javadoc")
public class EventDefinitionCrfType {

    protected boolean required;
    protected boolean doubleDataEntry;
    protected boolean passwordRequired;
    protected boolean hideCrf;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String sourceDataVerificaiton;
    @XmlElement(required = true)
    protected CrfObjType crf;
    @XmlElement(required = true)
    protected CrfVersionType defaultCrfVersion;

    /**
     * Gets the value of the required property.
     * 
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Sets the value of the required property.
     * 
     */
    public void setRequired(boolean value) {
        this.required = value;
    }

    /**
     * Gets the value of the doubleDataEntry property.
     * 
     */
    public boolean isDoubleDataEntry() {
        return doubleDataEntry;
    }

    /**
     * Sets the value of the doubleDataEntry property.
     * 
     */
    public void setDoubleDataEntry(boolean value) {
        this.doubleDataEntry = value;
    }

    /**
     * Gets the value of the passwordRequired property.
     * 
     */
    public boolean isPasswordRequired() {
        return passwordRequired;
    }

    /**
     * Sets the value of the passwordRequired property.
     * 
     */
    public void setPasswordRequired(boolean value) {
        this.passwordRequired = value;
    }

    /**
     * Gets the value of the hideCrf property.
     * 
     */
    public boolean isHideCrf() {
        return hideCrf;
    }

    /**
     * Sets the value of the hideCrf property.
     * 
     */
    public void setHideCrf(boolean value) {
        this.hideCrf = value;
    }

    /**
     * Gets the value of the sourceDataVerificaiton property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceDataVerificaiton() {
        return sourceDataVerificaiton;
    }

    /**
     * Sets the value of the sourceDataVerificaiton property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceDataVerificaiton(String value) {
        this.sourceDataVerificaiton = value;
    }

    /**
     * Gets the value of the crf property.
     * 
     * @return
     *     possible object is
     *     {@link CrfObjType }
     *     
     */
    public CrfObjType getCrf() {
        return crf;
    }

    /**
     * Sets the value of the crf property.
     * 
     * @param value
     *     allowed object is
     *     {@link CrfObjType }
     *     
     */
    public void setCrf(CrfObjType value) {
        this.crf = value;
    }

    /**
     * Gets the value of the defaultCrfVersion property.
     * 
     * @return
     *     possible object is
     *     {@link CrfVersionType }
     *     
     */
    public CrfVersionType getDefaultCrfVersion() {
        return defaultCrfVersion;
    }

    /**
     * Sets the value of the defaultCrfVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link CrfVersionType }
     *     
     */
    public void setDefaultCrfVersion(CrfVersionType value) {
        this.defaultCrfVersion = value;
    }

}
