/**
 */
package it.naturtalent.office.model.address.impl;

import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Kontakt;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Kontakt</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.impl.KontaktImpl#getName <em>Name</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.KontaktImpl#getAdresse <em>Adresse</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.KontaktImpl#getKommunikation <em>Kommunikation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class KontaktImpl extends MinimalEObjectImpl.Container implements Kontakt
{
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAdresse() <em>Adresse</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdresse()
	 * @generated
	 * @ordered
	 */
	protected Adresse adresse;

	/**
	 * The default value of the '{@link #getKommunikation() <em>Kommunikation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKommunikation()
	 * @generated
	 * @ordered
	 */
	protected static final String KOMMUNIKATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getKommunikation() <em>Kommunikation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKommunikation()
	 * @generated
	 * @ordered
	 */
	protected String kommunikation = KOMMUNIKATION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected KontaktImpl()
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
		return AddressPackage.Literals.KONTAKT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName)
	{
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.KONTAKT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Adresse getAdresse()
	{
		return adresse;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAdresse(Adresse newAdresse, NotificationChain msgs)
	{
		Adresse oldAdresse = adresse;
		adresse = newAdresse;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AddressPackage.KONTAKT__ADRESSE, oldAdresse, newAdresse);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAdresse(Adresse newAdresse)
	{
		if (newAdresse != adresse)
		{
			NotificationChain msgs = null;
			if (adresse != null)
				msgs = ((InternalEObject)adresse).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AddressPackage.KONTAKT__ADRESSE, null, msgs);
			if (newAdresse != null)
				msgs = ((InternalEObject)newAdresse).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AddressPackage.KONTAKT__ADRESSE, null, msgs);
			msgs = basicSetAdresse(newAdresse, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.KONTAKT__ADRESSE, newAdresse, newAdresse));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getKommunikation()
	{
		return kommunikation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKommunikation(String newKommunikation)
	{
		String oldKommunikation = kommunikation;
		kommunikation = newKommunikation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.KONTAKT__KOMMUNIKATION, oldKommunikation, kommunikation));
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
			case AddressPackage.KONTAKT__ADRESSE:
				return basicSetAdresse(null, msgs);
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
			case AddressPackage.KONTAKT__NAME:
				return getName();
			case AddressPackage.KONTAKT__ADRESSE:
				return getAdresse();
			case AddressPackage.KONTAKT__KOMMUNIKATION:
				return getKommunikation();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case AddressPackage.KONTAKT__NAME:
				setName((String)newValue);
				return;
			case AddressPackage.KONTAKT__ADRESSE:
				setAdresse((Adresse)newValue);
				return;
			case AddressPackage.KONTAKT__KOMMUNIKATION:
				setKommunikation((String)newValue);
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
			case AddressPackage.KONTAKT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AddressPackage.KONTAKT__ADRESSE:
				setAdresse((Adresse)null);
				return;
			case AddressPackage.KONTAKT__KOMMUNIKATION:
				setKommunikation(KOMMUNIKATION_EDEFAULT);
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
			case AddressPackage.KONTAKT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AddressPackage.KONTAKT__ADRESSE:
				return adresse != null;
			case AddressPackage.KONTAKT__KOMMUNIKATION:
				return KOMMUNIKATION_EDEFAULT == null ? kommunikation != null : !KOMMUNIKATION_EDEFAULT.equals(kommunikation);
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

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", kommunikation: ");
		result.append(kommunikation);
		result.append(')');
		return result.toString();
	}

} //KontaktImpl
