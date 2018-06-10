/**
 */
package it.naturtalent.office.model.address.impl;

import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.NtProjektKontakte;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Nt Projekt Kontakte</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.impl.NtProjektKontakteImpl#getNtProjektID <em>Nt Projekt ID</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.NtProjektKontakteImpl#getKontakte <em>Kontakte</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NtProjektKontakteImpl extends MinimalEObjectImpl.Container implements NtProjektKontakte
{
	/**
	 * The default value of the '{@link #getNtProjektID() <em>Nt Projekt ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNtProjektID()
	 * @generated
	 * @ordered
	 */
	protected static final String NT_PROJEKT_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNtProjektID() <em>Nt Projekt ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNtProjektID()
	 * @generated
	 * @ordered
	 */
	protected String ntProjektID = NT_PROJEKT_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getKontakte() <em>Kontakte</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKontakte()
	 * @generated
	 * @ordered
	 */
	protected EList<Kontakt> kontakte;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NtProjektKontakteImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return AddressPackage.Literals.NT_PROJEKT_KONTAKTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNtProjektID()
	{
		return ntProjektID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNtProjektID(String newNtProjektID)
	{
		String oldNtProjektID = ntProjektID;
		ntProjektID = newNtProjektID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.NT_PROJEKT_KONTAKTE__NT_PROJEKT_ID, oldNtProjektID, ntProjektID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Kontakt> getKontakte()
	{
		if (kontakte == null)
		{
			kontakte = new EObjectContainmentEList<Kontakt>(Kontakt.class, this, AddressPackage.NT_PROJEKT_KONTAKTE__KONTAKTE);
		}
		return kontakte;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case AddressPackage.NT_PROJEKT_KONTAKTE__KONTAKTE:
				return ((InternalEList<?>)getKontakte()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case AddressPackage.NT_PROJEKT_KONTAKTE__NT_PROJEKT_ID:
				return getNtProjektID();
			case AddressPackage.NT_PROJEKT_KONTAKTE__KONTAKTE:
				return getKontakte();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case AddressPackage.NT_PROJEKT_KONTAKTE__NT_PROJEKT_ID:
				setNtProjektID((String)newValue);
				return;
			case AddressPackage.NT_PROJEKT_KONTAKTE__KONTAKTE:
				getKontakte().clear();
				getKontakte().addAll((Collection<? extends Kontakt>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
			case AddressPackage.NT_PROJEKT_KONTAKTE__NT_PROJEKT_ID:
				setNtProjektID(NT_PROJEKT_ID_EDEFAULT);
				return;
			case AddressPackage.NT_PROJEKT_KONTAKTE__KONTAKTE:
				getKontakte().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case AddressPackage.NT_PROJEKT_KONTAKTE__NT_PROJEKT_ID:
				return NT_PROJEKT_ID_EDEFAULT == null ? ntProjektID != null : !NT_PROJEKT_ID_EDEFAULT.equals(ntProjektID);
			case AddressPackage.NT_PROJEKT_KONTAKTE__KONTAKTE:
				return kontakte != null && !kontakte.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (ntProjektID: ");
		result.append(ntProjektID);
		result.append(')');
		return result.toString();
	}

} //NtProjektKontakteImpl
