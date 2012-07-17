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

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.w3c.dom.Document;

/**
 * Contains some static utility functions for OC WS.
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl)
 */
public class Util {

	/**
	 * Setup a trust manager that basically accepts anything...
	 */
	public static void installPermissiveTrustManager() {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			@SuppressWarnings("unused")
			public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
				return true;
			}

			@SuppressWarnings("unused")
			public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
				return true;
			}

		} };

		HostnameVerifier hostnameVerifier = new HostnameVerifier() {
			@Override
			public boolean verify(String urlHostName, SSLSession session) {
				return true;
			}
		};

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
		} catch (Exception e) {
		}
	}

	/**
	 * Add a message handler to a SOAP binding
	 * @param binding binding to which we'll add the handler
	 * @param handler the handler to be added
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void addMessageHandler(Object binding, SOAPHandler<SOAPMessageContext> handler) {
		final Binding b = ((BindingProvider) binding).getBinding();
		List handlerList = b.getHandlerChain();
		if (handlerList == null)
			handlerList = new ArrayList();
		handlerList.add(handler);
		b.setHandlerChain(handlerList);
	}

	/**
	 * Add a message handler to a SOAP binding
	 * @param binding binding to which we'll add the handler
	 * @param handler the handler to be added
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void addMessageHandler(Object binding, LogicalHandler handler) {
		final Binding b = ((BindingProvider) binding).getBinding();
		List handlerList = b.getHandlerChain();
		if (handlerList == null)
			handlerList = new ArrayList();
		handlerList.add(handler);
		b.setHandlerChain(handlerList);
	}

	/**
	 * Add a message handler to a SOAP binding
	 * @param binding binding to which we'll add the handler
	 * @param index position in the list of handlers
	 * @param handler the handler to be added
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void addMessageHandler(Object binding, int index, SOAPHandler<SOAPMessageContext> handler) {
		final Binding b = ((BindingProvider) binding).getBinding();
		List handlerList = b.getHandlerChain();
		if (handlerList == null)
			handlerList = new ArrayList();
		handlerList.add(index, handler);
		b.setHandlerChain(handlerList);
	}

	/**
	 * Add a message handler to a SOAP binding
	 * @param binding binding to which we'll add the handler
	 * @param index position in the list of handlers
	 * @param handler the handler to be added
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void addMessageHandler(Object binding, int index, LogicalHandler handler) {
		final Binding b = ((BindingProvider) binding).getBinding();
		List handlerList = b.getHandlerChain();
		if (handlerList == null)
			handlerList = new ArrayList();
		handlerList.add(index, handler);
		b.setHandlerChain(handlerList);
	}

	/**
	 * Convert an XML DOM Document to a String representation
	 * @param d DOM Document
	 * @return String representation of DOM Document d
	 * @throws TransformerException 
	 */
	public static String documentToString(Document d) throws TransformerException {
		StringWriter s = new StringWriter();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		transformer.transform(new DOMSource(d), new StreamResult(s));
		return s.toString();
	}

}
