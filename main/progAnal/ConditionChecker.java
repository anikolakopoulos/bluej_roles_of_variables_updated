package main.progAnal;

import java.util.*;
import java.util.regex.Pattern;

import main.RoleAnalyser;

import java.util.regex.Matcher;


/**
 * Class to check conditions under which variables are assigned and used
 * 
 * @author cbishop
 */
public class ConditionChecker extends ProgramAnalyser {

    private String variable;

    private LinkedHashMap analysedMap;

    private ArrayList methods;
    
    private LinkedHashMap sourceSorter;
    
    /**
     * Constructor for ConditionChecker
     * 
     * @param var
     *            Name of input variable
     * @param inputMap
     *            Analysed map of variable statements
     * @param methodNames
     *            List of method names in calss
     */
    public ConditionChecker(LinkedHashMap inputMap, String var, ArrayList methodNames, LinkedHashMap currentMap) {
        variable = var;
        analysedMap = inputMap;
        methods = methodNames;
        sourceSorter = currentMap;
    }

    /**
     * Return whether there is no assignment statement for variable
     * 
     * @return boolean true is there is no assignment statement
     */
    public boolean noAssignmentStatement() {
        boolean noAssignment = false;
        HashMap assignmentWhereaboutsMap = (HashMap) analysedMap.get("assignment");
        if (assignmentWhereaboutsMap.size() < 1) {
            noAssignment = true;
        }
        return noAssignment;
    }

    /**
     * Return whether there is only one assignment statement for the variable in
     * the program
     * 
     * @return boolean true if there is only one assignment statement
     */
    public boolean onlyOneAssignmentStatement() {
        boolean onlyOneAssignment = false;
        HashMap assignmentWhereaboutsMap = (HashMap) analysedMap.get("assignment");
        //System.out.println(analysedMap.get("assignment"));
        if (assignmentWhereaboutsMap.size() <= 1) {
            onlyOneAssignment = true;
        }
        return onlyOneAssignment;
    }

    /**
     * Return map of statements found in loops or branches
     * 
     * @param what
     *            String being the category of statement
     * @param where
     *            String signifying whether to look in loops or branches
     * @return HashMap containing statement and loop/branch statement in which
     *         it is found
     */
    public HashMap foundIn(String what, String where) {
        HashMap foundIn = new HashMap();
        HashMap whereaboutsMap = (HashMap) analysedMap.get(what);
        Set statementSet = whereaboutsMap.keySet();
        Iterator it = statementSet.iterator();
        while (it.hasNext()) {
            ArrayList statementIn = new ArrayList();
            String statement = (String) it.next();//get statement
            ArrayList specificOccuranceIn = new ArrayList();
            //get array for given statement
            ArrayList occuranceArray = (ArrayList) whereaboutsMap.get(statement);
            boolean found = false;
            if (loop(statement) && where.equals("loop")) {
                //add line number of start of loop to array
                specificOccuranceIn.add(occuranceArray.get(1));
                //add loop statement to array
                specificOccuranceIn.add(statement);
                found = true;
            }
            //get index for statement array
            ArrayList indexArray = (ArrayList) occuranceArray.get(0);
            for (int j = 0; j < indexArray.size() - 1; j++) {
                //get position in array of statement line number
                Integer index = (Integer) indexArray.get(j);
                //get posiition in array of next statement line number
                Integer nextIndex = (Integer) indexArray.get(j + 1);
                for (int i = index.intValue() + 1; i < nextIndex.intValue(); i += 2) {
                    //get branch, loop, method, etc within which assignment
                    // found
                    String statementString = (String) occuranceArray.get(i + 1);
                    if (where.equals("loop")) {
                        if (loop(statementString)) { //isolate loops
                            //add line number of start of loop to array
                            specificOccuranceIn.add(occuranceArray.get(i));
                            //add loop statement to array
                            specificOccuranceIn.add(statementString);
                            found = true;
                        }
                    } else if (where.equals("branch")) {
                        if (branch(statementString)) {
                            //add line number of start of loop to array
                            specificOccuranceIn.add(occuranceArray.get(i));
                            //add loop statement to array
                            specificOccuranceIn.add(statementString);
                            found = true;
                        }
                    }
                }
                if (found) {
                    //if assignment in loop add line number of assignment
                    // statement
                    specificOccuranceIn.add(0, occuranceArray.get(index.intValue()));
                    statementIn.add(specificOccuranceIn);
                }
            }
            if (found) {
                foundIn.put(statement, statementIn);
            }
        }
        return foundIn;
    }

    /**
     * Return variable statements occuring in assignment loops together with
     * loop signatures and line numbers
     * 
     * @param assignmentsInLoop
     *            HashMap of assignment statements found in loops
     * @param inputLoopStatements
     *            HashMap of loop statements
     * @return HashMap containing assignment statements and which loops they are
     *         in
     */
    public HashMap foundInAssignmentLoop(HashMap assignmentsInLoop, HashMap inputLoopStatements) {
        HashMap foundInAssignmentLoop = new HashMap();
        Set statementSet = inputLoopStatements.keySet();
        Iterator it = statementSet.iterator();
        while (it.hasNext()) {
            String statement = (String) it.next();
            ArrayList statementInLoop = (ArrayList) inputLoopStatements.get(statement);
            ArrayList specificStatement = compareLoopStatements(assignmentsInLoop, statementInLoop);
            if (specificStatement.size() > 0) {
                foundInAssignmentLoop.put(statement, specificStatement);
            }
        }
        //System.out.println(foundInAssignmentLoop);
        return foundInAssignmentLoop;
    }

    /*
     * Return array list containing occurance arrays for a given statement
     */
    private ArrayList compareLoopStatements(HashMap assignmentsInLoop, ArrayList statementInLoop) {
        ArrayList specificStatement = new ArrayList();
        Set statementSet = assignmentsInLoop.keySet();
        Iterator it = statementSet.iterator();
        while (it.hasNext()) {
            String statement = (String) it.next();
            ArrayList assignStatInLoop = (ArrayList) assignmentsInLoop.get(statement);
            for (int i = 0; i < statementInLoop.size(); i++) {
                for (int j = 0; j < assignStatInLoop.size(); j++) {
                    ArrayList compares = (ArrayList) statementInLoop.get(i);
                    ArrayList assigns = (ArrayList) assignStatInLoop.get(j);
                    ArrayList inAssignLoop = lookForCommon(compares, assigns);
                    if (inAssignLoop.size() > 0) {
                        inAssignLoop.add(2, statement); //add assignment
                        // statement to array
                        specificStatement.add(inAssignLoop);
                    }
                }
            }
        }
        return specificStatement;
    }

    /*
     * Return array list containing details of occurance for given statement
     */
    private ArrayList lookForCommon(ArrayList compares, ArrayList assigns) {
        ArrayList returnArray = new ArrayList();
        boolean found = false;
        for (int i = 1; i < compares.size(); i += 2) {
            Integer comp = (Integer) compares.get(i);
            for (int j = 1; j < assigns.size(); j += 2) {
                Integer ass = (Integer) assigns.get(j);
                if (comp.intValue() == ass.intValue()) {
                    found = true;
                    returnArray.add(comp);
                    returnArray.add(compares.get(i + 1));
                }
            }
        }
        if (found) {
            //add line number of compare statement
            returnArray.add(0, compares.get(0));
            //add line number of assign statement
            returnArray.add(1, assigns.get(0));
           
        }
        //System.out.println(returnArray);
        return returnArray;
    }

    /**
     * Return ArrayList of statements where use in assignment loop is directly
     * or indirectly related to loop condition
     * 
     * @param foundInAssignmentLoop
     *            HashMap of statement found in assignment loop
     * @return ArrayList of statements that relate to loop condition
     */
    public ArrayList useForLoopCondition(HashMap foundInAssignmentLoop) {
        ArrayList useForLoopCondition = new ArrayList();
        Set statementSet = foundInAssignmentLoop.keySet();
        Iterator it = statementSet.iterator();
        while (it.hasNext()) {
            String statement = (String) it.next();
            ArrayList statementInLoop = (ArrayList) foundInAssignmentLoop.get(statement);
            for (int i = 0; i < statementInLoop.size(); i++) {
                ArrayList specificOccurance = (ArrayList) statementInLoop.get(i);
                for (int j = 4; j < specificOccurance.size(); j += 2) {
                    String loopStatement = (String) specificOccurance.get(j);
                    if (statement.equals(loopStatement)) {
                        useForLoopCondition.add(statement);
                    }
                }
            }
        }
        //System.out.println(useForLoopCondition);
        return useForLoopCondition;
    }

