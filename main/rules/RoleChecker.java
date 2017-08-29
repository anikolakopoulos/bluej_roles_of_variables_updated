package main.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import main.progAnal.ConditionChecker;

/**
 * @author cbishop
 */
public abstract class RoleChecker {

    protected String variable;

    protected LinkedHashMap analysedMap;

    protected ArrayList methods;

    protected ResultStringer resultStringer;

    protected ConditionChecker conditionChecker;

    //condition storage
    protected HashMap assignmentsInLoop;

    protected HashMap usagesInLoop;

    protected HashMap conditionalsInLoop;

    protected HashMap usageInAssignmentLoop;

    protected HashMap conditionalInAssignmentLoop;

    protected HashMap assignmentsInBranch;

    protected HashMap conditionalAssignmentBranch;

    protected ArrayList onBothSides;

    protected ArrayList toggleStatements;

    protected ArrayList incDecStatements;

    protected HashMap useOutsideAssign;

    protected ArrayList assignBeforeUse;

    protected HashMap condOutsideAssign;

    protected ArrayList indirectBothSides;

    protected ArrayList reorganize;

    protected ArrayList transformations;

    protected ArrayList nestedBooleanAssignment;

    protected ArrayList methodAssignments;

    protected ArrayList otherAssignments;

    protected ArrayList assignmentInForEach;

    protected ArrayList assignmentInFor;

    protected ArrayList useForAssignLoopCondition;

    protected ArrayList isPrimitiveType;

    protected ArrayList isArray;

    protected ArrayList isArrayFixedElement;

    protected ArrayList isContainerDataStructure;

    protected ArrayList isWalkerElement;

    protected ArrayList isWalkerMostRecentElement;

    protected ArrayList isWalkerCurrentElement;

    protected ArrayList isWalkerObjectElement;

    protected ArrayList isObjectElement;

    protected ArrayList isFixedNegativeElement;

    protected ArrayList isFixedValueNotWalkerElement;

    protected ArrayList isInpRandMostRecentElement;

    protected ArrayList isMostRecentSmStmntElement;
    
    protected ArrayList isMostRecentScannerElement;

    protected ArrayList isSwitchMostRecentElement;

    protected ArrayList isStepperElement;

    protected ArrayList isGathererElement;

    protected ArrayList isOneWayFlagElement;

    protected ArrayList isEnumarationIteratElement;

    //condition flags
    protected boolean collections;

    protected boolean onlyOneAssignment;

    protected boolean isAssignedInForEach;

    protected boolean isAssignedInFor;

    protected boolean assignedInLoop;

    protected boolean usedInAssignLoop;

    protected boolean conditionalUseInAssign;

    protected boolean indirectUse;

    protected boolean noDirectUsage;

    protected boolean conditionForAssignBranch;

    protected boolean onBothSidesOfAssign;

    protected boolean toggleStatement;

    protected boolean incDecStatement;

    protected boolean usedOutsideAssign;

    protected boolean assignedBeforeUse;

    protected boolean loopUseNoAssign;

    protected boolean assignCondLoop;

    protected boolean assignmentLoopConditionUse;

    protected boolean indirectBS;

    protected boolean isReorganize;

    protected boolean isPrimitiveVariable;

    protected boolean isArrayVariable;

    protected boolean isArrayFixedVariable;

    protected boolean isContainerVariable;

    protected boolean isWalkerVariable;

    protected boolean isWalkerMostRecentVariable;

    protected boolean isWalkerCurrentVariable;

    protected boolean isWalkerObjectVariable;

    protected boolean isObjectVariable;

    protected boolean isFixedNegativeVariable;

    protected boolean isFixedValueNotWalkerVariable;

    protected boolean isInpRandMostRecentVariable;

    protected boolean isMostRecentSmStmntVariable;
    
    protected boolean isMostRecentScannerVariable;

    protected boolean isSwitchMostRecentVariable;

    protected boolean isStepperVariable;

    protected boolean isGathererVariable;

    protected boolean isOneWayFlagVariable;

    protected boolean isEnumarationIteratVariable;

    protected boolean isTransform;

    protected boolean nestedBooleanAssign;

    protected boolean methodAssignment;

    protected boolean otherAssign;

    //result Flags
    protected boolean isFixedValue;

    protected boolean isOrganizer;

    protected boolean isStepper;

    protected boolean isMostRecentHolder;

    protected boolean isGatherer;

    protected boolean isMostWantedHolder;

    protected boolean isOneWayFlag;

    protected boolean isTransformation;

    protected boolean isFollower;

    protected boolean isTemporary;

    protected boolean isContainer;

    protected boolean isWalker;

    protected boolean isOther;

    //Explanatory strings
    protected String offendingStatement;

    protected String reason;

    /**
     * Constructor for RoleChecker
     * 
     * @param inputMap
     *            LinkedHashMap containing analysed statements
     * @param var
     *            String being variable for which role is to be checked
     * @param meths
     *            ArrayList of method names in source code
     */
    public RoleChecker(LinkedHashMap inputMap, String var, ArrayList meths, LinkedHashMap currentMap) {
        variable = var;
        analysedMap = inputMap;
        methods = meths;
        conditionChecker = new ConditionChecker(analysedMap, variable, methods, currentMap);
        resultStringer = new ResultStringer(variable);
        initialiseConditionFlags();
        getConditions();
        setConditionFlags();
    }

    /**
     * Abstract method to be completed in each Class extending RoleChecker
     * 
     * @return ArrayList of results from role checking
     */
    public abstract ArrayList checkRole();

    /*
     * Initialise condition flags
     */
    protected void initialiseConditionFlags() {
        // the variable is assigned in a loop
        assignedInLoop = false;
        // the variable is used in an assignment loop
        usedInAssignLoop = false;
        // the variable is used conditionally in an assignment loop
        conditionalUseInAssign = false;
        // the variable is used indirectly in a 'for' assignment loop condition
        indirectUse = false;
        // the variable is only assigned in the enhanced 'for' loop (a.k.a., for-each loop)
        isAssignedInForEach = false;
        // the variable is only assigned in a 'for' loop statement
        isAssignedInFor = false;
        // there is no direct usage of the variable
        noDirectUsage = false;
        // assignment in branch for which variable is condition
        conditionForAssignBranch = false;
        // the variable appears on both sides of an assignment statement
        onBothSidesOfAssign = false;
        // the variable is toggled within loop
        toggleStatement = false;
        // the variable incremented etc within loop
        incDecStatement = false;
        // variable is not used outside of loop in which it is assigned
        usedOutsideAssign = false;
        // variable is not assigned before it is used in assignment loop
        assignedBeforeUse = false;
        // variable is used as condition for assignment loop
        assignCondLoop = false;
        // variable used conditionally for loop outside of assignment loop
        loopUseNoAssign = false;
        // variable not used indirectly on both sides of assignment statement
        indirectBS = false;
        // the variable is reorganize array
        isReorganize = false;
        // the variable is a 'primitive type' variable
        isPrimitiveVariable = false;
        // variable is an 'array'
        isArrayVariable = false;

        isArrayFixedVariable = false;
        // variable is a data structure
        isContainerVariable = false;
        // variable is a data structure's element
        isWalkerVariable = false;
        // variable is a data structure's element associated with an object reference
        // obtained from the latest data-structure's gone through value
        isWalkerMostRecentVariable = false;
        // variable is a data structure's element - [a current node's index] 
        isWalkerCurrentVariable = false;
        // variable is an object and not a data structure's element 
        isWalkerObjectVariable = false;

        isObjectVariable = false;
        // variable appears to be assigned with its own negative value.
        isFixedNegativeVariable = false;

        isFixedValueNotWalkerVariable = false;

        isInpRandMostRecentVariable = false;

        isMostRecentSmStmntVariable = false;
        
        isMostRecentScannerVariable = false;

        isSwitchMostRecentVariable = false;

        isStepperVariable = false;

        isGathererVariable = false;

        isOneWayFlagVariable = false;

        isEnumarationIteratVariable = false;
        // variable is not used in transformation statement
        isTransform = false;
        // variable is not assigned twice in loop, one assignment being in nested loop
        nestedBooleanAssign = false;
        // variable is not assigned with output from method
        methodAssignment = false;

        otherAssign = false;
    }

