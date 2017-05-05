package it.naturtalent.e4.kontakte;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.PojoProperties;

/**
 * Composite zur Eingabe der Bankdaten
 * 
 * @author A682055
 *
 */
public class BankDataComposite extends Composite
{

	private DataBindingContext m_bindingContext;

	private it.naturtalent.e4.kontakte.IBankData bankData;
	
	private Text ibanText;
	
	private Text bicText;

	private Text kontonrText;

	private Text blzText;

	private Text institutText;

	public BankDataComposite(Composite parent, int style,
			it.naturtalent.e4.kontakte.IBankData newIBankData)
	{
		this(parent, style);
		setIBankData(newIBankData);
	}

	public BankDataComposite(Composite parent, int style)
	{
		super(parent, style);
		setLayout(new GridLayout(2, false));

		new Label(this, SWT.NONE).setText("IBAN:");

		ibanText = new Text(this, SWT.BORDER | SWT.SINGLE);
		ibanText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));

		new Label(this, SWT.NONE).setText("BIC:");

		bicText = new Text(this, SWT.BORDER | SWT.SINGLE);
		bicText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));

		new Label(this, SWT.NONE).setText("Kontonr:");

		kontonrText = new Text(this, SWT.BORDER | SWT.SINGLE);
		kontonrText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));

		new Label(this, SWT.NONE).setText("Blz:");

		blzText = new Text(this, SWT.BORDER | SWT.SINGLE);
		blzText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		new Label(this, SWT.NONE).setText("Institut:");

		institutText = new Text(this, SWT.BORDER | SWT.SINGLE);
		institutText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));

		//m_bindingContext = initDataBindings();

	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

	public void setIBankData(it.naturtalent.e4.kontakte.IBankData bankData)
	{
		if (m_bindingContext != null)
		{
			m_bindingContext.dispose();
			m_bindingContext = null;
		}
		if (bankData != null)
		{
			this.bankData = bankData;
			m_bindingContext = initDataBindings();
		}
	}
	
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue kontonrObserveWidget = SWTObservables.observeText(kontonrText, SWT.Modify);
		IObservableValue kontonrObserveValue = PojoObservables.observeValue(bankData, "kontonr");
		bindingContext.bindValue(kontonrObserveWidget, kontonrObserveValue, null, null);
		//
		IObservableValue blzObserveWidget = SWTObservables.observeText(blzText, SWT.Modify);
		IObservableValue blzObserveValue = PojoObservables.observeValue(bankData, "blz");
		bindingContext.bindValue(blzObserveWidget, blzObserveValue, null, null);
		//
		IObservableValue institutObserveWidget = SWTObservables.observeText(institutText, SWT.Modify);
		IObservableValue institutObserveValue = PojoObservables.observeValue(bankData, "institut");
		bindingContext.bindValue(institutObserveWidget, institutObserveValue, null, null);
		//
		IObservableValue observeTextIbanTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(ibanText);
		IObservableValue ibanIBankDataObserveValue = PojoProperties.value("iban").observe(bankData);
		bindingContext.bindValue(observeTextIbanTextObserveWidget, ibanIBankDataObserveValue, null, null);
		//
		IObservableValue observeTextBicTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(bicText);
		IObservableValue bicIBankDataObserveValue = PojoProperties.value("bic").observe(bankData);
		bindingContext.bindValue(observeTextBicTextObserveWidget, bicIBankDataObserveValue, null, null);
		//
		return bindingContext;
	}
}
