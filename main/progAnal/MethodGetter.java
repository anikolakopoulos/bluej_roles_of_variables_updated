package main.progAnal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * @author cbishop
 */
public class MethodGetter extends ProgramAnalyser {

	/**
	 * Return list of method names in source code
	 * 
	 * @param sortedStatements
	 *            HashMap being program slice for variable
	 * @return ArrayList of method names in program
	 */
	public ArrayList getMethods(HashMap sortedStatements) {
		ArrayList methods = new ArrayList();
		Set keySet = sortedStatements.keySet();
		Iterator it = keySet.iterator();
		String variable = (String) it.next(); //get first available variable
		ArrayList sortedArray = (ArrayList) sortedStatements.get(variable);
		it = sortedArray.iterator();
		while (it.hasNext()) {
			String statement = (String) it.next();
			if (methodStatement(statement)) {
				//add name of method to methods
				int j = 0;
				String reverseName = "";
				for (int i = 0; i < statement.length(); i++) {
					String statementSubstring = statement.substring(i);
					if (statementSubstring.startsWith("(")) { //where method
															  // name ends
						j = i - 1;
						i = statement.length();
					}
				}
				int position = 0; //so that possible gap between method name
								  // and '(' is not interpreted as method name
				while (j > 0) { //work backwards adding method name to
								// reverseName until beginning of method name
					String charString = statement.substring(j, j + 1);
					if (charString.equals(" ") && position > 0) {
						//look for space before method name
						j = 0;
					}
					reverseName += charString;
					j--;
					position++;
				}
				String methodName = "";
				for (j = reverseName.length() - 1; j >= 0; j--) {
					methodName += reverseName.substring(j, j + 1);
				}
				methods.add(methodName);
				//System.out.println(methodName); debug
			}
		}
		return methods;
	}
}