    /*
     * Get conditions under which variables are assigned and used in program
     */
    protected void getConditions() {
        onlyOneAssignment = conditionChecker.onlyOneAssignmentStatement();
        // find assignments in loop
        assignmentsInLoop = conditionChecker.foundIn("assignment", "loop");
        // find direct usage in loop
        usagesInLoop = conditionChecker.foundIn("usage", "loop");
        // find conditional usage in loop
        conditionalsInLoop = conditionChecker.foundIn("conditional", "loop");
        // find direct usage in assignment loop
        usageInAssignmentLoop = conditionChecker.foundInAssignmentLoop(assignmentsInLoop, usagesInLoop);
        // find conditional usage in assignment loop
        conditionalInAssignmentLoop = conditionChecker.foundInAssignmentLoop(assignmentsInLoop, conditionalsInLoop);
        // find assignment in branch
        assignmentsInBranch = conditionChecker.foundIn("assignment", "branch");
        // find incidents where assignment is dependent on condition of variable
        conditionalAssignmentBranch = conditionChecker.conditionForAssignmentBranch(assignmentsInBranch, conditionalsInLoop);
        // if not incidents where assignment dependent on condition of variable
        // check assignment statement for leading branch condition
        if (conditionalAssignmentBranch.size() < 1)
            conditionalAssignmentBranch = conditionChecker.conditionAssignStatement();
        // find assignment statements where variable appears on both sides of "="
        onBothSides = conditionChecker.statementTypeCheck((HashMap) analysedMap.get("assignment"), "bothSides");
        // find assignment statements in loop where variable is toggled
        toggleStatements = conditionChecker.statementTypeCheck(assignmentsInLoop, "toggle");
        // find assignment statements in loop where variable is incremented, decremented, etc
        incDecStatements = conditionChecker.statementTypeCheck(assignmentsInLoop, "incDec");
        // find use outside of assignment loop
        useOutsideAssign = conditionChecker.outsideAssignLoop(usageInAssignmentLoop, "usage");
        // find whether variable used before assigned in loop
        assignBeforeUse = conditionChecker.assignBeforeUse(usageInAssignmentLoop);
        // find conditional use for loop statements loops outside of assignment loop
        condOutsideAssign = conditionChecker.outsideAssignLoop(conditionalInAssignmentLoop, "conditional");
        // check if the variable only assigned value as part of "for" loop statement
        assignmentInFor = conditionChecker.assignmentInFor("usage");
        // check if the variable only assigned value as part of "for" loop statement
        if (assignmentInFor.size() < 1)
            assignmentInFor = conditionChecker.assignmentInFor("conditional"); 
        // check if the variable only assigned value as part of the "enhanced for-loop" statement
        assignmentInForEach = conditionChecker.assignmentInForEach("usage");
        // check if the variable only assigned value as part of the "enhanced for-loop" statement
        if (assignmentInForEach.size() < 1)
            assignmentInForEach = conditionChecker.assignmentInForEach("conditional");  
        // find assignment statements in loop where variable appears indirectly on both sides of "=")
        indirectBothSides = conditionChecker.statementTypeCheck(assignmentsInLoop, "indirect");
        reorganize = conditionChecker.reorganize();
        // check if the variable is of 'primitive' type
        isPrimitiveType = conditionChecker.primitiveCheck();
        // check if the variable is an 'array'
        isArray = conditionChecker.arrayCheck();

        isArrayFixedElement = conditionChecker.arrayFixedValueCheck();
        // check if the variable is assigned as a 'data structure' in which elements can be added and removed
        isContainerDataStructure = conditionChecker.containerCheck();
        // check if the variable is a data element traversing inside a data structure
        isWalkerElement = conditionChecker.walkerCheck();
        // check if the variable is a data element traversing inside a data structure (but which is associated
        // each time with an object reference obtained from the latest data-structure's gone through value)
        isWalkerMostRecentElement = conditionChecker.walkerMostRecentCheck();
        // check if variable is a data element used as the current element of traversals (e.g., in a depth-
        // first search)
        isWalkerCurrentElement = conditionChecker.walkerCheckCurrent();
        // check if variable is a data entity that traverses a data structure in some systematic way, or an
        // object 
        isWalkerObjectElement = conditionChecker.walkerIsObjectCheck();
        // check if variable is a primitive variable assigned to its negative value.
        isObjectElement = conditionChecker.isObjectCheck();

        isFixedNegativeElement = conditionChecker.isFixedValueNegativeCheck();

        isFixedValueNotWalkerElement = conditionChecker.isFixedValueNotWalker();

        isInpRandMostRecentElement = conditionChecker.inputRandomCheck();

        isMostRecentSmStmntElement = conditionChecker.inpRndmSameCheck();
        
        isMostRecentScannerElement = conditionChecker.inputRandomCheckScanner();

        isSwitchMostRecentElement = conditionChecker.switchCheck();

        isStepperElement = conditionChecker.stepperCheck();

        isGathererElement = conditionChecker.gathererCheck();

        isOneWayFlagElement = conditionChecker.oneWayFlagCheck();

        isEnumarationIteratElement = conditionChecker.isEnumerationIteratorCheck();
        // check if the variable is only assigned by transformation statement
        transformations = conditionChecker.transform(assignmentsInLoop);

        nestedBooleanAssignment = conditionChecker.nestedBooleanAssign(assignmentsInLoop);
        // check is the variable is assigned with output from method
        methodAssignments = conditionChecker.assignedWithMethod(assignmentsInLoop);
        // check if the variable is assigned with value other than of other variable
        otherAssignments = conditionChecker.otherAssignment(assignmentsInLoop);
    }

