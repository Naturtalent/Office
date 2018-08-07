/**
 */
package it.naturtalent.office.model.address.impl;

import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.ReferenzGruppe;
import it.naturtalent.office.model.address.ReferenzenClass;

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
 * An implementation of the model object '<em><b>Referenzen Class</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.impl.ReferenzenClassImpl#getReferenzenClassName <em>Referenzen Class Name</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.ReferenzenClassImpl#getReferenzClassReferenzen <em>Referenz Class Referenzen</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReferenzenClassImpl extends MinimalEObjectImpl.Container implements ReferenzenClass
{
	/**
	 * The default value of the '{@link #getReferenzenClassName() <em>Referenzen Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferenzenClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String REFERENZEN_CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReferenzenClassName() <em>Referenzen Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferenzenClassName()
	 * @generated
	 * @ordered
	 */
	protected String referenzenClassName = REFERENZEN_CLASS_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getReferenzClassReferenzen() <em>Referenz Class Referenzen</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferenzClassReferenzen()
	 * @generated
	 * @ordered
	 */
	protected EList<ReferenzGruppe> referenzClassReferenzen;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReferenzenClassImpl()
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
		return AddressPackage.Literals.REFERENZEN_CLASS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getReferenzenClassName()
	{
		return referenzenClassName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReferenzenClassName(String newReferenzenClassName)
	{
		String oldReferenzenClassName = referenzenClassName;
		referenzenClassName = newReferenzenClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.REFERENZEN_CLASS__REFERENZEN_CLASS_NAME, oldReferenzenClassName, referenzenClassName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ReferenzGruppe> getReferenzClassReferenzen()
	{
		if (referenzClassReferenzen == null)
		{
			referenzClassReferenzen = new EObjectContainmentEList<ReferenzGruppe>(ReferenzGruppe.class, this, AddressPackage.REFERENZEN_CLASS__REFERENZ_CLASS_REFERENZEN);
		}
		return referenzClassReferenzen;
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
			case AddressPackage.REFERENZEN_CLASS__REFERENZ_CLASS_REFERENZEN:
				return ((InternalEList<?>)getReferenzClassReferenzen()).basicRemove(otherEnd, msgs);
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
			case AddressPackage.REFERENZEN_CLASS__REFERENZEN_CLASS_NAME:
				return getReferenzenClassName();
			case AddressPackage.REFERENZEN_CLASS__REFERENZ_CLASS_REFERENZEN:
				return getReferenzClassReferenzen();
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
			case AddressPackage.REFERENZEN_CLASS__REFERENZEN_CLASS_NAME:
				setReferenzenClassName((String)newValue);
				return;
			case AddressPackage.REFERENZEN_CLASS__REFERENZ_CLASS_REFERENZEN:
				getReferenzClassReferenzen().clear();
				getReferenzClassReferenzen().addAll((Collection<? extends ReferenzGruppe>)newValue);
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
			case AddressPackage.REFERENZEN_CLASS__REFERENZEN_CLASS_NAME:
				setReferenzenClassName(REFERENZEN_CLASS_NAME_EDEFAULT);
				return;
			case AddressPackage.REFERENZEN_CLASS__REFERENZ_CLASS_REFERENZEN:
				getReferenzClassReferenzen().clear();
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
			case AddressPackage.REFERENZEN_CLASS__REFERENZEN_CLASS_NAME:
				return REFERENZEN_CLASS_NAME_EDEFAULT == null ? referenzenClassName != null : !REFERENZEN_CLASS_NAME_EDEFAULT.equals(referenzenClassName);
			case AddressPackage.REFERENZEN_CLASS__REFERENZ_CLASS_REFERENZEN:
				return referenzClassReferenzen != null && !referenzClassReferenzen.isEmpty();
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
		result.append(" (referenzenClassName: ");
		result.append(referenzenClassName);
		result.append(')');
		return result.toString();
	}

} //ReferenzenClassImpl
