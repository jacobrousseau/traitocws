
package org.openclinica.ws.studysubject.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.openclinica.ws.beans.ListStudySubjectsInStudyType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.openclinica.ws.studysubject.v1 package. 
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

    private final static QName _ListAllByStudyRequest_QNAME = new QName("http://openclinica.org/ws/studySubject/v1", "listAllByStudyRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.openclinica.ws.studysubject.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IsStudySubjectRequest }
     * 
     */
    public IsStudySubjectRequest createIsStudySubjectRequest() {
        return new IsStudySubjectRequest();
    }

    /**
     * Create an instance of {@link IsStudySubjectResponse }
     * 
     */
    public IsStudySubjectResponse createIsStudySubjectResponse() {
        return new IsStudySubjectResponse();
    }

    /**
     * Create an instance of {@link ListAllByStudyResponse }
     * 
     */
    public ListAllByStudyResponse createListAllByStudyResponse() {
        return new ListAllByStudyResponse();
    }

    /**
     * Create an instance of {@link CreateRequest }
     * 
     */
    public CreateRequest createCreateRequest() {
        return new CreateRequest();
    }

    /**
     * Create an instance of {@link CreateResponse }
     * 
     */
    public CreateResponse createCreateResponse() {
        return new CreateResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListStudySubjectsInStudyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://openclinica.org/ws/studySubject/v1", name = "listAllByStudyRequest")
    public JAXBElement<ListStudySubjectsInStudyType> createListAllByStudyRequest(ListStudySubjectsInStudyType value) {
        return new JAXBElement<ListStudySubjectsInStudyType>(_ListAllByStudyRequest_QNAME, ListStudySubjectsInStudyType.class, null, value);
    }

}