    /*
     * Set condition flags depending on conditions
     */
    protected void setConditionFlags() {
        if (assignmentsInLoop.size() > 0)
            assignedInLoop = true;
        if (usageInAssignmentLoop.size() > 0)
            usedInAssignLoop = true;
        if (conditionalInAssignmentLoop.size() > 0)
            conditionalUseInAssign = true;
        if (conditionalAssignmentBranch.size() > 0)
            conditionForAssignBranch = true;
        if (onBothSides.size() > 0)
            onBothSidesOfAssign = true;
        if (toggleStatements.size() > 0)
            toggleStatement = true;
        if (incDecStatements.size() > 0)
            incDecStatement = true;
        if (conditionalUseInAssign) {
            useForAssignLoopCondition = conditionChecker.useForLoopCondition(conditionalInAssignmentLoop);
            assignCondLoop = false;
            if (useForAssignLoopCondition.size() > 0)
                assignCondLoop = true;
            if (!assignCondLoop) {
                indirectUse = conditionChecker.indirectUse(conditionalInAssignmentLoop);
            }
        }
        if (assignmentInFor.size() > 0)
            isAssignedInFor = true;
        if (assignmentInForEach.size() > 0)
            isAssignedInForEach = true;
        if (conditionChecker.noDirectUsage() && !onBothSidesOfAssign)
            noDirectUsage = true;
        if (useOutsideAssign.size() > 0)
            usedOutsideAssign = true;
        if (assignBeforeUse.size() > 0)
            assignedBeforeUse = true;
        if (condOutsideAssign.size() > 0)
            loopUseNoAssign = true;
        assignmentLoopConditionUse = (indirectUse || assignCondLoop);
        if (indirectBothSides.size() > 0)
            indirectBS = true;
        if (reorganize.size() > 0)
            isReorganize = true;
        if (transformations.size() > 0)
            isTransform = true;
        if (nestedBooleanAssignment.size() > 0)
            nestedBooleanAssign = true;
        if (methodAssignments.size() > 0)
            methodAssignment = true;
        if (otherAssignments.size() > 0)
            otherAssign = true;
        if (isPrimitiveType.size() > 0)
            isPrimitiveVariable = true;
        if (isArray.size() > 0)
            isArrayVariable = true;
        if (isArrayFixedElement.size() > 0)
            isArrayFixedVariable = true;
        if (isContainerDataStructure.size() > 0)
            isContainerVariable = true;
        if (isWalkerElement.size() > 0)
            isWalkerVariable = true;
        if (isWalkerMostRecentElement.size() > 0)
            isWalkerMostRecentVariable = true;
        if (isWalkerCurrentElement.size() > 0)
            isWalkerCurrentVariable = true;
        if (isWalkerObjectElement.size() > 0)
            isWalkerObjectVariable = true;
        if (isObjectElement.size() > 0)
            isObjectVariable = true;
        if (isFixedNegativeElement.size() > 0)
            isFixedNegativeVariable = true; 
        if (isFixedValueNotWalkerElement.size() > 0)
            isFixedValueNotWalkerVariable = true; 
        if (isInpRandMostRecentElement.size() > 0)
            isInpRandMostRecentVariable = true;
        if (isMostRecentSmStmntElement.size() > 0)
            isMostRecentSmStmntVariable = true;
        if (isMostRecentScannerElement.size() > 0)
            isMostRecentScannerVariable = true;
        if (isSwitchMostRecentElement.size() > 0)
            isSwitchMostRecentVariable = true;
        if (isStepperElement.size() > 0)
            isStepperVariable = true;
        if (isGathererElement.size() > 0)
            isGathererVariable = true;
        if (isOneWayFlagElement.size() > 0)
            isOneWayFlagVariable = true;
        if (isEnumarationIteratElement.size() > 0)
            isEnumarationIteratVariable = true;
    }

    /*
     * Return if variable is 'fixed value'
     */
    protected boolean isFixedValue() {
        fixedValue();
        return isFixedValue;
    }

    /*
     * Return if variable is 'organizer'
     */
    protected boolean isOrganizer() {
        organizer();
        return isOrganizer;
    }

    /*
     * Return if variable is 'stepper'
     */
    protected boolean isStepper() {
        stepper();
        return isStepper;
    }

    /*
     * Return if variable is 'most recent holder'
     */
    protected boolean isMostRecentHolder() {
        mostRecentHolder();
        return isMostRecentHolder;
    }

    /*
     * Return if variable is 'gatherer'
     */
    protected boolean isGatherer() {
        gatherer();
        return isGatherer;
    }

    /*
     * Return if variable is 'most wanted holder'
     */
    protected boolean isMostWantedHolder() {
        mostWantedHolder();
        return isMostWantedHolder;
    }

    /*
     * Return if variable is 'one way flag'
     */
    protected boolean isOneWayFlag() {
        oneWayFlag();
        return isOneWayFlag;
    }

    /*
     * Return if variable is 'follower'
     */
    protected boolean isFollower() {
        follower();
        return isFollower;
    }

    /*
     * Return if variable is 'temporary'
     */
    protected boolean isTemporary() {
        temporary();
        return isTemporary;
    }

    /*
     * Return if variable is 'container'
     */
    protected boolean isContainer() {
        container();
        return isContainer;
    }

    /*
     * Return if variable is 'walker'
     */
    protected boolean isWalker() {
        walker();
        return isWalker;
    }

    /*
     * Return if variable is 'other'
     */
    protected boolean isOther() {
        other();
        return isOther;
    }

    /*
     * Test whether variable is 'fixed value'
     */
    protected ArrayList fixedValue() {
        ArrayList returnArray = new ArrayList();
        offendingStatement = "";
        reason = "";
        isFixedValue = true;
        isOther = false;
          if (isAssignedInFor) {
            isFixedValue = false;
            setMessage(7);
        } else if (isStepperVariable && isPrimitiveVariable) {
            isFixedValue = false;
            setMessage(28);
        } else if (isOneWayFlagVariable) {
            isFixedValue = false;
            setMessage(30);
        } else if (isGathererVariable) {
            isFixedValue = false;
            setMessage(29);
        } else if (isSwitchMostRecentVariable) {
            isFixedValue = false;
            setMessage(27);
        } else if (isEnumarationIteratVariable) {
            isFixedValue = false;
            setMessage(26); 
        } else if (isMostRecentSmStmntVariable) { 
            isFixedValue = false;
            setMessage(33);
        } else if (isFixedNegativeVariable) {
            isFixedValue = true;
            setMessage(24); 
        } else if (isWalkerCurrentVariable) {
            isFixedValue = false;
            setMessage(20); 
        } else if (isWalkerMostRecentVariable) {
            isFixedValue = false;
            setMessage(19);
        } else if (isWalkerVariable) { 
            isFixedValue = false;
            setMessage(17);
        } else if (isWalkerObjectVariable) {
            isFixedValue = false;
            setMessage(21);
        } else if (isObjectVariable) {
            isFixedValue = false;
            setMessage(31); 
        } else if (isArrayVariable && isReorganize) {
            isFixedValue = false;
            setMessage(1);
        } else if (isContainerVariable) {
            isFixedValue = false;
            Set keySet = assignmentsInLoop.keySet();
            Iterator it = keySet.iterator();
            setMessage(18);
        } else if (incDecStatement) {
            isFixedValue = false;
            setMessage(2);
        } else if (toggleStatement) {
            isFixedValue = false;
            setMessage(3);
        } else if (onBothSidesOfAssign) {
            isFixedValue = false;
            setMessage(4);
        } else if (indirectBS) {
            isFixedValue = false;
            setMessage(5);
        } else if (conditionForAssignBranch) {
            isFixedValue = false;
            setMessage(9);
        } else if (isTransform && usedInAssignLoop) {
            isFixedValue = false;
            setMessage(6);
        } else if (isAssignedInForEach) {
            isFixedValue = false;
            setMessage(23);
        } else if (nestedBooleanAssign) {
            isFixedValue = false;
            setMessage(8);
        } else if (!methodAssignment && !otherAssign && usedInAssignLoop) {
            isFixedValue = false;
            setMessage(10);
        } else if (usedInAssignLoop && !isArrayVariable) {
            isFixedValue = false;
            Set keySet = usageInAssignmentLoop.keySet();
            Iterator it = keySet.iterator();
            offendingStatement = (String) it.next();
            reason = "used in loop in which assigned";
        } else if (conditionalUseInAssign && !assignmentLoopConditionUse && !isArrayVariable) {
            isFixedValue = false;
            Set keySet = conditionalInAssignmentLoop.keySet();
            Iterator it = keySet.iterator();
            offendingStatement = (String) it.next();
            reason = "used as condition in loop in which assigned";
        } else if (noDirectUsage && assignCondLoop && !loopUseNoAssign) {
            isFixedValue = false;
            setMessage(11); 
        } else if (isPrimitiveVariable) {
            isFixedValue = true;
            setMessage(22);
        } 
        returnArray.add(offendingStatement);
        returnArray.add(reason);      
        returnArray.add(0, new Boolean(isFixedValue));
        return returnArray;
    }

