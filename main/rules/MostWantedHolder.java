package main.rules;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author cbishop
 */
public class MostWantedHolder extends RoleChecker {
    /**
     * Constructor for MostWantedHolder
     * 
     * @param analysedMap
     *            LinkedHashMap of analysed statements
     * @param variable
     *            String being variable name
     * @param methods
     *            ArrayList of method names
     */
    public MostWantedHolder(LinkedHashMap analysedMap, String variable,
    ArrayList methods, LinkedHashMap currentMap) {
        super(analysedMap, variable, methods, currentMap);
    }

    /**
     * Return list of result from role check
     * 
     * @return ArrayList
     */
    public ArrayList checkRole() {
        ArrayList results = mostWantedHolder();
        //for (int i = 0; i < results.size(); i++) {
        //System.out.println(reason);
        //System.out.println(offendingStatement);
        //}
        if (!isMostWantedHolder()) {
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
 * debug rules for most wanted holder
 * 
 * if (!conditionForAssignBranch) { result = false;
 * results.add(debugStringer.conditionForAssignBranch()); } else if
 * (onBothSidesOfAssign || indirectBS) { result = false;
 * results.add(debugStringer.bothSides(onBothSides, true)); } else if
 * (incDecStatement) { result = false;
 * results.add(debugStringer.incDec(incDecStatements)); } else if
 * (toggleStatement) { result = false;
 * results.add(debugStringer.toggle(toggleStatements)); } results.add(0, new
 * Boolean(result)); return results; }
 */
