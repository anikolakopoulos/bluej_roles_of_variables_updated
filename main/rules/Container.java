/*
 * Created on 20-Jun-2005
 */
package main.rules;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author cbishop
 */
public class Container extends RoleChecker {

    /**
     * Constructor for Container
     * 
     * @param analysedMap
     *            LinkedHashMap of analysed statements
     * @param variable
     *            String being variable name
     * @param methods
     *            ArrayList of method names
     */
    public Container(LinkedHashMap analysedMap, String variable, ArrayList methods, LinkedHashMap currentMap) {
        super(analysedMap, variable, methods, currentMap);
    }

    /**
     * Return list of result from role check
     * 
     * @return ArrayList
     */
    public ArrayList checkRole() {
        ArrayList results = container();
        //System.out.println(results);
        if (!isContainer()) {
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
            } else if (isTemporary()) {
                results.add(resultStringer.temporary());
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
 * debug rules for container
 * 
 * if (!onBothSidesOfAssign) { result = false;
 * results.add(debugStringer.bothSides(onBothSides, false)); } if (isArray &&
 * isReorganize) { result = false;
 * results.add(debugStringer.isOrganizer(reorganize)); 
 */
