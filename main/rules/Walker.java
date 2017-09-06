package main.rules;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.*;

/**
 * @author anikolakopoulos
 */
public class Walker extends RoleChecker {

    static ArrayList results;

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
    public Walker(LinkedHashMap analysedMap, String variable, ArrayList methods, LinkedHashMap currentMap) {
        super(analysedMap, variable, methods, currentMap);
    }

    /**
     * Return list of result from role check
     * 
     * @return ArrayList
     */
   public ArrayList checkRole() {
        results = walker();
        //System.out.println("Reason: " + reason);
        //System.out.println("Offending Statement: " + offendingStatement);
          if (!isWalker()) {
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
            } else {
                results.add(resultStringer.other());
            } 
        } 
        return results;
    }
}
    

