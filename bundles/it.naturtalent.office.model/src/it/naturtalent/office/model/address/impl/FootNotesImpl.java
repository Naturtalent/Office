/**
 */
package it.naturtalent.office.model.address.impl;

import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.FootNote;
import it.naturtalent.office.model.address.FootNotes;

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
 * An implementation of the model object '<em><b>Foot Notes</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.impl.FootNotesImpl#getFootNotes <em>Foot Notes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FootNotesImpl extends MinimalEObjectImpl.Container implements FootNotes
{
	/**
	 * The cached value of the '{@link #getFootNotes() <em>Foot Notes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFootNotes()
	 * @generated
	 * @ordered
	 */
	protected EList<FootNote> footNotes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FootNotesImpl()
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
		return AddressPackage.Literals.FOOT_NOTES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<FootNote> getFootNotes()
	{
		if (footNotes == null)
		{
			footNotes = new EObjectContainmentEList<FootNote>(FootNote.class, this, AddressPackage.FOOT_NOTES__FOOT_NOTES);
		}
		return footNotes;
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
			case AddressPackage.FOOT_NOTES__FOOT_NOTES:
				return ((InternalEList<?>)getFootNotes()).basicRemove(otherEnd, msgs);
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
			case AddressPackage.FOOT_NOTES__FOOT_NOTES:
				return getFootNotes();
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
			case AddressPackage.FOOT_NOTES__FOOT_NOTES:
				getFootNotes().clear();
				getFootNotes().addAll((Collection<? extends FootNote>)newValue);
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
			case AddressPackage.FOOT_NOTES__FOOT_NOTES:
				getFootNotes().clear();
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
			case AddressPackage.FOOT_NOTES__FOOT_NOTES:
				return footNotes != null && !footNotes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //FootNotesImpl
