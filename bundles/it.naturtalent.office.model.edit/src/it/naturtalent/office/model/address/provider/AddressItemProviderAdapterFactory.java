/**
 */
package it.naturtalent.office.model.address.provider;

import it.naturtalent.office.model.address.util.AddressAdapterFactory;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class AddressItemProviderAdapterFactory extends AddressAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable
{
	/**
	 * This keeps track of the root adapter factory that delegates to this adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComposedAdapterFactory parentAdapterFactory;

	/**
	 * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IChangeNotifier changeNotifier = new ChangeNotifier();

	/**
	 * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<Object> supportedTypes = new ArrayList<Object>();

	/**
	 * This constructs an instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddressItemProviderAdapterFactory()
	{
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}

	/**
	 * This keeps track of the one adapter used for all {@link it.naturtalent.office.model.address.Adresse} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AdresseItemProvider adresseItemProvider;

	/**
	 * This creates an adapter for a {@link it.naturtalent.office.model.address.Adresse}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createAdresseAdapter()
	{
		if (adresseItemProvider == null)
		{
			adresseItemProvider = new AdresseItemProvider(this);
		}

		return adresseItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link it.naturtalent.office.model.address.Referenz} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReferenzItemProvider referenzItemProvider;

	/**
	 * This creates an adapter for a {@link it.naturtalent.office.model.address.Referenz}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createReferenzAdapter()
	{
		if (referenzItemProvider == null)
		{
			referenzItemProvider = new ReferenzItemProvider(this);
		}

		return referenzItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link it.naturtalent.office.model.address.Empfaenger} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EmpfaengerItemProvider empfaengerItemProvider;

	/**
	 * This creates an adapter for a {@link it.naturtalent.office.model.address.Empfaenger}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createEmpfaengerAdapter()
	{
		if (empfaengerItemProvider == null)
		{
			empfaengerItemProvider = new EmpfaengerItemProvider(this);
		}

		return empfaengerItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link it.naturtalent.office.model.address.Receivers} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReceiversItemProvider receiversItemProvider;

	/**
	 * This creates an adapter for a {@link it.naturtalent.office.model.address.Receivers}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createReceiversAdapter()
	{
		if (receiversItemProvider == null)
		{
			receiversItemProvider = new ReceiversItemProvider(this);
		}

		return receiversItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link it.naturtalent.office.model.address.Kontakt} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected KontaktItemProvider kontaktItemProvider;

	/**
	 * This creates an adapter for a {@link it.naturtalent.office.model.address.Kontakt}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createKontaktAdapter()
	{
		if (kontaktItemProvider == null)
		{
			kontaktItemProvider = new KontaktItemProvider(this);
		}

		return kontaktItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link it.naturtalent.office.model.address.Kontakte} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected KontakteItemProvider kontakteItemProvider;

	/**
	 * This creates an adapter for a {@link it.naturtalent.office.model.address.Kontakte}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createKontakteAdapter()
	{
		if (kontakteItemProvider == null)
		{
			kontakteItemProvider = new KontakteItemProvider(this);
		}

		return kontakteItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link it.naturtalent.office.model.address.NtProjektKontakte} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NtProjektKontakteItemProvider ntProjektKontakteItemProvider;

	/**
	 * This creates an adapter for a {@link it.naturtalent.office.model.address.NtProjektKontakte}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createNtProjektKontakteAdapter()
	{
		if (ntProjektKontakteItemProvider == null)
		{
			ntProjektKontakteItemProvider = new NtProjektKontakteItemProvider(this);
		}

		return ntProjektKontakteItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link it.naturtalent.office.model.address.FootNote} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FootNoteItemProvider footNoteItemProvider;

	/**
	 * This creates an adapter for a {@link it.naturtalent.office.model.address.FootNote}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createFootNoteAdapter()
	{
		if (footNoteItemProvider == null)
		{
			footNoteItemProvider = new FootNoteItemProvider(this);
		}

		return footNoteItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link it.naturtalent.office.model.address.FootNotes} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FootNotesItemProvider footNotesItemProvider;

	/**
	 * This creates an adapter for a {@link it.naturtalent.office.model.address.FootNotes}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createFootNotesAdapter()
	{
		if (footNotesItemProvider == null)
		{
			footNotesItemProvider = new FootNotesItemProvider(this);
		}

		return footNotesItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link it.naturtalent.office.model.address.FooterClass} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FooterClassItemProvider footerClassItemProvider;

	/**
	 * This creates an adapter for a {@link it.naturtalent.office.model.address.FooterClass}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createFooterClassAdapter()
	{
		if (footerClassItemProvider == null)
		{
			footerClassItemProvider = new FooterClassItemProvider(this);
		}

		return footerClassItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link it.naturtalent.office.model.address.ReferenzSet} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReferenzSetItemProvider referenzSetItemProvider;

	/**
	 * This creates an adapter for a {@link it.naturtalent.office.model.address.ReferenzSet}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createReferenzSetAdapter()
	{
		if (referenzSetItemProvider == null)
		{
			referenzSetItemProvider = new ReferenzSetItemProvider(this);
		}

		return referenzSetItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link it.naturtalent.office.model.address.ReferenzenClass} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReferenzenClassItemProvider referenzenClassItemProvider;

	/**
	 * This creates an adapter for a {@link it.naturtalent.office.model.address.ReferenzenClass}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createReferenzenClassAdapter()
	{
		if (referenzenClassItemProvider == null)
		{
			referenzenClassItemProvider = new ReferenzenClassItemProvider(this);
		}

		return referenzenClassItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link it.naturtalent.office.model.address.ReferenzGruppe} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReferenzGruppeItemProvider referenzGruppeItemProvider;

	/**
	 * This creates an adapter for a {@link it.naturtalent.office.model.address.ReferenzGruppe}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createReferenzGruppeAdapter()
	{
		if (referenzGruppeItemProvider == null)
		{
			referenzGruppeItemProvider = new ReferenzGruppeItemProvider(this);
		}

		return referenzGruppeItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link it.naturtalent.office.model.address.Absender} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbsenderItemProvider absenderItemProvider;

	/**
	 * This creates an adapter for a {@link it.naturtalent.office.model.address.Absender}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createAbsenderAdapter()
	{
		if (absenderItemProvider == null)
		{
			absenderItemProvider = new AbsenderItemProvider(this);
		}

		return absenderItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link it.naturtalent.office.model.address.Senders} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SendersItemProvider sendersItemProvider;

	/**
	 * This creates an adapter for a {@link it.naturtalent.office.model.address.Senders}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createSendersAdapter()
	{
		if (sendersItemProvider == null)
		{
			sendersItemProvider = new SendersItemProvider(this);
		}

		return sendersItemProvider;
	}

	/**
	 * This returns the root adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComposeableAdapterFactory getRootAdapterFactory()
	{
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	/**
	 * This sets the composed adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory)
	{
		this.parentAdapterFactory = parentAdapterFactory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object type)
	{
		return supportedTypes.contains(type) || super.isFactoryForType(type);
	}

	/**
	 * This implementation substitutes the factory itself as the key for the adapter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter adapt(Notifier notifier, Object type)
	{
		return super.adapt(notifier, this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object adapt(Object object, Object type)
	{
		if (isFactoryForType(type))
		{
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class<?>) || (((Class<?>)type).isInstance(adapter)))
			{
				return adapter;
			}
		}

		return null;
	}

	/**
	 * This adds a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void addListener(INotifyChangedListener notifyChangedListener)
	{
		changeNotifier.addListener(notifyChangedListener);
	}

	/**
	 * This removes a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void removeListener(INotifyChangedListener notifyChangedListener)
	{
		changeNotifier.removeListener(notifyChangedListener);
	}

	/**
	 * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void fireNotifyChanged(Notification notification)
	{
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null)
		{
			parentAdapterFactory.fireNotifyChanged(notification);
		}
	}

	/**
	 * This disposes all of the item providers created by this factory. 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void dispose()
	{
		if (sendersItemProvider != null) sendersItemProvider.dispose();
		if (absenderItemProvider != null) absenderItemProvider.dispose();
		if (adresseItemProvider != null) adresseItemProvider.dispose();
		if (empfaengerItemProvider != null) empfaengerItemProvider.dispose();
		if (receiversItemProvider != null) receiversItemProvider.dispose();
		if (kontaktItemProvider != null) kontaktItemProvider.dispose();
		if (kontakteItemProvider != null) kontakteItemProvider.dispose();
		if (ntProjektKontakteItemProvider != null) ntProjektKontakteItemProvider.dispose();
		if (footNoteItemProvider != null) footNoteItemProvider.dispose();
		if (footNotesItemProvider != null) footNotesItemProvider.dispose();
		if (footerClassItemProvider != null) footerClassItemProvider.dispose();
		if (referenzItemProvider != null) referenzItemProvider.dispose();
		if (referenzSetItemProvider != null) referenzSetItemProvider.dispose();
		if (referenzenClassItemProvider != null) referenzenClassItemProvider.dispose();
		if (referenzGruppeItemProvider != null) referenzGruppeItemProvider.dispose();
	}

}