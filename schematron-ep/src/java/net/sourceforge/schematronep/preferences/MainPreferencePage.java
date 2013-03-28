package net.sourceforge.schematronep.preferences;

import net.sourceforge.schematronep.SchematronPlugin;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class MainPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage
{

	public MainPreferencePage()
	{
		super(GRID);
		setPreferenceStore(SchematronPlugin.getDefault().getPreferenceStore());
		setDescription("Preferences for Schematron Plugin");
	}

	protected MainPreferencePage(int style)
	{
		super(style);
	}

	protected void createFieldEditors()
	{
		addField(new BooleanFieldEditor(PreferenceKeys.BOOLEAN_DEBUG, "&Turn on Debugging", getFieldEditorParent()));
	}

	public void init(IWorkbench workbench)
	{
		// TODO Auto-generated method stub

	}

}
