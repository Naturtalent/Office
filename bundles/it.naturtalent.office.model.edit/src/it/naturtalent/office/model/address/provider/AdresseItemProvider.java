/**
 */
package it.naturtalent.office.model.address.provider;


import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Adresse;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link it.naturtalent.office.model.address.Adresse} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class AdresseItemProvider 
	extends ItemProviderAdapter
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource
{
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdresseItemProvider(AdapterFactory adapterFactory)
	{
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object)
	{
		if (itemPropertyDescriptors == null)
		{
			super.getPropertyDescriptors(object);

			addNamePropertyDescriptor(object);
			addName2PropertyDescriptor(object);
			addName3PropertyDescriptor(object);
			addStrassePropertyDescriptor(object);
			addPlzPropertyDescriptor(object);
			addOrtPropertyDescriptor(object);
			addTypePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Adresse_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Adresse_name_feature", "_UI_Adresse_type"),
				 AddressPackage.Literals.ADRESSE__NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Name2 feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addName2PropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Adresse_name2_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Adresse_name2_feature", "_UI_Adresse_type"),
				 AddressPackage.Literals.ADRESSE__NAME2,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Name3 feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addName3PropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Adresse_name3_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Adresse_name3_feature", "_UI_Adresse_type"),
				 AddressPackage.Literals.ADRESSE__NAME3,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Strasse feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addStrassePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Adresse_strasse_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Adresse_strasse_feature", "_UI_Adresse_type"),
				 AddressPackage.Literals.ADRESSE__STRASSE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Plz feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPlzPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Adresse_plz_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Adresse_plz_feature", "_UI_Adresse_type"),
				 AddressPackage.Literals.ADRESSE__PLZ,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Ort feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addOrtPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Adresse_ort_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Adresse_ort_feature", "_UI_Adresse_type"),
				 AddressPackage.Literals.ADRESSE__ORT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTypePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Adresse_type_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Adresse_type_feature", "_UI_Adresse_type"),
				 AddressPackage.Literals.ADRESSE__TYPE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This returns Adresse.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object)
	{
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Adresse"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getText(Object object)
	{
		String label = ((Adresse)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_Adresse_type") : label;			
	}
	

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification)
	{
		updateChildren(notification);

		switch (notification.getFeatureID(Adresse.class))
		{
			case AddressPackage.ADRESSE__NAME:
			case AddressPackage.ADRESSE__NAME2:
			case AddressPackage.ADRESSE__NAME3:
			case AddressPackage.ADRESSE__STRASSE:
			case AddressPackage.ADRESSE__PLZ:
			case AddressPackage.ADRESSE__ORT:
			case AddressPackage.ADRESSE__TYPE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object)
	{
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator()
	{
		return AddressEditPlugin.INSTANCE;
	}

}
