package main.progAnal;

import java.util.HashSet;
import java.util.ArrayList;


/**
 * Class containing common methods used by other program analyser classes
 * 
 * @author cbishop
 */
public class ProgramAnalyser {
	private static final char[] FOLLOWING_CHARS = { 32, 37, 38, 41, 42, 43, 44,
			45, 46, 47, 59, 60, 61, 62, 91, 93, 94, 124 };

	private static final char[] PREVIOUS_CHARS = { 32, 33, 37, 38, 40, 41, 42,
			43, 45, 47, 60, 61, 62, 91, 93, 94, 124 };

	private static final char[] ARITHMETIC_CHARS = { 32, 37, 40, 41, 42, 43,
			45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 59 };

	private static final char[] OPERATORS = { 33, 37, 38, 42, 43, 45, 47, 94,
			124 };

	private static final char[] OTHER_CHARS = { 34, 46, 40, 41 };

	/**
	 * Return whether input variable name found in input statement refers to the
	 * variable in question, or is simply a substring of a longer identifier
	 * 
	 * @param inputStatement
	 *            String containing statement
	 * @param potentialSubstring
	 *            String variable
	 * @return boolean true is variable is substring
	 */
	public boolean subString(String inputStatement, String potentialSubstring) {
		boolean isSubstring = true;
		for (int i = 0; i < inputStatement.length(); i++) {
			String tempString = inputStatement.substring(i);
			if (tempString.startsWith(potentialSubstring)) {
				char followingChar = tempString.charAt(potentialSubstring.length());
				if (i == 0 && isPermitted(followingChar, true)) {
					isSubstring = false;
					break;
				} else if (i > 0) {
					char previousChar = inputStatement.charAt(i - 1);
					if (isPermitted(previousChar, false) && isPermitted(followingChar, true)) {
						isSubstring = false;
						break;
					}
				}
			}
		}
		return isSubstring;
	}

	/*
	 * Return whether characters prior to or following the variable name in the
	 * statement are permitted, or not. If not permitted, then variable name is
	 * part of substring.
	 */
	private boolean isPermitted(char charToCheck, boolean following) {
		boolean isPermitted = false;
		if (following) {
			for (int i = 0; i < FOLLOWING_CHARS.length; i++) {
				char checker = FOLLOWING_CHARS[i];
				if (charToCheck == FOLLOWING_CHARS[i]) {
					isPermitted = true;
					break;
				}
			}
		} else {
			for (int i = 0; i < PREVIOUS_CHARS.length; i++) {
				char checker = PREVIOUS_CHARS[i];
				if (charToCheck == PREVIOUS_CHARS[i]) {
					isPermitted = true;
					break;
				}
			}
		}
		return isPermitted;
	}

	/**
	 * Remove white space from input String
	 * 
	 * @param inputString
	 * @return String with white space removed
	 */
	public String removeSpaces(String inputString) {
		String outputString = "";
		for (int i = 0; i < inputString.length(); i++) {
			char tempChar = inputString.charAt(i);
			if (tempChar != 32) { //look for white space
				outputString += inputString.substring(i, i + 1);
			}
		}
		return outputString;
	}

	/**
	 * Return whether input statement contain the variable
	 * 
	 * @param statement
	 *            String being statement to analyse
	 * @param var
	 *            String being name of variable
	 * @return boolean true if statement contains the variable
	 */
	public boolean contains(String statement, String var) {
		boolean contains = false;
		for (int i = 0; i < statement.length(); i++) {
			String statementSubstring = statement.substring(i);
			if (statementSubstring.startsWith(var)) {
				contains = true;
				break;
			}
		}
		return contains;
	}

	/**
	 * Return whether variable in statement is enclosed in brackets
	 * 
	 * @param statement
	 *            String being statement to analyse
	 * @param var
	 *            String being name of variable
	 * @return boolean true if variable is enclosed in brackets
	 */
	public boolean inBrackets(String statement, String var) {
		boolean bracketed = false;
		int bracketCount = 0;
		for (int i = 0; i < statement.length(); i++) {
			String statementSubstring = statement.substring(i);
			if (statement.charAt(i) == 40)
				bracketCount++;
			else if (statement.charAt(i) == 41)
				bracketCount--;
			if (statementSubstring.startsWith(var) && bracketCount > 0) {
				bracketed = true;
				break;
			} else if (statementSubstring.startsWith(var) && bracketCount == 0) {
				bracketed = false;
			}
		}
		return bracketed;
	}

