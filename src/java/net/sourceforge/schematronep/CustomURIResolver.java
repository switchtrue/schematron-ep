package net.sourceforge.schematronep;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;


import org.eclipse.core.resources.IFile;

public class CustomURIResolver implements URIResolver
{
	URIResolver parent = null;
	IFile base = null;
	public CustomURIResolver(URIResolver aParent, IFile aBase)
	{
		this.parent = aParent;
		this.base = aBase;
	}
	
	public void setBase(IFile aBase)
	{
		this.base = aBase;
	}
	
	public Source resolve(String path, String arg1) throws TransformerException
	{
		File f = resolve(path);
		
		if (f != null)
		{
			try
			{
				return new StreamSource(new FileInputStream(f));
			}
			catch (FileNotFoundException fnfe)
			{
				fnfe.printStackTrace();
			}
		}
		
		return parent.resolve(path, arg1);
	}
	
	public File resolve(String path)
	{
		if (path.startsWith("/") == false && path.indexOf(":") == -1)
		{
			Log.debug("Relative folder is: " + base.getParent().getLocation());
			String folder = base.getParent().getLocation().toString();
			
			File file = new File(folder, path);
			
			if (file.exists())
			{
				return file;
			}
		}
		return null;
	}

}
