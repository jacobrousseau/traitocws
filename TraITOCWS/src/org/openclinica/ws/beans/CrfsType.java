
package org.openclinica.ws.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for crfsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="crfsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="crf" type="{http://openclinica.org/ws/beans}crfObjType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "crfsType", propOrder = {
    "crf"
})
public class CrfsType {

    protected CrfObjType crf;

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

}
