package main.rules;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author cbishop
 */
public class OneWayFlag extends RoleChecker {

    /**
     * Constructor for OneWayFlag
     * 
     * @param analysedMap
     *            LinkedHashMap of analysed statements
     * @param variable
     *            String being variable name
     * @param methods
     *            ArrayList of method names
     */
    public OneWayFlag(LinkedHashMap analysedMap, String variable,
    ArrayList methods, LinkedHashMap currentMap) {
        super(analysedMap, variable, methods, currentMap);
    }

    /**
     * Return list of result from role check
     * 
     * @return ArrayList
     */
    public ArrayList checkRole() {
        ArrayList results = oneWayFlag();
        //System.out.println(results);
        if (!isOneWayFlag()) {
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
            } else if (isFollower()) {
                results.add(resultStringer.follower());
            } else if (isTemporary()) {
                results.add(resultStringer.temporary());
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
 * debug rules for one way flag
 * 
 * if (!assignedInLoop) { result = false;
 * results.add(debugStringer.notAssignedInLoop()); } else if(!noDirectUsage) {
 * result = false; results.add(debugStringer.directUse()); } else if
 * (onBothSidesOfAssign || indirectBS) { result = false;
 * results.add(debugStringer.bothSides(onBothSides, true)); } else if
 * (incDecStatement) { result = false;
 * results.add(debugStringer.incDec(incDecStatements)); } else if
 * (toggleStatement) { result = false;
 * results.add(debugStringer.toggle(toggleStatements)); } else if
 * (transformation) { result = false;
 * results.add(debugStringer.isTransformation(transformations)); } else
 * if(conditionForAssignBranch) { result = false;
 * results.add(debugStringer.conditionForAssignBranch(conditionalAssignmentBranch)); }
 * else if (nestedBooleanAssign) { result = false;
 * results.add(debugStringer.nestedBooleanAssign(nestedBooleanAssignment)); }
 * else if (!useForAssignLoopCondition) { result = false;
 * results.add(debugStringer.notUsedForAssignLoopCondition()); } else if
 * (loopUseNoAssign) { result = false;
 * results.add(debugStringer.loopUseNoAssign()); } results.add(0, new
 * Boolean(result)); return results;
 *  
 */
