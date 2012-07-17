package nl.vumc.trait.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;

import nl.vumc.trait.oc.connect.ConnectInfo;
import nl.vumc.trait.oc.connect.OCConnectorException;
import nl.vumc.trait.oc.connect.OCWebServices;
import nl.vumc.trait.oc.types.ScheduledEvent;
import nl.vumc.trait.oc.types.Study;
import nl.vumc.trait.oc.types.StudySubject;

import org.openclinica.ws.studysubject.v1.CreateResponse;

public class CreateSubjectsTest {

	public void fileRead(String filename) throws IOException, ParserConfigurationException,
			DatatypeConfigurationException, OCConnectorException {
		ConnectInfo connectInfo = new ConnectInfo("http://localhost:8081/OpenClinica-ws", "arjan",
				"8070379c3a8c300c71a50fefa07c68d49c416edd");
		OCWebServices connector = OCWebServices.getInstance(connectInfo, true, false);
		Study study = new Study();
		study.setStudyName("TEST001");
		//study.setSiteName("DEMO01 on My Site");
		// String siteName = "S2";
		ScheduledEvent event1 = new ScheduledEvent();
		event1.setEventOID("SE_EVENT01");
		ScheduledEvent event2 = new ScheduledEvent();
		event2.setEventOID("SE_EVENT02");
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String s;
		String[] columns;
		while ((s = reader.readLine()) != null) {
			columns = s.split("[,\t]"); // split by tab or by comma [,\t]
			StudySubject studySubject = new StudySubject(study);
			studySubject.setPersonID("AZZT_" + columns[0]);
			studySubject.setStudySubjectLabel(studySubject.getPersonID());
			studySubject.setSex((columns[2].equals("1") ? "m" : "f"));
			studySubject.setDateOfBirth(columns[3]);
			System.out.println(studySubject);
			try {
				// create study subject
				CreateResponse createResponse = connector.createStudySubject(studySubject);
				studySubject.setStudySubjectLabel(createResponse.getLabel());
				connector.updateOID(studySubject);
				// schedule event
				connector.scheduleEvent(studySubject, event1);
				connector.scheduleEvent(studySubject, event2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		CreateSubjectsTest bla = new CreateSubjectsTest();
		// bla.bla();
		bla.fileRead("sample/Database EB openclinica AS_270412.csv.small");
	}

}
