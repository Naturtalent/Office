/**
 */
package it.naturtalent.office.model.address.impl;

import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Referenz;
import it.naturtalent.office.model.address.Referenzen;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Referenzen</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.impl.ReferenzenImpl#getReferenzen <em>Referenzen</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReferenzenImpl extends MinimalEObjectImpl.Container implements Referenzen
{
	/**
	 * The cached value of the '{@link #getReferenzen() <em>Referenzen</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferenzen()
	 * @generated
	 * @ordered
	 */
	protected EList<Referenz> referenzen;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReferenzenImpl()
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
		return AddressPackage.Literals.REFERENZEN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Referenz> getReferenzen()
	{
		if (referenzen == null)
		{
			referenzen = new EObjectContainmentEList<Referenz>(Referenz.class, this, AddressPackage.REFERENZEN__REFERENZEN);
		}
		return referenzen;
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
			case AddressPackage.REFERENZEN__REFERENZEN:
				return ((InternalEList<?>)getReferenzen()).basicRemove(otherEnd, msgs);
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
			case AddressPackage.REFERENZEN__REFERENZEN:
				return getReferenzen();
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
			case AddressPackage.REFERENZEN__REFERENZEN:
				getReferenzen().clear();
				getReferenzen().addAll((Collection<? extends Referenz>)newValue);
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
			case AddressPackage.REFERENZEN__REFERENZEN:
				getReferenzen().clear();
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
			case AddressPackage.REFERENZEN__REFERENZEN:
				return referenzen != null && !referenzen.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ReferenzenImpl
