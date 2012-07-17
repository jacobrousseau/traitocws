
package org.openclinica.ws.data.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.openclinica.ws.data.v1 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
@SuppressWarnings("javadoc")
public class ObjectFactory {

    private final static QName _ImportRequest_QNAME = new QName("http://openclinica.org/ws/data/v1", "importRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.openclinica.ws.data.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ImportResponse }
     * 
     */
    public ImportResponse createImportResponse() {
        return new ImportResponse();
    }

    /**
     * Create an instance of {@link AuditMessagesType }
     * 
     */
    public AuditMessagesType createAuditMessagesType() {
        return new AuditMessagesType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://openclinica.org/ws/data/v1", name = "importRequest")
    public JAXBElement<Object> createImportRequest(Object value) {
        return new JAXBElement<Object>(_ImportRequest_QNAME, Object.class, null, value);
    }

}