    /**
     * Return true if assignment loop is dependent on assignment statement
     * following conditional use for a given variable
     * 
     * @param foundInAssignmentLoop
     *            HashMap of statements found in assignment loop
     * @return boolean
     */
    public boolean indirectUse(HashMap foundInAssignmentLoop) {
        boolean indirectUse = false;
        Set statementSet = foundInAssignmentLoop.keySet();
        Iterator it = statementSet.iterator();
        while (it.hasNext()) {
            String statement = (String) it.next();
            ArrayList statementInLoop = (ArrayList) foundInAssignmentLoop.get(statement);
            for (int i = 0; i < statementInLoop.size(); i++) {
                ArrayList specificOccurance = (ArrayList) statementInLoop.get(i);
                for (int j = 4; j < specificOccurance.size(); j += 2) {
                    String loopStatement = (String) specificOccurance.get(j);
                    int bracketCount = 0;
                    boolean afterBrackets = false;
                    String indirectAssign = "";
                    for (int k = 0; k < statement.length(); k++) {
                        String statementSubstring = statement.substring(k);
                        if (bracketCount == 0 && afterBrackets) {
                            indirectAssign += statement.substring(k, k + 1);
                            if (indirectAssign.endsWith("=")) {
                                //remove "="
                                indirectAssign = indirectAssign.substring(0, indirectAssign.length() - 1);
                                indirectAssign = indirectAssign.trim();
                                if (contains(statement, indirectAssign)
                                && !subString(statement, indirectAssign)
                                && statementSet.size() < 2) {
                                    indirectUse = true;
                                    break;
                                }
                            }
                        }
                        if (statementSubstring.startsWith("(")) {
                            bracketCount++;
                            afterBrackets = true;
                        }
                        if (statementSubstring.startsWith(")")) {
                            bracketCount--;
                        }
                    }
                    //System.out.println(loopStatement);
                }
            }
            //System.out.println(statementInLoop);
        }
        return indirectUse;
    }

    /**
     * Return list of statements where variable is assigned in a "for" loop
     * declaration
     * 
     * @param what
     *            String signfying the category of statement
     * @return ArrayList of "for" loop assignments
     */
    public ArrayList assignmentInFor(String what) {
        ArrayList assignInFor = new ArrayList();
        HashMap whereaboutsMap = (HashMap) analysedMap.get(what);
        Set statementSet = whereaboutsMap.keySet();
        Iterator it = statementSet.iterator();
        while (it.hasNext()) {
            String statement = (String) it.next();
            String noSpace = removeSpaces(statement);
            if (statement.startsWith("for") && inBrackets(noSpace, variable)
            &&  contains(noSpace, variable + "=")
            && !subString(statement, variable)) {
                assignInFor.add(statement);
            }
        }
        return assignInFor;
    }

    /**
     * Return list of statements where variable appears to be being used inside 
     * the enhanced for-loop construct (a.k.a., for-each loop) to simplify the 
     * processing of accessing elements in a collection.
     * 
     * @return ArrayList
     */
    public ArrayList assignmentInForEach(String what) {
        ArrayList assignInForEach = new ArrayList();
        HashMap whereaboutsMap = (HashMap) analysedMap.get(what);
        Set statementSet = whereaboutsMap.keySet();
        Iterator it = statementSet.iterator();
        while (it.hasNext()) {
            String statement = (String) it.next();
            String noSpace = removeSpaces(statement);
            if (statement.startsWith("for") && inBrackets(noSpace, variable)
            &&  contains(noSpace, variable + ":")
            && !subString(statement, variable)) {
                assignInForEach.add(statement); 
            }  
        }
        return assignInForEach;
    }

    /**
     * Return if variable is not used directly in the program
     * 
     * @return boolean
     */
    public boolean noDirectUsage() {
        boolean noDirectUsage = false;
        HashMap usageWhereaboutsMap = (HashMap) analysedMap.get("usage");
        if (usageWhereaboutsMap.size() < 1) {
            noDirectUsage = true;
        }
        return noDirectUsage;
    }

    /**
     * Return map containing assignment statements and branch statements where
     * variable is condition for branch
     * 
     * @return HashMap
     */
    public HashMap conditionAssignStatement() {
        HashMap conditionForAssignmentBranch = new HashMap();
        HashMap assignments = (HashMap) analysedMap.get("assignment");
        ArrayList conditionAssignment = new ArrayList();
        Set statementSet = assignments.keySet();
        Iterator it = statementSet.iterator();
        //check all assignment statements to see if they are branch statements
        while (it.hasNext()) {
            String assignmentStatement = (String) it.next();
            boolean conditionalAssignment = false;
            //check if variable is condition for its assignment
            if (branch(assignmentStatement)
            && inBrackets(assignmentStatement, variable)) {
                ArrayList statementIn = new ArrayList();
                //get array for given statement
                ArrayList occuranceArray = (ArrayList) assignments.get(assignmentStatement);
                //add line number of assignment statement
                conditionAssignment.add(occuranceArray.get(1));
                //add line number of conditional statement
                conditionAssignment.add(occuranceArray.get(1));
                //add assignment statement to array list
                conditionAssignment.add(0, assignmentStatement);
                conditionalAssignment = true;
            }
            if (conditionalAssignment)
                conditionForAssignmentBranch.put(assignmentStatement, conditionAssignment);
        }
        //System.out.println(conditionForAssignmentBranch);
        return conditionForAssignmentBranch;
    }

    /**
     * Return map containing incidences where assignment in branch is also in
     * loop
     * 
     * @param assignmentsInBranch
     *            HashMap containing assignmentsInBranch
     * @param inputLoopStatements
     *            HashMap containing loop statements
     * @return HashMap
     */
    public HashMap conditionForAssignmentBranch(HashMap assignmentsInBranch, HashMap inputLoopStatements) {
        HashMap conditionForAssignmentBranch = new HashMap();
        Set inputSet = inputLoopStatements.keySet();
        Iterator it = inputSet.iterator();
        while (it.hasNext()) {
            String statement = (String) it.next(); //get statement to look for
            Set branchAssignments = assignmentsInBranch.keySet();
            Iterator iter = branchAssignments.iterator();
            ArrayList conditionAssignment = new ArrayList();
            while (iter.hasNext()) {
                String assignStatement = (String) iter.next();
                ArrayList statementInBranch = (ArrayList) assignmentsInBranch.get(assignStatement);
                for (int i = 0; i < statementInBranch.size(); i++) {
                    ArrayList specificOccurance = (ArrayList) statementInBranch.get(i);
                    //add assignment statement to array list
                    conditionAssignment.add(assignStatement);
                    //add line number for assignment statement
                    conditionAssignment.add(specificOccurance.get(0));
                    boolean isCommon = false;
                    for (int j = 2; j < specificOccurance.size(); j += 2) {
                        String conditionalStatement = (String) specificOccurance.get(j);
                        if (statement.equals(conditionalStatement)) {
                            //add line number for branch
                            conditionAssignment.add(specificOccurance.get(j - 1));
                            isCommon = true;
                            j = specificOccurance.size();
                        }
                    }
                    //if no common statement found, remove assignment statement
                    // and line number
                    if (!isCommon) {
                        conditionAssignment.remove(conditionAssignment.size() - 1);
                        conditionAssignment.remove(conditionAssignment.size() - 1);
                    }
                }
            }
            //check if assignment in branches for which variable is condition
            if (conditionAssignment.size() > 0) {
                conditionForAssignmentBranch.put(statement, conditionAssignment);
            }
        }
        //System.out.println(conditionForAssignmentBranch);
        return conditionForAssignmentBranch;
    }

    /**
     * Return list of statement of a given type
     * 
     * @param assignmentsInLoop
     *            HashMap of assignment statements found in loops
     * @param statementType
     *            String being the type of statement to look for
     * @return ArrayList of statements
     */
    public ArrayList statementTypeCheck(HashMap assignmentsInLoop, String statementType) {
        ArrayList returnArray = new ArrayList();
        Set statementSet = assignmentsInLoop.keySet();
        Iterator it = statementSet.iterator();
        //check all assignment statements to see if variable appears on both
        // sides of statement
        while (it.hasNext()) {
            String statement = (String) it.next();
              if (statementType.equals("bothSides") && onBothSides(statement)) {     
                returnArray.add(statement);
            } else if (statementType.equals("indirect") && indirectBothSides(statement)) {
                returnArray.add(statement);
            } else if (statementType.equals("toggle") && toggleStatement(statement)) {
                returnArray.add(statement);
            } else if (statementType.equals("incDec") && incDecEtc(statement)) {
                returnArray.add(statement);
            }
        }
        //System.out.println(returnArray);
        return returnArray;
    }

