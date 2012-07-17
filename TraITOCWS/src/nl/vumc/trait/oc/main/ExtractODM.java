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

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;

import nl.vumc.trait.oc.connect.OCConnectorException;
import nl.vumc.trait.oc.connect.OCWebServices;
import nl.vumc.trait.oc.odm.AbstractODM;
import nl.vumc.trait.oc.odm.ODMException;
import nl.vumc.trait.oc.types.Study;

import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.ParseException;
import org.xml.sax.SAXException;

/**
 * Extract ODM for a given study (either metadata or metadata translated
 * to a template)
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl)
 */
public class ExtractODM extends Main {

	/** The study to extract ODM for */
	private Study study;
	/** Whether or not to return only unparsed metadata or not */
	private boolean meta;

	/**
	 * Constructor from Main. Setup processing command line arguments.
	 * @param command command string (see command attribute)
	 * @param args command line (i.e. as passed to main())
	 * @throws Exception 
	 * @throws ParserConfigurationException
	 */
	public ExtractODM(String command, String[] args) throws Exception {
		super(command, args);
	}

	/**
	 * Constructor based on Main.
	 * @throws ParserConfigurationException 
	 */
	public ExtractODM() throws ParserConfigurationException {
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
		options.addOption("m", "meta", false, "get plain metadata (do not translate to template)");
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
			meta = line.hasOption('m');
			setConnectInfo(line.getOptionValue('b'), line.getOptionValue('u'), line.getOptionValue('p'));
		}
	}

	
	/**
	 * Perform OpenClinica web service calls to retrieve (and translate) metadata ODM
	 * @return metadata ODM for the study set in the study attribute.
	 * @throws ParserConfigurationException
	 * @throws DatatypeConfigurationException
	 * @throws NoSuchAlgorithmException
	 * @throws OCConnectorException
	 * @throws ODMException
	 * @throws SAXException
	 * @throws IOException
	 */
	public AbstractODM getStudyODMTemplate() throws ParserConfigurationException, DatatypeConfigurationException,
			NoSuchAlgorithmException, OCConnectorException, ODMException, SAXException, IOException {
		OCWebServices connector = OCWebServices.getInstance(connectInfo, debug, false);
		if (meta) {
			return connector.fetchStudyMetadata(study);
		} else {
			return connector.fetchStudyMetadata(study).getClinicalTemplate();
		}
	}

	@Override
	public void runCmd() throws Exception {
		System.out.println(getStudyODMTemplate());
	}

	/**
	 * main()
	 * @param args command line arguments
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		new ExtractODM("extract", args);
	}

}
