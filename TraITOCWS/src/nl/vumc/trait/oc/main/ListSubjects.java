/*
	
	Copyright 2012 VU Medical Center Amsterdam
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	    http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
	
*/

package nl.vumc.trait.oc.main;

import javax.xml.parsers.ParserConfigurationException;

import nl.vumc.trait.oc.connect.OCConnectorException;
import nl.vumc.trait.oc.connect.OCWebServices;
import nl.vumc.trait.oc.types.Study;

import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.ParseException;
import org.openclinica.ws.beans.StudySubjectWithEventsType;
import org.openclinica.ws.studysubject.v1.ListAllByStudyResponse;

/**
 * List subjects for a given study.
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl)
 */
public class ListSubjects extends Main {

	/** Study to list subjects for */
	private Study study;

	/**
	 * Constructor from Main. Setup processing command line arguments.
	 * @param command command string (see command attribute)
	 * @param args command line (i.e. as passed to main())
	 * @throws Exception 
	 * @throws ParserConfigurationException
	 */
	public ListSubjects(String command, String[] args) throws Exception {
		super(command, args);
	}

	/**
	 * Constructor based on Main.
	 * @throws ParserConfigurationException 
	 */
	public ListSubjects() throws ParserConfigurationException {
		super();
	}

	@SuppressWarnings("static-access")
	@Override
	protected void setupOptions() {
		// create the Options
		options.addOption(OptionBuilder.withLongOpt("base-url")
				.withDescription("OpenClinica base URL (i.e. https://www.example.org/OpenClinica-ws/)").hasArg()
				.withArgName("URL").isRequired(true).create("b"));
		options.addOption(OptionBuilder.withLongOpt("user").withDescription("OpenClinica username").hasArg()
				.withArgName("username").isRequired(true).create("u"));
		options.addOption(OptionBuilder.withLongOpt("password").withDescription("OpenClinica password").hasArg()
				.withArgName("password").isRequired(true).create("p"));
		options.addOption(OptionBuilder.withLongOpt("study")
				.withDescription("OpenClinica study name (or site unique identifier)").hasArg().withArgName("study")
				.isRequired(true).create("s"));
		options.addOption("h", "help", false, "this help screen");
		options.addOption("v", "verbose", false, "be (very) verbose");
	}

	@Override
	protected void processArgs(String[] args) throws ParseException, OCConnectorException {
		// parse the command line arguments
		line = parser.parse(options, args);
		if (line.hasOption("help")) {
			help();
		} else {
			if (line.hasOption("verbose")) {
				setDebug(true);
			}
			study = new Study();
			study.setStudyName(line.getOptionValue('s'));
			setConnectInfo(line.getOptionValue('b'), line.getOptionValue('u'), line.getOptionValue('p'));
		}
	}

	@Override
	public void runCmd() throws Exception {
		OCWebServices connector = OCWebServices.getInstance(connectInfo, debug, false);
		ListAllByStudyResponse response = connector.listAllByStudy(study);
		System.out.println("Label\tEnrollment date\tGender\tDate of birth");
		for (StudySubjectWithEventsType subject : response.getStudySubjects().getStudySubject()) {
			System.out.println(subject.getLabel() + "\t" + subject.getEnrollmentDate() + "\t"
					+ subject.getSubject().getGender() + "\t" + subject.getSubject().getDateOfBirth());
		}
	}

	/**
	 * main()
	 * @param args command line arguments
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		new ListSubjects("subjects", args);
	}

}