    /*
     * Check if variable appears on both sides of assignment statement
     */
    private boolean onBothSides(String statement) {
        boolean onBothSides = false;
        String afterEquals = afterEquals(statement);
        if (contains(afterEquals, variable) && !contains(afterEquals, ",")
        && !subString(afterEquals, variable)) {
            onBothSides = true;
        }
        return onBothSides;
    }

    /*
     * Check if variable appears indirectly on both sides of assignment
     * statement
     */
    private boolean indirectBothSides(String statement) {
        boolean indirectBothSides = false;
        int bracketCount = 0;
        for (int k = 0; k < statement.length(); k++) {
            String statementSubstring = statement.substring(k);
            if (statementSubstring.startsWith(variable) && bracketCount == 0) {
                String afterVar = statementSubstring.substring(variable.length());
                afterVar = removeSpaces(afterVar);
                if(afterVar.startsWith("+=") || afterVar.startsWith("-=")
                || afterVar.startsWith("*=")
                || afterVar.startsWith("/=")
                || afterVar.startsWith("%=")
                &&!arithExp(afterVar.substring(2))) {
                    indirectBothSides = true;
                    k = statement.length(); //end loop if incDec detected
                }
            }
            if (statementSubstring.startsWith("(")) {
                bracketCount++;
            }
            if (statementSubstring.startsWith(")")) {
                bracketCount--;
            }
        }
        return indirectBothSides;
    }

    /*
     * Return true if variable is toggled within statement
     */
    private boolean toggleStatement(String statement) {
        boolean toggleStatement = false;
        String afterEquals = afterEquals(statement);
        if (contains(afterEquals, "!" + variable)
        && !subString(afterEquals, "!" + variable)) {
            toggleStatement = true;
        }
        //System.out.println(toggleStatement);
        return toggleStatement;
    }

    /*
     * Return true if variable is incremented/decremented in statement
     */
    private boolean incDecEtc(String statement) {
        boolean incDecStatement = false;
        int bracketCount = 0;
        for (int k = 0; k < statement.length(); k++) {
            String statementSubstring = statement.substring(k);
            if (statementSubstring.startsWith(variable) && bracketCount == 0) {
                String afterVar = statementSubstring.substring(variable.length());
                afterVar = removeSpaces(afterVar);
                if(afterVar.startsWith("++")
                || afterVar.startsWith("--")
                || afterVar.startsWith("**")
                || afterVar.startsWith("//")
                ||(afterVar.startsWith("+=")
                || afterVar.startsWith("-=")
                || afterVar.startsWith("*=")
                || afterVar.startsWith("/=") || afterVar.startsWith("%=")
                && arithExp(afterVar.substring(2)))
                ||(afterVar.startsWith("=") && onBothSides(statement) 
                    && arithExp(afterVar.substring(1), variable))) {
                    incDecStatement = true;
                    k = statement.length(); //end loop if incDec detected
                }
            }
            if (statementSubstring.startsWith("(")) {
                bracketCount++;
            }
            if (statementSubstring.startsWith(")")) {
                bracketCount--;
            }
        }
        return incDecStatement;
    }

    /**
     * Return map of statements where variable is used outside of the loop in
     * which it is assigned
     * 
     * @param inAssignLoop
     *            HashMap containing usage/conditional statement found in
     *            assignment loop
     * @param what
     *            String being category of statement
     * @return HashMap
     */
    public HashMap outsideAssignLoop(HashMap inAssignLoop, String what) {
        HashMap outsideAssignLoop = new HashMap();
        HashMap whereaboutsMap = (HashMap) analysedMap.get(what);
        Set statementSet = whereaboutsMap.keySet();
        Iterator it = statementSet.iterator();
        while (it.hasNext()) {
            ArrayList statementIn = new ArrayList();
            String statement = (String) it.next();//get statement
            boolean foundOutsideAssign = false;
            ArrayList specificOccuranceOutside = new ArrayList();
            if ((loop(statement) && what.equals("conditional")) || what.equals("usage")) {
                //get array for given statement
                ArrayList occuranceArray = (ArrayList) whereaboutsMap.get(statement);
                //get index for statement array
                ArrayList indexArray = (ArrayList) occuranceArray.get(0);
                for (int j = 0; j < indexArray.size() - 1; j++) {
                    //get position in array of statement line number
                    Integer index = (Integer) indexArray.get(j);
                    //get branch, loop, method, etc within which assignment found
                    Integer statementLineNo = (Integer) occuranceArray.get(index.intValue());
                    specificOccuranceOutside = foundOutside(statementLineNo, inAssignLoop);
                }
            }
            //if statement found outside assign loop
            if (specificOccuranceOutside.size() > 0) {
                //add array of line numbers for that statement to return map
                outsideAssignLoop.put(statement, specificOccuranceOutside);
            }
        }
        //System.out.println(outsideAssignLoop);
        return outsideAssignLoop;
    }

    /*
     * Return list of statements found outside assignment loop
     */
    private ArrayList foundOutside(Integer statementLineNo, HashMap foundInAssignLoop) {
        ArrayList returnArray = new ArrayList();
        Set foundSet = foundInAssignLoop.keySet();
        Iterator it = foundSet.iterator();
        boolean matchFound = false;
        while (it.hasNext()) {
            //get statement found in assign loop
            String foundStatement = (String) it.next();
            //get array for given statement
            ArrayList statementInLoop = (ArrayList) foundInAssignLoop.get(foundStatement);
            for (int i = 0; i < statementInLoop.size(); i++) {
                //get array list for occurance of statement in loop
                ArrayList specificOccurance = (ArrayList) statementInLoop.get(i);
                //get line number of statement
                Integer statementInLoopLineNo = (Integer) specificOccurance.get(0);
                //see if input statement found in assign loop
                if (statementLineNo.intValue() == statementInLoopLineNo.intValue()) {
                    matchFound = true;
                }
            }
        }
        if (!matchFound) { //if input statement line number not found in assign
            //loop add input statement line number to return array
            returnArray.add(statementLineNo);
        }
        return returnArray;
    }

    /**
     * Return list of statements where variable appears to be being used like an
     * array
     * 
     * @return ArrayList
     */
    public ArrayList arrayCheck() {
        ArrayList isArray = new ArrayList();
        String[] whatArray = { "assignment", "usage", "conditional", "other" };
        for (int i = 0; i < whatArray.length; i++) {
            HashMap whereaboutsMap = (HashMap) analysedMap.get(whatArray[i]);
            Set statementSet = whereaboutsMap.keySet();
            Iterator it = statementSet.iterator();
            while (it.hasNext()) {
                String statement = (String) it.next();//get statement
                String noSpace = removeSpaces(statement);
                  if (contains(noSpace, variable + "[") && !subString(noSpace, variable)) {
                    isArray.add(statement);
                    break;
                } else if (contains(noSpace, "]" + variable)) {
                    isArray.add(statement);
                    break;
                }
            }
        }
        //System.out.println(isArray);
        return isArray;
    }

    /**
     * Return list of statements where variable appears to be being used like an
     * array
     * 
     * @return ArrayList
     */
    public ArrayList arrayFixedValueCheck() {
        ArrayList isArrayFixedValue = new ArrayList();
        String[] whatArrayFixedValue = { "assignment", "usage", "conditional", "other" };
        String[] numbers = {"0","1","2","3","4","5","6","7","8","9"};
        for (int i = 0; i < whatArrayFixedValue.length; i++) {
            HashMap whereaboutsMap = (HashMap) analysedMap.get(whatArrayFixedValue[i]);
            Set statementSet = whereaboutsMap.keySet();
            Iterator it = statementSet.iterator();
            while (it.hasNext()) {
                String statement = (String) it.next();         
                String noSpace = removeSpaces(statement);
                String afterEquals = afterEquals(noSpace);
                for(String digit : numbers) {
                    if (contains(noSpace, variable + "[") || contains(noSpace, "]" + variable)
                     && contains(afterEquals, "new") && contains(afterEquals, "[") 
                     && contains(afterEquals, "]") && !subString(noSpace, variable)) {
                        isArrayFixedValue.add(statement);
                        break; 
                    } else if (contains(noSpace, variable + "[") || contains(noSpace, "]" + variable)
                     && contains(afterEquals, "{") && contains(afterEquals, "}")
                     && !subString(noSpace, variable)) {
                        isArrayFixedValue.add(statement);
                        break;
                  } 
               }
            }
        }
        //System.out.println(isArrayFixedValue);
        return isArrayFixedValue;
    }

