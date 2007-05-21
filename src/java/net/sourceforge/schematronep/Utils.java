package net.sourceforge.schematronep;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.w3c.dom.Node;

public final class Utils
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
	
	public static void dump(Node node)
	{
		try
		{
			TransformerFactory fac = TransformerFactory.newInstance();
			Transformer t = fac.newTransformer();
			FileOutputStream fos = new FileOutputStream(new File("xsl.xsl"));
			t.transform(new DOMSource(node), new StreamResult(fos));
			fos.close();	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
