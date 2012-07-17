package nl.vumc.trait.test;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;

import nl.vumc.trait.oc.connect.ConnectInfo;
import nl.vumc.trait.oc.connect.OCConnectorException;
import nl.vumc.trait.oc.connect.OCWebServices;
import nl.vumc.trait.oc.types.Study;
import nl.vumc.trait.oc.types.StudySubject;

public class TestOCWS {

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException,
			MalformedURLException, ParserConfigurationException, DatatypeConfigurationException, OCConnectorException {
		ConnectInfo connectInfo = new ConnectInfo("http://appserv2.ctms/OpenClinica-ws/", "arjan");
		connectInfo.setPassword("momixco9027.");
		OCWebServices connector = OCWebServices.getInstance(connectInfo, true, false);
		Study study = connector.findStudy("Studie 001", false);
		connector.populateStudy(study, true, false);
		// System.out.println(study);
		System.out.println("Number of subjects: " + study.getStudySubjects().size());
		StudySubject myVictim = study.getStudySubjects().get(study.getStudySubjects().size() - 1);
		// System.out.println(connector.fetchStudySubjectOID(study, myVictim));
		System.out.println(myVictim);
		System.out.println("-------------------------------------------------------------------------");
		System.out.println(study);

		// System.out.println(connector.findStudy("S2", false));
	}

}