    public ArrayList switchCheck() {
        ArrayList isSwitch = new ArrayList();
        String[] whatSwitch = { "assignment", "usage", "conditional", "other"  };
        for (int i = 0; i < whatSwitch.length; i++) {
            HashMap whereaboutsMap = (HashMap) analysedMap.get(whatSwitch[i]);
            Set statementSet = whereaboutsMap.keySet();
            Iterator it = statementSet.iterator();
            while (it.hasNext()) {
                String statement = (String) it.next();         
                String noSpace = removeSpaces(statement);
                String afterEquals = afterEquals(noSpace);
                if (contains(noSpace, "switch(" + variable) && !subString(noSpace, variable)) {
                    isSwitch.add(statement);
                }
            }
        }
        //System.out.println(isSwitch);
        return isSwitch;
    }

    public ArrayList stepperCheck() {
        ArrayList isStepper = new ArrayList();
        String[] whatStepper = { "assignment", "usage", "conditional", "other"  };
        String[] numbers = {"0","1","2","3","4","5","6","7","8","9"};
        for (int i = 0; i < whatStepper.length; i++) {
            HashMap whereaboutsMap = (HashMap) analysedMap.get(whatStepper[i]);
            Set statementSet = whereaboutsMap.keySet();
            Iterator it = statementSet.iterator();
            while (it.hasNext()) {
                String statement = (String) it.next();         
                String noSpace = removeSpaces(statement);
                String afterEquals = afterEquals(noSpace); 
                for(String digit : numbers) {
                if((contains(noSpace, "case") && contains(noSpace, "for") 
                && (contains(noSpace, variable + "++") || contains(noSpace, variable + "--")
                ||  contains(noSpace, variable + "**") || contains(noSpace, variable + "\\")))
                ||  contains(noSpace, variable + "++") || contains(noSpace, variable + "--")
                ||  contains(noSpace, variable + "**") || contains(noSpace, variable + "\\")
                ||  contains(noSpace,  variable + "=" + variable + "+" + digit)
                ||  contains(noSpace,  variable + "=" + variable + "-" + digit)
                ||  contains(noSpace,  variable + "=" + variable + "*" + digit)
                ||  contains(noSpace,  variable + "=" + variable + "/" + digit)
                ||  contains(noSpace,  variable + "=" + variable + "%" + digit)
                && !subString(noSpace, variable)) {
                    isStepper.add(statement);
                } 
              }
           }
        }
        //System.out.println(isStepper);
        return isStepper;
    }

    public ArrayList gathererCheck() {
        ArrayList isGatherer = new ArrayList();
        String[] whatGatherer = { "assignment", "usage", "conditional", "other"  };
        String[] numbers = {"0","1","2","3","4","5","6","7","8","9"};
        boolean notGatherer = false;
        for (int i = 0; i < whatGatherer.length; i++) {
            HashMap whereaboutsMap = (HashMap) analysedMap.get(whatGatherer[i]);
            Set statementSet = whereaboutsMap.keySet();
            Iterator it = statementSet.iterator();
            while (it.hasNext()) {
                String statement = (String) it.next();         
                String noSpace = removeSpaces(statement);
                String afterEquals = afterEquals(noSpace);
                for(String digit : numbers) {
                if (contains(noSpace, variable + "+=") || contains(noSpace, variable + "-=")
                ||  contains(noSpace, variable + "*=") || contains(noSpace, variable + "/=")  
                ||  contains(noSpace, variable + "%=")  
                ||  contains(noSpace, variable + "=" + variable + "+") || contains(noSpace, variable + "=" + variable + "-")
                ||  contains(noSpace, variable + "=" + variable + "*") || contains(noSpace, variable + "=" + variable + "/")
                ||  contains(noSpace, variable + "=" + variable + "%")
                || (contains(noSpace, variable + "[") && contains(noSpace, "]+="))
                || (contains(noSpace, variable + "[") && contains(noSpace, "]-="))
                || (contains(noSpace, variable + "[") && contains(noSpace, "]*="))
                || (contains(noSpace, variable + "[") && contains(noSpace, "]/="))
                || (contains(noSpace, variable + "[") && contains(noSpace, "]%="))
                && !contains(noSpace, variable + "=" + variable + "+" + digit)
                && !contains(noSpace, variable + "=" + variable + "-" + digit)
                && !contains(noSpace, variable + "=" + variable + "*" + digit)
                && !contains(noSpace, variable + "=" + variable + "/" + digit)
                && !contains(noSpace, variable + "=" + variable + "%" + digit)
                && !subString(noSpace, variable)) {
                    isGatherer.add(statement);
                } 
              }
           }
        }
        //System.out.println(isGatherer);
        return isGatherer;
    }
    
    public ArrayList oneWayFlagCheck() {
        ArrayList isOneWayFlag = new ArrayList();
        String[] whatOneWayFlag = { "assignment", "usage", "conditional", "other"  };
        String strng = null;
        for (int i = 0; i < whatOneWayFlag.length; i++) {
            HashMap whereaboutsMap = (HashMap) analysedMap.get(whatOneWayFlag[i]);
            Set statementSet = whereaboutsMap.keySet();
            Iterator it = statementSet.iterator();
            while (it.hasNext()) {
              String statement = (String) it.next();         
              String noSpace = removeSpaces(statement);
              String afterEquals = afterEquals(noSpace);
              strng = checkBoolean(isOneWayFlag);
                 if (contains(noSpace, strng + "=") && contains(afterEquals, "(") 
                 &&  contains(afterEquals, "==")
                 &&  contains(afterEquals, variable)
                 &&  contains(afterEquals, ")")
                 && !subString(noSpace, strng)) {
                     isOneWayFlag.add(statement);
                     break;
                 }
                 else if (contains(noSpace, strng + "=") && contains(afterEquals, "(") 
                 &&  contains(afterEquals, "!=")
                 &&  contains(afterEquals, variable)
                 &&  contains(afterEquals, ")")
                 && !subString(noSpace, strng)) {
                     isOneWayFlag.add(statement);
                     break;
                 }
            }
        }
        //System.out.println(isOneWayFlag);
        return isOneWayFlag;
    }
    
    private String checkBoolean(ArrayList list) {
        ArrayList values = new ArrayList();
        String[] whatBoolean = { "assignment", "usage", "conditional", "other" };
        String x = null;
        Object z = null; 
        String y = null;
        for (int i = 0; i < whatBoolean.length; i++) {
            HashMap whereaboutsMap = (HashMap) analysedMap.get(whatBoolean[i]);
            Set statementSet = whereaboutsMap.keySet();
            Iterator it = statementSet.iterator();
            while (it.hasNext()) {
                String statement = (String) it.next();
                String noSpace = removeSpaces(statement);
                String afterEquals = afterEquals(noSpace);
                if (contains(noSpace, "boolean" + variable)) {
                    x = variable;
                    values.add(x);
                    z = values.get(0);
                    y = z.toString();
                }        
            }
        }
        return y;
    }
    
    /**
     * Return list of statements where variable appears to be being used like an
     * 'input' or alternatively as a 'random' number
     * 
     * @return ArrayList
     */
    public ArrayList inputRandomCheck() {
        ArrayList isInput = new ArrayList();
        String[] whatInput = {"assignment", "usage", "conditional", "other"};
        for (int i = 0; i < whatInput.length; i++) {
           HashMap whereaboutsMap = (HashMap) analysedMap.get(whatInput[i]);
           Set statementSet = whereaboutsMap.keySet();
           Iterator it = statementSet.iterator();
           while (it.hasNext()) {
             String statement = (String) it.next();         
             String noSpace = removeSpaces(statement);
             String afterEquals = afterEquals(noSpace);
                if (contains(afterEquals, ".nextDouble()")
                 || contains(afterEquals, ".nextBoolean()") || contains(afterEquals, ".nextInt()") 
                 || contains(afterEquals, ".nextBigInteger()") || contains(afterEquals, ".nextShort()")
                 || contains(afterEquals, ".readByte();") || contains(afterEquals, ".readDouble()")
                 || contains(afterEquals, ".readBoolean();") || contains(afterEquals, ".readLong()")
                 || contains(afterEquals, ".readInt()") || contains(afterEquals, ".readLine()")
                 || contains(afterEquals, ".readChar()") || contains(afterEquals, ".readShort()")
                 || contains(afterEquals, ".readFloat()") || contains(afterEquals, "(int)(Math.random()")
                 ||(contains(noSpace, "String" + variable) && contains(afterEquals, "];") && (Pattern.matches(".*[a-zA-Z]+.*", afterEquals)))
                 &&!contains(noSpace, "Scanner" + variable + "=") && !contains(noSpace, "Scanner" + variable + ";")
                 &&!subString(noSpace, variable)) {
                     isInput.add(statement);
                     break;
              } 
           }
        }
        //System.out.println(isInput);
        return isInput;
    }
    
