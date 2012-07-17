package org.openclinica.ws.study.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.openclinica.ws.beans.StudiesType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="result" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="warning" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="error" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="studies" type="{http://openclinica.org/ws/beans}studiesType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "result", "warning", "error", "studies" })
@XmlRootElement(name = "listAllResponse")
@SuppressWarnings("javadoc")
public class ListAllResponse {

	@XmlElement(required = true)
	protected String result;
	protected List<String> warning;
	protected List<String> error;

	// TODO: related to OC issue report 13333
	// TODO: (https://issuetracker.openclinica.com/view.php?id=13333)
	// TODO: See also nl.vumc.trait.oc.connect.StudyListAllHandler
	// TODO: and the explicit change in namespace here for StudiesType
	@XmlElement(namespace = "http://openclinica.org/ws/beans", required = true)
	protected StudiesType studies;

	/**
	 * Gets the value of the result property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getResult() {
		return result;
	}

	/**
	 * Sets the value of the result property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setResult(String value) {
		this.result = value;
	}

	/**
	 * Gets the value of the warning property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the warning property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getWarning().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link String }
	 * 
	 * 
	 */
	public List<String> getWarning() {
		if (warning == null) {
			warning = new ArrayList<String>();
		}
		return this.warning;
	}

	/**
	 * Gets the value of the error property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the error property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getError().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link String }
	 * 
	 * 
	 */
	public List<String> getError() {
		if (error == null) {
			error = new ArrayList<String>();
		}
		return this.error;
	}

	/**
	 * Gets the value of the studies property.
	 * 
	 * @return possible object is {@link StudiesType }
	 * 
	 */
	public StudiesType getStudies() {
		return studies;
	}

	/**
	 * Sets the value of the studies property.
	 * 
	 * @param value
	 *            allowed object is {@link StudiesType }
	 * 
	 */
	public void setStudies(StudiesType value) {
		this.studies = value;
	}

}
