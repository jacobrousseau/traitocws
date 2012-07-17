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

package nl.vumc.trait.oc.connect;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Connection credentials and URL. Also facilitates the hashing of the
 * password. Clear-text password is not saved and cannot be retrieved.
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl)
 */
public class ConnectInfo {

	/** base url of openclinica web services (i.e. https://www.example.com/OpenClinica-ws/) */
	private String baseURL;
	/** user name to use when calling web service methods */
	private String userName;
	/** password hash to use when calling web service methods */
	private String passwordHash;

	/**
	 * Constructs a ConnectionInfo object from a url, username and password hash
	 * @param baseURL base url of openclinica web services (i.e. https://www.example.com/OpenClinica-ws/)
	 * @param userName user name to use when calling web service methods
	 * @param passwordHash password hash to use when calling web service methods
	 */
	public ConnectInfo(String baseURL, String userName, String passwordHash) {
		super();
		this.baseURL = baseURL;
		this.userName = userName;
		this.passwordHash = passwordHash;
		if (baseURL.charAt(baseURL.length() - 1) != '/') {
			this.baseURL = baseURL + "/";
		} else {
			this.baseURL = baseURL;
		}
	}

	/**
	 * Constructs a ConnectionInfo object from a url and username. Password needs to be set separately.
	 * @param baseURL base url of openclinica web services (i.e. https://www.example.com/OpenClinica-ws/)
	 * @param userName user name to use when calling web service methods
	 */
	public ConnectInfo(String baseURL, String userName) {
		super();
		this.baseURL = baseURL;
		this.userName = userName;
		if (baseURL.charAt(baseURL.length() - 1) != '/') {
			this.baseURL = baseURL + "/";
		} else {
			this.baseURL = baseURL;
		}
	}

	/**
	 * Returns the SHA1 hash of a given string, in human readable format
	 *
	 * @param message
	 *            message to be digested
	 * @return SHA1 hash of the supplied string
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String toSHA1(String message) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA1");
		byte[] bytesOfMessage = message.getBytes("UTF-8");
		byte[] bytesOfDigest = md.digest(bytesOfMessage);
		BigInteger bigInt = new BigInteger(1, bytesOfDigest);
		return bigInt.toString(16);
	}

	/**
	 * Get the base url of openclinica web services.
	 * @return base url of openclinica web services */
	public String getBaseURL() {
		return baseURL;
	}

	/** 
	 * Set the base url of openclinica web services
	 * @param baseURL baseURL base url of openclinica web services (i.e. https://www.example.com/OpenClinica-ws/)
	 * */
	public void setBaseURL(String baseURL) {
		if (baseURL.charAt(baseURL.length() - 1) != '/') {
			this.baseURL = baseURL + "/";
		} else {
			this.baseURL = baseURL;
		}
	}

	/**
	 * Get the OpenClinica user name
	 * @return OpenClinica user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Set the OpenClinica user name
	 * @param userName OpenClinica user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Get the password hash (in ASCII format)
	 * @return password hash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Set the password hash (in ASCII format)
	 * @param passwordHash password hash in ASCII format
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Set the password(hash) from a plain-text password.
	 * @param password plain-text password to be hashed
	 * @throws OCConnectorException Exceptions in digets pushed up.
	 */
	public void setPassword(String password) throws OCConnectorException {
		try {
			this.passwordHash = toSHA1(password);
		} catch (Exception e) {
			throw new OCConnectorException("Cannot digest password", e);
		}
	}

	@Override
	public String toString() {
		return "ConnectInfo: baseURL: " + baseURL + ", userName: " + userName + ", passwordHash: " + passwordHash;
	}

}
