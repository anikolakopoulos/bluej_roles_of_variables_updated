package main.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * @author cbishop
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DebugStringer {

	private String variable;

	private LinkedHashMap analysedMap;

	/**
	 * Constructor for DebugStringer
	 * 
	 * @param inputMap
	 *            LinkedHashMap containing analysed statements
	 * @param var
	 *            String variable for which string to be generated
	 */
	public DebugStringer(LinkedHashMap inputMap, String var) {
		variable = var;
		analysedMap = inputMap;
	}

	/**
	 * Show statement for variable found in loop
	 * 
	 * @param foundInLoop
	 *            HashMap containing all statements for a given variable found
	 *            in a loop
	 * @param what
	 *            String specifying what sort of statement to look for
	 * @return String giving statement and its location in the program slice
	 */
	public String showFoundInLoop(HashMap foundInLoop, String what) {
		String returnString = "";
		boolean inLoop = false;
		Set foundSet = foundInLoop.keySet();
		Iterator it = foundSet.iterator();
		while (it.hasNext()) {
			String statementInQuestion = (String) it.next();
			ArrayList statementInLoop = (ArrayList) foundInLoop
					.get(statementInQuestion);
			returnString += variable + " " + what + " statement in loop is: "
					+ statementInQuestion + "\n";
			inLoop = true;
			for (int i = 0; i < statementInLoop.size(); i++) {
				ArrayList specificOccurance = (ArrayList) statementInLoop
						.get(i);
				returnString += "at line " + specificOccurance.get(0)
						+ " in loop: ";
				for (int j = 1; j < specificOccurance.size() - 1; j += 2) {
					returnString += specificOccurance.get(j + 1) + " at line: ";
					returnString += specificOccurance.get(j) + "\n";
				}
			}
		}
		return returnString;
	}

	/**
	 * Show statements found in loop in which variable is assigned
	 * 
	 * @param foundInLoop
	 *            HashMap containing all statements found in assignment loop
	 * @param what
	 *            String The sort of statemen to look for
	 * @return String giving statement and its location in the program slice
	 */
	public String showFoundInAssignmentLoop(HashMap foundInLoop, String what) {
		String returnString = "";
		//boolean inLoop = false;
		Set foundSet = foundInLoop.keySet();
		Iterator it = foundSet.iterator();
		while (it.hasNext()) {
			String statementInQuestion = (String) it.next();
			ArrayList statementInLoop = (ArrayList) foundInLoop
					.get(statementInQuestion);
			//inLoop = true;
			for (int i = 0; i < statementInLoop.size(); i++) {
				ArrayList specificOccurance = (ArrayList) statementInLoop
						.get(i);
				returnString += what + " statement '" + statementInQuestion
						+ "' found at line: " + specificOccurance.get(0);
				for (int j = 3; j < specificOccurance.size() - 1; j += 2) {
					returnString += "\nin loop '"
							+ specificOccurance.get(j + 1) + "' (at line: ";
					returnString += specificOccurance.get(j) + ")";
				}
				returnString += "\nassignment statement '"
						+ specificOccurance.get(2) + "' found at line "
						+ specificOccurance.get(1) + "\n";
			}
		}
		return returnString;
	}

	/**
	 * Show if variable assigned in for loop statement
	 * 
	 * @param is
	 *            boolean Whether statement is assigned in for loop statement
	 * @return String stating whether statement is assigned in for loop
	 *         statement
	 */
	public String assignedInFor(boolean is) {
		String returnString;
		if (is)
			returnString = "variable " + variable
					+ " is assigned in 'for' loop statement";
		else
			returnString = "variable " + variable
					+ " is not assigned in 'for' loop statement";
		return returnString;
	}

	/**
	 * Show whether there is any direct use of the variable in teh program
	 * 
	 * @return String indicating that there is no direct use of variable in the
	 *         program
	 */
	public String noDirectUse() {
		String returnString = "limited or no conditional use of variable in assignment loop and direct condition for assignment loop";
		return returnString;
	}

	/**
	 * Show when toggle statement is found in loop
	 * 
	 * @param toggleStatements
	 *            ArrayList of toggleStatements for variable
	 * @return String givin toggle statement found in a loop
	 */
	public String toggle(ArrayList toggleStatements) {
		String returnString = "";
		for (int i = 0; i < toggleStatements.size(); i++) {
			returnString += "toggle statement " + toggleStatements.get(i)
					+ " found in loop\n";
		}
		return returnString;
	}

	/**
	 * Show that variable is not toggled within loop
	 * 
	 * @return String
	 */
	public String toggle() {
		return "variable is not toggled in loop";
	}

	/**
	 * Show that variable is condition for its assignment branch
	 * 
	 * @param conditionalAssignmentBranch
	 *            HashMap containing assignmentBranch condition statements
	 * @return String giving offending statement and whereabouts in program
	 *         slice
	 */
	public String conditionForAssignBranch(HashMap conditionalAssignmentBranch) {
		String returnString = "";
		Set statementSet = conditionalAssignmentBranch.keySet();
		Iterator it = statementSet.iterator();
		while (it.hasNext()) {
			String statement = (String) it.next();
			ArrayList branchStatements = (ArrayList) conditionalAssignmentBranch
					.get(statement);
			for (int i = 0; i < branchStatements.size() - 1; i += 3) {
				returnString += "assignment statement '"
						+ branchStatements.get(i) + "'\n";
				returnString += "found at line " + branchStatements.get(i + 1)
						+ " in branch '" + statement + "' at line "
						+ branchStatements.get(i + 2) + "\n";
			}
			returnString += "variable appears to be condition for branch in which it is assigned\n";
		}
		return returnString;
	}

	/**
	 * State that variable is not assigned in branch, or condition for
	 * assignment branch
	 * 
	 * @return Message to that effect
	 */
	public String conditionForAssignBranch() {
		return "variable is not assigned in branch, or is not condition for branch in which it is assigned\n";
	}

	/**
	 * Show that variable appears on both side of assignment statement
	 * 
	 * @param onBothSides
	 *            ArrayList containing statements in question
	 * @param is
	 *            whether variable does appear on both sides or not
	 * @return String stating whether variable appears on both sides and if so
	 *         giving offending statement
	 */
	public String bothSides(ArrayList onBothSides, boolean is) {
		String returnString = "";
		if (is) {
			Iterator it = onBothSides.iterator();
			while (it.hasNext()) {
				String statement = (String) it.next();
				returnString += "variable " + variable
						+ " appears on both sides of assignment statement "
						+ statement + "\n";
			}
		} else
			returnString += returnString += "variable "
					+ variable
					+ " does not appear on both sides of assignment statement\n";
		return returnString;
	}

	/**
	 * Show that variable is incremented/decremented in loop
	 * 
	 * @param incDecStatements
	 *            ArrayList of incDec statements
	 * @return String giving offending statements
	 */
	public String incDec(ArrayList incDecStatements) {
		String returnString = "";
		Iterator it = incDecStatements.iterator();
		while (it.hasNext()) {
			String statement = (String) it.next();
			returnString += "variable "
					+ variable
					+ " is incremented/decremented etc within loop in statement "
					+ statement + "\n";
		}
		return returnString;
	}

	/**
	 * State that variable is not incremented or decremented within loop
	 * 
	 * @return String to that effect
	 */
	public String incDec() {
		String returnString = "variable " + variable
				+ " is not incremented/decremented in loop statement";
		return returnString;
	}

	/**
	 * State that variable does not appear to be array
	 * 
	 * @return String to that effect
	 */
	public String notArray() {
		String returnString = "variable " + variable
				+ " is not array, or is not used directly as an array\n";
		return returnString;
	}

	/**
	 * State that variable is not assigned within a loop
	 * 
	 * @return String to that effect
	 */
	public String notAssignedInLoop() {
		String returnString = "variable " + variable
				+ " is not assigned within loop\n";
		return returnString;
	}

	/**
	 * State that there appear to be no organizer statements for variable
	 * 
	 * @return String to that effect
	 */
	public String notOrganizer() {
		String returnString = "there are no organizer type assignment statements for variable "
				+ variable + "\n";
		return returnString;
	}

	/**
	 * Show that variable appears in organizer type statements
	 * 
	 * @param organizerStatements
	 *            ArrayList of organizer statements
	 * @return String confirming this and giving organizer statements in
	 *         question
	 */
	public String isOrganizer(ArrayList organizerStatements) {
		String returnString = "variable " + variable
				+ " is used as organizer in statement ";
		for (int i = 0; i < organizerStatements.size(); i++) {
			String statement = (String) organizerStatements.get(i) + "\n";
			returnString += statement;
		}
		return returnString;
	}

	/**
	 * State that variable does not appear to be transformation
	 * 
	 * @return String to that effect
	 */
	public String notTransform() {
		String returnString = "one or more assignment statement either contains no operator characters, or call to method";
		return returnString;
	}

	/**
	 * Show that variable is transformation
	 * 
	 * @param transformations
	 *            ArrayList containing transformation statements
	 * @return String showing transformation type statement for variable
	 */
	public String isTransformation(ArrayList transformations) {
		String returnString = "";
		for (int i = 0; i < transformations.size(); i++) {
			returnString += "statement " + transformations.get(i)
					+ " appears to be transformation statement\n";
		}
		return returnString;
	}

	/**
	 * State that variable does appear to be indirectly toggled within loop
	 * 
	 * @return String to that effect
	 */
	public String nestedBooleanAssign() {
		return "variable "
				+ variable
				+ " is not boolean assigned with alternate values in nested and outer loops";
	}

	/**
	 * Show that variable appears to be indirectly toggled within loop
	 * 
	 * @param nestedAssigns
	 *            ArrayList of nested boolean assignments
	 * @return String showing statements in question
	 */
	public String nestedBooleanAssign(ArrayList nestedAssigns) {
		String returnString = "";
		for (int i = 0; i < nestedAssigns.size(); i += 3) {
			String statementType = (String) nestedAssigns.get(i + 1);
			if (statementType.equals("nested")) {
				returnString += "assignment statement " + nestedAssigns.get(i)
						+ " found in nested loop in loop containing statement "
						+ nestedAssigns.get(i + 2);
			} else {
				returnString += "assignment statement "
						+ nestedAssigns.get(i + 2)
						+ " found in nested loop in loop containing statement "
						+ nestedAssigns.get(i);
			}
		}
		return returnString;
	}

	/**
	 * Show that variable appears to be assigned with return from method call
	 * 
	 * @param methodAssigns
	 *            ArrayList of assignment statements within method calls on
	 *            right hand side
	 * @return String showing variable and statements in question
	 */
	public String methodAssigns(ArrayList methodAssigns) {
		String returnString = "";
		for (int i = 0; i < methodAssigns.size(); i++) {
			String statement = (String) methodAssigns.get(i);
			returnString += "variable " + variable
					+ " appears to be assigned with output "
					+ "from method in statement " + statement + "\n";
		}
		return returnString;
	}

	/**
	 * State that variable is not used in assignment loop
	 * 
	 * @return String to that effect
	 */
	public String notUsedInAssign() {
		return "variable "
				+ variable
				+ " does not appear to be used in same loop in which it is assigned\n";
	}

	/**
	 * Show that variable is assigned before use in loop
	 * 
	 * @param assignBeforeUse
	 *            ArrayList of statements
	 * @return String showing specific statements and fact that variable not
	 *         used outside of loop
	 */
	public String temporary(ArrayList assignBeforeUse) {
		String returnString = "";
		returnString += "variable " + variable + " used in statement '"
				+ assignBeforeUse.get(0) + "'\n" + "before assignment '"
				+ assignBeforeUse.get(1) + "\n";
		for (int i = 2; i < assignBeforeUse.size(); i++) {
			returnString += "in loop " + assignBeforeUse.get(i) + "\n";
		}
		returnString += "and not used outside of assign loop\n";
		return returnString;
	}

	/**
	 * State that variable is used outside of loop in which it is assigned
	 * 
	 * @return String to that effect
	 */
	public String usedOutsideAssign() {
		return "variable " + variable
				+ " is used outside of loop in which it is assigned";
	}

	/**
	 * State that variable is not assigned before use in a loop
	 * 
	 * @return String to that effect
	 */
	public String assignBeforeUse() {
		return "variable " + variable
				+ " is not assigned value before use in loop";
	}

	/**
	 * State that variable is only assigned directly with value of other
	 * variable
	 * 
	 * @return String to that effect
	 */
	public String notMethodAssign() {
		return "variable " + variable
				+ " only assigned with value of other variable";
	}

	/**
	 * State that variable appears to be array filled in loop
	 * 
	 * @return String to that effect
	 */
	public String arrayAssignInLoop() {
		return "variable " + variable + " is array that is filled in loop";
	}

	/**
	 * State that variable is used directly in program
	 * 
	 * @return String to that effect
	 */
	public String directUse() {
		return "variable " + variable + " is used directly";
	}

	/**
	 * State that variable is not used for condition of assignment loop
	 * 
	 * @return String to that effect
	 */
	public String notUsedForAssignLoopCondition() {
		return "variable " + variable
				+ " is not used for condition of loop in which it is assigned";
	}

	/**
	 * State that variable is used for loop condition outside of loop in which
	 * it is assigned
	 * 
	 * @return String to that effect
	 */
	public String loopUseNoAssign() {
		return "variable "
				+ variable
				+ " is used for loop condition outside of loop in which it is assigned";
	}
}