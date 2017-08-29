package main.blueJExt;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.GridLayout;

import bluej.extensions.BlueJ;
import bluej.extensions.PreferenceGenerator;

/**
 * @author cbishop
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PreferenceSetter implements PreferenceGenerator {
	private JPanel myPanel;

	private JCheckBox checkRoles;

	private JCheckBox giveReason;

	private JCheckBox suggestRole;

	private BlueJ bluej;

	public static final String CHECK_ROLES = "Check roles with compilation";

	public static final String GIVE_REASON = "Give reason if incorrect role detected";

	public static final String SUGGEST_ROLE = "Suggest role for incorrectly annotated role";

	/**
	 * Construct the panel, and initialise it from any stored values
	 * 
	 * @param bluej
	 *            BlueJ object
	 */
	public PreferenceSetter(BlueJ bluej) {
		this.bluej = bluej;
		myPanel = new JPanel(new GridLayout(0, 2));
		checkRoles = new JCheckBox();
		giveReason = new JCheckBox();
		suggestRole = new JCheckBox();
		checkRoles.addItemListener(new ItemEventListener());
		myPanel.add(new JLabel("    Check roles with compilation"));
		myPanel.add(checkRoles);
		myPanel.add(new JLabel("    Give reason if incorrect role detected"));
		myPanel.add(giveReason);
		myPanel.add(new JLabel(
				"    Suggest role for incorrectly annotated role"));
		myPanel.add(suggestRole);
		// Load the default value
		loadValues();
		if (checkRoles()) {
			giveReason.setEnabled(true);
			suggestRole.setEnabled(true);
		} else {
			giveReason.setEnabled(false);
			suggestRole.setEnabled(false);
		}
	}

	/**
	 * @return JPanel preference panel
	 */
	public JPanel getPanel() {
		return myPanel;
	}

	/**
	 * Save preferences for extension
	 */
	public void saveValues() {
		// Save the preference value in the BlueJ properties file
		if (checkRoles.isSelected()) {
			bluej.setExtensionPropertyString(CHECK_ROLES, "true");
		} else {
			bluej.setExtensionPropertyString(CHECK_ROLES, "false");
		}
		if (giveReason.isSelected()) {
			bluej.setExtensionPropertyString(GIVE_REASON, "true");
		} else {
			bluej.setExtensionPropertyString(GIVE_REASON, "false");
		}
		if (suggestRole.isSelected()) {
			bluej.setExtensionPropertyString(SUGGEST_ROLE, "true");
		} else {
			bluej.setExtensionPropertyString(SUGGEST_ROLE, "false");
		}
	}

	/**
	 * Load preferences for extension
	 */
	public void loadValues() {
		// Load the property value from the BlueJ proerties file,
		// default to an empty string
		String cr = bluej.getExtensionPropertyString(CHECK_ROLES, "");
		if (cr.equals("true")) {
			checkRoles.setSelected(true);
		} else
			checkRoles.setSelected(false);
		String gr = bluej.getExtensionPropertyString(GIVE_REASON, "");
		if (gr.equals("true")) {
			giveReason.setSelected(true);
		} else
			giveReason.setSelected(false);
		String sr = bluej.getExtensionPropertyString(SUGGEST_ROLE, "");
		if (sr.equals("true")) {
			suggestRole.setSelected(true);
		} else
			suggestRole.setSelected(false);
	}

	/**
	 * @return whether role checking is required
	 */
	public boolean checkRoles() {
		return checkRoles.isSelected();
	}

	/**
	 * @return whether to give reason for incorrect role adjudication
	 */
	public boolean giveReason() {
		return giveReason.isSelected();
	}

	/**
	 * @return whether to suggest correct role for variable
	 */
	public boolean suggestRole() {
		return suggestRole.isSelected();
	}

	/**
	 * inner class to disabled reason and role suggestion if role checking not
	 * selected
	 * 
	 * @author cbishop
	 */
	class ItemEventListener implements ItemListener {

		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == 1) {
				giveReason.setEnabled(true);
				suggestRole.setEnabled(true);
			} else {
				giveReason.setEnabled(false);
				suggestRole.setEnabled(false);
			}
		}
	}
}