	/**
	 * Return whether statement is a loop statement or not
	 * 
	 * @param statement
	 *            String being statement to analyse
	 * @return boolean true if statement is loop statement
	 */
	public boolean loop(String statement) {
		boolean isLoop = false;
		if (statement.startsWith("while") || statement.startsWith("for") || statement.startsWith("do")) {
			isLoop = true;
		}
		return isLoop;
	}

	/**
	 * Return whether statement is "else" statement
	 * 
	 * @param stringToCheck
	 *            String being statement to analyse
	 * @return boolean true if statement is "else"
	 */
	public boolean isElse(String stringToCheck) {
		boolean isElse = false;
		if (stringToCheck.startsWith("else ")
				|| stringToCheck.startsWith("else{")
				|| stringToCheck.startsWith("else(")
				&& !stringToCheck.startsWith("else if")) {
			isElse = true;
		}
		return isElse;
	}

	/**
	 * Return whether statement is "if" statement
	 * 
	 * @param stringToCheck
	 *            String being statement to analyse
	 * @return boolean true if statement is "if"
	 */
	public boolean isIf(String stringToCheck) {
		boolean isIf = false;
		if (stringToCheck.startsWith("if ") || stringToCheck.startsWith("if(")
				|| stringToCheck.startsWith("else if ")
				|| stringToCheck.startsWith("else if(")) {
			isIf = true;
		}
		return isIf;
	}

	/**
	 * Return whether statement is branch statement
	 * 
	 * @param statementToCheck
	 *            String being statement to analyse
	 * @return boolean true if statement is branch statement
	 */
	public boolean branch(String statementToCheck) {
		boolean isBranch = false;
		if (isIf(statementToCheck) || isElse(statementToCheck)) {
			isBranch = true;
		}
		return isBranch;
	}

	/**
	 * Return whether statement is control construct
	 * 
	 * @param stringToCheck
	 *            String being statement to analyse
	 * @return boolean true if statement is "if"
	 */
	public boolean control(String stringToCheck) {
		boolean isControl = false;
		if (loop(stringToCheck) || branch(stringToCheck)) {
			isControl = true;
		}
		return isControl;
	}

	/**
	 * Return whether statement is usage statement where value of variable is
	 * output to terminal for use by program user
	 * 
	 * @param inputStatement
	 *            String being statement to analyse
	 * @param variable
	 *            String being variable in question
	 * @return boolean true if statement is print usage statement
	 */
	public boolean printUse(String inputStatement, String variable) {
		boolean isPrintUse = false;
		if (inputStatement.startsWith("System.out.print")
				&& inBrackets(inputStatement, variable)
				&& contains(inputStatement, variable)
				&& !subString(inputStatement, variable)) {
			isPrintUse = true;
		}
		return isPrintUse;
	}

	/**
	 * Return whether statement is throw statement
	 * 
	 * @param statementArray
	 *            String being statement to analyse
	 * @return boolean true is statement is throw statement
	 */
	public boolean throwStatement(ArrayList statementArray) {
		boolean isThrow = false;
		for (int i = 0; i < statementArray.size(); i++) {
			String tempStatement = (String) statementArray.get(i);
			if (tempStatement.startsWith("throw")) {
				isThrow = true;
			}
		}
		return isThrow;
	}

	/**
	 * Return whether statement is try or catch statement
	 * 
	 * @param statement
	 *            String being statement to analyse
	 * @return boolean true is statement is try/catch
	 */
	public boolean tryCatchStatement(String statement) {
		boolean isTryCatch = false;
		if (statement.startsWith("try") || statement.startsWith("catch")) {
			isTryCatch = true;
		}
		return isTryCatch;
	}

	/**
	 * Return whether statement is method signature
	 * 
	 * @param statement
	 *            String being statement to analyse
	 * @return boolean true is statement is method signature
	 */
	public boolean methodStatement(String statement) {
		boolean methodStatement = false;
		if ((statement.startsWith("private") || statement.startsWith("public"))
				&& contains(statement, "(") && !contains(statement, " class ")
				&& statement.endsWith("{")) {
			methodStatement = true;
		}
		return methodStatement;

	}

	/**
	 * Return whether statement is class declaration
	 * 
	 * @param statement
	 *            String being statement to analyse
	 * @return boolean true is statement is class declaration
	 */
	public boolean isClass(String statement) {
		boolean classDeclaration = false;
		for (int i = 0; i < statement.length(); i++) {
			if (statement.substring(i).startsWith("class")
					&& !subString(statement, "class")) {
				classDeclaration = true;
			}
		}
		return classDeclaration;
	}