    /*
     * Test whether variable is 'organizer'
     */
    protected ArrayList organizer() {
        ArrayList returnArray = new ArrayList();
        offendingStatement = "";
        reason = "";
        isOrganizer = true;
        isOther = false;
          if (!isArrayVariable) {
            isOrganizer = false;
            offendingStatement = resultStringer.offendingStatement("organizer");
            reason = "does not appear to be array or is not used directly as array";
        } else if (!isReorganize) {
            isOrganizer = false;
            offendingStatement = resultStringer.offendingStatement("organizer");
            reason = "no organizer type statements found";
        } else if (!onBothSidesOfAssign) {
            isOrganizer = false;
            offendingStatement = resultStringer.offendingStatement("organizer");
            reason = "no statements found where variable on both sides of assignment";
        }
        returnArray.add(offendingStatement);
        returnArray.add(reason);
        returnArray.add(0, new Boolean(isOrganizer));
        return returnArray;
    }

    /*
     * Test whether variable is 'stepper'
     */
    protected ArrayList stepper() {
        ArrayList returnArray = new ArrayList();
        offendingStatement = "";
        reason = "";
        isStepper = true;
        isOther = false;
          if (isStepperVariable) {
            isStepper = true;
            setMessage(28);
        } else if (!incDecStatement && !isAssignedInFor && !toggleStatement && !nestedBooleanAssign) {
            isStepper = false;
            offendingStatement = resultStringer.offendingStatement("stepper");
            reason = "no stepper type assign statements found";
        }
        returnArray.add(offendingStatement);
        returnArray.add(reason);
        returnArray.add(0, new Boolean(isStepper));
        return returnArray;
    }

    /*
     * Test whether variable is 'most recent holder'
     */
    protected ArrayList mostRecentHolder() {
        ArrayList returnArray = new ArrayList();
        offendingStatement = "";
        reason = "";
        isMostRecentHolder = true; 
        isOther = false;
           if (isObjectVariable) {
            isMostRecentHolder = true;
            setMessage(31);
        } else if (isWalkerMostRecentVariable) {
            isMostRecentHolder = true;
            setMessage(19);
        } else if (isMostRecentScannerVariable) {
            isMostRecentHolder = true;
            setMessage(25); 
        } else if (isWalkerVariable) {
            isMostRecentHolder = false;
            setMessage(17);
        } else if (isAssignedInForEach) {
            isMostRecentHolder = true;
            setMessage(23);
        } else if (isAssignedInFor) {
            isMostRecentHolder = false;
            setMessage(7);
        } else if (isStepperVariable) {
            isMostRecentHolder = false;
            setMessage(28);
        } else if (isOneWayFlagVariable) {
            isMostRecentHolder = false;
            setMessage(30);
        } else if (isGathererVariable) {
            isMostRecentHolder = false;
            setMessage(29);
        } else if (isWalkerCurrentVariable) {
            isMostRecentHolder = false;
            setMessage(20);
        } else if (isWalkerObjectVariable) {
            isMostRecentHolder = true;
            setMessage(21);
        } else if (isContainerVariable) {
            isMostRecentHolder = false;
            Set keySet = assignmentsInLoop.keySet();
            Iterator it = keySet.iterator();
            setMessage(18);
        } else if (isFixedNegativeVariable) {
            isMostRecentHolder = false;
            setMessage(24); 
        } else if (isSwitchMostRecentVariable) {
            isMostRecentHolder = true;
            setMessage(27);
        } else if (isMostRecentSmStmntVariable) { 
            isMostRecentHolder = true;
            setMessage(33);
        } else if (isArrayVariable && isReorganize) {
            isMostRecentHolder = false;
            setMessage(1);
        } else if (isArrayVariable && isFixedValue()) {
			isMostRecentHolder = false;
			Set keySet = assignmentsInLoop.keySet();
			Iterator it = keySet.iterator();
			offendingStatement = (String) it.next();
			reason = "is array filled in loop";
		} else if (!assignedInLoop && !isInpRandMostRecentVariable) { 
            isMostRecentHolder = false;
            reason = "not assigned in loop";
        } else if (isEnumarationIteratVariable) {
            isMostRecentHolder = false;
            setMessage(26); 
        } else if (isTransform && (isInpRandMostRecentVariable || isMostRecentScannerVariable)) {
            isMostRecentHolder = true;
            setMessage(6);
        } else if (incDecStatement) {
            isMostRecentHolder = false;
            setMessage(2);
        } else if (toggleStatement) {
            isMostRecentHolder = false;
            setMessage(3);
        } else if (onBothSidesOfAssign) {
            isMostRecentHolder = false;
            setMessage(4);
        } else if (indirectBS) {
            isMostRecentHolder = false;
            setMessage(5);
        } else if (conditionForAssignBranch) {
            isMostRecentHolder = false;
            setMessage(9);
        } else if (nestedBooleanAssign) {
            isMostRecentHolder = false;
            setMessage(8);
        } else if (noDirectUsage && assignCondLoop && !loopUseNoAssign) {
            isMostRecentHolder = false;
            setMessage(11);
        } else if (isTransform && assignedBeforeUse && !usedOutsideAssign) {
            isMostRecentHolder = false;
            reason = "variable is assigned in loop before its use and is not used outside of its assignment loop";
        } else if (!methodAssignment && !otherAssign) {
            isMostRecentHolder = false;
            setMessage(10);
        } else if (!usedInAssignLoop && (!conditionalUseInAssign || assignmentLoopConditionUse)) {
            isMostRecentHolder = false;
            Set keySet = assignmentsInLoop.keySet();
            Iterator it = keySet.iterator();
            setMessage(13);
        } 
        returnArray.add(offendingStatement);
        returnArray.add(reason);
        returnArray.add(0, new Boolean(isMostRecentHolder));
        return returnArray;
    }

