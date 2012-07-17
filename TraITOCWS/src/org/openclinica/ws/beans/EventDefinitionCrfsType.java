
package org.openclinica.ws.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for eventDefinitionCrfsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eventDefinitionCrfsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="eventDefinitionCrf" type="{http://openclinica.org/ws/beans}eventDefinitionCrfType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eventDefinitionCrfsType", propOrder = {
    "eventDefinitionCrf"
})
@SuppressWarnings("javadoc")
public class EventDefinitionCrfsType {

    protected List<EventDefinitionCrfType> eventDefinitionCrf;

    /**
     * Gets the value of the eventDefinitionCrf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eventDefinitionCrf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEventDefinitionCrf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EventDefinitionCrfType }
     * 
     * 
     */
    public List<EventDefinitionCrfType> getEventDefinitionCrf() {
        if (eventDefinitionCrf == null) {
            eventDefinitionCrf = new ArrayList<EventDefinitionCrfType>();
        }
        return this.eventDefinitionCrf;
    }

}
