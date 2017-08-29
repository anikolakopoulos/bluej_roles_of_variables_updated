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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Stack;

import main.progAnal.ProgramAnalyser;
import main.progAnal.RoleHolder;

/**
 * @author cbishop
 * 
 * Class to print out information for debug etc.
 */
public class InfoPrinter {

	private ProgramAnalyser programAnalyser;

	/**
	 * Constructor for InfoPrinter
	 *  
	 */
	public InfoPrinter() {
		programAnalyser = new ProgramAnalyser();
	}

	/**
	 * Print roles for each variable
	 * 
	 * @param roleHolder
	 *            RoleHolder containing variables and their roles
	 */
	public void printRoles(RoleHolder roleHolder) {
		String roleSet = roleHolder.toString();
		System.out.println(roleSet);
	}

	/**
	 * Print formatted source code from sorted source LinkeHashMap
	 * 
	 * @param brokenSource
	 *            LinkedHashMap containing hierarchy of source code statements
	 */
	public void printSource(LinkedHashMap brokenSource) {
		Set keySet = (Set) brokenSource.keySet();
		Stack iterators = new Stack();
		Stack maps = new Stack();
		Iterator it = keySet.iterator();
		String indent = "";
		while (it.hasNext()) {
			String key = (String) it.next();
			if (key.endsWith("{")) {
				System.out.println(indent + key);
				indent += "    ";
				maps.push(brokenSource);
				iterators.push(it);
				brokenSource = (LinkedHashMap) brokenSource.get(key);
				keySet = brokenSource.keySet();
				it = keySet.iterator();
			} else if (key.endsWith("}")) {
				indent = indent.substring(0, indent.length() - 4);
				System.out.println(indent + key);
				brokenSource = (LinkedHashMap) maps.pop();
				it = (Iterator) iterators.pop();
			} else {
				System.out.println(indent + key);
			}
		}
		System.out.println();
	}

	/**
	 * Print ArrayLists of statements for a given variable, together will all
	 * preceding control constructs
	 * 
	 * @param vars
	 * @param statements
	 */
	public void printStatementArrays(Set vars, LinkedHashMap statements) {
		Iterator iter = vars.iterator();
		while (iter.hasNext()) {
			String keyString = (String) iter.next();
			ArrayList tempArray = (ArrayList) statements.get(keyString);
			System.out.println(keyString + ": " + tempArray.size());
			for (int i = 0; i < tempArray.size(); i++) {
				ArrayList printArray = (ArrayList) tempArray.get(i);
				for (int count = 0; count < printArray.size(); count++) {
					String printString = (String) (printArray.get(count));
					System.out.println(printString);
				}
			}
			//System.out.println();
		}
	}

	/**
	 * Print program slices for each variable
	 * 
	 * @param vars
	 *            Set of variable names
	 * @param statements
	 *            HashMap of statement for each variable
	 */
	public void printStatements(Set vars, HashMap statements) {
		Iterator iter = vars.iterator();
		while (iter.hasNext()) {
			String keyString = (String) iter.next();
			ArrayList statementArray = (ArrayList) statements.get(keyString);
			System.out.println(keyString + ": " + statementArray.size());
			String indent = "";
			for (int i = 0; i < statementArray.size(); i++) {
				String statementString = (String) (statementArray.get(i));
				if (statementString.endsWith("}")) {
					indent = indent.substring(0, indent.length() - 4);
				}
				//System.out.println(statementString);
				System.out.println(indent + statementString);
				if (statementString.endsWith("{")) {
					indent += "    ";
				}
			}
			System.out.println();
		}
	}

	/**
	 * Print analysis of slices for each variable e.g. whether assignemt, usage,
	 * conditional or other statements, and their positions within the source.
	 * 
	 * @param analysedStatements
	 *            HashMap of analysed statements
	 */
	public void printAnalysedStatements(HashMap analysedStatements) {
		Set keySet = analysedStatements.keySet();
		Iterator it = keySet.iterator();
		while (it.hasNext()) {
			String keyString = (String) it.next();
			LinkedHashMap analysedMap = (LinkedHashMap) analysedStatements
					.get(keyString);
			printWhereaboutsInfo(keyString, analysedMap);
			System.out.println();
		}
	}

	/**
	 * Print specific category statements for a given variable, e.g. usage
	 * statements
	 * 
	 * @param inputArray
	 *            ArrayList containing all relevant statements
	 * @param whatArray
	 *            String specifying what sort of statements to print
	 * @param variable
	 *            String variable for which statements are to be printed
	 */
	public void printArray(ArrayList inputArray, String whatArray,
			String variable) {
		System.out.println(whatArray + " statements for " + variable);
		for (int i = 0; i < inputArray.size(); i++) {
			System.out.println(inputArray.get(i));
		}
	}

