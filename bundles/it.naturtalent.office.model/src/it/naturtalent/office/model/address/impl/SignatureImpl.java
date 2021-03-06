/**
 */
package it.naturtalent.office.model.address.impl;

import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Signature;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Signature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.impl.SignatureImpl#getName <em>Name</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.SignatureImpl#getGreeting <em>Greeting</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.SignatureImpl#getSigner <em>Signer</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.SignatureImpl#getCosigner <em>Cosigner</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.SignatureImpl#getContext <em>Context</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.SignatureImpl#getStatus1 <em>Status1</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.impl.SignatureImpl#getStatus2 <em>Status2</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SignatureImpl extends MinimalEObjectImpl.Container implements Signature
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
	 * The default value of the '{@link #getGreeting() <em>Greeting</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGreeting()
	 * @generated
	 * @ordered
	 */
	protected static final String GREETING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGreeting() <em>Greeting</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGreeting()
	 * @generated
	 * @ordered
	 */
	protected String greeting = GREETING_EDEFAULT;

	/**
	 * The default value of the '{@link #getSigner() <em>Signer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSigner()
	 * @generated
	 * @ordered
	 */
	protected static final String SIGNER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSigner() <em>Signer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSigner()
	 * @generated
	 * @ordered
	 */
	protected String signer = SIGNER_EDEFAULT;

	/**
	 * The default value of the '{@link #getCosigner() <em>Cosigner</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCosigner()
	 * @generated
	 * @ordered
	 */
	protected static final String COSIGNER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCosigner() <em>Cosigner</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCosigner()
	 * @generated
	 * @ordered
	 */
	protected String cosigner = COSIGNER_EDEFAULT;

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
	 * The default value of the '{@link #getStatus1() <em>Status1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus1()
	 * @generated
	 * @ordered
	 */
	protected static final String STATUS1_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStatus1() <em>Status1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus1()
	 * @generated
	 * @ordered
	 */
	protected String status1 = STATUS1_EDEFAULT;

	/**
	 * The default value of the '{@link #getStatus2() <em>Status2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus2()
	 * @generated
	 * @ordered
	 */
	protected static final String STATUS2_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStatus2() <em>Status2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus2()
	 * @generated
	 * @ordered
	 */
	protected String status2 = STATUS2_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SignatureImpl()
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
		return AddressPackage.Literals.SIGNATURE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.SIGNATURE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getGreeting()
	{
		return greeting;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGreeting(String newGreeting)
	{
		String oldGreeting = greeting;
		greeting = newGreeting;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.SIGNATURE__GREETING, oldGreeting, greeting));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSigner()
	{
		return signer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSigner(String newSigner)
	{
		String oldSigner = signer;
		signer = newSigner;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.SIGNATURE__SIGNER, oldSigner, signer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCosigner()
	{
		return cosigner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCosigner(String newCosigner)
	{
		String oldCosigner = cosigner;
		cosigner = newCosigner;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.SIGNATURE__COSIGNER, oldCosigner, cosigner));
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
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.SIGNATURE__CONTEXT, oldContext, context));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getStatus1()
	{
		return status1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStatus1(String newStatus1)
	{
		String oldStatus1 = status1;
		status1 = newStatus1;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.SIGNATURE__STATUS1, oldStatus1, status1));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getStatus2()
	{
		return status2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStatus2(String newStatus2)
	{
		String oldStatus2 = status2;
		status2 = newStatus2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.SIGNATURE__STATUS2, oldStatus2, status2));
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
			case AddressPackage.SIGNATURE__NAME:
				return getName();
			case AddressPackage.SIGNATURE__GREETING:
				return getGreeting();
			case AddressPackage.SIGNATURE__SIGNER:
				return getSigner();
			case AddressPackage.SIGNATURE__COSIGNER:
				return getCosigner();
			case AddressPackage.SIGNATURE__CONTEXT:
				return getContext();
			case AddressPackage.SIGNATURE__STATUS1:
				return getStatus1();
			case AddressPackage.SIGNATURE__STATUS2:
				return getStatus2();
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
			case AddressPackage.SIGNATURE__NAME:
				setName((String)newValue);
				return;
			case AddressPackage.SIGNATURE__GREETING:
				setGreeting((String)newValue);
				return;
			case AddressPackage.SIGNATURE__SIGNER:
				setSigner((String)newValue);
				return;
			case AddressPackage.SIGNATURE__COSIGNER:
				setCosigner((String)newValue);
				return;
			case AddressPackage.SIGNATURE__CONTEXT:
				setContext((String)newValue);
				return;
			case AddressPackage.SIGNATURE__STATUS1:
				setStatus1((String)newValue);
				return;
			case AddressPackage.SIGNATURE__STATUS2:
				setStatus2((String)newValue);
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
			case AddressPackage.SIGNATURE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AddressPackage.SIGNATURE__GREETING:
				setGreeting(GREETING_EDEFAULT);
				return;
			case AddressPackage.SIGNATURE__SIGNER:
				setSigner(SIGNER_EDEFAULT);
				return;
			case AddressPackage.SIGNATURE__COSIGNER:
				setCosigner(COSIGNER_EDEFAULT);
				return;
			case AddressPackage.SIGNATURE__CONTEXT:
				setContext(CONTEXT_EDEFAULT);
				return;
			case AddressPackage.SIGNATURE__STATUS1:
				setStatus1(STATUS1_EDEFAULT);
				return;
			case AddressPackage.SIGNATURE__STATUS2:
				setStatus2(STATUS2_EDEFAULT);
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
			case AddressPackage.SIGNATURE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AddressPackage.SIGNATURE__GREETING:
				return GREETING_EDEFAULT == null ? greeting != null : !GREETING_EDEFAULT.equals(greeting);
			case AddressPackage.SIGNATURE__SIGNER:
				return SIGNER_EDEFAULT == null ? signer != null : !SIGNER_EDEFAULT.equals(signer);
			case AddressPackage.SIGNATURE__COSIGNER:
				return COSIGNER_EDEFAULT == null ? cosigner != null : !COSIGNER_EDEFAULT.equals(cosigner);
			case AddressPackage.SIGNATURE__CONTEXT:
				return CONTEXT_EDEFAULT == null ? context != null : !CONTEXT_EDEFAULT.equals(context);
			case AddressPackage.SIGNATURE__STATUS1:
				return STATUS1_EDEFAULT == null ? status1 != null : !STATUS1_EDEFAULT.equals(status1);
			case AddressPackage.SIGNATURE__STATUS2:
				return STATUS2_EDEFAULT == null ? status2 != null : !STATUS2_EDEFAULT.equals(status2);
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
		result.append(", greeting: ");
		result.append(greeting);
		result.append(", signer: ");
		result.append(signer);
		result.append(", cosigner: ");
		result.append(cosigner);
		result.append(", context: ");
		result.append(context);
		result.append(", status1: ");
		result.append(status1);
		result.append(", status2: ");
		result.append(status2);
		result.append(')');
		return result.toString();
	}

} //SignatureImpl
