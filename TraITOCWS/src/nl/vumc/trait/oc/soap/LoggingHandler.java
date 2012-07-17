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

package nl.vumc.trait.oc.soap;

import java.util.Set;

import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import nl.vumc.trait.oc.util.Logger;

/**
 * A quick and dirty logging handler of SOAP messages.
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl)
 */
public class LoggingHandler implements SOAPHandler<SOAPMessageContext> {
	
	/** Internal logger */
	private Logger logger;

	/**
	 * Create the handler
	 */
	public LoggingHandler() {
		super();
		logger = Logger.getInstance();
	}

	@Override
	public boolean handleMessage(SOAPMessageContext c) {
		SOAPMessage msg = c.getMessage();
		boolean request = ((Boolean) c.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY)).booleanValue();
		try {
			if (request) { // This is a request message.
				// Write the message to the output stream
				logger.debug("Request:\n"
						+ Util.documentToString(msg.getSOAPBody().getOwnerDocument()));
			} else { // This is the response message
				logger.debug("Response:\n"
						+ Util.documentToString(msg.getSOAPBody().getOwnerDocument()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext c) {
		SOAPMessage msg = c.getMessage();
		try {
			logger.debug("Fault:\n"
					+ Util.documentToString(msg.getSOAPBody().getOwnerDocument()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void close(MessageContext c) {
		// empty. nothing to be done here.
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Set getHeaders() {
		// Not required for logging
		return null;
	}

}
