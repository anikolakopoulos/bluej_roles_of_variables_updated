package main.progAnal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Class to produce program slice for each variable
 * 
 * @author cbishop
 */
public class ProgramSlicer {

	HashMap statementMap;

	Iterator iter;

	/**
	 * Return map containing program slices for each variable
	 * 
	 * @param variables
	 *            Set of variable name
	 * @param relevantStatements
	 *            LinkedHashMap containing statement for program slice
	 * @return HashMap of program slices
	 */
	public HashMap sortStatements(Set variables, LinkedHashMap relevantStatements) {
		statementMap = new HashMap();
		iter = variables.iterator();
		while (iter.hasNext()) {
			String keyString = (String) iter.next();
			ArrayList parentArray = (ArrayList) relevantStatements.get(keyString);
			ArrayList variableArray = getVariableStatements(parentArray);
			statementMap.put(keyString, variableArray);
		}
		return statementMap;
	}

	/*
	 * Return ArrayList of program slice statements for each variable
	 */
	private ArrayList getVariableStatements(ArrayList inputArray) {
		ArrayList sortedArray = new ArrayList();
		ArrayList previousControlStatements = new ArrayList();
		int bracketCount = 0;
		for (int i = 0; i < inputArray.size(); i++) {
			ArrayList statementArray = (ArrayList) inputArray.get(i);
			int startIndex = 0;
			//System.out.println(sortedArray);
			for (int count = 0; count < statementArray.size(); count++) {
				String statementString = (String) (statementArray.get(count));
				if (!previous(statementString, previousControlStatements, startIndex)) {
					if (statementString.endsWith("}")) {
						bracketCount--;
						previousControlStatements.add(statementString);
						startIndex++;
					}
					sortedArray.add(statementString);
					if (statementString.endsWith("{")) {
						bracketCount++;
						for (int j = startIndex; j < previousControlStatements.size(); j++) {
							previousControlStatements.remove(startIndex);
							sortedArray.add("}");
							bracketCount--;
						}
						previousControlStatements.add(statementString);
						startIndex++;
					}
				} else {
					startIndex++; //so don't remove relevant statement from previous control statements
				}
			}
			if (i == inputArray.size() - 1) {
				for (int k = 0; k < bracketCount; k++) {
					sortedArray.add("}");
				}
			}
		}
		return sortedArray;
	}

	/*
	 * Return whether control statement has already been added to program slice
	 */
	private boolean previous(String statement, ArrayList previousStatements, int start) {
		boolean isPrevious = false;
		if (previousStatements.size() > 0) {
			for (int prevIndex = 0; prevIndex < previousStatements.size(); prevIndex++) {
				String tempString = (String) previousStatements.get(prevIndex);
				if (statement.equals(tempString) && start <= prevIndex) {
					isPrevious = true;
					break;
				}
			}
		}
		return isPrevious;
	}
}