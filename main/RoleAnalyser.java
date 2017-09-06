package main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import main.progAnal.MethodGetter;
import main.progAnal.StatementAnalyser;
import main.progAnal.RoleHolder;
import main.progAnal.SourceSorter;
import main.progAnal.StatementGetter;
import main.progAnal.ProgramSlicer;
import main.rules.RuleApplyer;

public class RoleAnalyser {
       
    private SourceSorter sourceSorter;

    private LinkedHashMap brokenSource;

    private StatementGetter statementGetter;

    private static final String[] PROGRAMS = { //Joensuu University examples
                                               "BubbleSort.java", "Closest.java",
            "DiceGame.java", "DivMod7.java", "Doubles.java", "Fibonacci.java",
            "Growth.java", "Histogram.java", "Lexical.java", "Multiplication.java",
            "Number.java", "Occur.java", "ProgramTime.java", "Saw.java",
            "SmoothedAverage.java", "Square.java", "TwoLargest.java", "Bank.java",
            "SalesApplication.java", "Animals.java", "School.java", "Median.java",
	    "Palindrome.java", "Sum.java",  
        // other examples (including sources as suggested by Joensuu University's references)
		                               "App.java", "Apps.java", 
	    "DeleteNodes.java", "QuestionCellRenderer.java", "TreeExample1", 
	    "TreeExample2", "EditableTree.java", "MainClass.java",
	    "BinarySearchExample.java", "Puzzle_007.java", 
	    "GraphTraversals.java", "TreeExpansionListenerDemo.java",
            "BinaryTree.java", "TreeIconDemo", "TreeIconDemo2.java",
            "SinglyLinkedListInsertDeleteLastExample.java", "QueueLinkedList.java",
            "MyBinarySearch.java", "methods.java", "Lesson27.java", 
            "SelectableTree.java", "LinkedList.java",
            "PostPreorderAndDepthEnumeration.java", "Assignment4.java",
            "Apps_I.java", "Rational.java", "Application.java",
            "ArralistIteratorExample.java", "Sanakirja.java", "BinarySearch.java",
            "EditableTrees.java", "hw_7.java", "MainRandom.java",
            "ScannerNextDemo.java"};

    private static final String[] ROLES = { "Fixed Value", "Organizer",
            "Stepper", "Most Recent Holder", "Gatherer", "Most Wanted Holder",
            "One Way Flag", "Follower", "Temporary", "Container", "Walker" };

    private HashMap roleMap;
    
    private File[] files;

	public RoleAnalyser(File[] inputFiles) //should input name of file to check here
	{
		files = inputFiles;
	}
                               
    public RoleAnalyser() //should input name of file to check here
    {

    }
    
    public static void main(String[] args) {

        RoleAnalyser roleAnalyser = new RoleAnalyser();
        roleAnalyser.checkRoles();
		
    }
    
    /**
     * Check roles played by variables
     * 
     * @return HashMap containing results of role checking
     */
    public HashMap checkRoles() {
        HashMap returnMap = new HashMap();
            for (int i = 0; i < files.length; i++) {
                ArrayList fileResults = new ArrayList();
                String fileName = files[i].toString();
                roleMap = initialiseMap();
                brokenSource = getSortedSource(fileName);
                Set variables = getVariables();
                LinkedHashMap variableStatements = getStatementMap(brokenSource, variables);
                RoleHolder roleHolder = sourceSorter.getRoleHolder();
                LinkedHashMap roles = roleHolder.getRoles();
                HashMap sortedStatements = sortStatements(variables, variableStatements);
                HashMap analysedStatements = getStatementAnalysis(sortedStatements,variables);
                ArrayList methods = getMethods(sortedStatements);
                HashMap checkedRoles = getResults(analysedStatements, roles, methods, brokenSource);
                fileResults.add(checkedRoles); //add results of analysis for given file
                fileResults.add(roles); //add annotated roles for each variable in given file
                returnMap.put(files[i].getName(), fileResults); //add fileResults
                // to return map for given file
                  
        }
        
        returnMap.put("role map", roleMap); //add role map to return map
        return returnMap;
    }

    /*
     * Return hierarchy of source code statements
     */
    public LinkedHashMap getSortedSource(String fileName) {
        sourceSorter = new SourceSorter();
        LinkedHashMap returnMap = sourceSorter.sortSource(fileName);
        return returnMap;
    }

    /*
     * Return Set of variables
     */
    public Set getVariables() {
        RoleHolder roles = sourceSorter.getRoleHolder();
        Set returnSet = roles.getVariables();
        return returnSet;
    }

    /*
     * Return map of variable statements
     */
    public LinkedHashMap getStatementMap(LinkedHashMap sourceMap, Set variables) {
        StatementGetter statementGetter = new StatementGetter();
        LinkedHashMap returnMap = statementGetter.getStatements(brokenSource, variables);
        return returnMap;
    }

    /*
     * Return map of program slices
     */
    public HashMap sortStatements(Set variables, LinkedHashMap relevantStatements) {
        ProgramSlicer statementSorter = new ProgramSlicer();
        HashMap returnMap = statementSorter.sortStatements(variables, relevantStatements);
        return returnMap;
    }

    /*
     * Return map of analysed statements
     */
    public HashMap getStatementAnalysis(HashMap statements, Set variables) {
        HashMap analysedStatements = new HashMap();
        StatementAnalyser statementAnalyser = new StatementAnalyser(statements, variables);
        analysedStatements = statementAnalyser.getStatementAnalysis();
        return analysedStatements;
    }

    /*
     * Return map of results from role checking
     */
    public HashMap getResults(HashMap analysedStatements, LinkedHashMap roles, ArrayList methods, LinkedHashMap currentMap) {
        HashMap checkedRoles = new HashMap();
        RuleApplyer ruleApplyer = new RuleApplyer();
        checkedRoles = ruleApplyer.applyRules(analysedStatements, roles, methods, currentMap);
        return checkedRoles;
    }

    /*
     * Initialised map with roles and associated int values
     */
    public HashMap initialiseMap() {
        HashMap returnMap = new HashMap();
        for (int i = 0; i < 10; i++) {
            returnMap.put(new Integer(i + 1), ROLES[i]);
        }
        return returnMap;
    }

    /*
     * Return list of methods in analysed program
     */
    public ArrayList getMethods(HashMap sortedStatements) {
        ArrayList returnArray = new ArrayList();
        MethodGetter methodGetter = new MethodGetter();
        returnArray = methodGetter.getMethods(sortedStatements);
        return returnArray;
    }
}