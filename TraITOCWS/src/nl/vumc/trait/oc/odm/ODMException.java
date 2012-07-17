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

/**
 * General ODM exception for ODM related exceptions.
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl)
 */
public class ODMException extends Exception {

	/** auto generated serial */
	private static final long serialVersionUID = -2749503517042116579L;

	/** init exception */
	public ODMException() {
		super();
	}

	/**
	 * init exception with custom message and causing exception
	 * @param arg0 error message
	 * @param arg1 causing exception
	 */
	public ODMException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * init exception wih custom message
	 * @param arg0 error message
	 */
	public ODMException(String arg0) {
		super(arg0);
	}

	/**
	 * init exception with causing exception
	 * @param arg0 causing exception
	 */
	public ODMException(Throwable arg0) {
		super(arg0);
	}

}
