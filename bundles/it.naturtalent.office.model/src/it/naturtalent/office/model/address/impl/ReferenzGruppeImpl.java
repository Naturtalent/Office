/**
 */
package it.naturtalent.office.model.address.impl;

import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Referenz;
import it.naturtalent.office.model.address.ReferenzGruppe;

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
 * An implementation of the model object '<em><b>Referenz Gruppe</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.impl.ReferenzGruppeImpl#getGroupname <em>Groupname</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.ReferenzGruppeImpl#getReferenz <em>Referenz</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReferenzGruppeImpl extends MinimalEObjectImpl.Container implements ReferenzGruppe
{
	/**
	 * The default value of the '{@link #getGroupname() <em>Groupname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroupname()
	 * @generated
	 * @ordered
	 */
	protected static final String GROUPNAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGroupname() <em>Groupname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroupname()
	 * @generated
	 * @ordered
	 */
	protected String groupname = GROUPNAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getReferenz() <em>Referenz</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferenz()
	 * @generated
	 * @ordered
	 */
	protected EList<Referenz> referenz;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReferenzGruppeImpl()
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
		return AddressPackage.Literals.REFERENZ_GRUPPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGroupname()
	{
		return groupname;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGroupname(String newGroupname)
	{
		String oldGroupname = groupname;
		groupname = newGroupname;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.REFERENZ_GRUPPE__GROUPNAME, oldGroupname, groupname));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Referenz> getReferenz()
	{
		if (referenz == null)
		{
			referenz = new EObjectContainmentEList<Referenz>(Referenz.class, this, AddressPackage.REFERENZ_GRUPPE__REFERENZ);
		}
		return referenz;
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
			case AddressPackage.REFERENZ_GRUPPE__REFERENZ:
				return ((InternalEList<?>)getReferenz()).basicRemove(otherEnd, msgs);
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
			case AddressPackage.REFERENZ_GRUPPE__GROUPNAME:
				return getGroupname();
			case AddressPackage.REFERENZ_GRUPPE__REFERENZ:
				return getReferenz();
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
			case AddressPackage.REFERENZ_GRUPPE__GROUPNAME:
				setGroupname((String)newValue);
				return;
			case AddressPackage.REFERENZ_GRUPPE__REFERENZ:
				getReferenz().clear();
				getReferenz().addAll((Collection<? extends Referenz>)newValue);
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
			case AddressPackage.REFERENZ_GRUPPE__GROUPNAME:
				setGroupname(GROUPNAME_EDEFAULT);
				return;
			case AddressPackage.REFERENZ_GRUPPE__REFERENZ:
				getReferenz().clear();
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
			case AddressPackage.REFERENZ_GRUPPE__GROUPNAME:
				return GROUPNAME_EDEFAULT == null ? groupname != null : !GROUPNAME_EDEFAULT.equals(groupname);
			case AddressPackage.REFERENZ_GRUPPE__REFERENZ:
				return referenz != null && !referenz.isEmpty();
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
		result.append(" (groupname: ");
		result.append(groupname);
		result.append(')');
		return result.toString();
	}

} //ReferenzGruppeImpl
