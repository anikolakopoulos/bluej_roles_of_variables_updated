/*
* Copyright (c) 2005-08, Craig Bishop and Colin Graeme Johnson
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*     * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*     * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer in the
*       documentation and/or other materials provided with the distribution.
*     * Neither the names of Craig Bishop and/or Colin Graeme Johnson, 
*       nor the name of the University of Kent may be used to endorse 
*       or promote products derived from this software without
*       specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY COLIN GRAEME JOHNSON ``AS IS'' AND ANY
* EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL COLIN GRAEME JOHNSON BE LIABLE FOR ANY
* DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

/*
 * Created on 24-Jun-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package main.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * @author cbishop
 * 
 * @version June 2005
 * 
 * Class to output sliced programs to appropriate files for analysis
 */
public class SliceWriter {

	/**
	 * Constructor for SliceWriter
	 * 
	 * @param roles
	 *            LinkedHashMap containg variables as keys and their associated
	 *            roles as values
	 * @param statements
	 *            HashMap contaning a sliced program for each variable
	 */
	public void writeSlice(LinkedHashMap roles, HashMap statements) {
		Set vars = roles.keySet();
		Iterator iter = vars.iterator();
		while (iter.hasNext()) {
			String variable = (String) iter.next();
			Integer roleValue = (Integer) roles.get(variable);
			switch (roleValue.intValue()) {
			case 1:
				writeRole("../../Sliced files/Fixed Value.txt", variable,
						statements);
				break;
			case 2:
				writeRole("../../Sliced files/Organizer.txt", variable,
						statements);
				break;
			case 3:
				writeRole("../../Sliced files/Stepper.txt", variable,
						statements);
				break;
			case 4:
				writeRole("../../Sliced files/Most Recent Holder.txt",
						variable, statements);
				break;
			case 5:
				writeRole("../../Sliced files/Gatherer.txt", variable,
						statements);
				break;
			case 6:
				writeRole("../../Sliced files/Most Wanted Holder.txt",
						variable, statements);
				break;
			case 7:
				writeRole("../../Sliced files/One Way Flag.txt", variable,
						statements);
				break;
			case 8:
				writeRole("../../Sliced files/Transformation.txt", variable,
						statements);
				break;
			case 9:
				writeRole("../../Sliced files/Follower.txt", variable,
						statements);
				break;
			case 10:
				writeRole("../../Sliced files/Temporary.txt", variable,
						statements);
				break;
			}
		}
	}

	/*
	 * Write slice for each variable to specified file
	 */
	private void writeRole(String fileName, String variableName,
			HashMap statements) {
		ArrayList statementArray = (ArrayList) statements.get(variableName);
		try {
			FileWriter dataOutput = new FileWriter(fileName, true);
			System.out.println(variableName);
			dataOutput.write(variableName + "\n");
			String indent = "";
			for (int i = 0; i < statementArray.size(); i++) {
				String statementString = (String) (statementArray.get(i));
				if (statementString.endsWith("}")) {
					indent = indent.substring(0, indent.length() - 4);
				}
				dataOutput.write(indent + statementString + "\n");
				System.out.println(indent + statementString);
				if (statementString.endsWith("{")) {
					indent += "    ";
				}
			}
			dataOutput.write("\n");
			System.out.println();
			dataOutput.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
