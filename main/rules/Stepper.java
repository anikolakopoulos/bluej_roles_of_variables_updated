package main.rules;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author cbishop
 */
public class Stepper extends RoleChecker {
    /**
     * Constructor for Stepper
     * 
     * @param analysedMap
     *            LinkedHashMap of analysed statements
     * @param variable
     *            String being variable name
     * @param methods
     *            ArrayList of method names
     */
    public Stepper(LinkedHashMap analysedMap, String variable, ArrayList methods, LinkedHashMap currentMap) {
        super(analysedMap, variable, methods, currentMap);
    }

    /**
     * Return list of result from role check
     * 
     * @return ArrayList
     */
    public ArrayList checkRole() {
        ArrayList results = stepper();
        //System.out.println(results);
        if (!isStepper()) {
            if (isFixedValue()) {
                results.add(resultStringer.fixedValue());
            } else if (isOrganizer()) {
                results.add(resultStringer.organizer());
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
 * debug rules for stepper
 * 
 * if (!incDecStatement) {
 *     results.add(debugStringer.incDec() + " and ");
 *  } else if (!isAssignedInFor) {
 *      results.add(debugStringer.assignedInFor(false) + " and ");
 *  } else if (!toggleStatement) {
 *      results.add(debugStringer.toggle() + " and ");
 *  } else if (!nestedBooleanAssign) {
 *      results.add(debugStringer.nestedBooleanAssign());
 *  } else if (!incDecStatement && !isAssignedInFor && !toggleStatement && !nestedBooleanAssign) {
 *      result = false;
 *  } results.add(0, new Boolean(result));
 *  
 *  return results;
 */