    /**
     * Return list of statements where variable appears to be being used like an
     * Scanner 'input' 
     * 
     * @return ArrayList
     */
    public ArrayList inputRandomCheckScanner() {
        ArrayList isInputScanner = new ArrayList();
        String[] whatInputScanner = {"assignment", "usage", "conditional", "other"};
        for (int i = 0; i < whatInputScanner.length; i++) {
           HashMap whereaboutsMap = (HashMap) analysedMap.get(whatInputScanner[i]);
           Set statementSet = whereaboutsMap.keySet();
           Iterator it = statementSet.iterator();
           while (it.hasNext()) {
             String statement = (String) it.next();         
             String noSpace = removeSpaces(statement);
             String afterEquals = afterEquals(noSpace);
             String strn = checkNonIterator(isInputScanner);
              if (contains(afterEquals, strn + ".next();")) {
                     isInputScanner.add(statement);
                     break;
              } 
           }
        }
        //System.out.println(isInputScanner);
        return isInputScanner;
    }
    
    public boolean checkForDuplicates(HashMap setWithDuplicates){
    	boolean found = false;
        for(Object entry : setWithDuplicates.values()){
        	if(entry instanceof ArrayList){
        		if(((ArrayList)entry).size() > 1){
        			if(!((ArrayList)entry).contains("break;")){
	        			found = true;
	        			return found;
        			}
                }
        	} else if(entry instanceof Map){
        		HashMap hashMap = (HashMap) entry;
        		return checkForDuplicates(hashMap);
        	}
        }
        return found;
    }
    
    /**
     * Return list of statements where variable appears to be being used like an
     * 'input' or alternatively as a 'random' number, but multiple times with the
     * same variable assignment
     * 
     * @return ArrayList
     */
    public ArrayList inpRndmSameCheck() {
        ArrayList isInput = new ArrayList();
        String[] whatInput = {"assignment", "usage", "conditional", "other"};
        for (int i = 0; i < whatInput.length; i++) {
            HashMap whereaboutsMap = (HashMap) analysedMap.get(whatInput[i]);
            Set statementSet = whereaboutsMap.keySet();    
            Iterator it = statementSet.iterator();
            while (it.hasNext()) {
                String statement = (String) it.next();         
                String noSpace = removeSpaces(statement);
                String afterEquals = afterEquals(noSpace);     
                String strn = checkNonIterator(isInput);
                boolean found = checkForDuplicates(sourceSorter);  
                   if((found) 
                    &&(contains(afterEquals, ".nextLine()") || contains(afterEquals, ".nextLong()")
                    || contains(afterEquals, ".nextByte()") || contains(afterEquals, ".nextDouble()")
                    || contains(afterEquals, ".nextBoolean()") || contains(afterEquals, ".nextInt()") 
                    || contains(afterEquals, ".nextBigInteger()") || contains(afterEquals, ".nextShort()")
                    || contains(afterEquals, ".readByte();") || contains(afterEquals, ".readDouble()")
                    || contains(afterEquals, ".readInputLine()")
                    || contains(afterEquals, ".readBoolean();") || contains(afterEquals, ".readLong()")
                    || contains(afterEquals, ".readInt()") || contains(afterEquals, ".readLine()")
                    || contains(afterEquals, ".readChar()") || contains(afterEquals, ".readShort()")
                    || contains(afterEquals, ".readFloat()") || contains(afterEquals, "(int)(Math.random()")
                    ||(contains(noSpace, "String" + variable) && contains(afterEquals, "];") && (Pattern.matches(".*[a-zA-Z]+.*", afterEquals)))
                    &&!contains(noSpace, "Scanner" + variable + "=") && !contains(noSpace, "Scanner" + variable + ";")
                    &&!subString(noSpace, variable))) {
                        isInput.add(statement);
                        break;
                }  else if ((found) && contains(afterEquals, strn + ".next();")) {
                        isInput.add(statement);
                        break;
              } 
            }
        }
        //System.out.println(isInput);
        return isInput;
    }

    private String checkNonIterator(ArrayList list) {
        ArrayList values = new ArrayList();
        String[] whatNonIterator = { "assignment", "usage", "conditional", "other" };
        String x = null;
        Object z = null; 
        String y = null;
        for (int i = 0; i < whatNonIterator.length; i++) {
            HashMap whereaboutsMap = (HashMap) analysedMap.get(whatNonIterator[i]);
            Set statementSet = whereaboutsMap.keySet();
            Iterator it = statementSet.iterator();
            while (it.hasNext()) {
                String statement = (String) it.next();
                String noSpace = removeSpaces(statement);
                String afterEquals = afterEquals(noSpace);
                if (contains(noSpace, "Scanner" + variable)) {
                    x = variable;
                    values.add(x);
                    z = values.get(0);
                    y = z.toString();
                }        
            }
        }
        //System.out.println(values);
        return y;
    }
    
    private String checkNonScanner(ArrayList list) {
        ArrayList values = new ArrayList();
        String[] whatNonScanner = { "assignment", "usage", "conditional", "other" };
        String x = null;
        Object z = null; 
        String y = null;
        for (int i = 0; i < whatNonScanner.length; i++) {
            HashMap whereaboutsMap = (HashMap) analysedMap.get(whatNonScanner[i]);
            Set statementSet = whereaboutsMap.keySet();
            Iterator it = statementSet.iterator();
            while (it.hasNext()) {
                String statement = (String) it.next();
                String noSpace = removeSpaces(statement);
                String afterEquals = afterEquals(noSpace);
                if ( (contains(noSpace, "Iterator" + variable) || contains(noSpace, "Iterator<String>" + variable)) 
                  && !contains(noSpace, "Scanner" + variable)) {
                    x = variable;
                    values.add(x);
                    z = values.get(0);
                    y = z.toString();
                }        
            }
        }
        //System.out.println(values);
        return y;
    }
    
    public ArrayList isEnumerationIteratorCheck() {
        ArrayList isEnumerationIterator = new ArrayList();
        String[] whatEnumerationIterator = { "assignment", "usage", "conditional", "other" };
        for (int i = 0; i < whatEnumerationIterator.length; i++) {
            HashMap whereaboutsMap = (HashMap) analysedMap.get(whatEnumerationIterator[i]);
            Set statementSet = whereaboutsMap.keySet();
            Iterator it = statementSet.iterator();
            while (it.hasNext()) {
                String statement = (String) it.next();
                String noSpace = removeSpaces(statement);
                String afterEquals = afterEquals(noSpace);
                String value = checkNonScanner(isEnumerationIterator);
                  if ((contains(noSpace, "Node)") || contains(noSpace, "String" + variable + "=")
                    || contains(noSpace, "Object" + variable + "=")) && (contains(afterEquals, ".nextElement();")
                    || contains(afterEquals, ".previous();"))) {
                    isEnumerationIterator.add(statement);
                    break;
                } else if (contains(afterEquals, value + ".next();")) {
                    isEnumerationIterator.add(statement);
                    break;
                } 
            }
        }
        //System.out.println(isEnumerationIterator);
        return isEnumerationIterator;
    }
    
    /**
     * Return list of statements where variable appears to be of a 'primitive' type
     * 
     * @return ArrayList
     */
    public ArrayList primitiveCheck() {
        ArrayList isPrimitive = new ArrayList();
        String[] whatPrimitive = { "assignment", "usage", "conditional", "other" };
        for (int i = 0; i < whatPrimitive.length; i++) {
            HashMap whereaboutsMap = (HashMap) analysedMap.get(whatPrimitive[i]);
            Set statementSet = whereaboutsMap.keySet();
            Iterator it = statementSet.iterator();
            while (it.hasNext()) {
                String statement = (String) it.next();
                String noSpace = removeSpaces(statement);
                String afterEquals = afterEquals(noSpace);
                if(contains(noSpace, "int"   + variable + "=") || contains(noSpace, "int"   + variable + ";")
                || contains(noSpace, "char"  + variable + "=") || contains(noSpace, "char"  + variable + ";")
                || contains(noSpace, "long"  + variable + "=") || contains(noSpace, "long"  + variable + ";")
                || contains(noSpace, "float" + variable + "=") || contains(noSpace, "float" + variable + ";")
                || contains(noSpace, "short" + variable + "=") || contains(noSpace, "short" + variable + ";")             
                || contains(noSpace, "byte"  + variable + "=") || contains(noSpace, "byte"  + variable + ";")
                || contains(noSpace, "boolean" + variable + "=")
                || contains(noSpace, "boolean" + variable + ";")
                || contains(noSpace, "double" + variable + "=")
                || contains(noSpace, "double" + variable + ";")
                &&!contains(noSpace, variable + "[") && !contains(noSpace, "]" + variable)
                //&&!contains(noSpace, variable + "++") && !contains(noSpace, variable + "--")
                //&&!contains(noSpace, variable + "**") && !contains(noSpace, variable + "\\")
                &&!subString(noSpace, variable)) {
                    isPrimitive.add(statement);
                    break;
                } 
            }
        }
        //System.out.println(isPrimitive);
        return isPrimitive;
    }