    /*
     * Test whether variable is 'gatherer'
     */
    protected ArrayList gatherer() {
        ArrayList returnArray = new ArrayList();
        offendingStatement = "";
        reason = "";
        isGatherer = true;
        isOther = false;
          if (isOneWayFlagVariable) {
            isGatherer = false;
            setMessage(30);
        } else if (isGathererVariable) {
            isGatherer = true;
            setMessage(29);
        } else if (isContainerVariable) {
            isGatherer = false;
            setMessage(16);
        } else if (isWalkerMostRecentVariable) {
            isGatherer = false;
            setMessage(19);
        } else if (isWalkerCurrentVariable) {
            isGatherer = false;
            setMessage(20);
        } else if (isWalkerObjectVariable) {
            isGatherer = false;
            setMessage(21);
        } else if (isWalkerVariable) {
            isGatherer = false;
            setMessage(17);
        } else if (isSwitchMostRecentVariable) {
            isGatherer = false;
            setMessage(27);
        } else if (isMostRecentSmStmntVariable) { 
            isGatherer = false;
            setMessage(33);
        } else if (isAssignedInFor) {
            isGatherer = false;
            setMessage(7);
        } else if (!assignedInLoop) {
            isGatherer = false;
            reason = "not assigned in loop";
        } else if (isFixedNegativeVariable) {
            isGatherer = false;
            setMessage(24); 
        } else if (isAssignedInForEach) {             
            isGatherer = false;
            setMessage(23);
        } else if (isArrayVariable && isReorganize) {
            isGatherer = false;
            setMessage(1);
        } else if (incDecStatement) {
            isGatherer = false;
            setMessage(2);
        } else if (toggleStatement) {
            isGatherer = false;
            setMessage(3);
        } else if (nestedBooleanAssign) {
            isGatherer = false;
            setMessage(8);
        } else if (conditionForAssignBranch) {
            isGatherer = false;
            setMessage(9);
        } else if (isTransform && !onBothSidesOfAssign && !indirectBS) {
            isGatherer = false;
            setMessage(6);
        } else if (noDirectUsage && assignCondLoop && !loopUseNoAssign && !onBothSidesOfAssign && !indirectBS) {
            isGatherer = false;
            setMessage(11);
        } else if (!methodAssignment && !otherAssign && !onBothSidesOfAssign && !indirectBS) {
            isGatherer = false;
            setMessage(10);
        } else if (!onBothSidesOfAssign && !indirectBS) {
            isGatherer = false;
            Set keySet = assignmentsInLoop.keySet();
            Iterator it = keySet.iterator();
            offendingStatement = (String) it.next();
            reason = "not found directly, or indirectly on both sides of assignment statement";
        } 
        returnArray.add(offendingStatement);
        returnArray.add(reason);
        returnArray.add(0, new Boolean(isGatherer));
        return returnArray;
    }

    /*
     * Test whether variable is 'most wanted holder'
     */
    protected ArrayList mostWantedHolder() {
        ArrayList returnArray = new ArrayList();
        offendingStatement = "";
        reason = "";
        isMostWantedHolder = true;
        isOther = false;
          if (isAssignedInFor) {
            isMostWantedHolder = false;
            setMessage(7);
        } else if (isAssignedInForEach) {             
            isMostWantedHolder = false;
            setMessage(23);
        } else if (isContainerVariable) {
            isMostWantedHolder = false;
            setMessage(18);
        } else if (isWalkerMostRecentVariable) {
            isMostWantedHolder = false;
            setMessage(19);
        } else if (isWalkerCurrentVariable) {
            isMostWantedHolder = false;
            setMessage(20);
        } else if (isWalkerObjectVariable) {
            isMostWantedHolder = false;
            setMessage(21);
        } else if (isWalkerVariable) {
            isMostWantedHolder = false;
            setMessage(17);
        } else if (isArrayVariable && isReorganize) {
            isMostWantedHolder = false;
            setMessage(1);
        } else if (isOneWayFlagVariable) {
            isMostWantedHolder = false;
            setMessage(30);
        } else if (isGathererVariable) {
            isMostWantedHolder = false;
            setMessage(29);
        } else if (isSwitchMostRecentVariable) {
            isMostWantedHolder = false;
            setMessage(27);
        } else if (isMostRecentSmStmntVariable) { 
            isMostWantedHolder = false;
            setMessage(33);
        } else if (!assignedInLoop && isInpRandMostRecentVariable) {
            isMostWantedHolder = false;
            setMessage(12);
        } else if (isFixedNegativeVariable) {
            isMostWantedHolder = false;
            setMessage(24); 
        } else if (incDecStatement) {
            isMostWantedHolder = false;
            setMessage(2);
        } else if (toggleStatement) {
            isMostWantedHolder = false;
            setMessage(3);
        } else if (onBothSidesOfAssign) {
            isMostWantedHolder = false;
            setMessage(4);
        } else if (indirectBS) {
            isMostWantedHolder = false;
            setMessage(5);
        } else if (nestedBooleanAssign) {
            isMostWantedHolder = false;
            setMessage(8);
        } else if (!conditionForAssignBranch) {
            isMostWantedHolder = false;
            HashMap assignments = (HashMap) analysedMap.get("assignment");
            Set keySet = assignments.keySet();
            Iterator it = keySet.iterator();
            offendingStatement = (String) it.next();
            reason = "not assigned in branch, or is not condition for branch in which it is assigned";
        } 
        returnArray.add(offendingStatement);
        returnArray.add(reason);
        returnArray.add(0, new Boolean(isMostWantedHolder));
        return returnArray;
    }

    /*
     * Test whether variable is 'one way flag'
     */
    protected ArrayList oneWayFlag() {
        ArrayList returnArray = new ArrayList();
        offendingStatement = "";
        reason = "";
        isOneWayFlag = true;
        isOther = false;
          if (isOneWayFlagVariable) {
            isOneWayFlag = true;
            setMessage(30);
        } else if (isAssignedInFor) {
            isOneWayFlag = false;
            setMessage(7);
        } else if (isAssignedInForEach) {             
            isOneWayFlag = false;
            setMessage(23);
        } else if (isWalkerVariable) {
            isOneWayFlag = false;
            setMessage(17);
        } else if (!assignedInLoop) {
            isOneWayFlag = false;
            reason = "not assigned in loop";
        } else if (isContainerVariable) {
            isOneWayFlag = false;
            setMessage(16);
        } else if (isArrayVariable && isReorganize) {
            isOneWayFlag = false;
            setMessage(1);
        } else if (isArrayVariable && !isReorganize) {
            isOneWayFlag = false;
            Set keySet = assignmentsInLoop.keySet();
            Iterator it = keySet.iterator();
            offendingStatement = (String) it.next();
            reason = "does not appear to be used as one way flag";
        } else if (isWalkerMostRecentVariable) {
            isOneWayFlag = false;
            setMessage(19);
        } else if (isWalkerCurrentVariable) {
            isOneWayFlag = false;
            setMessage(20);
        } else if (isWalkerObjectVariable) {
            isOneWayFlag = false;
            setMessage(21);
        } else if (isSwitchMostRecentVariable) {
            isOneWayFlag = false;
            setMessage(27);
        } else if (isMostRecentSmStmntVariable) { 
            isOneWayFlag = false;
            setMessage(33);
        } else if (isFixedNegativeVariable) {
            isOneWayFlag = false;
            setMessage(24); 
        } else if (incDecStatement) {
            isOneWayFlag = false;
            setMessage(2);
        } else if (toggleStatement) {
            isOneWayFlag = false;
            setMessage(3);
        } else if (onBothSidesOfAssign) {
            isOneWayFlag = false;
            setMessage(4);
        } else if (indirectBS) {
            isOneWayFlag = false;
            setMessage(5);
        } else if (nestedBooleanAssign) {
            isOneWayFlag = false;
            setMessage(8);
        } else if (assignedBeforeUse && !usedOutsideAssign) {
            isOneWayFlag = false;
            reason = "variable is assigned in loop before its use and is not used outside of its assignment loop";
        } else if (isTransform && !assignCondLoop) {
            isOneWayFlag = false;
            setMessage(6);
        } else if (!methodAssignment && !otherAssign) {
            isOneWayFlag = false;
            setMessage(10);
        } else if (!noDirectUsage) {
            isOneWayFlag = false;
            HashMap usages = (HashMap) analysedMap.get("usage");
            Set keySet = usages.keySet();
            Iterator it = keySet.iterator();
            offendingStatement = (String) it.next();
            reason = "direct use of variable";
        } else if (!assignCondLoop) {
            isOneWayFlag = false;
            offendingStatement = resultStringer.offendingStatement("one way flag");
            reason = "not used directly for assign loop condition";
        } else if (loopUseNoAssign) {
            isOneWayFlag = false;
            offendingStatement = resultStringer.offendingStatement("one way flag");
            reason = "used for loop condition outside of loop in which it is assigned";
        }
        returnArray.add(offendingStatement);
        returnArray.add(reason);
        returnArray.add(0, new Boolean(isOneWayFlag));
        return returnArray;
    }

