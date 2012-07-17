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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;

import nl.vumc.trait.oc.connect.ConnectInfo;
import nl.vumc.trait.oc.connect.OCConnectorException;
import nl.vumc.trait.oc.odm.NSContext;
import nl.vumc.trait.oc.util.Logger;

/**
 * Skeleton for any program contained within this package. It provides and initializes
 * basic support data structures for xml handling and command line parsing. 
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl)
 */
public abstract class Main {
	/** Document Builder Factory, used as a namespace aware document builder factory. */
	protected DocumentBuilderFactory documentBuilderFactory;
	/** Document Builder */
	protected DocumentBuilder documentBuilder;
	/** flag to indicate whether to run in debug mode or not */
	protected boolean debug;
	/** Transformer Factory */
	protected TransformerFactory transformerFactory;
	/** xpath factory */
	protected XPathFactory xPathFactory;
	/** xpath */
	protected XPath xPath;
	/** the logger to be used for logging output */
	protected Logger logger;
	/** credentials for OpenClinica web service calls */
	protected ConnectInfo connectInfo;
	/** CLI options */
	protected Options options;
	/** command (name to show when showing CLI help) */
	protected String command;
	/** CLI parser */
	protected CommandLineParser parser;
	/** CLI command line */
	protected CommandLine line;

	/**
	 * Initialize a Main() object 
	 * @throws ParserConfigurationException
	 */
	public Main() throws ParserConfigurationException {
		BasicConfigurator.configure(); // log4j default logging to stdout...
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setValidating(false);
		documentBuilderFactory.setNamespaceAware(true); // <- important!
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
		transformerFactory = TransformerFactory.newInstance();
		xPathFactory = XPathFactory.newInstance();
		xPath = xPathFactory.newXPath();
		xPath.setNamespaceContext(new NSContext()); // <- important too!
		debug = false;
		logger = Logger.getInstance();
		logger.setLevel(Level.WARN);
		options = new Options();
		parser = new PosixParser();
	}

	/**
	 * Initialize a Main() object setting processing command line arguments.
	 * @param command command string (see command attribute)
	 * @param args command line (i.e. as passed to main())
	 * @throws Exception 
	 * @throws ParserConfigurationException
	 */
	public Main(String command, String[] args) throws Exception {
		this();
		this.command = command;
		setupOptions();
		try {
			processArgs(args);
			runCmd();
		} catch (ParseException e) {
			System.out.println(e.getMessage() + "\n");
			help();
		}
	}

	/**
	 * Check whether we are in debugging mode or not
	 * @return true if we are in debugging mode
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * Set debugging mode. True for debugging, false to turn off debugging.
	 * @param debug debugging mode to set.
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
		if (debug) {
			logger.setLevel(Level.DEBUG);
		}
	}

	/**
	 * Turn debugging mode on
	 */
	public void setDebug() {
		setDebug(true);
	}

	/**
	 * Get the command string 
	 * @return command
	 */
	protected String getCommand() {
		return command;
	}

	/**
	 * Set the command string
	 * @param command command to set
	 */
	protected void setCommand(String command) {
		this.command = command;
	}
	
	/** show formatted help based on CLI setup */
	protected void help() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(command, options);
	}

	/**
	 * Set credentials and connect info.
	 * @param baseURL base URL
	 * @param user user name
	 * @param password password
	 * @throws OCConnectorException
	 */
	protected void setConnectInfo(String baseURL, String user, String password) throws OCConnectorException {
		connectInfo = new ConnectInfo(baseURL, user);
		connectInfo.setPassword(password);
	}

	/**
	 * Setup CLI options (series of options.addOption(....) and such)
	 */
	protected abstract void setupOptions();

	/**
	 * Process command line arguments
	 * @param args the command line
	 * @throws ParseException
	 * @throws OCConnectorException
	 */
	protected abstract void processArgs(String[] args) throws ParseException, OCConnectorException;
	
	/**
	 * Implementation of the program after all has been setup -- doIt().
	 * @throws Exception
	 */
	protected abstract void runCmd() throws Exception;


}
