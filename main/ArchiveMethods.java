package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;
/**
* this is not a Class, but a place holder for methods developed, but not currently being used.
*/

/**
 * From code analyser
 */
/*private LinkedHashMap removeSpace()
{
    LinkedHashMap noSpaceMap = new LinkedHashMap();
	stack = new Stack();
    iter = keySet.iterator();
	while (iter.hasNext()) {
        inputKey = (String) iter.next();
        String noSpaceKey = "";
        for (int i = 0; i < inputKey.length(); i++) {
        	char tempChar = inputKey.charAt(i);
        	if (tempChar != 32) { //look for white space
        		noSpaceKey += inputKey.substring(i, i+1);
        	}
        }
        if (inputKey.endsWith("{")) {
    		stack.push(noSpaceMap); //add current no space map to stack
        	inputMap = goDownLevel(inputMap, inputKey);
    		LinkedHashMap tempMap = new LinkedHashMap();
        	noSpaceMap.put(noSpaceKey, tempMap); //add new map at next level with noSpace string as key
        	noSpaceMap = tempMap; //set current no space map to next level map
        }
        else if (inputKey.endsWith("}")) {
    		noSpaceMap.put(noSpaceKey, noSpaceKey); //add string to map with key as itself
        	inputMap = goUpLevel();
    		noSpaceMap = (LinkedHashMap) stack.pop();
        }
        else {
        	noSpaceMap.put(noSpaceKey, noSpaceKey);
        }
        
    }
	return noSpaceMap;
}

	public void printStatements(Set vars, HashMap statements)
	{
		String printStatements = "";
		Iterator iter = vars.iterator();
		while (iter.hasNext()) {
			String keyString = (String) iter.next();
			ArrayList tempArray = (ArrayList) statements.get(keyString);
			//System.out.println(keyString + ": " + tempArray.size());
			printStatements += keyString+": "+ tempArray.size()+"\n";
			ArrayList previousControlStatements = new ArrayList();
			String indent = "";
			int bracketCount = 0;
			for (int i = 0; i < tempArray.size(); i++) {
				ArrayList printArray = (ArrayList) tempArray.get(i);
				int startIndex = 0;
				for (int count = 0; count < printArray.size(); count ++) {
					String printString = (String) (printArray.get(count));
					if (!previous(printString, previousControlStatements, startIndex)) {
						if (printString.endsWith("}")) {
							indent = indent.substring(0, indent.length()- 4);
							bracketCount--;
							previousControlStatements.add(printString);
							startIndex++;
						}
						printStatements += indent+printString+"\n";
						//System.out.println(indent+printString);
						if (printString.endsWith("{")) {
							indent+="    ";
							bracketCount++;
							for (int j = startIndex; j < previousControlStatements.size(); j++) {
								previousControlStatements.remove(startIndex);
								indent = indent.substring(0, indent.length()- 4);
								printStatements += indent+"}\n";
								//System.out.println(indent+"}");
								bracketCount--;
							}
							previousControlStatements.add(printString);
							startIndex++;
						}
					}
					else {
						startIndex ++;
					}
				}
				if (i == tempArray.size()-1) {
					for (int k = 0; k < bracketCount; k++) {
		                indent = indent.substring(0, indent.length()- 4);
						printStatements += indent+"}\n";
		                //System.out.println(indent+"}");
					}
				}
			}
			printStatements += "\n";
			//System.out.println();
		}
		System.out.println(printStatements);
	}
	
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
	
	private ArrayList findAssignments(String var, ArrayList varStatements) {
		System.out.println("Assignment statements :" +  var); //debug
		ArrayList assignments = new ArrayList();
		int bracketCount = 0;
		for (int i = 0; i < varStatements.size(); i++) {
			String statement = (String) varStatements.get(i);
			statement = removeSpaces(statement);
			String preEquals = "";
			for (int j = 0; j < statement.length(); j++) {
				String statementSubstring = statement.substring(j);
				preEquals += statement.substring(j, j+1);
				if (statementSubstring.startsWith("(")) {
					bracketCount ++;
				}
				else if (statementSubstring.startsWith(")")) {
					bracketCount --;
				} 
				if (statementSubstring.startsWith("=") && contains(preEquals, var)
						&& bracketCount == 0) {
					assignments.add(statement);
					System.out.println(statement); // debug
					break;
				}
			}
		}
		return assignments;
	}
	
	private ArrayList findUsages(String var, ArrayList varStatements) {
		System.out.println("Usage statements: " + var); //debug
		ArrayList usages = new ArrayList();
		int bracketCount = 0;
		for (int i = 0; i < varStatements.size(); i++) {
			String statement = (String) varStatements.get(i);
			statement = removeSpaces(statement);
			if (!assignment(statement)) {
				for (int j = 0; j < statement.length(); j++) {
				String statementSubstring = statement.substring(j);
					if (statementSubstring.startsWith("(")) {
						bracketCount ++;
					}
					else if (statementSubstring.startsWith(")")) {
						bracketCount --;
					} 
					if (statementSubstring.startsWith("=") && contains(statementSubstring, var)
							&& bracketCount == 0) {
						usages.add(statement);
						System.out.println(statement); // debug
						j = statement.length();
					}	
			
				}
			}
		}
		return usages;
	}
	
	private ArrayList findConditionalUsages(String var, ArrayList varStatements) {
		ArrayList conditionals = new ArrayList();
		System.out.println("Conditional usage statements: " + var); //debug
		int bracketCount = 0;
		for (int i = 0; i < varStatements.size(); i++) {
			String statement = (String) varStatements.get(i);
			statement = removeSpaces(statement);
			if (!assignment(statement) && !usage(statement)) {
				for (int j = 0; j < statement.length(); j++) {
				String statementSubstring = statement.substring(j);
					if (statementSubstring.startsWith("(")) {
						bracketCount ++;
					}
					else if (statementSubstring.startsWith(")")) {
						bracketCount --;
					} 
					if (contains(statementSubstring, var)
							&& bracketCount > 0) {
						conditionals.add(statement);
						System.out.println(statement); // debug
						j = statement.length();
					}	
			
				}
			}
		}
		return conditionals;
	}
	
		private boolean assignment(String statement) {
		boolean isAssignment = false;
		for (int i = 0; i < assignmentStatements.size(); i++) {
			String assignment = (String) assignmentStatements.get(i);
			if (statement.equals(assignment)) isAssignment = true;
		}
		return isAssignment;
	}
	
	private boolean usage(String statement) {
		boolean isUsage = false;
		for (int i = 0; i < usageStatements.size(); i++) {
			String usage = (String) usageStatements.get(i);
			if (statement.equals(usage)) isUsage = true;
		}
		return isUsage;
	}
	
	{"BubbleSort.java", "Closest.java", "DiceGame.java", "DivMod7.java", "Doubles.java",
			"Fibonachi.java", "Growth.java", "Histogram.java", "Lexical.java", "Multiplication.java",
			"Number.java", "Occur.java", "ProgramTime.java", "Saw.java", "SmoothedAverage.java", "Square.java", "TwoLargest.java"};
	}
	
	private ArrayList buildArray(String statement, ArrayList statementBank) {
		int curlyCount = 0;
		int lineCount = 1;
		int latestPosition = 0;
		int foundCount = 0;
		ArrayList indexCard = new ArrayList();
		Stack processingStack = new Stack();
		//Stack doStack = new Stack();
		ArrayList returnArray = new ArrayList();
		for (int i = 0; i < statementBank.size() ; i++) {
			String bankedStatement = (String) statementBank.get(i);
			if (bankedStatement.endsWith("{")) {
				curlyCount ++;
			}
			else if (bankedStatement.endsWith("}")) {
				if (!processingStack.empty()) {
					Integer loopIndex = (Integer) processingStack.peek();
					//Integer doIndex = (Integer) doStack.peek(); 
					if (curlyCount == loopIndex.intValue()) { // && curlyCount == doIndex.intValue()) { //end of lowest current loop detected
						for (int p = 0; p < 3; p++) processingStack.pop(); //remove curlyCount, loop index and signature from processing stack
						//doStack.pop();// remove curlyCount from doStack
					}
				}
				curlyCount --;
			}
			if (loop(bankedStatement)) {
				processingStack.push(new Integer(lineCount)); //add loop line number to stack
				processingStack.push(bankedStatement); //add loop signature to stack
				processingStack.push(new Integer(curlyCount)); //add curly count index to Stack
				//if (bankedStatement.startsWith("do")) doStack.push(new Integer(curlyCount)); //add position of "do" loop to doStack.
			}
			if (statement.equals(bankedStatement) && lineCount > latestPosition 
					&& !processingStack.empty()) {
				Iterator iter = processingStack.iterator();
				while (iter.hasNext()) {
					returnArray.add(processingStack.pop()); //add to tempArray
				}
				for (int j = foundCount ; j < returnArray.size(); j += 2) {
					returnArray.remove(j); //remove curlyCount values from end of ArrayList
				}
				latestPosition = lineCount;
				processingStack.clear();
				returnArray.add(foundCount, new Integer(lineCount)); //add line number after previous found line number
				lineCount = 0;
				indexCard.add(new Integer(foundCount+1)); //note where new found statement number will be located in returnArray
				foundCount = returnArray.size(); // to ensure that future statement line numbers are added before latest loop line numbers
			}
			else if (statement.equals(bankedStatement) && lineCount > latestPosition) {
				latestPosition = lineCount;
				returnArray.add(foundCount, new Integer(lineCount));
				indexCard.add(new Integer(foundCount+1)); //note where new found statement number will be located in returnArray
				foundCount = returnArray.size();
			}
			lineCount ++;
		}
		indexCard.add(new Integer(foundCount+1));
		returnArray.add(0, indexCard); //add index beginning of array
		return returnArray;
	}
	
						if (programAnalyser.loop(statementSignature)) {
						System.out.print("in loop: " + statementSignature);
					}
					else if (programAnalyser.isIf(statementSignature)) {
						System.out.print("in branch: " + statementSignature);
					}
					else if (programAnalyser.isClass(statementSignature)) {
						System.out.print("in class: " + statementSignature);
					}
					else if (programAnalyser.accessModifierStatement(statementSignature)) {
						System.out.print("in method: " + statementSignature);
					}
					
package main.progAnal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

public class StatementPruner extends ProgramAnalyser {
	
	public LinkedHashMap pruneStatements(LinkedHashMap variableStatements)
	{
		LinkedHashMap prunedMap = variableStatements;
		Set keySet = prunedMap.keySet();
		Iterator it = keySet.iterator();
		String tempKey = "";
		while (it.hasNext()) {
			tempKey = (String) it.next();
			ArrayList variableArray = (ArrayList) prunedMap.get(tempKey);
			for (int i = 0; i < variableArray.size(); i++) {
				ArrayList tempArray = (ArrayList) variableArray.get(i);
				//if (printStatement(tempArray) || throwStatement(tempArray)) {
				//	variableArray.remove(i);
				//	i--; //so doesn't miss next statementArrayList, or cause IndexOutOfBoundsException
				//}
			}
		}
		return prunedMap;
	}
}

//from RoleMain development implementation
			//InfoPrinter printer = new InfoPrinter(); //debug purposes
			//
			//while (it.hasNext()) {	
			//	String variable = (String) it.next();
			//  ArrayList checkResults = (ArrayList) checkedRoles.get(variable);
			//	Integer roleKey = (Integer) roles.get(variable);
			//	String role = (String) roleMap.get(roleKey);	
			//printer.printResults(checkResults, variable, role);
			//}
			//printer.printSource(brokenSource); //debug purposes
			//printer.printRoles(roleHolder); //debug purposes
			//printer.printStatementArrays(variables, variableStatements); //debug purposes
			//printer.printStatements(variables, sortedStatements);
			//printer.printMethods(methods);
			//SliceWriter slicer = new SliceWriter();
			//slicer.writeSlice(roles, sortedStatements);
			//printer.printAnalysedStatements(analysedStatements);

*/