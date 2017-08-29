package main.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Stack;
import main.RoleAnalyser;
import main.progAnal.MethodGetter;
import main.progAnal.RoleHolder;
import main.progAnal.SourceSorter;
import main.progAnal.StatementAnalyser;
import main.progAnal.StatementGetter;
import main.progAnal.ProgramSlicer;
import main.rules.RuleApplyer;
import main.utils.InfoPrinter;

/**
 * @author cbishop
 *  
 */
public class TestMain {
	private static final String[] FILES = { "BubbleSort.java", "Closest.java",
			"DiceGame.java", "DivMod7.java", "Doubles.java", "Fibonacci.java",
			"Growth.java", "Histogram.java", "Lexical.java",
			"Multiplication.java", "Number.java", "Occur.java",
			"ProgramTime.java", "Saw.java", "SmoothedAverage.java",
			"Square.java", "TwoLargest.java" };

	private static final String[] ROLES = { "Fixed Value", "Organizer",
			"Stepper", "Most Recent Holder", "Gatherer", "Most Wanted Holder",
			"One Way Flag", "Transformation", "Follower", "Temporary" };

	private SourceSorter sourceSorter;

	private StatementGetter statementGetter;

	private LinkedHashMap brokenSource;

	private Set variables;

	private LinkedHashMap relevantStatements;

	private LinkedHashMap variableStatements;

	private HashMap sortedStatements;

	private LinkedHashMap roles;

	private String file;

	private HashMap roleMap;

	public static void main(String[] args) {
		TestMain testMain = new TestMain(FILES);
	}

	/**
	 * Constructor for test class
	 * 
	 * @param files
	 *            String[] giving files names of all training programs
	 */
	public TestMain(String[] files) {
		//runAll();
		testRA(files);
		//testRoV(files);
	}

	/*
	 * Test RoV software in same way that RolesOfVariables class will use
	 * RoleAnalyser
	 */
	private void testRoV(String[] fileStrings) {
		File[] files = new File[fileStrings.length];
		for (int i = 0; i < fileStrings.length; i++) {
			files[i] = new File("../../java programs/RoVs/" + fileStrings[i]);
		}
		RoleAnalyser roleAnalyser = new RoleAnalyser();
		HashMap roleResults = roleAnalyser.checkRoles();
		Set fileNames = roleResults.keySet();
		Iterator it = fileNames.iterator();
		HashMap roleMap = (HashMap) roleResults.get("role map");
		while (it.hasNext()) {
			String fileName = (String) it.next();
			if (!fileName.equals("role map")) {
				ArrayList fileResults = (ArrayList) roleResults.get(fileName);
				HashMap checkedResults = (HashMap) fileResults.get(0);
				LinkedHashMap roles = (LinkedHashMap) fileResults.get(1);
				Set variables = checkedResults.keySet();
				Iterator iter = variables.iterator();
				while (iter.hasNext()) {
					String variable = (String) iter.next();
					Integer roleInt = (Integer) roles.get(variable);
					String role = (String) roleMap.get(roleInt);
					ArrayList checkedRole = (ArrayList) checkedResults
							.get(variable);
					Boolean isOK = (Boolean) checkedRole.get(0);
					if (!isOK.booleanValue()) {
						String errorString = "Possible incorrect role anotation of '"
								+ role + "' for variable '" + variable + "'.\n";
						errorString += checkedRole.get(1) + "\n";
						errorString += " - " + checkedRole.get(2) + "\n"; //add
																		  // reason
						errorString += checkedRole.get(3); //add role
														   // suggestion
						System.out.println(errorString + "\n");
					} else {
						System.out.println("Variable '" + variable
								+ "', role annotation OK.\n");
					}
				}
			}
		}
	}

	/*
	 * Test RoV in similar way to way RoleAnalyser instantiates program analysis
	 * and rules
	 */
	private void testRA(String[] files) {
		InfoPrinter printer = new InfoPrinter();
		for (int i = 0; i < files.length; i++) {
			file = files[i];
			roleMap = initialiseMap();
			SourceSorter sourceSorter = new SourceSorter();
			brokenSource = sourceSorter.sortSource("../../java programs/RoVs/"
					+ file);
			RoleHolder roleHolder = sourceSorter.getRoleHolder();
			variables = roleHolder.getVariables();
			roles = roleHolder.getRoles();
			StatementGetter statementGetter = new StatementGetter();
			variableStatements = statementGetter.getStatements(brokenSource,
					variables);
			ProgramSlicer statementSorter = new ProgramSlicer();
			sortedStatements = statementSorter.sortStatements(variables,
					variableStatements);
			//printer.printStatements(variables, sortedStatements);
			StatementAnalyser statementAnalyser = new StatementAnalyser(
					sortedStatements, variables);
			HashMap analysedStatements = statementAnalyser
					.getStatementAnalysis();
			//printer.printAnalysedStatements(analysedStatements);
			MethodGetter methodGetter = new MethodGetter();
			ArrayList methods = methodGetter.getMethods(sortedStatements);
			RuleApplyer ruleApplyer = new RuleApplyer();
			HashMap checkedRoles = new HashMap();
			checkedRoles = ruleApplyer.applyRules(analysedStatements, roles,
					methods);
			Iterator it = variables.iterator();
			while (it.hasNext()) {
				String variable = (String) it.next();
				ArrayList checkResults = (ArrayList) checkedRoles.get(variable);
				Integer roleKey = (Integer) roles.get(variable);
				String role = (String) roleMap.get(roleKey);
				printer.printTestResults(checkResults, variable, role);
			}
		}
	}

