package main;

/**
 * @author cbishop
 */
import bluej.extensions.*;
import bluej.extensions.editor.Editor;
import bluej.extensions.event.*;
import bluej.extensions.editor.TextLocation;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.LinkedHashMap;

//import main.blueJExt.MenuBuilder;
import main.blueJExt.PreferenceSetter;
import main.progAnal.ProgramAnalyser;

public class RolesOfVariables extends Extension {

    //private MenuBuilder roleMenus;
    private PreferenceSetter rolePreferences;
    
    private BlueJ bluej;

    /**
     * Starts extension
     */
    public void startup(BlueJ blueJ) {
        // Listen for BlueJ events at the "package" level
        bluej = blueJ;
        bluej.addCompileListener(new CompileEventListener());
        rolePreferences = new PreferenceSetter(bluej);
        bluej.setPreferenceGenerator(rolePreferences);
    }

    /**
     * @return version number of extension
     */
    public String getVersion() {
        return ("Version 1.0. 2005.09");
    }

    /**
     * @return name of extension
     */
    public String getName() {
        return ("Roles of Variables");
    }

    /**
     * Outputs terminate message to debug.txt
     */
    public void terminate() {
        System.out.println("Roles of variables terminates");
    }

    /**
     * @return description of extension
     */
    public String getDescription() {
        return ("Extension to analyse roles of variables"
            + "\nand notify if roles annotated correctly or otherwise.");
    }

    /**
     * @return true - assume for now that extension is compatible with current
     *         BlueJ version.
     */
    public boolean isCompatible() {
        return true;
    }

    /**
     * @return URL of Roles of Variables website in the absence of any URL
     *         associated with this extension
     */
    public URL getURL() {
        try {
            return new URL("http://www.cs.joensuu.fi/~saja/var_roles/");
        } catch (Exception e) {
            // The link is either dead or otherwise unreachable
            System.out.println("Roles of Variables: getURL: Exception="
                + e.getMessage());
            return null;
        }
    }
    
    /*
     * Display results of role checking
     */
    private void displayResults(HashMap roleResults) {
        BPackage currentPackage = bluej.getCurrentPackage();
        Set fileNames = roleResults.keySet();
        Iterator it = fileNames.iterator();
        HashMap roleMap = (HashMap) roleResults.get("role map");
        try {
            while (it.hasNext()) {
                String fileName = (String) it.next();
                if (!fileName.equals("role map")) {
                    String className = fileName.substring(0, fileName.length() - 5);
                    BClass currentClass = currentPackage.getBClass(className);
                    Editor editor = currentClass.getEditor();
                    ArrayList fileResults = (ArrayList) roleResults.get(fileName);
                    HashMap checkedResults = (HashMap) fileResults.get(0);
                    LinkedHashMap roles = (LinkedHashMap) fileResults.get(1);
                    Set variables = checkedResults.keySet();
                    Iterator iter = variables.iterator();
                    while (iter.hasNext()) {
                        String variable = (String) iter.next();
                        Integer roleInt = (Integer) roles.get(variable);
                        String role = (String) roleMap.get(roleInt);
                        ArrayList checkedRole = (ArrayList) checkedResults.get(variable);
                        Boolean isOK = (Boolean) checkedRole.get(0);
                        if (!isOK.booleanValue()) {
                            highlightText((String) checkedRole.get(1), editor);
                            String errorString = "";
                            if (role != null) {
                                errorString = "Possible incorrect role annotation of '"
                                + role
                                + "' for variable '"
                                + variable
                                + "'";
                            } else {
                                errorString = "Possible incorrect role annotation "
                                + "for variable '" + variable + "'";
                            }
                            if (rolePreferences.giveReason()) {
                                errorString += " - " + checkedRole.get(2)
                                + "\n";
                            } else
                                errorString += "\n";
                            if (rolePreferences.suggestRole()) {
                                errorString += checkedRole.get(3);
                            }
                            editor.showMessage(errorString);
                            editor.setVisible(true);
                            System.out.println("message shown: " + errorString);
                            return; //if role annotation problem return
                        }
                    }
                    editor.showMessage("Class compiled - no syntax errors.\n"
                        + "Role annotations OK.");
                }
            }
        } catch (PackageNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (ProjectNotOpenException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * Highlight offending statement returned from role checking
     */
    private void highlightText(String offendingStatement, Editor editor) {
        int lines = editor.getLineCount();
        int textLength = editor.getTextLength();
        ProgramAnalyser progAnal = new ProgramAnalyser();
        for (int i = 0; i < lines - 1; i++) {
            String lineToCheck = editor.getText(new TextLocation(i, 0),
                    new TextLocation(i + 1, 0));
            if (progAnal.contains(lineToCheck, offendingStatement)) {
                editor.setSelection(new TextLocation(i, 0), new TextLocation(
                        i + 1, 0));
                System.out.println("text highlighted");
            }
        }
    }

    /*
     * Inner class for compile listener @author cbishop
     *  
     */
    class CompileEventListener implements CompileListener {

        public void compileStarted(CompileEvent e) {
        }

        public void compileError(CompileEvent e) {
        }

        public void compileWarning(CompileEvent e) {
        }

        /*
         * Start new thread for role checking when source code has been compiled
         * successfully
         */
        public void compileSucceeded(CompileEvent e) {
            System.out.println("compile succeeded");
            if (rolePreferences.checkRoles()) {
                File[] files = e.getFiles();
                RoleThread roleThread = new RoleThread(files);
                roleThread.start();
            }
        }

        public void compileFailed(CompileEvent e) {
        }
    }

    /*
     * Inner class for role checking thread @author cbishop
     */
    class RoleThread extends Thread {

		File[] files;

		RoleThread(File[] files) {
			this.files = files;
		}

		/**
		 * Start new thread for role checking
		 */
		public void run() {
			System.out.println("thread started");
			RoleAnalyser roleAnalyser = new RoleAnalyser(files);
			HashMap roleResults = roleAnalyser.checkRoles();
			displayResults(roleResults);
		}
	}
}