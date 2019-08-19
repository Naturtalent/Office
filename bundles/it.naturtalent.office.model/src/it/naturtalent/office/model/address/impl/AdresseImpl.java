/**
 */
package it.naturtalent.office.model.address.impl;

import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.AddressType;
import it.naturtalent.office.model.address.Adresse;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Adresse</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.impl.AdresseImpl#getName <em>Name</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.AdresseImpl#getName2 <em>Name2</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.AdresseImpl#getName3 <em>Name3</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.AdresseImpl#getStrasse <em>Strasse</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.AdresseImpl#getPlz <em>Plz</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.AdresseImpl#getOrt <em>Ort</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.AdresseImpl#getType <em>Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AdresseImpl extends MinimalEObjectImpl.Container implements Adresse
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
	 * The default value of the '{@link #getName2() <em>Name2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName2()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME2_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName2() <em>Name2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName2()
	 * @generated
	 * @ordered
	 */
	protected String name2 = NAME2_EDEFAULT;

	/**
	 * The default value of the '{@link #getName3() <em>Name3</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName3()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME3_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName3() <em>Name3</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName3()
	 * @generated
	 * @ordered
	 */
	protected String name3 = NAME3_EDEFAULT;

	/**
	 * The default value of the '{@link #getStrasse() <em>Strasse</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStrasse()
	 * @generated
	 * @ordered
	 */
	protected static final String STRASSE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStrasse() <em>Strasse</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStrasse()
	 * @generated
	 * @ordered
	 */
	protected String strasse = STRASSE_EDEFAULT;

	/**
	 * The default value of the '{@link #getPlz() <em>Plz</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlz()
	 * @generated
	 * @ordered
	 */
	protected static final String PLZ_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPlz() <em>Plz</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlz()
	 * @generated
	 * @ordered
	 */
	protected String plz = PLZ_EDEFAULT;

	/**
	 * The default value of the '{@link #getOrt() <em>Ort</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrt()
	 * @generated
	 * @ordered
	 */
	protected static final String ORT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOrt() <em>Ort</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrt()
	 * @generated
	 * @ordered
	 */
	protected String ort = ORT_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final AddressType TYPE_EDEFAULT = AddressType.PRIVATE_ADDRESS;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected AddressType type = TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AdresseImpl()
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
		return AddressPackage.Literals.ADRESSE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName()
	{
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName)
	{
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.ADRESSE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName2()
	{
		return name2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName2(String newName2)
	{
		String oldName2 = name2;
		name2 = newName2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.ADRESSE__NAME2, oldName2, name2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName3()
	{
		return name3;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName3(String newName3)
	{
		String oldName3 = name3;
		name3 = newName3;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.ADRESSE__NAME3, oldName3, name3));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getStrasse()
	{
		return strasse;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStrasse(String newStrasse)
	{
		String oldStrasse = strasse;
		strasse = newStrasse;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.ADRESSE__STRASSE, oldStrasse, strasse));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPlz()
	{
		return plz;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPlz(String newPlz)
	{
		String oldPlz = plz;
		plz = newPlz;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.ADRESSE__PLZ, oldPlz, plz));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getOrt()
	{
		return ort;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOrt(String newOrt)
	{
		String oldOrt = ort;
		ort = newOrt;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.ADRESSE__ORT, oldOrt, ort));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AddressType getType()
	{
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(AddressType newType)
	{
		AddressType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.ADRESSE__TYPE, oldType, type));
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
			case AddressPackage.ADRESSE__NAME:
				return getName();
			case AddressPackage.ADRESSE__NAME2:
				return getName2();
			case AddressPackage.ADRESSE__NAME3:
				return getName3();
			case AddressPackage.ADRESSE__STRASSE:
				return getStrasse();
			case AddressPackage.ADRESSE__PLZ:
				return getPlz();
			case AddressPackage.ADRESSE__ORT:
				return getOrt();
			case AddressPackage.ADRESSE__TYPE:
				return getType();
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
			case AddressPackage.ADRESSE__NAME:
				setName((String)newValue);
				return;
			case AddressPackage.ADRESSE__NAME2:
				setName2((String)newValue);
				return;
			case AddressPackage.ADRESSE__NAME3:
				setName3((String)newValue);
				return;
			case AddressPackage.ADRESSE__STRASSE:
				setStrasse((String)newValue);
				return;
			case AddressPackage.ADRESSE__PLZ:
				setPlz((String)newValue);
				return;
			case AddressPackage.ADRESSE__ORT:
				setOrt((String)newValue);
				return;
			case AddressPackage.ADRESSE__TYPE:
				setType((AddressType)newValue);
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
			case AddressPackage.ADRESSE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AddressPackage.ADRESSE__NAME2:
				setName2(NAME2_EDEFAULT);
				return;
			case AddressPackage.ADRESSE__NAME3:
				setName3(NAME3_EDEFAULT);
				return;
			case AddressPackage.ADRESSE__STRASSE:
				setStrasse(STRASSE_EDEFAULT);
				return;
			case AddressPackage.ADRESSE__PLZ:
				setPlz(PLZ_EDEFAULT);
				return;
			case AddressPackage.ADRESSE__ORT:
				setOrt(ORT_EDEFAULT);
				return;
			case AddressPackage.ADRESSE__TYPE:
				setType(TYPE_EDEFAULT);
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
			case AddressPackage.ADRESSE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AddressPackage.ADRESSE__NAME2:
				return NAME2_EDEFAULT == null ? name2 != null : !NAME2_EDEFAULT.equals(name2);
			case AddressPackage.ADRESSE__NAME3:
				return NAME3_EDEFAULT == null ? name3 != null : !NAME3_EDEFAULT.equals(name3);
			case AddressPackage.ADRESSE__STRASSE:
				return STRASSE_EDEFAULT == null ? strasse != null : !STRASSE_EDEFAULT.equals(strasse);
			case AddressPackage.ADRESSE__PLZ:
				return PLZ_EDEFAULT == null ? plz != null : !PLZ_EDEFAULT.equals(plz);
			case AddressPackage.ADRESSE__ORT:
				return ORT_EDEFAULT == null ? ort != null : !ORT_EDEFAULT.equals(ort);
			case AddressPackage.ADRESSE__TYPE:
				return type != TYPE_EDEFAULT;
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
		result.append(", name2: ");
		result.append(name2);
		result.append(", name3: ");
		result.append(name3);
		result.append(", strasse: ");
		result.append(strasse);
		result.append(", plz: ");
		result.append(plz);
		result.append(", ort: ");
		result.append(ort);
		result.append(", type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}

} //AdresseImpl