	/*
	 * Initialise map associating role within specific int keys.
	 */
	private HashMap initialiseMap() {
		HashMap returnMap = new HashMap();
		for (int i = 0; i < 10; i++) {
			returnMap.put(new Integer(i + 1), ROLES[i]);
		}
		return returnMap;
	}

	/*
	 * Run all program slicing tests
	 */
	private void runAll() {
		testSourceBreakdown();
		testArrayStatements();
		testSortedStatements();
	}

	/*
	 * Compare broken source with expected source break down and report errors
	 */
	private void testSourceBreakdown() {
		System.out.println("Source Test: " + file);
		ArrayList returnArray = new ArrayList();
		Set keySet = (Set) brokenSource.keySet();
		Stack iterators = new Stack();
		Stack maps = new Stack();
		Iterator it = keySet.iterator();
		int lineCounter = 1;
		boolean testOk = true;
		try {
			FileInputStream fileIn = new FileInputStream(
					"../../test files/source files/" + file);
			BufferedReader input = new BufferedReader(new InputStreamReader(
					fileIn));
			while (it.hasNext()) {
				String key = (String) it.next();
				String testLine = input.readLine();
				String testKey = removeSpaces(key);
				testLine = removeSpaces(testLine);
				if (!testKey.equals(testLine)) {
					System.out.println("Error at line :" + lineCounter + ": "
							+ testKey + "\t" + testLine);
					testOk = false;
				}
				if (key.endsWith("{")) {
					maps.push(brokenSource);
					iterators.push(it);
					brokenSource = (LinkedHashMap) brokenSource.get(key);
					keySet = brokenSource.keySet();
					it = keySet.iterator();
				} else if (key.endsWith("}")) {
					brokenSource = (LinkedHashMap) maps.pop();
					it = (Iterator) iterators.pop();
				}
				lineCounter++;
			}
			if (testOk) {
				System.out.println("Passed!");
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();
	}

	/*
	 * Compare statement ArrayLists with expected ArrayList for each variable
	 * and report errors
	 */
	private void testArrayStatements() {
		Iterator iter = variables.iterator();
		System.out.println("Array Test: " + file);
		boolean testOk = true;
		try {
			FileInputStream fileIn = new FileInputStream(
					"../../test files/array files/" + file);
			BufferedReader input = new BufferedReader(new InputStreamReader(
					fileIn));
			int lineCounter = 1;
			while (iter.hasNext()) {
				String keyString = (String) iter.next();
				ArrayList tempArray = (ArrayList) variableStatements
						.get(keyString);
				System.out.println(keyString);
				for (int i = 0; i < tempArray.size(); i++) {
					ArrayList variableArray = (ArrayList) tempArray.get(i);
					for (int count = 0; count < variableArray.size(); count++) {
						String variableString = (String) (variableArray
								.get(count));
						String testString = input.readLine();
						if (!variableString.equals(testString)) {
							System.out
									.println("Error at line " + lineCounter
											+ "\t" + variableString + "\t"
											+ testString);
							testOk = false;
						}
					}
				}
				lineCounter++;
			}
			if (testOk) {
				System.out.println("Passed!\n");
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * Compare program slices for each variable with expected program slices
	 */
	private void testSortedStatements() {
		System.out.println("Statement Test: " + file);
		Iterator iter = variables.iterator();
		boolean testOk = true;
		int lineCounter = 1;
		try {
			FileInputStream fileIn = new FileInputStream(
					"../../test files/statement files/" + file);
			BufferedReader input = new BufferedReader(new InputStreamReader(
					fileIn));
			while (iter.hasNext()) {
				String keyString = (String) iter.next();
				System.out.println(keyString);
				ArrayList statementArray = (ArrayList) sortedStatements
						.get(keyString);
				for (int i = 0; i < statementArray.size(); i++) {
					String statementString = (String) (statementArray.get(i));
					String testString = input.readLine();
					if (!statementString.equals(testString)) {
						System.out.println("Error at line " + lineCounter
								+ "\t" + statementString + "\t" + testString);
						testOk = false;
					}
				}
				lineCounter++;
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		if (testOk) {
			System.out.println("Passed!\n");
		}
	}

	private String removeSpaces(String inputString) {
		String outputString = "";
		for (int i = 0; i < inputString.length(); i++) {
			char tempChar = inputString.charAt(i);
			if (tempChar != 32) { //look for white space
				outputString += inputString.substring(i, i + 1);
			}
		}
		return outputString;
	}
}