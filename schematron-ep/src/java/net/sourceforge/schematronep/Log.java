package net.sourceforge.schematronep;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


import net.sourceforge.schematronep.preferences.PreferenceKeys;

import org.eclipse.core.runtime.Status;

public class Log
{
	public static boolean isDebug()
	{
		return Utils.getBooleanPreference(PreferenceKeys.BOOLEAN_DEBUG);
	}
	
	public static void debug(String message)
	{
		if (Utils.getBooleanPreference(PreferenceKeys.BOOLEAN_DEBUG))
		{
			try
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(message.getBytes())));
				String line = reader.readLine();
				while (line != null)
				{
					log(new Status(Status.INFO, SchematronPlugin.PLUGIN_ID, Status.OK, "***DEBUG*** " + message, null));
					line = reader.readLine();
				}
			}
			catch (IOException e)
			{
				log(new Status(Status.INFO, SchematronPlugin.PLUGIN_ID, Status.OK, "***DEBUG*** " + "Failed to write debug message", e));
			}
		}
	}
	
	public static void info(String message)
	{
		log(new Status(Status.INFO, SchematronPlugin.PLUGIN_ID, Status.OK, message, null));
	}
	
	public static void warn(String message, Exception e)
	{
		log(new Status(Status.WARNING, SchematronPlugin.PLUGIN_ID, Status.OK, message, e));
	}
	
	public static void warn(String message)
	{
		log(new Status(Status.WARNING, SchematronPlugin.PLUGIN_ID, Status.OK, message, null));
	}
	
	public static void error(String message, Exception e)
	{
		log(new Status(Status.ERROR, SchematronPlugin.PLUGIN_ID, Status.OK, message, e));
	}
	
	public static void error(String message)
	{
		log(new Status(Status.ERROR, SchematronPlugin.PLUGIN_ID, Status.OK, message, null));
	}
	
	public static void log(Status status)
	{
		SchematronPlugin.getDefault().getLog().log(status);
	}
}