    /**
     * Return list of statements where variable appears to be being used like a data
     * structure
     * 
     * @return ArrayList
     */
    public ArrayList containerCheck() {
        ArrayList isContainer = new ArrayList();
        String[] whatContainer = { "assignment", "usage", "conditional", "other" };
        for (int i = 0; i < whatContainer.length; i++) {
            HashMap whereaboutsMap = (HashMap) analysedMap.get(whatContainer[i]);
            Set statementSet = whereaboutsMap.keySet();
            Iterator it = statementSet.iterator();
            while (it.hasNext()) {
                String statement = (String) it.next();
                String noSpace = removeSpaces(statement);
                String afterEquals = afterEquals(noSpace);
                if(contains(afterEquals, "(") && contains(afterEquals, "new") 
                    && (contains(afterEquals, "List") 
                    ||  contains(afterEquals, "Vector") 
                    ||  contains(afterEquals, "Set") 
                    ||  contains(afterEquals, "Stack")
                    ||  contains(afterEquals, "Map") 
                    ||  contains(afterEquals, "Hashtable") 
                    ||  contains(afterEquals, "Queue") 
                    ||  contains(afterEquals, "Deque") 
                    ||  contains(afterEquals, "Collection") 
                    ||  contains(afterEquals, "JTree")
                    ||  contains(afterEquals, "Tree")
                    || (contains(afterEquals, "ListNode") && contains(noSpace, variable + ".addElement"))
                    || (contains(afterEquals, "MutableTreeNode") && (contains(noSpace, variable + ".insert")
                    ||  contains(noSpace, variable + ".remove")))
                    || (contains(afterEquals, "DefaultMutableTreeNode") && (contains(noSpace, variable + ".add")
                    ||  contains(noSpace, variable + ".remove") || contains(noSpace, variable + ".insert"))))) {
                    isContainer.add(statement);
                    break;
                } 
            }
        }
        //System.out.println(isContainer);
        return isContainer;
    }

    /**
     * Return list of statements where variable appears to be being used like a data
     * entity that traverses a data structure in some systematic way.
     * 
     * @return ArrayList
     */
    public ArrayList walkerCheck() {
        ArrayList isWalker = new ArrayList();
        String[] whatWalker = {"assignment", "usage", "conditional", "other"};
        String[] numbers = {"0","1","2","3","4","5","6","7","8","9"};
        for (int i = 0; i < whatWalker.length; i++) {
            HashMap whereaboutsMap = (HashMap) analysedMap.get(whatWalker[i]);
            Set statementSet = whereaboutsMap.keySet();
            Iterator it = statementSet.iterator();
            while (it.hasNext()) {
                String statement = (String) it.next();
                String noSpace = removeSpaces(statement);
                String afterEquals = afterEquals(noSpace);
                for(String digit : numbers) {
                      if (contains(noSpace, "DefaultMutableTreeNode" + variable) && contains(afterEquals, "(DefaultMutableTreeNode")) {
                        isWalker.add(statement);
                        break;
                    } else if
                      ((contains(noSpace, "Node" + variable) || contains(noSpace, "Node[]" + variable)
                    ||  contains(noSpace, "TreeNode" + variable) ||  contains(noSpace, "ListNode" + variable)
                    ||  contains(noSpace, "MutableTreeNode" + variable) ||  contains(noSpace, "Node<String>" + variable)
                    ||  contains(noSpace, "DefaultMutableTreeNode" + variable))
                    && (contains(afterEquals, ".getParent()") || contains(afterEquals, ".getChildAfter(" + variable)
                    ||  contains(afterEquals, ".getChildBefore(" + variable) || contains(afterEquals, ".getFirstChild()")
                    ||  contains(afterEquals, ".getFirstLeaf()")|| contains(afterEquals, ".getLastChild()")
                    ||  contains(afterEquals, ".getLastLeaf()") || contains(afterEquals, ".getNextLeaf()")
                    ||  contains(afterEquals, ".getNextNode()") || contains(afterEquals, ".getNextSibling()")
                    ||  contains(afterEquals, ".getPreviousLeaf()") || contains(afterEquals, ".getPreviousNode()")
                    ||  contains(afterEquals, ".getPreviousSibling()") || contains(afterEquals, ".getRoot()")
                    ||  contains(afterEquals, ".getSharedAncestor(" + variable) || contains(afterEquals, ".getNth(")
                    ||  contains(noSpace, ".getLastPathComponent();") || contains(noSpace, ".getLastSelectedPathComponent();"))) {
                        isWalker.add(statement);
                        break;
                    } else if   
                       (contains(noSpace, variable + ".next;") || contains(noSpace, variable + ".getNext")
                    ||  contains(noSpace, variable + ".left") || contains(noSpace, variable + ".right")
                    ||  contains(noSpace, variable + ".next=")) { 
                        isWalker.add(statement);
                        break;
                    } else if 
                       (contains(noSpace, "DefaultMutableTreeNode" + variable) && contains(afterEquals, "].getLastPathComponent()")
                    &&  contains(afterEquals, digit + "].getLastPathComponent()") && !contains(noSpace, variable + ".getLastPathComponent()")) {
                        isWalker.add(statement);
                        break;
                    } else if 
                       (contains(noSpace, "DefaultMutableTreeNode" + variable) && contains(afterEquals, "].getLastSelectedPathComponent()")
                    &&  contains(afterEquals, digit + "].getLastSelectedPathComponent()") && !contains(noSpace, variable + ".getLastSelectedPathComponent()")) {
                        isWalker.add(statement);
                        break;
                    } else if 
                      ((contains(noSpace, "JTree" + variable + "=") && contains(afterEquals, variable + ".getLastPathComponent();"))
                    || (contains(noSpace, "JTree" + variable + "=") && contains(afterEquals, variable + ".getLastSelectedPathComponent();"))) {
                        isWalker.clear();
                        break;
                    } 
                }
            }
        }
        //System.out.println(isWalker);
        return isWalker;
    }

    /**
     * Return list of statements where a variable appears to be assigned to its own negative
     * value.
     * 
     * @return ArrayList
     */
    public ArrayList isFixedValueNegativeCheck() {
        ArrayList isFixedValueNegative = new ArrayList();
        String[] whatFixedValueNegative = {"assignment", "usage", "conditional", "other"};
        for (int i = 0; i < whatFixedValueNegative.length; i++) {
            HashMap whereaboutsMap = (HashMap) analysedMap.get(whatFixedValueNegative[i]);
            Set statementSet = whereaboutsMap.keySet();
            Iterator it = statementSet.iterator();
            while (it.hasNext()) {
                String statement = (String) it.next();
                String noSpace = removeSpaces(statement);
                String afterEquals = afterEquals(noSpace);
                if(contains(afterEquals, "-" + variable) && contains(afterEquals, variable + ";")) {
                   isFixedValueNegative.add(statement);
                   break;
                } 
            }
        }
        //System.out.println(isFixedValueNegative);
        return isFixedValueNegative;
    }

    /**
     * Return list of statements where a variable refers to a specific object having got its value
     * during run-time only once and never changing after that its value.
     * 
     * @return ArrayList
     */
    public ArrayList isFixedValueNotWalker() {
        ArrayList isFixedValueNotWalker = new ArrayList();
        String[] whatFixedValueNotWalker = {"assignment", "usage", "conditional", "other"};
        for (int i = 0; i < whatFixedValueNotWalker.length; i++) {
            HashMap whereaboutsMap = (HashMap) analysedMap.get(whatFixedValueNotWalker[i]);
            Set statementSet = whereaboutsMap.keySet();
            Iterator it = statementSet.iterator();
            while (it.hasNext()) {
                String statement = (String) it.next();
                String noSpace = removeSpaces(statement);
                String afterEquals = afterEquals(noSpace);
                  if ( (contains(noSpace, "Node" + variable) || contains(noSpace, "Node[]" + variable)
                     || contains(noSpace, "TreeNode" + variable) || contains(noSpace, "ListNode" + variable)
                     || contains(noSpace, "MutableTreeNode" + variable) 
                     || contains(noSpace, "DefaultMutableTreeNode" + variable)
                     || contains(noSpace, "Node<String>" + variable) || contains(noSpace, variable)) 
                     &&(variable.toString().startsWith("next") || variable.toString().startsWith("left")
                     || variable.toString().startsWith("right") && !contains(noSpace, "Stack<Node"))) {
                        isFixedValueNotWalker.add(statement);
                        break;
                } else if (contains(noSpace, "Node" + variable) && contains(afterEquals, "new" + "Node")) {
                        isFixedValueNotWalker.add(statement);
                        break;
                } 
            }
        }
        //System.out.println(isFixedValueNotWalker);
        return isFixedValueNotWalker;
    }
    
