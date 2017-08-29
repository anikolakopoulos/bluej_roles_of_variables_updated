package main.progAnal;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Stack;
import java.util.*;

/**
 * Class to take java source code file as input and output ArrayList containing
 * hierarchical breakdown of code into methods, branches, sub-branches, etc
 * 
 * @author Craig Bishop
 * @version 090607
 */
public class SourceSorter {
	public LinkedHashMap currentMap;
	
	private Stack hashMaps;

	private String inputLine;

	private int bracketCount;

	private boolean openQuote;

	private RoleHolder roles;
	
	private ArrayList duplicateValues;

	/**
	 * Constructor for objects of class SourceBreakDown
	 */
	public SourceSorter() {
	}

	/**
	 * Method to break down input file into ArrayList of Strings with separate
	 * entry in list for each level in the code hierarchy.
	 * 
	 * @param fileName
	 *            String giving name of file to be checked
	 * @return LinkedHashMap containing a breakdown of the input source code
	 */
	public LinkedHashMap sortSource(String fileName) {
		roles = new RoleHolder();
		currentMap = new LinkedHashMap();
		duplicateValues = new ArrayList();
		hashMaps = new Stack();
		inputLine = "";
		String tempLine;
		boolean openQuote = false;
		try {
			FileInputStream fileIn = new FileInputStream(fileName);
			BufferedReader input = new BufferedReader(new InputStreamReader(fileIn));
			do {
				tempLine = input.readLine();
				if (tempLine != null) {
					String trimLine = tempLine.trim();
					processLine(trimLine);
				}
			} while (tempLine != null);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return currentMap;
	}

	/**
	 * Method to process each line for addition to LinkedHashMap
	 * 
	 * @param lineToProcess the line to be processed.
	 */
	private void processLine(String lineToProcess) {
		for (int i = 0; i < lineToProcess.length(); i++) {
			inputLine += lineToProcess.substring(i, i + 1); //add first character of string to inputLine
			if (lineToProcess.charAt(i) == 34) {
				openQuote = !openQuote;
			}
			if (!openQuote) { //check that string to be processed is not within inverted commas.
				if (lineToProcess.substring(i).startsWith("*/")) { //check if previous string is comment
					inputLine = ""; //remove comment string prior to "*/"
					i++; // increment i so that "/" is not added to input line next time round
				} else if (comment(lineToProcess.substring(i))) { //check if subsequent string is comment
					String commentLine = lineToProcess.substring(i);
					if (roleDeclaration(commentLine)) {
						roles.noteRole(commentLine);
					}
					if (commentLine.startsWith("//")) {
						inputLine = inputLine.substring(0, inputLine.length() - 1); // remove first character from inputLine
						i = lineToProcess.length();
					}
				} else if (lineToProcess.substring(i, i + 1).equals("(")) { //check if character within brackets
					bracketCount++;
				} else if (lineToProcess.substring(i, i + 1).equals(")")) {
					bracketCount--;
				} else if (lineToProcess.substring(i, i + 1).equals(";") && bracketCount == 0) { //only treat ";" as end line if not within brackets
					inputLine = inputLine.trim();
					if(currentMap.get(inputLine) != null) {
					    duplicateValues = (ArrayList)currentMap.get(inputLine);
					    duplicateValues.add(inputLine);
					    currentMap.put(inputLine, duplicateValues);
					} else {
					    duplicateValues = new ArrayList();
					    duplicateValues.add(inputLine);
					    currentMap.put(inputLine, duplicateValues);
					}
					//currentMap.put(inputLine, inputLine); 
					inputLine = "";
				} else if (lineToProcess.substring(i, i + 1).equals("{") && !lineToProcess.endsWith("};")) {
					LinkedHashMap tempMap = new LinkedHashMap();
					inputLine = inputLine.trim();
					currentMap.put(inputLine, tempMap);
					hashMaps.push(currentMap);
					currentMap = (LinkedHashMap) currentMap.get(inputLine);
					inputLine = "";
				} else if (lineToProcess.substring(i, i + 1).equals("}") && !lineToProcess.endsWith("};")) {
					inputLine = inputLine.trim();
					currentMap.put(inputLine, inputLine);
					currentMap = (LinkedHashMap) hashMaps.pop();
					inputLine = "";
				}
			}
		}
	}

	/*
	 * Return whether line is comment
	 */
	private boolean comment(String lineString) {
		boolean isComment = false;
		if (lineString.startsWith("/*") || lineString.startsWith("//")) {
			isComment = true;
		}
		return isComment;
	}

	/*
	 * Return whether role is declared for variable in comment line
	 */
	private boolean roleDeclaration(String commentLine) {
		boolean roleDeclared = false;
		for (int i = 0; i < commentLine.length() - 1; i++) {
			if (commentLine.substring(i, i + 2).equals("%%")) {
				roleDeclared = true;
			}
		}
		return roleDeclared;
	}

	public RoleHolder getRoleHolder() {
		return roles;
	}
}

