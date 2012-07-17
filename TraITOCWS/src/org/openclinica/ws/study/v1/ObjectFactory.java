
package org.openclinica.ws.study.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.openclinica.ws.study.v1 package. 
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

    private final static QName _ListAllRequest_QNAME = new QName("http://openclinica.org/ws/study/v1", "listAllRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.openclinica.ws.study.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetMetadataResponse }
     * 
     */
    public GetMetadataResponse createGetMetadataResponse() {
        return new GetMetadataResponse();
    }

    /**
     * Create an instance of {@link ListAllResponse }
     * 
     */
    public ListAllResponse createListAllResponse() {
        return new ListAllResponse();
    }

    /**
     * Create an instance of {@link GetMetadataRequest }
     * 
     */
    public GetMetadataRequest createGetMetadataRequest() {
        return new GetMetadataRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://openclinica.org/ws/study/v1", name = "listAllRequest")
    public JAXBElement<Object> createListAllRequest(Object value) {
        return new JAXBElement<Object>(_ListAllRequest_QNAME, Object.class, null, value);
    }

}
