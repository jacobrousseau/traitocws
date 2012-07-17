
package org.openclinica.ws.studysubject.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.openclinica.ws.beans.StudySubjectType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="studySubject" type="{http://openclinica.org/ws/beans}studySubjectType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "studySubject"
})
@XmlRootElement(name = "isStudySubjectRequest")
public class IsStudySubjectRequest {

    @XmlElement(required = true)
    protected StudySubjectType studySubject;

    /**
     * Gets the value of the studySubject property.
     * 
     * @return
     *     possible object is
     *     {@link StudySubjectType }
     *     
     */
    public StudySubjectType getStudySubject() {
        return studySubject;
    }

    /**
     * Sets the value of the studySubject property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudySubjectType }
     *     
     */
    public void setStudySubject(StudySubjectType value) {
        this.studySubject = value;
    }

}