    /**
     * Return list of statements where variable appears to be being used like a data entity
     * that traverses a data structure (but which is associated each time with an object re-
     * ference obtained from the latest data-structure's gone through value)  
     * 
     * @return ArrayList
     */
    public ArrayList walkerMostRecentCheck() {
        ArrayList isWalkerMostRecent = new ArrayList();
        String[] whatWalkerMostRecent = {"assignment", "usage", "conditional", "other"};
        String[] alphabet = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        for (int i = 0; i < whatWalkerMostRecent.length; i++) {
            HashMap whereaboutsMap = (HashMap) analysedMap.get(whatWalkerMostRecent[i]);
            Set statementSet = whereaboutsMap.keySet();
            Iterator it = statementSet.iterator();           
            while (it.hasNext()) {
                String statement = (String) it.next();
                String noSpace = removeSpaces(statement);
                String afterEquals = afterEquals(noSpace);
                for(String letter : alphabet) {
                  if ( contains(noSpace, ".removeNodeFromParent(" + variable) 
                    || contains(noSpace, ".insertNodeInto(") || contains(noSpace, ".nodeStructureChanged(" + variable) 
                    || contains(noSpace, ".removeFromParent()") 
                    || contains(noSpace, ".insertBefore(") || contains(noSpace, ".removeChild(" + variable)
                    || contains(noSpace, ".replaceChild(") || contains(noSpace, ".appendChild(" + variable) 
                    || contains(noSpace, ".reload(" + variable) || contains(noSpace, ".reload()")
                    || contains(noSpace, ".nodesWereInserted(" + variable)) {
                       isWalkerMostRecent.add(statement);
                       break;
                } else if (contains(noSpace, "DefaultMutableTreeNode" + variable) && contains(afterEquals, "].getLastPathComponent();")
                      && contains(afterEquals, letter + "].getLastPathComponent();")) {
                       isWalkerMostRecent.add(statement);
                       break;
                } 
              }
            }
         }
        //System.out.println(isWalkerMostRecent);
        return isWalkerMostRecent;
    }

    /**
     * Return list of statements where variable appears to be used as the current element 
     * of traversals (e.g., in a depth-first search)
     * 
     * @return ArrayList
     */
    public ArrayList walkerCheckCurrent() {
        ArrayList iswalkerCheckCurrent = new ArrayList();
        String[] whatArray = { "assignment", "usage", "conditional", "other" };
        for (int i = 0; i < whatArray.length; i++) {
            HashMap whereaboutsMap = (HashMap) analysedMap.get(whatArray[i]);
            Set statementSet = whereaboutsMap.keySet();
            Iterator it = statementSet.iterator();
            while (it.hasNext()) {
                String statement = (String) it.next();
                String noSpace = removeSpaces(statement);
                String afterEquals = afterEquals(noSpace);
                if((contains(noSpace, "Node" + variable) || contains(noSpace, "Node[]" + variable)
                 || contains(noSpace, "TreeNode" + variable) || contains(noSpace, "ListNode" + variable)
                 || contains(noSpace, "MutableTreeNode" + variable) || contains(noSpace, "DefaultMutableTreeNode" + variable)
                 || contains(noSpace, "Node<String>" + variable))
                &&((contains(afterEquals, ".pop();")  && !contains(afterEquals, variable + ".pop();"))
                 ||(contains(afterEquals, ".poll();") && !contains(afterEquals, variable + ".poll();"))
                 ||(contains(afterEquals, ".peek();") && !contains(afterEquals, variable + ".peek();"))
                 || contains(noSpace, ".pop(" + variable) || contains(noSpace, ".push(" + variable)
                 || contains(noSpace, ".push(this." + variable) || contains(noSpace, ".pop(this." + variable))) {
                    iswalkerCheckCurrent.add(statement);
                    break;
                }
            }
        }
        //System.out.println(iswalkerCheckCurrent);
        return iswalkerCheckCurrent;
    }

    /**
     * Return list of statements where variable appears to be an instance of an object
     * (related with some data structure's entity).
     * 
     * @return ArrayList
     */
    public ArrayList walkerIsObjectCheck() {
        ArrayList isWalkerObjectElement = new ArrayList();
        String[] whatWalkerObjectElement = { "assignment", "usage", "conditional", "other" };
        for (int i = 0; i < whatWalkerObjectElement.length; i++) {
            HashMap whereaboutsMap = (HashMap) analysedMap.get(whatWalkerObjectElement[i]);
            Set statementSet = whereaboutsMap.keySet();
            Iterator it = statementSet.iterator();
            while (it.hasNext()) {
                String statement = (String) it.next();
                String noSpace = removeSpaces(statement);
                String afterEquals = afterEquals(noSpace);
                  if(contains(afterEquals, "]).getUserObject()")) {
                    isWalkerObjectElement.add(statement);
                    break;
                } else if (contains(afterEquals, ".getLastPathComponent();") && !contains(afterEquals, "].getLastPathComponent();")
                ||  contains(afterEquals, ".getLastSelectedPathComponent();") && !contains(afterEquals, "].getLastSelectedPathComponent();")
                &&((contains(noSpace, "Object" + variable) && !contains(afterEquals, "(Node)"))
                || (contains(noSpace, "Object" + variable) && !contains(afterEquals, "(MutableTreeNode)"))
                || (contains(noSpace, "Object" + variable) && !contains(afterEquals, "(DefaultMutableTreeNode)")))) {
                    isWalkerObjectElement.add(statement);
                    break;
                } else if (contains(noSpace, variable + ".toString()") && !contains(noSpace, "DefaultMutableTreeNode" + variable)
                && !contains(noSpace, "MutableTreeNode" + variable) && !contains(noSpace, "Node" + variable)
                && !contains(noSpace, "TreeNode" + variable) && !contains(noSpace, "ListNode" + variable)
                && !contains(noSpace, "Node<String>" + variable) && !contains(noSpace, "Node[]" + variable)) {
                    isWalkerObjectElement.add(statement);
                    break;
                } else if((contains(noSpace, "Node" + variable) || contains(noSpace, "Node[]" + variable)
                ||  contains(noSpace, "TreeNode" + variable) || contains(noSpace, "ListNode" + variable)
                ||  contains(noSpace, "MutableTreeNode" + variable) || contains(noSpace, "DefaultMutableTreeNode" + variable)
                ||  contains(noSpace, "Node<String>" + variable)) && contains(noSpace, ".getUserObject();")) {
                    isWalkerObjectElement.add(statement);
                    break;
                } 
            }  
        }
        //System.out.println(isWalkerObjectElement);
        return isWalkerObjectElement;
    }

    /**
     * Return list of statements where variable appears to be an instance of an object that
     * implements either the: Iterator, ListIterator, Enumerator, Scanner or Random class i-
     * nterface
     * 
     * @return ArrayList
     */
    public ArrayList isObjectCheck() {
        ArrayList isObjectElement = new ArrayList();
        String[] whatObjectElement = { "assignment", "usage", "conditional", "other" };
        for (int i = 0; i < whatObjectElement.length; i++) {
            HashMap whereaboutsMap = (HashMap) analysedMap.get(whatObjectElement[i]);
            Set statementSet = whereaboutsMap.keySet();
            Iterator it = statementSet.iterator();
            while (it.hasNext()) {
                String statement = (String) it.next();
                String noSpace = removeSpaces(statement);
                String afterEquals = afterEquals(noSpace);
                if (contains(noSpace, "Iterator" + variable) || contains(noSpace, "Scanner" + variable)
                ||  contains(noSpace, "Enumeration" + variable) || contains(noSpace, "Iterator<String>" + variable)
                ||  contains(noSpace, "ListIterator<String>" + variable) ||  contains(noSpace, "Random" + variable) 
                || (contains(noSpace, "Enumeration<") && contains(noSpace, ">" + variable))) {
                    isObjectElement.add(statement);
                    break;
                }
            }  
        }
        //System.out.println(isObjectElement);
        return isObjectElement;
    }