    /*
     * Test whether variable is follower
     */
    protected ArrayList follower() {
        ArrayList returnArray = new ArrayList();
        offendingStatement = "";
        reason = "";
        isFollower = true;
        isOther = false;
          if (isAssignedInForEach) {             
            isFollower = false;
            setMessage(23);
        } else if (isSwitchMostRecentVariable) {
            isFollower = false;
            setMessage(27);
        } else if (isMostRecentSmStmntVariable) { 
            isFollower = false;
            setMessage(33);
        } else if (!assignedInLoop) {
            isFollower = false;
            setMessage(12);
        } else if (isWalkerVariable) {
            isFollower = false;
            setMessage(17);
        } else if (isContainerVariable) {
            isFollower = false;
            setMessage(16);
        } else if (isWalkerMostRecentVariable) {
            isFollower = false;
            setMessage(19);
        } else if (isWalkerCurrentVariable) {
            isFollower = false;
            setMessage(20);
        } else if (isWalkerObjectVariable) {
            isFollower = false;
            setMessage(21);
        } else if (isArrayVariable && isReorganize) {
            isFollower = false;
            setMessage(1);
        } else if (isOneWayFlagVariable) {
            isFollower = false;
            setMessage(30);
        } else if (isGathererVariable) {
            isFollower = false;
            setMessage(29);
        } else if (isFixedNegativeVariable) {
            isFollower = false;
            setMessage(24); 
        } else if (isAssignedInFor) {
            isFollower = false;
            setMessage(7);
        } else if (incDecStatement) {
            isFollower = false;
            setMessage(2);
        } else if (toggleStatement) {
            isFollower = false;
            setMessage(3);
        } else if (onBothSidesOfAssign) {
            isFollower = false;
            setMessage(4);
        } else if (indirectBS) {
            isFollower = false;
            setMessage(5);
        } else if (nestedBooleanAssign) {
            isFollower = false;
            setMessage(8);
        } else if (conditionForAssignBranch) {
            isFollower = false;
            setMessage(9);
        } else if (methodAssignment) {
            isFollower = false;
            setMessage(14);
        } else if (isTransform) {
            isFollower = false;
            setMessage(6);
        } else if (noDirectUsage && assignCondLoop && !loopUseNoAssign) {
            isFollower = false;
            setMessage(11);
        } else if (otherAssign) {
            isFollower = false;
            setMessage(15);
        } else if (assignedBeforeUse && !usedOutsideAssign) { 
            isFollower = false;
            offendingStatement = (String) assignBeforeUse.get(0);
            reason = "assigned before use in loop and not used outside of assignment loop";
        } else if (!usedInAssignLoop) {
            isFollower = false;
            setMessage(13);
        } 
        returnArray.add(offendingStatement);
        returnArray.add(reason);
        returnArray.add(0, new Boolean(isFollower));
        return returnArray;
    }

    /*
     * Test whether variable is temporary
     */
    protected ArrayList temporary() {
        ArrayList returnArray = new ArrayList();
        offendingStatement = "";
        reason = "";
        isTemporary = true;
        isOther = false;
          if (isContainerVariable) {
            isTemporary = false;
            setMessage(16);
        } else if (isWalkerMostRecentVariable) {
            isTemporary = false;
            setMessage(19);
        } else if (isWalkerCurrentVariable) {
            isTemporary = false;
            setMessage(20);
        } else if (isWalkerObjectVariable) {
            isTemporary = false;
            setMessage(21);
        } else if (isWalkerVariable) {
            isTemporary = false;
            setMessage(17);
        } else if (isOneWayFlagVariable) {
            isTemporary = false;
            setMessage(30);
        } else if (isGathererVariable) {
            isTemporary = false;
            setMessage(29);
        } else if (isAssignedInFor) {
            isTemporary = false;
            setMessage(7);
        } else if (isSwitchMostRecentVariable) {
            isTemporary = false;
            setMessage(27);
        } else if (isMostRecentSmStmntVariable) { 
            isTemporary = false;
            setMessage(33);
        } else if (!assignedInLoop && isInpRandMostRecentVariable) {
            isTemporary = false;
            setMessage(12);
        } else if (isTransform && isInpRandMostRecentVariable) { 
            isTemporary = false;
            setMessage(25);
        } else if (isFixedNegativeVariable) {
            isTemporary = false;
            setMessage(24); 
        } else if (isAssignedInForEach) {             
            isTemporary = false;
            setMessage(23);
        } else if (isArrayVariable && isReorganize) {
            isTemporary = false;
            setMessage(1);
        } else if (incDecStatement) {
            isTemporary = false;
            setMessage(2);
        } else if (toggleStatement) {
            isTemporary = false;
            setMessage(3);
        } else if (onBothSidesOfAssign) {
            isTemporary = false;
            setMessage(4);
        } else if (indirectBS) {
            isTemporary = false;
            setMessage(5);
        } else if (nestedBooleanAssign) {
            isTemporary = false;
            setMessage(8);
        } else if (conditionForAssignBranch) {
            isTemporary = false;
            setMessage(9);
        } else if (isTransform) {
			isTemporary = true;
			setMessage(6);
		} else if (methodAssignment) {
            isTemporary = false;
            setMessage(14);
        } else if (noDirectUsage && assignCondLoop && !loopUseNoAssign) {
            isTemporary = false;
            setMessage(11);
        } else if (otherAssign) {
            isTemporary = false;
            setMessage(15);
        } else if (!assignedBeforeUse) {
            isTemporary = false;
            reason = "not assigned in loop before use";
        } else if (usedOutsideAssign) {
            isTemporary = false;
            Set keySet = useOutsideAssign.keySet();
            Iterator it = keySet.iterator();
            offendingStatement = (String) it.next();
            reason = "used outside of loop in which it is assigned";
        } else if (!usedInAssignLoop) {
            isTemporary = false;
            setMessage(13);
        } 
        returnArray.add(offendingStatement);
        returnArray.add(reason);
        returnArray.add(0, new Boolean(isTemporary));
        return returnArray;
    }

