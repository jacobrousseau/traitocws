
package org.openclinica.ws.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for studyEventDefinitionsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="studyEventDefinitionsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="studyEventDefinition" type="{http://openclinica.org/ws/beans}studyEventDefinitionType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "studyEventDefinitionsType", propOrder = {
    "studyEventDefinition"
})
@SuppressWarnings("javadoc")
public class StudyEventDefinitionsType {

    protected List<StudyEventDefinitionType> studyEventDefinition;

    /**
     * Gets the value of the studyEventDefinition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the studyEventDefinition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStudyEventDefinition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StudyEventDefinitionType }
     * 
     * 
     */
    public List<StudyEventDefinitionType> getStudyEventDefinition() {
        if (studyEventDefinition == null) {
            studyEventDefinition = new ArrayList<StudyEventDefinitionType>();
        }
        return this.studyEventDefinition;
    }

}
