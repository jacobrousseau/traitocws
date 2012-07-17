
package org.openclinica.ws.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for studyMetadataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="studyMetadataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="studyRef" type="{http://openclinica.org/ws/beans}studyRefType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "studyMetadataType", propOrder = {
    "studyRef"
})
public class StudyMetadataType {

    @XmlElement(required = true)
    protected StudyRefType studyRef;

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

}
