package main.rules;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author cbishop
 */
public class Temporary extends RoleChecker {
    /**
     * Constructor for Temporary
     * 
     * @param analysedMap
     *            LinkedHashMap of analysed statements
     * @param variable
     *            String being variable name
     * @param methods
     *            ArrayList of method names
     */
    public Temporary(LinkedHashMap analysedMap, String variable,
    ArrayList methods, LinkedHashMap currentMap) {
        super(analysedMap, variable, methods, currentMap);
    }

    /**
     * Return list of result from role check
     * 
     * @return ArrayList
     */
    public ArrayList checkRole() {
        ArrayList results = temporary();
        //System.out.println(results);
        if (!isTemporary()) {
            if (isFixedValue()) {
                results.add(resultStringer.fixedValue());
            } else if (isOrganizer()) {
                results.add(resultStringer.organizer());
            } else if (isStepper()) {
                results.add(resultStringer.stepper());
            } else if (isMostRecentHolder()) {
                results.add(resultStringer.mostRecentHolder());
            } else if (isGatherer()) {
                results.add(resultStringer.gatherer());
            } else if (isMostWantedHolder()) {
                results.add(resultStringer.mostWantedHolder());
            } else if (isOneWayFlag()) {
                results.add(resultStringer.oneWayFlag());
            } else if (isFollower()) {
                results.add(resultStringer.follower());
            } else if (isContainer()) {
                results.add(resultStringer.container());
            } else if (isWalker()) {
                results.add(resultStringer.walker());
            } else {
                results.add(resultStringer.other());
            }
        }
        return results;
    }
}

/*
 * debug rules for temporary
 * 
 * if (!assignedInLoop) { result = false;
 * results.add(debugStringer.notAssignedInLoop()); } if (onBothSidesOfAssign ||
 * indirectBS) { result = false;
 * results.add(debugStringer.bothSides(onBothSides, true)); } if
 * (incDecStatement) { result = false;
 * results.add(debugStringer.incDec(incDecStatements)); } if (toggleStatement) {
 * result = false; results.add(debugStringer.toggle(toggleStatements)); } if
 * (transformation) { result = false;
 * results.add(debugStringer.isTransformation(transformations)); }
 * if(conditionForAssignBranch) { result = false;
 * results.add(debugStringer.conditionForAssignBranch(conditionalAssignmentBranch)); }
 * if (nestedBooleanAssign) { result = false;
 * results.add(debugStringer.nestedBooleanAssign(nestedBooleanAssignment)); } if
 * (methodAssignment) { result = false;
 * results.add(debugStringer.methodAssigns(methodAssignments)); } if
 * (!usedInAssignLoop) { result = false;
 * results.add(debugStringer.notUsedInAssign()); } if (usedOutsideAssign) {
 * result = false; results.add(debugStringer.usedOutsideAssign()); } if
 * (!assignedBeforeUse) { result = false;
 * results.add(debugStringer.assignBeforeUse()); } results.add(0, new
 * Boolean(result)); return results;
 */

