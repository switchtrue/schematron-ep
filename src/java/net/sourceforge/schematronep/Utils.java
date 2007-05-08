package net.sourceforge.schematronep;

import java.io.File;


import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;

public class Utils
{
	public static File convert(IFile file)
	{
		return new File(file.getLocation().toString());
	}
	
	public static IFile convert(File file)
	{
		Path path = new Path(file.getAbsolutePath());
		return ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(path);
	}
	
	public static boolean getBooleanPreference(String key)
	{
		return SchematronPlugin.getDefault().getPreferenceStore().getBoolean(key);
	}
}
