package net.sourceforge.schematronep;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;


import org.eclipse.core.resources.IFile;

public class TemplatesManager
{
	private Map templatesMap = new HashMap();

	private TransformerFactory factory = null;

	public TemplatesManager(TransformerFactory aFactory)
	{
		factory = aFactory;
	}

	public Templates getTemplates(File f)
	{
		return (Templates) templatesMap.get(getKey(f));
	}

	private Templates createTemplates(String key, InputStream xsl) throws TransformerConfigurationException
	{
		Templates t = factory.newTemplates(new StreamSource(xsl));
		templatesMap.put(key, t);
		Log.debug("Created templates for " + key);
		return t;
	}

	/**
	 * Create a templates object by applying an existing XSL to XML to create another XSL
	 * @param templates
	 * @param xmlFile
	 * @return
	 */
	public Templates createTemplates(Templates templates, File xmlFile) throws Exception
	{
		String key = getKey(xmlFile);
		Templates retVal = (Templates) templatesMap.get(key);

		if (retVal == null)
		{
			Transformer t = templates.newTransformer();
			DOMResult result = new DOMResult();
			t.transform(new StreamSource(new FileInputStream(xmlFile)), result);

			retVal = factory.newTemplates(new DOMSource(result.getNode()));
			templatesMap.put(key, retVal);
			Log.debug("Created templates for " + key);
		}
		else if (Log.isDebug())
		{
			Log.debug("Reusing existing templates for " + key);
		}
		return retVal;
	}

	public Templates createTemplates(File file) throws TransformerConfigurationException, FileNotFoundException
	{
		Templates retVal = (Templates) templatesMap.get(getKey(file));

		if (retVal == null)
		{
			retVal = createTemplates(getKey(file), new FileInputStream(file));
		}
		else if (Log.isDebug())
		{
			Log.debug("Reusing existing templates for " + getKey(file));
		}

		return retVal;
	}

	public Templates createTemplates(IFile file) throws TransformerConfigurationException, FileNotFoundException
	{
		Templates retVal = (Templates) templatesMap.get(getKey(file.getLocation().toFile()));

		if (retVal == null)
		{
			retVal = createTemplates(file.getLocation().toFile());
		}
		else if (Log.isDebug())
		{
			Log.debug("Reusing existing templates for " + getKey(file.getLocation().toFile()));
		}

		return retVal;
	}

	public Templates createTemplates(URL url) throws TransformerConfigurationException, FileNotFoundException,
					IOException
	{
		Templates retVal = (Templates) templatesMap.get(getKey(url));

		if (retVal == null)
		{
			retVal = createTemplates(getKey(url), url.openStream());
		}
		else if (Log.isDebug())
		{
			Log.debug("Reusing existing templates for " + getKey(url));
		}

		return retVal;
	}

	private void removeTemplates(String key)
	{
		templatesMap.remove(key);
		
		if (Log.isDebug())
		{
			Log.debug("Removed templates for " + key);
		}
	}

	public void removeTemplates(File file)
	{
		removeTemplates(getKey(file));
	}

	public void removeTemplates(IFile file)
	{
		removeTemplates(getKey(file.getLocation().toFile()));
	}

	private String getKey(File f)
	{
		return f.getAbsolutePath();
	}

	private String getKey(URL url)
	{
		return url.toString();
	}
}
