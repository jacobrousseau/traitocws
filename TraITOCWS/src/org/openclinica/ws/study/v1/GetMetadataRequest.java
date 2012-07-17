
package org.openclinica.ws.study.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.openclinica.ws.beans.SiteRefType;


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
 *         &lt;element name="studyMetadata" type="{http://openclinica.org/ws/beans}siteRefType"/>
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
    "studyMetadata"
})
@XmlRootElement(name = "getMetadataRequest")
public class GetMetadataRequest {

    @XmlElement(required = true)
    protected SiteRefType studyMetadata;

    /**
     * Gets the value of the studyMetadata property.
     * 
     * @return
     *     possible object is
     *     {@link SiteRefType }
     *     
     */
    public SiteRefType getStudyMetadata() {
        return studyMetadata;
    }

    /**
     * Sets the value of the studyMetadata property.
     * 
     * @param value
     *     allowed object is
     *     {@link SiteRefType }
     *     
     */
    public void setStudyMetadata(SiteRefType value) {
        this.studyMetadata = value;
    }

}
