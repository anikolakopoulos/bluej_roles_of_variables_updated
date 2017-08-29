package main.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * @author cbishop
 */
public class RuleApplyer {

    /**
     * Return map of results from role checking
     * 
     * @param analysedStatements
     *            HashMap containing whereabouts of variable statements
     * @param roles
     *            Map containing variables and their declared roles
     * @return HashMap containing checked roles for each variable
     */
    public HashMap applyRules(HashMap analysedStatements, LinkedHashMap roles, ArrayList methods, LinkedHashMap currentMap) {
        HashMap checkedRoles = new HashMap();
        ArrayList checkedRole = new ArrayList();
        Set variableSet = roles.keySet();
        Iterator it = variableSet.iterator();
        while (it.hasNext()) {
            String variable = (String) it.next();
            Integer roleValue = (Integer) roles.get(variable);
            LinkedHashMap analysedMap = (LinkedHashMap) analysedStatements.get(variable);
            RoleChecker roleChecker;
            switch (roleValue.intValue()) {
                case 1:
                roleChecker = new FixedValue(analysedMap, variable, methods, currentMap);
                break;
                case 2:
                roleChecker = new Organizer(analysedMap, variable, methods, currentMap);
                break;
                case 3:
                roleChecker = new Stepper(analysedMap, variable, methods, currentMap);
                break;
                case 4:
                roleChecker = new MostRecentHolder(analysedMap, variable, methods, currentMap);
                break;
                case 5:
                roleChecker = new Gatherer(analysedMap, variable, methods, currentMap);
                break;
                case 6:
                roleChecker = new MostWantedHolder(analysedMap, variable, methods, currentMap);
                break;
                case 7:
                roleChecker = new OneWayFlag(analysedMap, variable, methods, currentMap);
                break;
                case 8:
                roleChecker = new Follower(analysedMap, variable, methods, currentMap);
                break;
                case 9:
                roleChecker = new Temporary(analysedMap, variable, methods, currentMap);
                break;
                case 10:
                roleChecker = new Container(analysedMap, variable, methods, currentMap);
                break;
                case 11:
                roleChecker = new Walker(analysedMap, variable, methods, currentMap);
                break;
                default:
                roleChecker = new OtherRole(analysedMap, variable, methods, currentMap);
            }
            checkedRole = roleChecker.checkRole();
            checkedRoles.put(variable, checkedRole);
        }
        return checkedRoles;
    }
}