    /*
     * Test whether variable is 'container'
     */
    protected ArrayList container() {
        ArrayList returnArray = new ArrayList();
        offendingStatement = "";
        reason = ""; 
        isContainer = true;
        isOther = false;
          if (isOneWayFlagVariable) {
            isContainer = false;
            setMessage(30);
        } else if (!isContainerVariable && isEnumarationIteratVariable) {
            isContainer = false;
            reason = "does not appear to be a data structure where elements can be added and removed";
        } else if (isGathererVariable) {
            isContainer = false;
            setMessage(29);
        } else if (isObjectVariable) {
            isContainer = false;
            setMessage(31);
        } else if (isWalkerMostRecentVariable) {
            isContainer = false;
            setMessage(19);
        } else if (isWalkerCurrentVariable) {
            isContainer = false;
            setMessage(20);
        } else if (isWalkerObjectVariable) {
            isContainer = false;
            setMessage(21);
        } else if (isWalkerVariable) {
            isContainer = false;
            setMessage(17);
        } else if (isAssignedInFor) {
            isContainer = false;
            setMessage(7);
        } else if (!isContainerVariable && usedInAssignLoop) {
            isContainer = false;
            reason = "does not appear to be a data structure where elements can be added and removed";
        } else if (isArrayVariable) {
            isContainer = false;
            setMessage(32);
        } else if (isArrayVariable && isReorganize) {
            isContainer = false;
            setMessage(1);
        } else if (isSwitchMostRecentVariable) {
            isContainer = false;
            setMessage(27);
        } else if (isMostRecentSmStmntVariable) { 
            isContainer = false;
            setMessage(33);
        } else if (isAssignedInForEach) {             
            isContainer = false;
            setMessage(23);
        } else if (isInpRandMostRecentVariable) {
            isContainer = false;
            setMessage(25);
        } else if (isFixedNegativeVariable) {
            isContainer = false;
            setMessage(24); 
        } else if (isTransform && usedInAssignLoop) {
            isContainer = false;
            setMessage(6);
        } else if (isPrimitiveVariable){
            isContainer = false;
            setMessage(22);
        } else if (incDecStatement) {
            isContainer = false;
            setMessage(2);
        } else if (conditionForAssignBranch) {
            isContainer = false;
            setMessage(9);
        } else if (onBothSidesOfAssign) {
            isContainer = false;
            setMessage(4);
        } 
        returnArray.add(offendingStatement);
        returnArray.add(reason);
        returnArray.add(0, new Boolean(isContainer));
        return returnArray;
    }

    /*
     * Test whether variable is 'walker'
     */
    protected ArrayList walker() {
        ArrayList returnArray = new ArrayList();
        offendingStatement = "";
        reason = ""; 
        isWalker = true;
        isOther = false;
          if (isObjectVariable) {
            isWalker = false;
            setMessage(31);
        } else if (isEnumarationIteratVariable ) { 
            isWalker = true;
            setMessage(26);
        } else if (isMostRecentScannerVariable) {
            isWalker = false;
            setMessage(25); 
        } else if (isWalkerVariable) {
            isWalker = true;
            setMessage(17);
        } else if (isWalkerVariable) {
            isWalker = true;
            setMessage(17);
        } else if (isFixedNegativeVariable) {
            isWalker = false;
            setMessage(24); 
        } else if (isContainerVariable) {
            isWalker = false;
            setMessage(16);
        } else if (isWalkerMostRecentVariable) {
            isWalker = false;
            setMessage(19);
        } else if (isWalkerCurrentVariable) {
            isWalker = true;
            setMessage(20);
        } else if (isWalkerObjectVariable) {
            isWalker = false;
            setMessage(21);
        } else if (isFixedValueNotWalkerVariable) {
            isWalker = false;
            reason = "appears to be a variable that refers to a data entity whose value is not changed in run-time after initialization";
        } else if (isAssignedInFor) {
            isWalker = false;
            setMessage(7);
        } else if (conditionForAssignBranch) {
            isWalker = false;
            setMessage(9);
        } else if (isArrayVariable && isReorganize) {
            isWalker = false;
            setMessage(1);
        } else if (isOneWayFlagVariable) {
            isWalker = false;
            setMessage(30);
        } else if (isGathererVariable) {
            isWalker = false;
            setMessage(29);
        } else if (isSwitchMostRecentVariable) {
            isWalker = false;
            setMessage(27);
        } else if (isMostRecentSmStmntVariable) { 
            isWalker = false;
            setMessage(33);
        } else if (isMostRecentSmStmntVariable || isInpRandMostRecentVariable) {
            isWalker = false;
            reason = "does not appear to be a data item traversing in a data structure";
        } else if (isAssignedInForEach) {             
            isWalker = false;
            setMessage(23);
        } else if (incDecStatement) {
            isWalker = false;
            setMessage(2);
        } else if (nestedBooleanAssign) {
            isWalker = false;
            setMessage(8);
        }
        returnArray.add(offendingStatement);
        returnArray.add(reason);
        returnArray.add(0, new Boolean(isWalker));
        return returnArray;
    }

    /*
     * Test whether variable is other
     */
    protected ArrayList other() {
        ArrayList returnArray = new ArrayList();
        offendingStatement = "";
        reason = ""; 
        isOther = true;
        isFixedValue = false;
        isOrganizer = false;
        isStepper = false;
        isMostRecentHolder = false;
        isGatherer = false;
        isMostWantedHolder = false;
        isOneWayFlag = false;
        isFollower = false;
        isTemporary = false;
        isContainer = false;
        isWalker = false;
           if (isAssignedInFor) {
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is assigned inside a 'for' loop";
        }  else if (isSwitchMostRecentVariable) {
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is a value (expression) of a data type that is compared with other case label expressions inside a 'switch' block";
        }  else if (isMostRecentSmStmntVariable) { 
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is used as 'input' or as a 'random' number, multiple times with the same variable assignment";
        }  else if (isGathererVariable) {
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is a data entity accumulating the effect of individual values";
        }  else if (isContainerVariable) {
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is a data structure in which elements can be added and removed";
        }  else if (isFixedValueNotWalkerVariable) {
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable refers to a data entity whose value is not changed in run-time after initialization";
        }  else if (isWalkerVariable || isWalkerCurrentVariable) {
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is a data entity that traverses a data structure";
        }  else if (isEnumarationIteratVariable) {
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is an element of a collection returned by an enumeration/iterator object instance";
        }  else if (!assignedInLoop) {   
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is not assigned inside a loop";
        }  else if (isArrayVariable && isFixedValue()) {
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is an array filled inside a loop";
        }  else if (isArrayFixedVariable && !isReorganize && !isInpRandMostRecentVariable) {
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is an array container object that holds a fixed number of values declared/initialized at compile time";
        }  else if (isFixedNegativeVariable) {
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is a primitive variable assigned to its negative value";
        }  else if (isArrayVariable && isReorganize) {
            isOther = false;
            Set keySet = assignmentsInLoop.keySet();
            Iterator it = keySet.iterator();
            offendingStatement = (String) it.next();
            reason = "role declared for variable is not recognized - however since the variable is an array used for rearranging its elements";
        }  else if (isStepperVariable && isPrimitiveVariable) {
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is a data item stepping through a systematic, predictable succession of values";
        }  else if (incDecStatement && isAssignedInFor) {
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is assigned and incremented/decremented(etc..) exclusively inside a 'for' loop statement";
        }  else if (toggleStatement) {
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is toggled within a loop";
        }  else if (nestedBooleanAssign) {
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is indirectly toggled by being assigned twice within a loop (once inside a nested loop with opposing values)";
        }  else if (conditionForAssignBranch) {
            isOther = false; 
            reason = "role declared for variable is not recognized - however since the variable is assigned in a branch, or is a condition for the branch in which it is assigned";
        }  else if (isWalkerMostRecentVariable) {
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is a node object that constantly references (holds in memory) the latest node element value that the tree encounters";
        }  else if (isOneWayFlagVariable) {
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is a boolean data entity (returned by a compound expression) that can be effectively changed only once";
        }  else if (assignCondLoop) {
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is used directly as the condition for the loop within which it is assigned (but not used within the loop itself)";
        }  else if (isWalkerObjectVariable) {
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is an object (string, or custom object) that contains or points to the data associated with a node";
        }  else if (!assignedBeforeUse && !methodAssignment && !otherAssign && usedInAssignLoop) { 
            isOther = false; 
            reason = "role declared for variable is not recognized - however since the variable is not assigned before its use in a loop and is used outside of its assignment loop (always with value of other variable)";
        }  else if (assignedBeforeUse && !usedOutsideAssign && (!otherAssign || isTransform)) { 
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is assigned in loop before its use and is not used outside of its assignment loop";    
        }  else if (!methodAssignment && !otherAssign) {
            isOther = false; 
            reason = "role declared for variable is not recognized - however since the variable is always assigned in loop with value of other variable";
        }  else if (isAssignedInForEach) {             
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is assigned and used inside an enhanced 'for-loop' construct to iterate through a collection's elements";
        }  else if (isObjectVariable) {
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is an object instantiated to generate a series of elements, to read input, or to generate a stream of pseudorandom numbers";
        }  else if (usedInAssignLoop && !isArrayVariable) {
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is used in loop in which assigned";
        }  else if (isInpRandMostRecentVariable) {
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is a data entity holding the latest value encountered, or simply the latest value(s) obtained as input";
        }  else if (isTransform) {
            isOther = false;
            reason = "role declared for variable is not recognized - however since the variable is assigned within a loop and combined along with other variables, operators and constants";
        }  
        returnArray.add(offendingStatement);
        returnArray.add(reason);
        returnArray.add(0, new Boolean(isOther));
        return returnArray;
    }

