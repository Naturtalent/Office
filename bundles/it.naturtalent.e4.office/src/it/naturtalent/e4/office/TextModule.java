package it.naturtalent.e4.office;

import java.beans.PropertyChangeEvent;

import org.apache.commons.lang3.StringUtils;

public class TextModule extends BaseBean implements Cloneable
{
	public static final String TEXTMODULE_NAME = "name"; //$NON-NLS-N$
	public static final String TEXTMODULE_CONTENT = "content"; //$NON-NLS-N$	
	
	private java.lang.String name;
	private java.lang.String content;
	
	public java.lang.String getName()
	{
		return name;
	}
	public void setName(java.lang.String name)
	{
		firePropertyChange(new PropertyChangeEvent(this, TEXTMODULE_NAME, this.name,
				this.name = name));				
	}
	public java.lang.String getContent()
	{
		return content;
	}
	public void setContent(java.lang.String content)
	{
		firePropertyChange(new PropertyChangeEvent(this, TEXTMODULE_CONTENT, this.content,
				this.content = content));				
	}
	
	@Override
	public Object clone()
	{		
		try
		{
			return super.clone();
		} catch (CloneNotSupportedException e)
		{			
		}
		return null;
	}
	
	public static String getToolTip(TextModule textModule)
	{
		String toolTip = "";
		if (StringUtils.isNotEmpty(textModule.content))
			toolTip = (textModule.content.length() < 100) ? textModule.content
					: StringUtils.substring(textModule.content, 0, 95) + "...";

		return toolTip;
	}
	
}
