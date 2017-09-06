package java_program;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Apps_I {

    public static String[] vehicles = { "ambulance", "helicopter", "lifeboat" }; //%%vehicles%%fixed value%%

    public static String[][] drivers = { 
            { "Fred", "Sue", "Pete" },
            { "Sue", "Richard", "Bob", "Fred" }, 
            { "Pete", "Mary", "Bob" }, }; //%%drivers%%fixed value%%

     public static void main(String[] args) {

        Map<String, Set<String>> personnel = new HashMap<String, Set<String>>();

        for (int i = 0; i < vehicles.length; i++) {
            String vehicle = vehicles[i]; 
            String[] driversList = drivers[i];

            Set<String> driverSet = new LinkedHashSet<String>(); 

            for (String driver : driversList) { //%%driver%%most recent holder%%
                driverSet.add(driver);
            }

            personnel.put(vehicle, driverSet);
        }

       
        // Iterate through whole thing
        for (String vehicleX : personnel.keySet()) {
            System.out.print(vehicleX);
            System.out.print(": ");
            Set<String> driversList = personnel.get(vehicleX); 

            for (String driverX : driversList) {
                System.out.print(driverX);
                System.out.print(" ");
            }
            
            System.out.println();
        }
    }

}

 