package it.naturtalent.e4.office;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class TextModuleTheme extends BaseBean
{
	// Properties	
	public static final String TEXTMODULE_THEME_NAME = "name"; //$NON-NLS-N$
	public static final String TEXTMODULE_THEME_MODULES = "modules"; //$NON-NLS-N$

	private java.lang.String name;
	private List<TextModule>modules = new ArrayList<TextModule>();
	
	public java.lang.String getName()
	{
		return name;
	}
	public void setName(java.lang.String name)
	{
		firePropertyChange(new PropertyChangeEvent(this, TEXTMODULE_THEME_NAME, this.name,
				this.name = name));
	}
	
	public List<TextModule> getModules()
	{
		return modules;
	}
	public void setModules(List<TextModule> modules)
	{
		firePropertyChange(new PropertyChangeEvent(this, TEXTMODULE_THEME_MODULES, this.modules,
				this.modules = modules));
	}


	
}
