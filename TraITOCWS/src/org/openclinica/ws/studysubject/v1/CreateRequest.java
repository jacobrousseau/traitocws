
package org.openclinica.ws.studysubject.v1;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="studySubject" type="{http://openclinica.org/ws/beans}studySubjectType" maxOccurs="unbounded"/>
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
@XmlRootElement(name = "createRequest")
@SuppressWarnings("javadoc")
public class CreateRequest {

    @XmlElement(required = true)
    protected List<StudySubjectType> studySubject;

    /**
     * Gets the value of the studySubject property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the studySubject property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStudySubject().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StudySubjectType }
     * 
     * 
     */
    public List<StudySubjectType> getStudySubject() {
        if (studySubject == null) {
            studySubject = new ArrayList<StudySubjectType>();
        }
        return this.studySubject;
    }

}
