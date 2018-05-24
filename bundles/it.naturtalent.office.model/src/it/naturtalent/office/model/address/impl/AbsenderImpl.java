/**
 */
package it.naturtalent.office.model.address.impl;

import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.AddressPackage;

import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Referenz;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Absender</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.impl.AbsenderImpl#getName <em>Name</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.AbsenderImpl#getAdresse <em>Adresse</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.AbsenderImpl#getReferenz <em>Referenz</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AbsenderImpl extends MinimalEObjectImpl.Container implements Absender
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
	 * The cached value of the '{@link #getReferenz() <em>Referenz</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferenz()
	 * @generated
	 * @ordered
	 */
	protected Referenz referenz;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbsenderImpl()
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
		return AddressPackage.Literals.ABSENDER;
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
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.ABSENDER__NAME, oldName, name));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AddressPackage.ABSENDER__ADRESSE, oldAdresse, newAdresse);
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
				msgs = ((InternalEObject)adresse).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AddressPackage.ABSENDER__ADRESSE, null, msgs);
			if (newAdresse != null)
				msgs = ((InternalEObject)newAdresse).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AddressPackage.ABSENDER__ADRESSE, null, msgs);
			msgs = basicSetAdresse(newAdresse, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.ABSENDER__ADRESSE, newAdresse, newAdresse));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Referenz getReferenz()
	{
		return referenz;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReferenz(Referenz newReferenz, NotificationChain msgs)
	{
		Referenz oldReferenz = referenz;
		referenz = newReferenz;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AddressPackage.ABSENDER__REFERENZ, oldReferenz, newReferenz);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReferenz(Referenz newReferenz)
	{
		if (newReferenz != referenz)
		{
			NotificationChain msgs = null;
			if (referenz != null)
				msgs = ((InternalEObject)referenz).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AddressPackage.ABSENDER__REFERENZ, null, msgs);
			if (newReferenz != null)
				msgs = ((InternalEObject)newReferenz).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AddressPackage.ABSENDER__REFERENZ, null, msgs);
			msgs = basicSetReferenz(newReferenz, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.ABSENDER__REFERENZ, newReferenz, newReferenz));
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
			case AddressPackage.ABSENDER__ADRESSE:
				return basicSetAdresse(null, msgs);
			case AddressPackage.ABSENDER__REFERENZ:
				return basicSetReferenz(null, msgs);
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
			case AddressPackage.ABSENDER__NAME:
				return getName();
			case AddressPackage.ABSENDER__ADRESSE:
				return getAdresse();
			case AddressPackage.ABSENDER__REFERENZ:
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
			case AddressPackage.ABSENDER__NAME:
				setName((String)newValue);
				return;
			case AddressPackage.ABSENDER__ADRESSE:
				setAdresse((Adresse)newValue);
				return;
			case AddressPackage.ABSENDER__REFERENZ:
				setReferenz((Referenz)newValue);
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
			case AddressPackage.ABSENDER__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AddressPackage.ABSENDER__ADRESSE:
				setAdresse((Adresse)null);
				return;
			case AddressPackage.ABSENDER__REFERENZ:
				setReferenz((Referenz)null);
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
			case AddressPackage.ABSENDER__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AddressPackage.ABSENDER__ADRESSE:
				return adresse != null;
			case AddressPackage.ABSENDER__REFERENZ:
				return referenz != null;
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //AbsenderImpl
