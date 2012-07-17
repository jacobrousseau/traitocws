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

package nl.vumc.trait.oc.odm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

/**
 * Namespace context for XPath queries on OpenClinica related XML
 * 
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl)
 * 
 */
public class NSContext implements NamespaceContext {

	/** lookup table (label to uri) */
	private HashMap<String, String> forward;
	
	/** backward lookup table (uri to label) */
	private HashMap<String, String> reverse;

	/**
	 * Construct a new NamespaceContext
	 */
	public NSContext() {
		forward = new HashMap<String, String>();
		reverse = new HashMap<String, String>();
		
		forward.put("cdisc", "http://www.cdisc.org/ns/odm/v1.3");
		forward.put("xsl", "http://www.w3.org/1999/XSL/Transform");
		forward.put("Mirth", "http://www.vumc.nl/trait/odm/mirth/v0.1");
		forward.put("beans", "http://openclinica.org/ws/beans");
		forward.put("studysubject", "http://openclinica.org/ws/studySubject/v1");
		forward.put("OpenClinica", "http://www.openclinica.org/ns/odm_ext_v130/v3.1");
		
		for (String s : forward.keySet()) {
			reverse.put(forward.get(s), s);
		}
	}

	@Override
	public String getNamespaceURI(String prefix) {
		if (forward.containsKey(prefix)) {
			return forward.get(prefix);
		} else {
			return XMLConstants.NULL_NS_URI;
		}
	}

	@Override
	public String getPrefix(String namespaceURI) {
		if (reverse.containsKey(namespaceURI)) {
			return reverse.get(namespaceURI);
		} else {
			return null;
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Iterator getPrefixes(String namespaceURI) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(reverse.get(namespaceURI));
		return list.iterator();
	}

}
