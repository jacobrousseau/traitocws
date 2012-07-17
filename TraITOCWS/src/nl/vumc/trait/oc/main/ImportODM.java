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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;

import nl.vumc.trait.oc.connect.OCConnectorException;
import nl.vumc.trait.oc.connect.OCWebServices;
import nl.vumc.trait.oc.odm.ClinicalODMResolver;
import nl.vumc.trait.oc.odm.ODMException;

import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Import an ODM string.
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl)
 */
public class ImportODM extends Main {

	/** input file */
	private String file;

	/**
	 * Constructor from Main. Setup processing command line arguments.
	 * @param command command string (see command attribute)
	 * @param args command line (i.e. as passed to main())
	 * @throws Exception 
	 * @throws ParserConfigurationException
	 */
	public ImportODM(String command, String[] args) throws Exception {
		super(command, args);
	}

	/**
	 * Constructor based on Main.
	 * @throws ParserConfigurationException 
	 */
	public ImportODM() throws ParserConfigurationException {
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
		options.addOption(OptionBuilder.withLongOpt("file")
				.withDescription("XML file containing the ClinicalData. A hyphen (-) means stdin.").hasArg()
				.withArgName("file").isRequired(true).create("f"));
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
			file = line.getOptionValue('f');
			setConnectInfo(line.getOptionValue('b'), line.getOptionValue('u'), line.getOptionValue('p'));
		}
	}

	@Override
	public void runCmd() throws ParserConfigurationException, DatatypeConfigurationException, ODMException,
			OCConnectorException, SAXException, IOException {
		OCWebServices connector = OCWebServices.getInstance(connectInfo, debug, false);
		InputStream reader;
		if (file.equals("-")) {
			reader = System.in;
		} else {
			reader = new FileInputStream(file);
		}
		ClinicalODMResolver odm = new ClinicalODMResolver(documentBuilder.parse(reader), connector, true);
		odm.resolveOdmDocument();
		Document odmDoc = odm.getOdm();
		Node odmNode = odm.getOdm().getFirstChild();
		// bulk load -- chop up into ClinicaDatas...
		NodeList clinicalDatas = odm.xPath(odmNode, "//ClinicalData");
		for (int i = 0; i < clinicalDatas.getLength(); ++i) {
			odmNode.removeChild(clinicalDatas.item(i));
		}
		for (int i = 0; i < clinicalDatas.getLength(); ++i) {
			odmNode.appendChild(clinicalDatas.item(i));
			logger.debug("============================ setOdm start =================");
			odm.setOdm(odmDoc); // important!
			logger.debug("Current ODM:\n" + odm.extraClean().toString());
			logger.debug("============================ setOdm end ===================");
			connector.importODM(odm.extraClean().toString());
			odmNode.removeChild(clinicalDatas.item(i));
		}
	}

	/**
	 * main()
	 * @param args command line arguments
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		new ImportODM("import", args);
	}

}
