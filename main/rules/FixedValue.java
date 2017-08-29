package main.rules;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author cbishop
 */
public class FixedValue extends RoleChecker {

    /**
     * Constructor for fixed value
     * 
     * @param analysedMap
     *            LinkedHashMap of analysed statements
     * @param variable
     *            String being variable name
     * @param methods
     *            ArrayList of method names
     */
    public FixedValue(LinkedHashMap analysedMap, String variable, ArrayList methods, LinkedHashMap currentMap) {
        super(analysedMap, variable, methods, currentMap);
    }

    /**
     * Return list of result from role check
     * 
     * @return ArrayList
     */
    public ArrayList checkRole() {
        ArrayList results = fixedValue();
        if (!isFixedValue()) {
              if (isOrganizer()) {
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
 * debug rules for fixed value 
 * if (onBothSidesOfAssign || indirectBS) {
 *     result = false; 
 *     results.add(debugStringer.bothSides(onBothSides, true));
 *  } else if (incDecStatement) {
 *     result = false;
 *     results.add(debugStringer.incDec(incDecStatements));
    } else if (toggleStatement) {
       result = false;
 *     results.add(debugStringer.toggle(toggleStatements));
 *  } else if (isAssignedInFor) {
 *      result = false;
 *      results.add(debugStringer.assignedInFor(true));
 *  } else if (conditionForAssignBranch) {
 *      result = false;
 *      results.add(debugStringer.conditionForAssignBranch(conditionalAssignmentBranch));
 *  }  else if (usedInAssignLoop && !isArray) {
 *      result = false;
 *      results.add(debugStringer.showFoundInAssignmentLoop(usageInAssignmentLoop, "usage"));
 *  } else if (conditionalUseInAssign && !assignmentLoopConditionUse && !isArray) {
 *      result = false;
 *      results.add(debugStringer.showFoundInAssignmentLoop(conditionalInAssignmentLoop, "conditional"));
 *  } else if (noDirectUsage && useForAssignLoopCondition && !loopUseNoAssign) {
 *      result = false;
 *      results.add(debugStringer.noDirectUse());
 *  }
 */