	/**
	 * Return true if input statement is already in ArrayList of statements
	 * 
	 * @param statement
	 *            String being statment to analyse
	 * @param inputStatements
	 *            ArrayList of statements
	 * @return boolean true if statement is in ArrayList
	 */
	public boolean isInArray(String statement, ArrayList inputStatements) {
		boolean isInArray = false;
		for (int i = 0; i < inputStatements.size(); i++) {
			String arrayStatement = (String) inputStatements.get(i);
			if (statement.equals(arrayStatement)) {
				isInArray = true;
				break;
			}
		}
		return isInArray;
	}

	/**
	 * Return true if input statement is an arithmetic expression
	 * 
	 * @param statementToCheck
	 *            String being statement to analyse
	 * @return boolean true if statement is arithmetic expression
	 */
	public boolean arithExp(String statementToCheck) {
		boolean arithExp = true;
		for (int i = 0; i < statementToCheck.length(); i++) {
			boolean charCheck = false;
			char charToCheck = statementToCheck.charAt(i);
			for (int j = 0; j < ARITHMETIC_CHARS.length; j++) {
				if (charToCheck == ARITHMETIC_CHARS[j]) {
					charCheck = true; // check if particular character is
									  // arithmetic char
					j = ARITHMETIC_CHARS.length;
				}
			}
			if (!charCheck) { // if any character is not arithmetic char, set
							  // arithExp false
				arithExp = false;
				break;
			}
		}
		return arithExp;
	}

	/**
	 * Return true if input statement containing variable is an arithmetic
	 * expression
	 * 
	 * @param statementToCheck
	 *            String being statement to analyse
	 * @param var
	 *            String being variable
	 * @return boolean true if statement is arithmetic expression
	 */
	public boolean arithExp(String statementToCheck, String var) {
		boolean arithExp = true;
		for (int i = 0; i < statementToCheck.length(); i++) {
			String statementSubstring = statementToCheck.substring(i);
			if (statementSubstring.startsWith(var + "[")) {
				statementToCheck = statementSubstring.substring(var.length());
				for (int j = 0; j < statementToCheck.length(); j++) {
					if (statementToCheck.substring(j).equals("]")) {
						statementToCheck = statementToCheck.substring(j);
					}
				}
				i = 0;
			} else if (statementSubstring.startsWith(var)) {
				statementToCheck = statementSubstring.substring(var.length());
				i = 0;
			}
			boolean charCheck = false;
			char charToCheck = statementToCheck.charAt(i);
			for (int j = 0; j < ARITHMETIC_CHARS.length; j++) {
				if (charToCheck == ARITHMETIC_CHARS[j]) {
					charCheck = true; //check if particular character is
									  // arithmetic char
					j = ARITHMETIC_CHARS.length;
				}
			}
			if (!charCheck) { //if any character is not arithmetic char, set
							  // arithExp false
				arithExp = false;
				break;
			}
		}
		return arithExp;
	}

	/**
	 * Return part of input statement that occurs after the "=" sign
	 * 
	 * @param inputStatement
	 *            String being statement to analyse
	 * @return String representing part of statement after "="
	 */
	public String afterEquals(String inputStatement) {
		String returnString = "";
		int bracketCount = 0;
		for (int k = 0; k < inputStatement.length(); k++) {
			String statementSubstring = inputStatement.substring(k);
			if (statementSubstring.startsWith("=") && bracketCount == 0) {
				returnString = statementSubstring;
				break;
			}
			if (statementSubstring.startsWith("(")) {
				bracketCount++;
			}
			if (statementSubstring.startsWith(")")) {
				bracketCount--;
			}
		}
		return returnString;
	}

	/**
	 * Return whether input statement is operator or other statement
	 * 
	 * @param statement
	 *            String being statement to analyse
	 * @param what
	 *            String stating what sort of characters to look for
	 * @return boolean true if statement contains any of the characters
	 */
	public boolean is(String statement, String what) {
		boolean is = false;
		for (int i = 0; i < statement.length(); i++) {
			char charToCheck = statement.charAt(i);
			boolean contains = false;
			if (what.equals("operator")) {
				for (int j = 0; j < OPERATORS.length; j++) {
					if (charToCheck == OPERATORS[j]) { //see if character from
													   // statement is an
													   // operator character
						contains = true;
						j = OPERATORS.length;
					}
				}
			} else if (what.equals("other")) {
				for (int j = 0; j < OTHER_CHARS.length; j++) {
					if (charToCheck == OTHER_CHARS[j]) { //see if character
														 // from statement is an
														 // other character
						contains = true;
						j = OTHER_CHARS.length;
					}
				}
			}
			if (contains) { //if one character in statement is operator/other
							// character
				is = true;
				break;
			}
		}
		return is;
	}
	
}