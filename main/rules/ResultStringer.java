package main.rules;

/**
 * @author cbishop
 */
public class ResultStringer {

	private String variable;

	/**
	 * Constructor for ResultStringer
	 * 
	 * @param var
	 *            Variable name
	 */
	public ResultStringer(String var) {
		variable = var;
	}

	/**
	 * Return statement to highlight where no offending statement has been
	 * explicitly detected
	 * 
	 * @param role
	 *            String being role declared for variable in source code
	 * @return String
	 */
	public String offendingStatement(String role) {
		return variable + "%%" + role;
	}

	/**
	 * Return string stating that variable appears to be fixed value
	 * 
	 * @return String
	 */
	public String fixedValue() {
		return "variable '" + variable + "' appears to be a 'fixed value'";
	}

	/**
	 * Return string stating that variable appears to be organizer
	 * 
	 * @return String
	 */
	public String organizer() {
		return "variable '" + variable + "' appears to be an 'organizer'";
	}

	/**
	 * Return string stating that variable appears to be stepper
	 * 
	 * @return String
	 */
	public String stepper() {
		return "variable '" + variable + "' appears to be a 'stepper'";
	}

	/**
	 * Return string stating that variable appears to be most recent holder
	 * 
	 * @return String
	 */
	public String mostRecentHolder() {
		return "variable '" + variable + "' appears to be a 'most recent holder'";
	}

	/**
	 * Return string stating that variable appears to be gatherer
	 * 
	 * @return String
	 */
	public String gatherer() {
		return "variable '" + variable + "' appears to be a 'gatherer'";
	}

	/**
	 * Return string stating that variable appears to be most wanted holder
	 * 
	 * @return String
	 */
	public String mostWantedHolder() {
		return "variable '" + variable + "' appears to be a 'most wanted holder'";
	}

	/**
	 * Return string stating that variable appears to be one way flag
	 * 
	 * @return String
	 */
	public String oneWayFlag() {
		return "variable '" + variable + "' appears to be a 'one way flag'";
	}

	/**
	 * Return string stating that variable appears to be follower
	 * 
	 * @return String
	 */
	public String follower() {
		return "variable '" + variable + "' appears to be a 'follower'";
	}

	/**
	 * Return string stating that variable appears to be temporary
	 * 
	 * @return String
	 */
	public String temporary() {
		return "variable '" + variable + "' appears to be a 'temporary'";
	}

	/**
	 * Return string stating that variable appears to be container
	 * 
	 * @return String
	 */
	public String container() {
		return "variable '" + variable + "' appears to be a 'container'";
	}
	
	/**
	 * Return string stating that variable appears to be walker
	 * 
	 * @return String
	 */
	public String walker() {
		return "variable '" + variable + "' appears to be a 'walker'";
	}
	
	/**
	 * Return string stating that variable appears to have other role
	 * 
	 * @return String
	 */
	public String other() {
		return "not clear which role type variable \"" + variable + "\" is playing";
	}
}