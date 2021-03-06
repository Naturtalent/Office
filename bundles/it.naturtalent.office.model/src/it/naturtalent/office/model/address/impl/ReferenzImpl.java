/**
 */
package it.naturtalent.office.model.address.impl;

import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Referenz;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Referenz</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.impl.ReferenzImpl#getName <em>Name</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.ReferenzImpl#getContext <em>Context</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.ReferenzImpl#getReferenz <em>Referenz</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.ReferenzImpl#getReferenz2 <em>Referenz2</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.ReferenzImpl#getReferenz3 <em>Referenz3</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReferenzImpl extends MinimalEObjectImpl.Container implements Referenz
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
	 * The default value of the '{@link #getContext() <em>Context</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContext()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContext() <em>Context</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContext()
	 * @generated
	 * @ordered
	 */
	protected String context = CONTEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getReferenz() <em>Referenz</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferenz()
	 * @generated
	 * @ordered
	 */
	protected static final String REFERENZ_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReferenz() <em>Referenz</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferenz()
	 * @generated
	 * @ordered
	 */
	protected String referenz = REFERENZ_EDEFAULT;

	/**
	 * The default value of the '{@link #getReferenz2() <em>Referenz2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferenz2()
	 * @generated
	 * @ordered
	 */
	protected static final String REFERENZ2_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReferenz2() <em>Referenz2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferenz2()
	 * @generated
	 * @ordered
	 */
	protected String referenz2 = REFERENZ2_EDEFAULT;

	/**
	 * The default value of the '{@link #getReferenz3() <em>Referenz3</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferenz3()
	 * @generated
	 * @ordered
	 */
	protected static final String REFERENZ3_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReferenz3() <em>Referenz3</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferenz3()
	 * @generated
	 * @ordered
	 */
	protected String referenz3 = REFERENZ3_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReferenzImpl()
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
		return AddressPackage.Literals.REFERENZ;
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
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.REFERENZ__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getContext()
	{
		return context;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setContext(String newContext)
	{
		String oldContext = context;
		context = newContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.REFERENZ__CONTEXT, oldContext, context));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getReferenz()
	{
		return referenz;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReferenz(String newReferenz)
	{
		String oldReferenz = referenz;
		referenz = newReferenz;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.REFERENZ__REFERENZ, oldReferenz, referenz));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getReferenz2()
	{
		return referenz2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReferenz2(String newReferenz2)
	{
		String oldReferenz2 = referenz2;
		referenz2 = newReferenz2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.REFERENZ__REFERENZ2, oldReferenz2, referenz2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getReferenz3()
	{
		return referenz3;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReferenz3(String newReferenz3)
	{
		String oldReferenz3 = referenz3;
		referenz3 = newReferenz3;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.REFERENZ__REFERENZ3, oldReferenz3, referenz3));
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
			case AddressPackage.REFERENZ__NAME:
				return getName();
			case AddressPackage.REFERENZ__CONTEXT:
				return getContext();
			case AddressPackage.REFERENZ__REFERENZ:
				return getReferenz();
			case AddressPackage.REFERENZ__REFERENZ2:
				return getReferenz2();
			case AddressPackage.REFERENZ__REFERENZ3:
				return getReferenz3();
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
			case AddressPackage.REFERENZ__NAME:
				setName((String)newValue);
				return;
			case AddressPackage.REFERENZ__CONTEXT:
				setContext((String)newValue);
				return;
			case AddressPackage.REFERENZ__REFERENZ:
				setReferenz((String)newValue);
				return;
			case AddressPackage.REFERENZ__REFERENZ2:
				setReferenz2((String)newValue);
				return;
			case AddressPackage.REFERENZ__REFERENZ3:
				setReferenz3((String)newValue);
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
			case AddressPackage.REFERENZ__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AddressPackage.REFERENZ__CONTEXT:
				setContext(CONTEXT_EDEFAULT);
				return;
			case AddressPackage.REFERENZ__REFERENZ:
				setReferenz(REFERENZ_EDEFAULT);
				return;
			case AddressPackage.REFERENZ__REFERENZ2:
				setReferenz2(REFERENZ2_EDEFAULT);
				return;
			case AddressPackage.REFERENZ__REFERENZ3:
				setReferenz3(REFERENZ3_EDEFAULT);
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
			case AddressPackage.REFERENZ__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AddressPackage.REFERENZ__CONTEXT:
				return CONTEXT_EDEFAULT == null ? context != null : !CONTEXT_EDEFAULT.equals(context);
			case AddressPackage.REFERENZ__REFERENZ:
				return REFERENZ_EDEFAULT == null ? referenz != null : !REFERENZ_EDEFAULT.equals(referenz);
			case AddressPackage.REFERENZ__REFERENZ2:
				return REFERENZ2_EDEFAULT == null ? referenz2 != null : !REFERENZ2_EDEFAULT.equals(referenz2);
			case AddressPackage.REFERENZ__REFERENZ3:
				return REFERENZ3_EDEFAULT == null ? referenz3 != null : !REFERENZ3_EDEFAULT.equals(referenz3);
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
		result.append(", context: ");
		result.append(context);
		result.append(", referenz: ");
		result.append(referenz);
		result.append(", referenz2: ");
		result.append(referenz2);
		result.append(", referenz3: ");
		result.append(referenz3);
		result.append(')');
		return result.toString();
	}

} //ReferenzImpl