	/*
	 * Used by print whereabouts infor to print specific map
	 */
	private void printMap(HashMap whereaboutsMap) {
		Set keySet = whereaboutsMap.keySet();
		Iterator it = keySet.iterator();
		while (it.hasNext()) {
			String statement = (String) it.next();
			System.out.println("Statement: " + statement); //debug
			ArrayList tempArray = (ArrayList) whereaboutsMap.get(statement);
			ArrayList indexCard = (ArrayList) tempArray.get(0);
			for (int index = 0; index < indexCard.size() - 1; index++) {
				Integer tempInt = (Integer) indexCard.get(index);
				Integer statementLine = (Integer) tempArray.get(tempInt
						.intValue());
				System.out.println("found at line " + statementLine.intValue());
				Integer nextInt = (Integer) indexCard.get(index + 1);
				for (int i = tempInt.intValue() + 1; i < nextInt.intValue(); i += 2) {
					String statementSignature = (String) tempArray.get(i + 1);
					if (programAnalyser.loop(statementSignature)) {
						System.out.print("in loop: " + statementSignature);
					} else if (programAnalyser.isIf(statementSignature)
							|| programAnalyser.isElse(statementSignature)) {
						System.out.print("in branch: " + statementSignature);
					} else if (programAnalyser.isClass(statementSignature)) {
						System.out.print("in class: " + statementSignature);
					} else if (programAnalyser
							.methodStatement(statementSignature)) {
						System.out.print("in method: " + statementSignature);
					} else if (programAnalyser
							.tryCatchStatement(statementSignature)) {
						System.out.print("in block: " + statementSignature);
					}
					Integer loopBranchLine = (Integer) tempArray.get(i);
					System.out.println(" (at line " + loopBranchLine.intValue()
							+ ")");
				}
			}
		}
	}

	/*
	 * Used by printAnalysedStatements to print whereabouts info
	 */
	private void printWhereaboutsInfo(String variable, HashMap inputMap) {
		Set keySet = inputMap.keySet();
		System.out.println("Statements for variable: " + variable);
		Iterator it = keySet.iterator();
		while (it.hasNext()) {
			String mapType = (String) it.next();
			HashMap whereaboutsMap = (HashMap) inputMap.get(mapType);
			System.out.println("Analysed map for " + variable + ": " + mapType
					+ " statements");
			printMap(whereaboutsMap);
		}
	}

	/**
	 * Print simply results following roles checking
	 * 
	 * @param checkResults
	 *            ArrayList containg results for a given variable
	 * @param variable
	 *            String specifying variable for which to print results
	 * @param role
	 *            String specifying role played by variable
	 */
	public void printResults(ArrayList checkResults, String variable,
			String role) {
		Boolean isOk = (Boolean) checkResults.get(0);
		if (!isOk.booleanValue()) {
			System.out.println("Possible incorrect role declaration of '"
					+ role + "' for variable: " + variable);
			for (int i = 1; i < checkResults.size(); i++) {
				System.out.println(checkResults.get(i));
			}
		} else
			System.out.println("Role declaration OK for variable: " + variable);
		System.out.println();
	}

	/**
	 * Print results in format output by BlueJ
	 * 
	 * @param checkResults
	 *            ArrayList containg results for a given variable
	 * @param variable
	 *            String specifying variable for which to print results
	 * @param role
	 *            String specifying role played by variable
	 */
	public void printTestResults(ArrayList checkResults, String variable,
			String role) {
		Boolean isOk = (Boolean) checkResults.get(0);
		if (!isOk.booleanValue()) {
			System.out.println("Possible incorrect role declaration of '"
					+ role + "' for variable: " + variable);
			System.out.println("offending statement: " + checkResults.get(1));
			System.out.println("reason: " + checkResults.get(2));
			System.out.println("possible role: " + checkResults.get(3));
		} else
			System.out.println("Role declaration OK for variable: " + variable);
		System.out.println();
	}

	/**
	 * Print all methods name detected in source code
	 * 
	 * @param methods
	 *            ArrayList containing names of all methods in source code
	 */
	public void printMethods(ArrayList methods) {
		Iterator it = methods.iterator();
		System.out.println("method statements:");
		while (it.hasNext()) {
			System.out.println(it.next() + "\n");
		}
	}
}