/**
 */
package it.naturtalent.office.model.address.impl;

import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.FootNotes;
import it.naturtalent.office.model.address.FooterClass;

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
 * An implementation of the model object '<em><b>Footer Class</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.impl.FooterClassImpl#getFooterClassName <em>Footer Class Name</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.FooterClassImpl#getFooterClassFootNotes <em>Footer Class Foot Notes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FooterClassImpl extends MinimalEObjectImpl.Container implements FooterClass
{
	/**
	 * The default value of the '{@link #getFooterClassName() <em>Footer Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFooterClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String FOOTER_CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFooterClassName() <em>Footer Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFooterClassName()
	 * @generated
	 * @ordered
	 */
	protected String footerClassName = FOOTER_CLASS_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFooterClassFootNotes() <em>Footer Class Foot Notes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFooterClassFootNotes()
	 * @generated
	 * @ordered
	 */
	protected EList<FootNotes> footerClassFootNotes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FooterClassImpl()
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
		return AddressPackage.Literals.FOOTER_CLASS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFooterClassName()
	{
		return footerClassName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFooterClassName(String newFooterClassName)
	{
		String oldFooterClassName = footerClassName;
		footerClassName = newFooterClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.FOOTER_CLASS__FOOTER_CLASS_NAME, oldFooterClassName, footerClassName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FootNotes> getFooterClassFootNotes()
	{
		if (footerClassFootNotes == null)
		{
			footerClassFootNotes = new EObjectContainmentEList<FootNotes>(FootNotes.class, this, AddressPackage.FOOTER_CLASS__FOOTER_CLASS_FOOT_NOTES);
		}
		return footerClassFootNotes;
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
			case AddressPackage.FOOTER_CLASS__FOOTER_CLASS_FOOT_NOTES:
				return ((InternalEList<?>)getFooterClassFootNotes()).basicRemove(otherEnd, msgs);
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
			case AddressPackage.FOOTER_CLASS__FOOTER_CLASS_NAME:
				return getFooterClassName();
			case AddressPackage.FOOTER_CLASS__FOOTER_CLASS_FOOT_NOTES:
				return getFooterClassFootNotes();
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
			case AddressPackage.FOOTER_CLASS__FOOTER_CLASS_NAME:
				setFooterClassName((String)newValue);
				return;
			case AddressPackage.FOOTER_CLASS__FOOTER_CLASS_FOOT_NOTES:
				getFooterClassFootNotes().clear();
				getFooterClassFootNotes().addAll((Collection<? extends FootNotes>)newValue);
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
			case AddressPackage.FOOTER_CLASS__FOOTER_CLASS_NAME:
				setFooterClassName(FOOTER_CLASS_NAME_EDEFAULT);
				return;
			case AddressPackage.FOOTER_CLASS__FOOTER_CLASS_FOOT_NOTES:
				getFooterClassFootNotes().clear();
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
			case AddressPackage.FOOTER_CLASS__FOOTER_CLASS_NAME:
				return FOOTER_CLASS_NAME_EDEFAULT == null ? footerClassName != null : !FOOTER_CLASS_NAME_EDEFAULT.equals(footerClassName);
			case AddressPackage.FOOTER_CLASS__FOOTER_CLASS_FOOT_NOTES:
				return footerClassFootNotes != null && !footerClassFootNotes.isEmpty();
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
		result.append(" (footerClassName: ");
		result.append(footerClassName);
		result.append(')');
		return result.toString();
	}

} //FooterClassImpl
