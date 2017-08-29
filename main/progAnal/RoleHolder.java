package main.progAnal;

import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * @author cbishop Class to note/hold variables and their roles
 */
public class RoleHolder {

    private LinkedHashMap varRoles;

    /**
     * Constructor for RoleHolder
     */
    public RoleHolder() {
        varRoles = new LinkedHashMap();
    }

    /**
     * Note role declared for given variable in comment string
     * 
     * @param roleString
     *            String containing role declaration
     */
    public void noteRole(String roleString) {
        String[] stringArray = new String[3];
        stringArray = roleString.split("%%");
        if (stringArray.length >= 3) {
            Integer roleValue = new Integer(getValue(stringArray[2]));
            //if (roleValue.intValue() > 0) {
            varRoles.put(stringArray[1], roleValue);
            //}
        }
    }

    /*
     * Return int value for declared role
     */
    private int getValue(String role) {
        if (role.equalsIgnoreCase("Fixed value")) {
            return 1;
        } else if (role.equalsIgnoreCase("Organizer")) {
            return 2;
        } else if (role.equalsIgnoreCase("Stepper")) {
            return 3;
        } else if (role.equalsIgnoreCase("Most recent holder")) {
            return 4;
        } else if (role.equalsIgnoreCase("Gatherer")) {
            return 5;
        } else if (role.equalsIgnoreCase("Most wanted holder")) {
            return 6;
        } else if (role.equalsIgnoreCase("One way flag")) {
            return 7;
        } else if (role.equalsIgnoreCase("Follower")) {
            return 8;
        } else if (role.equalsIgnoreCase("Temporary")) {
            return 9;
        } else if (role.equalsIgnoreCase("Container")) {
            return 10;
        } else if (role.equalsIgnoreCase("Walker")) {
            return 11;
        } else {
            return -1;
        }
    }

    /**
     * Print variables and their roles
     * 
     * @return String giving variables and their roles
     */
    public String toString() {
        Set roleSet = (Set) varRoles.keySet();
        Iterator roleIt = roleSet.iterator();
        String roleString = "";
        while (roleIt.hasNext()) {
            String var = (String) roleIt.next();
            roleString += "Variable: " + var + ", Role: " + varRoles.get(var)
            + "\n";
        }
        return roleString;
    }

    /**
     * Return set of variables
     * 
     * @return Set Variables
     */
    public Set getVariables() {
        Set variables = varRoles.keySet();
        return variables;
    }

    /**
     * Return map of variables and their roles
     * 
     * @return LinkedHashMap Variables as keys and their roles as values
     */
    public LinkedHashMap getRoles() {
        return varRoles;
    }
}