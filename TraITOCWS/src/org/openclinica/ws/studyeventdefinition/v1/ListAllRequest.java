
package org.openclinica.ws.studyeventdefinition.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.openclinica.ws.beans.StudyEventDefinitionListAllType;


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
 *         &lt;element name="studyEventDefinitionListAll" type="{http://openclinica.org/ws/beans}studyEventDefinitionListAllType"/>
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
    "studyEventDefinitionListAll"
})
@XmlRootElement(name = "listAllRequest")
public class ListAllRequest {

    @XmlElement(required = true)
    protected StudyEventDefinitionListAllType studyEventDefinitionListAll;

    /**
     * Gets the value of the studyEventDefinitionListAll property.
     * 
     * @return
     *     possible object is
     *     {@link StudyEventDefinitionListAllType }
     *     
     */
    public StudyEventDefinitionListAllType getStudyEventDefinitionListAll() {
        return studyEventDefinitionListAll;
    }

    /**
     * Sets the value of the studyEventDefinitionListAll property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudyEventDefinitionListAllType }
     *     
     */
    public void setStudyEventDefinitionListAll(StudyEventDefinitionListAllType value) {
        this.studyEventDefinitionListAll = value;
    }

}
