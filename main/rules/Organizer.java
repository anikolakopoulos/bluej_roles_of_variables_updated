package main.rules;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author cbishop
 */
public class Organizer extends RoleChecker {

    /**
     * Constructor for Organizer
     * 
     * @param analysedMap
     *            LinkedHashMap of analysed statements
     * @param variable
     *            String being variable name
     * @param methods
     *            ArrayList of method names
     */
    public Organizer(LinkedHashMap analysedMap, String variable,
            ArrayList methods, LinkedHashMap currentMap) {
        super(analysedMap, variable, methods, currentMap);
    }

    /**
     * Return list of result from role check
     * 
     * @return ArrayList
     */
    public ArrayList checkRole() {
        ArrayList results = organizer();
        //System.out.println(reason);
        if (!isOrganizer()) {
            if (isFixedValue()) {
                results.add(resultStringer.fixedValue());
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
 * debug rules for organizer
 * 
 * if (!isArray) { result = false; results.add(debugStringer.notArray()); } if
 * (!isReorganize) { result = false; results.add(debugStringer.notOrganizer()); }
 * if (!onBothSidesOfAssign) { result = false;
 * results.add(debugStringer.bothSides(onBothSides, false)); } results.add(0,
 * new Boolean(result)); return results;
 */