    /*
     * Set message applicable condition detected
     */
    private void setMessage(int reasonInt) {
        Iterator it;
        Set keySet;
        switch (reasonInt) {
            case 1:
            offendingStatement = (String) reorganize.get(0);
            reason = "appears to be organizer statement";      
            break;
            case 2:
            offendingStatement = (String) incDecStatements.get(0);
            reason = "incremented/decremented within loop";
            break;
            case 3:
            offendingStatement = (String) toggleStatements.get(0);
            reason = "toggled within loop";
            break;
            case 4:
            offendingStatement = (String) onBothSides.get(0);
            reason = "appears on both sides of assignment";
            break;
            case 5:
            offendingStatement = (String) indirectBothSides.get(0);
            reason = "indirectly appears on both sides of assignment";
            break;
            case 6:
            offendingStatement = (String) transformations.get(0);
            reason = "assigned in loop with combination of other data entity(s) value(s), and used for a very short time only";
            //reason = "assigned in loop with combination of other variables, operators and constants";
            break;
            case 7:
            offendingStatement = (String) assignmentInFor.get(0);
            reason = "assigned in 'for' loop statement";
            break;
            case 8:
            String statementType = (String) nestedBooleanAssignment.get(1);
            if (statementType.equals("nested")) {
                offendingStatement = (String) nestedBooleanAssignment.get(0);
            } else {
                offendingStatement = (String) nestedBooleanAssignment.get(2);
            }
            reason = "appears to be indirectly toggled within loop";
            break;
            case 9:
            keySet = conditionalAssignmentBranch.keySet();
            it = keySet.iterator();
            offendingStatement = (String) it.next();
            reason = "used as condition for branch in which assigned";
            break;
            case 10:
            keySet = assignmentsInLoop.keySet();
            it = keySet.iterator();
            offendingStatement = (String) it.next();
            reason = "always assigned in loop with value of other variable";
            break;
            case 11:
            offendingStatement = (String) useForAssignLoopCondition.get(0);
            reason = "condition for loop in which assigned and limited use outside of loop";
            break;
            case 12:
            HashMap assignments = (HashMap) analysedMap.get("assignment");
            keySet = assignments.keySet();
            it = keySet.iterator();
            offendingStatement = (String) it.next();
            reason = "not assigned in loop";
            break;
            case 13:
            HashMap usages = (HashMap) analysedMap.get("usage");
            keySet = usages.keySet();
            if (!keySet.isEmpty()) {
                it = keySet.iterator();
                offendingStatement = (String) it.next();
            } else {
                HashMap conditionals = (HashMap) analysedMap.get("conditional");
                keySet = conditionals.keySet();
                it = keySet.iterator();
                offendingStatement = (String) it.next();
            }
            reason = "not used in loop in which assigned";
            break;
            case 14:
            offendingStatement = (String) methodAssignments.get(0);
            reason = "assigned with output from call to method";
            break;
            case 15:
            offendingStatement = (String) otherAssignments.get(0);
            reason = "assigned directly with value, instantiation of object, or call to method of other object";
            break;
            case 16:
            offendingStatement = (String) isContainerDataStructure.get(0);
            reason = "appears to be a data structure for adding and removing elements";
            break;
            case 17:
            offendingStatement = (String) isWalkerElement.get(0);
            reason = "appears to be a data item traversing in a data structure";
            break;
            case 18:
            assignments = (HashMap) analysedMap.get("assignment");
            keySet = assignments.keySet();
            if (!keySet.isEmpty()) {
                it = keySet.iterator();
                offendingStatement = (String) it.next();
            } else {
                HashMap others = (HashMap) analysedMap.get("other");
                keySet = others.keySet();
                it = keySet.iterator();
                offendingStatement = (String) it.next();
            }
            reason = "declared and assigned as a data structure";
            break;
            case 19:
            offendingStatement = (String) isWalkerMostRecentElement.get(0);
            reason = "appears to be a node object that constantly references (holds in memory) the latest node element value that the tree encounters";
            break;
            case 20:
            offendingStatement = (String) isWalkerCurrentElement.get(0);
            reason = "appears to be a data entity that traverses a data structure in some systematic way";
            break;
            case 21:
            offendingStatement = (String) isWalkerObjectElement.get(0);
            reason = "appears to be an object (string, or custom object) that contains or points to the data associated with a node";
            break;
            case 22:
            offendingStatement = (String) isPrimitiveType.get(0);
            reason = "appears to be a primitive data entity whose value does not change after initialization"; 
            break;
            case 23:
            offendingStatement = (String) assignmentInForEach.get(0);
            reason = "appears to be a variable assigned and used inside the enhanced for-loop construct to iterate through a collection's elements";
            break;
            case 24:
            offendingStatement = (String) isFixedNegativeElement.get(0);
            reason = "appears to be a primitive variable assigned to its negative value";
            break;   
            case 25:
            offendingStatement = (String) isInpRandMostRecentElement.get(0);
            reason = "appears to be a data entity holding the latest value encountered in going through a succession of unpredictable values, or simply the latest value(s) obtained as input";
            break;      
            case 26:
            offendingStatement = (String) isEnumarationIteratElement.get(0);
            reason = "appears to be an element of a collection returned by an enumeration/iterator object instance";
            break;  
            case 27:
            offendingStatement = (String) isSwitchMostRecentElement.get(0);
            reason = "appears to be a value (expression) of a data type that is compared with other case label expressions inside a 'switch' block";
            break;  
            case 28:
            offendingStatement = (String) isStepperElement.get(0);
            reason = "appears to be a data item stepping through a systematic, predictable succession of values";
            break;  
            case 29:
            offendingStatement = (String) isGathererElement.get(0);
            reason = "appears to be a data entity accumulating the effect of individual values";
            break; 
            case 30:
            offendingStatement = (String) isOneWayFlagElement.get(0);
            reason = "appears to be a boolean data entity (returned by a compound expression) that can be effectively changed only once";
            break; 
            case 31:
            offendingStatement = (String) isObjectElement.get(0);
            reason = "appears to be an object that is instantiated in order to generate a series of elements, to read input, or to generate a stream of pseudorandom numbers";
            break;     
            case 32:
            offendingStatement = (String) isArray.get(0);
            reason = "appears to be an array container object that holds a fixed number of values of a single type";
            break;
            case 33:
            offendingStatement = (String) isMostRecentSmStmntElement.get(0);
            reason = "variable appears to be being used like an 'input' or alternatively as a 'random' number, but multiple times with the same variable assignment"; 
            break;
        }
    }
}