    /**
     * Return list of statements where variable appears to be being used as
     * organizer
     * 
     * @return ArrayList
     */
    public ArrayList reorganize() {
        ArrayList returnArray = new ArrayList();
        HashMap whereaboutsMap = (HashMap) analysedMap.get("assignment");
        Set statementSet = whereaboutsMap.keySet();
        Iterator it = statementSet.iterator();
        boolean notReorganize = false;
        while (it.hasNext()) {
            String statement = (String) it.next();
            String afterEquals = afterEquals(statement);
            String noSpace = removeSpaces(afterEquals);
            if (contains(noSpace, variable + "[") && !subString(afterEquals, variable) && !arithExp(noSpace, variable)) {
                returnArray.add(statement);
            } 
        }
        //System.out.println(returnArray);
        return returnArray;
    }

    /**
     * Return list of statements where variable appears to be being used as
     * transformation
     * 
     * @param assignmentsInLoop
     *              HashMap containing assignments in loops
     * @return ArrayList
     */
    public ArrayList transform(HashMap assignmentsInLoop) {
        ArrayList returnArray = new ArrayList();
        Set statementSet = assignmentsInLoop.keySet();
        Iterator it = statementSet.iterator();
        boolean isTransform = true;
        while (it.hasNext()) {
            String statement = (String) it.next();
            returnArray.add(statement);
            String afterEquals = afterEquals(statement);
            for (int i = 0; i < methods.size(); i++) {
                String method = (String) methods.get(i);
                //check if method is called on right hand side of assignment
                if (contains(afterEquals, method + "(")) {
                    //if any statement is not transformation clear array and end method
                    returnArray.clear();
                    break;
                }
            }
            //if any statement is not transformation clear array and end method
            if (!is(afterEquals, "operator")) {
                returnArray.clear();
                break;
            }
        }
        return returnArray;
    }

    /**
     * Return list of statements in which variable is indirectly toggled by
     * being set twice within a loop, once in a nested loop, with opposing
     * values
     * 
     * @param assignmentsInLoop
     *            HashMap of assignment statements in loop
     * @return ArrayList
     */
    public ArrayList nestedBooleanAssign(HashMap assignmentsInLoop) {
        ArrayList returnArray = new ArrayList();
        Set statementSet = assignmentsInLoop.keySet();
        Iterator it = statementSet.iterator();
        boolean isBoolean = false;
        boolean isTrue = false;
        ArrayList compareArray = new ArrayList();
        while (it.hasNext()) {
            String statement = (String) it.next();
            String afterEquals = afterEquals(statement);
              if (contains(afterEquals, "true") && !subString(afterEquals, "true")) {
                isBoolean = true;
                isTrue = true;
            } else if (contains(afterEquals, "false") && !subString(afterEquals, "false")) {
                isBoolean = true;
            }
            if (isBoolean) {
                ArrayList statementInLoop = (ArrayList) assignmentsInLoop.get(statement);
                boolean isNested = false;
                for (int i = 0; i < statementInLoop.size(); i++) {
                    ArrayList specificOccurance = (ArrayList) statementInLoop.get(i);
                    if (specificOccurance.size() > 3) {
                        isNested = true;
                    }
                    compareArray.add(statement); //add statement in question
                    //add outer most line number of loop
                    compareArray.add(specificOccurance.get(1));
                    //add whether assignment is true or false
                    compareArray.add(new Boolean(isTrue));
                    //add whether assign statement is in nest loop
                    compareArray.add(new Boolean(isNested));
                }
            }
        }
        if (compareArray.size() > 4) {
            returnArray = compareStatements(compareArray);
        }
        //System.out.println(returnArray);
        return returnArray;
    }

    /*
     * Return list of statement stating whether each assignment statement is
     * in a nested loop or not
     */
    private ArrayList compareStatements(ArrayList compareArray) {
        ArrayList returnArray = new ArrayList();
        for (int i = 0; i < compareArray.size(); i += 4) {
            Boolean isNested = (Boolean) compareArray.get(i + 3);
            if (isNested.booleanValue()) {
                Integer comparedLineNum = (Integer) compareArray.get(i + 1);
                Boolean comparedValue = (Boolean) compareArray.get(i + 2);
                //compare line number with loop lines for rested of array
                for (int j = i + 4; j < compareArray.size(); j += 4) {
                    Boolean compareNested = (Boolean) compareArray.get(j + 3);
                    Integer compareInteger = (Integer) compareArray.get(j + 1);
                    Boolean compareValue = (Boolean) compareArray.get(j + 2);
                    if (!compareNested.booleanValue()
                      && comparedLineNum.intValue() == compareInteger.intValue()
                      && comparedValue != compareValue) {
                        returnArray.add(compareArray.get(i));
                        returnArray.add("nested");
                        returnArray.add(compareArray.get(j));
                        break;
                    }
                }
            } else {
                Integer comparedLineNum = (Integer) compareArray.get(i + 1);
                Boolean comparedValue = (Boolean) compareArray.get(i + 2);
                //compare line number with loop lines for rested of array
                for (int j = i + 4; j < compareArray.size(); j++) {
                    Boolean compareNested = (Boolean) compareArray.get(j + 3);
                    Integer compareInteger = (Integer) compareArray.get(j + 1);
                    Boolean compareValue = (Boolean) compareArray.get(j + 2);
                    if (compareNested.booleanValue() 
                     && comparedLineNum.intValue() == compareInteger.intValue()
                     && comparedValue != compareValue) {
                        returnArray.add(compareArray.get(i));
                        returnArray.add("not nested");
                        returnArray.add(compareArray.get(j));
                        break;
                    }
                }
            }
        }
        return returnArray;
    }

    /**
     * Return list of statement in which variable is assigned with output from
     * method call
     * 
     * @param assignmentsInLoop
     *            HashMap of assignment statements found in loops
     * @return ArrayList
     */
    public ArrayList assignedWithMethod(HashMap assignmentsInLoop) {
        ArrayList returnArray = new ArrayList();
        Set statementSet = assignmentsInLoop.keySet();
        Iterator it = statementSet.iterator();
        boolean isTransform = true;
        while (it.hasNext()) {
            String statement = (String) it.next();
            String afterEquals = afterEquals(statement);
            for (int i = 0; i < methods.size(); i++) {
                String method = (String) methods.get(i);
                //check if method is called on right hand side of assignment
                if (contains(afterEquals, method + "(")) {
                    returnArray.add(statement);
                }
            }
        }
        //System.out.println(returnArray);
        return returnArray;
    }

    /**
     * Return list of statements where variable is assigned with value in loop
     * before it is used in that loop
     * 
     * @param usageInAssignments
     * @return ArrayList
     */
    public ArrayList assignBeforeUse(HashMap usageInAssignments) {
        ArrayList returnArray = new ArrayList();
        Set usageSet = usageInAssignments.keySet();
        Iterator it = usageSet.iterator();
        while (it.hasNext()) {
            String usageStatement = (String) it.next();
            ArrayList usageInLoop = (ArrayList) usageInAssignments.get(usageStatement);
            for (int i = 0; i < usageInLoop.size(); i++) {
                ArrayList usageOccurance = (ArrayList) usageInLoop.get(i);
                Integer assLineNum = (Integer) usageOccurance.get(1);
                int assL = assLineNum.intValue();
                //get outer most loop line number
                Integer useLineNum = (Integer) usageOccurance.get(0);
                int useL = useLineNum.intValue();
                if (assLineNum.intValue() < useLineNum.intValue()) {
                    returnArray.add(usageStatement);
                    returnArray.add(usageOccurance.get(2));
                    for (int j = 4; j < usageOccurance.size(); j += 2) {
                        returnArray.add(usageOccurance.get(j));
                    }
                } else {
                    returnArray.clear();
                }
            }
        }
        //System.out.println(returnArray);
        return returnArray;
    }

    /**
     * Return list of statements where variable is assigned with value from
     * instantiation of new object, or assigned directly with boolean value
     * 
     * @param assignmentsInLoop
     *            HashMap of assignment statement found in loops
     * @return ArrayList
     */
    public ArrayList otherAssignment(HashMap assignmentsInLoop) {
        ArrayList returnArray = new ArrayList();
        Set usageSet = assignmentsInLoop.keySet();
        Iterator it = usageSet.iterator();
        while (it.hasNext()) {
            String assignStatement = (String) it.next();
            String afterEquals = afterEquals(assignStatement);
            String noSpace = removeSpaces(afterEquals);
              if (is(afterEquals, "other")) {
                returnArray.add(assignStatement);
            }// check if value assigned by instantiating 'new' object
              else if (contains(afterEquals, "new") && !subString(afterEquals, "new")) {
                returnArray.add(assignStatement);
            } else if (noSpace.equals("=true;")) {
                returnArray.add(assignStatement);
            } else if (noSpace.equals("=false;")) {

            }
        }
        //System.out.println(returnArray);
        return returnArray;
    }
    
}