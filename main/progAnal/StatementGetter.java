package main.progAnal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Stack;

/**
 * @author cbishop
 *  
 */
public class StatementGetter extends ProgramAnalyser {

	private String inputKey;

	private LinkedHashMap inputMap;

	private Set keySet;

	private Iterator iter;

	private Stack stack;

	private Set variables;

	/**
	 * Constructor for StatementGetter
	 *  
	 */
	public StatementGetter() {
		super();
	}

	/**
	 * Return map of control and variable containing statements
	 * 
	 * @param inputCode
	 *            LinkedHashMap being hierarchical store of source code
	 * @param inputVars
	 *            Set of variable names
	 * @return LinkedHashMap of variable statements
	 */
	public LinkedHashMap getStatements(LinkedHashMap inputCode, Set inputVars) {
		inputMap = inputCode;
		variables = inputVars;
		keySet = inputCode.keySet();
		stack = new Stack();
		LinkedHashMap variableStatements = getStatementMap(inputMap);
		return variableStatements;
	}

	/*
	 * Iterate through set of declared variables and return map containing
	 * relevant statements for each one
	 */
	private LinkedHashMap getStatementMap(LinkedHashMap mapToAnalyse) {
		LinkedHashMap returnMap = new LinkedHashMap();
		Iterator it = variables.iterator();
		while (it.hasNext()) { //for each variable, search code for variable
							   // statements
			String variable = (String) it.next();
			ArrayList tempArray = fillArray(variable, mapToAnalyse);
			returnMap.put(variable, tempArray);
		}
		return returnMap;

	}

	/*
	 * Return ArrayList of relevant statements for a given variable
	 */
	private ArrayList fillArray(String variableName, LinkedHashMap mapToAnalyse) {
		ArrayList returnArray = new ArrayList();
		keySet = mapToAnalyse.keySet();
		iter = keySet.iterator();
		stack.clear();
		ArrayList tempArray = new ArrayList();
		int lineCounter = 0;
		int latestPosition = 0;
		boolean openQuote = false; //flag for if string is in ""s.
		while (iter.hasNext()) {
			boolean variablePresent = false;
			String tempStatement = (String) iter.next();
			lineCounter++;
			for (int i = 0; i < tempStatement.length(); i++) {
				String tempString = tempStatement.substring(i);
				if (tempStatement.charAt(i) == 34) {
					openQuote = !openQuote;
				}
				if (tempString.startsWith(variableName)
						&& !subString(tempStatement, variableName)
						&& lineCounter > latestPosition && !openQuote) {
					variablePresent = true;
					mapToAnalyse = inputMap; //start search from top of map
											 // again
					latestPosition = lineCounter;
					lineCounter = 0;
					keySet = mapToAnalyse.keySet();
					iter = keySet.iterator();
					stack.clear();
					break;
				}
			}
			if (variablePresent) {
				tempArray.add(tempStatement);
				returnArray.add(tempArray);
				tempArray = new ArrayList();
			} else if (tempStatement.endsWith("{")) {
				tempArray.add(tempStatement);
				mapToAnalyse = goDownLevel(mapToAnalyse, tempStatement);
			} else if (tempStatement.endsWith("}")) {
				tempArray.add(tempStatement);
				mapToAnalyse = goUpLevel();
			} else if ((tempStatement.startsWith("while ") || tempStatement
					.startsWith("while("))
					&& lineCounter > latestPosition) { //add all while if
													   // statements without
													   // following '{' to cater
													   // for "do" loops
				tempArray.add(tempStatement); //in case condition depends on
											  // variale set by relevant
											  // variable during loop
			}
		}
		returnArray.add(tempArray); //add final array, even if not variable
									// statement present, in case relevant while
									// statement follows last use of variable
		return returnArray;
	}

	/*
	 * Go down one level in source code hierarchy
	 */
	private LinkedHashMap goDownLevel(LinkedHashMap mapToTraverse, String mapKey) {
		stack.push(mapToTraverse); //put current input map onto stack
		stack.push(iter); //put current iterator onto stack
		mapToTraverse = (LinkedHashMap) mapToTraverse.get(mapKey);
		keySet = mapToTraverse.keySet();
		iter = keySet.iterator();
		return mapToTraverse;
	}

	/*
	 * Go up one level in source code hiearchy
	 */
	private LinkedHashMap goUpLevel() {
		LinkedHashMap returnMap;
		iter = (Iterator) stack.pop(); //pop higher level iterator of stack
		returnMap = (LinkedHashMap) stack.pop(); //pop higher level input map
												 // off stack
		return returnMap;
	}
}