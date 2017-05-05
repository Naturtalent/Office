package it.naturtalent.e4.kontakte.ui.dialogs;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;

import it.naturtalent.e4.kontakte.ContactCategoryModel;
import it.naturtalent.e4.kontakte.IKontakteDataModel;
import it.naturtalent.e4.kontakte.KontakteData;
import it.naturtalent.e4.kontakte.KontakteDataModel;
import it.naturtalent.e4.kontakte.ui.parts.ContactDetailsComposite;

import org.eclipse.swt.widgets.Label;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import it.naturtalent.e4.kontakte.ui.Messages;

public class ContactDetailsDialog extends TitleAreaDialog
{

	private KontakteDataModel kontaktDataModel;
	
	private ContactDetailsComposite contactDetailsComposite;
	
	private IEventBroker eventBroker;
	private Button okButton;
	
	// Aenderungen im Modell ueberwachen
	private EventHandler validationEventHandler = new EventHandler()
	{
		@Override
		public void handleEvent(Event event)
		{	
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_ADDRESS_VALIDATION_STATUS))
			{
				IStatus status = (IStatus) event.getProperty(IEventBroker.DATA);
				okButton.setEnabled(status.isOK());				
			}
		}
	};

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public ContactDetailsDialog(Shell parentShell)
	{
		super(parentShell);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent)
	{
		setTitle(Messages.ContactDetailsDialog_this_title);
		//setMessage(Messages.ContactDetailsDialog_this_message);		
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		contactDetailsComposite = new ContactDetailsComposite(container, SWT.NONE);
		contactDetailsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		return area;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}
	
	public void setEventBroker(IEventBroker eventBroker)
	{		
		if(contactDetailsComposite != null)
		{
			this.eventBroker = eventBroker;
			contactDetailsComposite.setEventBroker(this.eventBroker);			
			this.eventBroker.subscribe(IKontakteDataModel.KONTAKT_EVENT+"*",validationEventHandler);
		}
	}
	
	@Override
	public boolean close()
	{
		if(eventBroker != null)
			eventBroker.unsubscribe(validationEventHandler);
			
		return super.close();
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize()
	{
		return new Point(714, 851);
	}

	public void setKontakteDataModel(KontakteDataModel model)			
	{
		contactDetailsComposite.setModel(model);	
		this.kontaktDataModel = model;
	}

	public void setKontakteData(KontakteData kontakteData)			
	{
		contactDetailsComposite.setKontakteData(kontakteData);
		
		// die aktuelle Datenbank als Message im Dialog zeigen
		if(kontaktDataModel != null)
		{
			String collectionLabel = kontaktDataModel.getModelFactory()
					.getCollectionLabel(kontaktDataModel.getCollectionName());		
			setMessage(Messages.bind(Messages.ContactDetailsDialog_this_message, collectionLabel));
		}
	}
	
	public void setContactCategoryModel(ContactCategoryModel contactCategoryModel)			
	{
		contactDetailsComposite.setContactCategoryModel(contactCategoryModel);
	}
	
	
	/*
	public void saveCategories()
	{
		contactDetailsComposite.saveCategories();
	}
	*/

}
