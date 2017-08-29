package main.progAnal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Stack;

/**
 * Class to analyse statements and return which category of statement they are
 * 
 * @author cbishop
 */
public class StatementAnalyser extends ProgramAnalyser {

	private HashMap statements;

	private Set variables;

	private ArrayList assignmentStatements;

	private ArrayList usageStatements;

	private ArrayList conditionalUsageStatements;

	private ArrayList otherStatements;

	/**
	 * Constructor for StatementAnalyser
	 * 
	 * @param inputStatements
	 *            Statements for all varaiables to be analysed
	 * @param variables
	 *            Set of variables
	 */
	public StatementAnalyser(HashMap inputStatements, Set variables) {
		statements = inputStatements;
		this.variables = variables;
	}

	/**
	 * Get analysis of variables whereabouts in source code
	 * 
	 * @return Map containing analysis for all variables
	 */
	public HashMap getStatementAnalysis() {
		HashMap analysedStatements = new HashMap();
		Iterator it = variables.iterator();
		ArrayList variableStatements = new ArrayList();
		while (it.hasNext()) {
			String variable = (String) it.next();
			variableStatements = (ArrayList) statements.get(variable);
			LinkedHashMap analysedMap = analyseStatements(variable,
					variableStatements);
			analysedStatements.put(variable, analysedMap);
		}
		return analysedStatements;
	}

	/*
	 * Get map of analysed statements for given input variable
	 */
	private LinkedHashMap analyseStatements(String var, ArrayList varStatements) {
		LinkedHashMap analysedMap = new LinkedHashMap();
		assignmentStatements = find(var, varStatements, "Assignment");
		usageStatements = find(var, varStatements, "Usage");
		conditionalUsageStatements = find(var, varStatements,
				"Conditional usage");
		otherStatements = find(var, varStatements, "Other");
		HashMap assignmentWhereaboutsMap = whereabouts(assignmentStatements,
				varStatements);
		//System.out.println(var + " map size = " +
		// assignmentWhereaboutsMap.size()); //debug
		HashMap usageWhereaboutsMap = whereabouts(usageStatements,
				varStatements);
		HashMap conditionalWhereaboutsMap = whereabouts(
				conditionalUsageStatements, varStatements);
		HashMap otherWhereaboutsMap = whereabouts(otherStatements,
				varStatements);
		analysedMap.put("assignment", assignmentWhereaboutsMap);
		analysedMap.put("usage", usageWhereaboutsMap);
		analysedMap.put("conditional", conditionalWhereaboutsMap);
		analysedMap.put("other", otherWhereaboutsMap);
		return analysedMap;
	}

	/*
	 * Find whereabouts of statements for a given statement type
	 */
	private ArrayList find(String var, ArrayList varStatements, String what) {
		ArrayList returnArray = new ArrayList();
		//System.out.println(what + " statements :" + var); //debug
		for (int i = 0; i < varStatements.size(); i++) {
			String statement = (String) varStatements.get(i);
			if (what.equals("Assignment")
					|| (what.equals("Usage") && !isInArray(statement,
							assignmentStatements))
					|| (what.equals("Conditional usage")
							&& !isInArray(statement, assignmentStatements) && !isInArray(
							statement, usageStatements))
					|| (what.equals("Other")
							&& !isInArray(statement, assignmentStatements)
							&& !isInArray(statement, usageStatements) && !isInArray(
							statement, conditionalUsageStatements))) {
				String tempString = retrieveStatement(var, statement, what);
				if (tempString != null && !isInArray(tempString, returnArray)) {
					returnArray.add(tempString);
				}
			}
		}
		return returnArray;
	}

	/*
	 * Analyse input statement and return whether it is a given type.
	 */
	private String retrieveStatement(String var, String statement, String what) {
		String returnString = null;
		int bracketCount = 0;
		int window = var.length() + 3;
		String preEquals = "";
		for (int j = 0; j < statement.length(); j++) {
			String statementSubstring = statement.substring(j);
			preEquals += statement.substring(j, j + 1);
			String noSpace = removeSpaces(statementSubstring);
			String windowedPreEquals = "";
			if (preEquals.length() > window) { //create sliding window for var
											   // to prevent erroneous detection
											   // of var prior to "=".
				windowedPreEquals = preEquals.substring(preEquals.length()
						- window, preEquals.length());
			}
			if (statementSubstring.startsWith("(")) {
				bracketCount++;
			} else if (statementSubstring.startsWith(")")) {
				bracketCount--;
			}
			if (what.equals("Assignment")
					&& bracketCount == 0
					&& !arrayIndex(var, statement)
					&& (assignmentCharacter(statementSubstring)
							&& statement.startsWith(var)
							&& !subString(preEquals, var)
							|| assignmentCharacter(statementSubstring)
							&& !subString(windowedPreEquals, var) || noSpace
							.startsWith("]=")
							&& !subString(preEquals, var)
							&& contains(preEquals, var + "["))) {
				returnString = statement;
				break;
			} else if (what.equals("Usage")
					&& (bracketCount == 0 && statementSubstring.startsWith("=")
							^ printUse(statementSubstring, var))
					&& (contains(statement, var)
							&& !subString(statementSubstring, var)
							|| contains(preEquals, "[" + var) || contains(
							preEquals, var + "]")
							&& !subString(preEquals, var))) {
				returnString = statement;
				break;
			} else if (what.equals("Conditional usage")
					&& contains(statement, var) && !subString(statement, var)
					&& control(statement)) {
				returnString = statement;
				break;
			} else if (what.equals("Other") && contains(statement, var)
					&& !subString(statement, var)) {
				returnString = statement;
				break;
			}
		}
		return returnString;
	}

