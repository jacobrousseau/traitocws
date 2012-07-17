
package org.openclinica.ws.beans;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for subjectType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="subjectType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="uniqueIdentifier" type="{http://openclinica.org/ws/beans}customStringType" minOccurs="0"/>
 *         &lt;element name="gender" type="{http://openclinica.org/ws/beans}genderType" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="dateOfBirth" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *           &lt;element name="yearOfBirth" type="{http://openclinica.org/ws/beans}customDateType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subjectType", propOrder = {
    "uniqueIdentifier",
    "gender",
    "dateOfBirth",
    "yearOfBirth"
})
public class SubjectType {

    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String uniqueIdentifier;
    protected GenderType gender;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateOfBirth;
    protected BigInteger yearOfBirth;

    /**
     * Gets the value of the uniqueIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    /**
     * Sets the value of the uniqueIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueIdentifier(String value) {
        this.uniqueIdentifier = value;
    }

    /**
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link GenderType }
     *     
     */
    public GenderType getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenderType }
     *     
     */
    public void setGender(GenderType value) {
        this.gender = value;
    }

    /**
     * Gets the value of the dateOfBirth property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the value of the dateOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateOfBirth(XMLGregorianCalendar value) {
        this.dateOfBirth = value;
    }

    /**
     * Gets the value of the yearOfBirth property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getYearOfBirth() {
        return yearOfBirth;
    }

    /**
     * Sets the value of the yearOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setYearOfBirth(BigInteger value) {
        this.yearOfBirth = value;
    }

}
