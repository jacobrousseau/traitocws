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

// TODO: logging works but the whole logging mechanism is a big todo.
// TODO: Logging should be setup to use log4j concepts only

package nl.vumc.trait.oc.util;

import org.apache.log4j.Level;

//import org.apache.log4j.*;

/**
 * Basic Logger class.
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl)
 */
public class Logger {
	
	/** internal log4j logger */
	private org.apache.log4j.Logger logger;
	/** we are a singleton */
	protected static Logger instance;

	/** disable public access to constructor */
	protected Logger() {
		// disable public default constructor
		logger = org.apache.log4j.Logger.getLogger("nl.vumc.trait.oc.mirth"); 
	}

	/**
	 * setup a new logger instance or return the existing instance
	 * @return a logger instance
	 */
	public static Logger getInstance() {
		if (instance != null) {
			return instance;
		} else {
			return (instance = new Logger());
		}
	}

	/**
	 * Get current log level
	 * @return log level
	 */
	public Level getLevel() {
		return logger.getLevel();
	}

	/**
	 * Set log level
	 * @param level log level
	 */
	public void setLevel(Level level) {
		logger.setLevel(level);
	}

	/**
	 * Log something (relies on toString()) at "info" level
	 * @param o object to log (toString())
	 */
	public void log(Object o) {
		logger.info(o);
	}
	
	/**
	 * Log something (relies on toString()) at "info" level
	 * @param o object to log (toString())
	 */
	public void info(Object o) {
		logger.info(o);
	}

	/**
	 * Log something (relies on toString()) at "warn" level
	 * @param o object to log (toString())
	 */
	public void warn(Object o) {
		logger.warn(o);
	}

	/**
	 * Log something (relies on toString()) at "debug" level
	 * @param o object to log (toString())
	 */
	public void debug(Object o) {
		logger.debug(o);
	}

}