	/*
	 * Return true if input string starts with character that qualifies is as
	 * variable assignment statement
	 */
	private boolean assignmentCharacter(String substring) {
		boolean appropriateCharacter = false;
		if (substring.startsWith("=") || substring.startsWith("++")
				|| substring.startsWith("--")) {
			appropriateCharacter = true;
		}
		return appropriateCharacter;
	}

	/*
	 * Return true if input string starts with character that qualifies is as
	 * array assignment statement
	 */
	private boolean arrayIndex(String var, String statement) {
		boolean isIndex = false;
		for (int i = 0; i < statement.length(); i++) {
			String statementSubstring = statement.substring(i);
			if (statementSubstring.startsWith("[" + var)
					&& !subString(statement, var)) {
				isIndex = true;
				break;
			}
		}
		return isIndex;
	}

	/*
	 * Get whereabouts map for a given variable @param inputStatements
	 * Assignment, usage, conditional, or other statements for a given variable
	 * @param statementBank Program sliced statements for variable
	 */
	private HashMap whereabouts(ArrayList inputStatements,
			ArrayList statementBank) {
		HashMap returnMap = new HashMap();
		Iterator it = inputStatements.iterator();
		while (it.hasNext()) {
			String statement = (String) it.next();
			ArrayList analysedArray = buildArray(statement, statementBank);
			returnMap.put(statement, analysedArray);
		}
		return returnMap;
	}

	/*
	 * Return array containing whereabouts of given input statement in program
	 * sliced statements
	 */
	private ArrayList buildArray(String statement, ArrayList statementBank) {
		int lineCount = 1;
		int latestPosition = 0;
		int foundCount = 0;
		ArrayList indexCard = new ArrayList();
		Stack doStack = new Stack();
		ArrayList returnArray = new ArrayList();
		int curlyCount = 0;
		while (lineCount <= statementBank.size()) {
			String bankedStatement = (String) statementBank.get(lineCount - 1);
			if (bankedStatement.endsWith("{")) {
				returnArray.add(new Integer(lineCount)); //add statement line
														 // index to return
														 // array
				returnArray.add(bankedStatement); //add statement to return
												  // array
				if (bankedStatement.startsWith("do")) {
					doStack.push(new Integer(curlyCount)); //note position of
														   // do loop in
														   // hierarchy
				}
				curlyCount++;
			} else if (bankedStatement.endsWith("}")) {
				for (int p = 0; p < 2; p++) {
					returnArray.remove(returnArray.size() - 1); //remove line
																// index and
																// signature
																// from end of
																// array
				}
				curlyCount--;
				if (!doStack.empty()) {
					Integer tempInt = (Integer) doStack.peek();
					if (curlyCount == tempInt.intValue()) {
						doStack.pop(); //remove do loop indicator from top of
									   // stack

					}
				}
			}
			if (statement.equals(bankedStatement) && lineCount > latestPosition) {
				if (!doStack.empty()) { //add while parts of outstanding do
										// loops
					int doWhileCount = lineCount; //starting from next line
					for (int j = 0; j < doStack.size(); j++) {
						Integer doCount = (Integer) doStack.pop();
						while (doWhileCount < statementBank.size()) {
							String testString = (String) statementBank
									.get(doWhileCount);
							if (curlyCount == doCount.intValue()) {
								String tempString = "do {} " + testString;
								int index = (2 * doCount.intValue()) + 1;
								returnArray.add(index, tempString); //replace
																	// existing
																	// do
																	// statment
																	// with do
																	// while
																	// statement
								returnArray.remove(index + 1);
								doWhileCount = statementBank.size();
							}
							if (testString.endsWith("{"))
								curlyCount++;
							else if (testString.endsWith("}"))
								curlyCount--;
							doWhileCount++;
						}
					}
				}
				latestPosition = lineCount;
				returnArray.add(foundCount, new Integer(lineCount)); //add line
																	 // number
																	 // after
																	 // previous
																	 // found
																	 // line
																	 // number
				lineCount = 0;
				indexCard.add(new Integer(foundCount + 1)); //note where new
															// found statement
															// number will be
															// located in
															// returnArray
				foundCount = returnArray.size(); // to ensure that future
												 // statement line numbers are
												 // added before latest loop
												 // line numbers
			}
			lineCount++;
		}
		indexCard.add(new Integer(foundCount + 1));
		returnArray.add(0, indexCard); //add index beginning of array
		return returnArray;
	}
}