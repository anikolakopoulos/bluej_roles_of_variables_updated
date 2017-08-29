package main.rules;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author cbishop
 */
public class OtherRole extends RoleChecker {

    /**
     * Constructor for OtherRole
     * 
     * @param analysedMap
     *            LinkedHashMap of analysed statements
     * @param variable
     *            String being variable name
     * @param methods
     *            ArrayList of method names
     */
    public OtherRole(LinkedHashMap analysedMap, String variable, ArrayList methods, LinkedHashMap currentMap) {
        super(analysedMap, variable, methods, currentMap);
    }

    /**
     * Return list of result from role check
     * 
     * @return ArrayList
     */
    public ArrayList checkRole() {
        //String offendingStatement = resultStringer.offendingStatement("");
        //String reason = "role declared for variable is not recognized";
        //Boolean result = new Boolean(false);
        ArrayList results = other();
        //results.add(result);
        //results.add(offendingStatement);
        //results.add(reason);
        //System.out.println(results);
        //System.out.println(offendingStatement);
        //System.out.println(reason);
        if (!isOther()) {
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
            } else if (isContainer()) {
                results.add(resultStringer.container());
            } else if (isWalker()) {
                results.add(resultStringer.walker());
            }
        }
        return results;
    }
    
}

   