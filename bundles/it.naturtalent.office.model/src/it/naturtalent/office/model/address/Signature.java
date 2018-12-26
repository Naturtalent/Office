/**
 */
package it.naturtalent.office.model.address;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Signature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.Signature#getName <em>Name</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.Signature#getGreeting <em>Greeting</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.Signature#getSigner <em>Signer</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.Signature#getCosigner <em>Cosigner</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.Signature#getContext <em>Context</em>}</li>
 * </ul>
 *
 * @see it.naturtalent.office.model.address.AddressPackage#getSignature()
 * @model
 * @generated
 */
public interface Signature extends EObject
{
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see it.naturtalent.office.model.address.AddressPackage#getSignature_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link it.naturtalent.office.model.address.Signature#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Greeting</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Greeting</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Greeting</em>' attribute.
	 * @see #setGreeting(String)
	 * @see it.naturtalent.office.model.address.AddressPackage#getSignature_Greeting()
	 * @model
	 * @generated
	 */
	String getGreeting();

	/**
	 * Sets the value of the '{@link it.naturtalent.office.model.address.Signature#getGreeting <em>Greeting</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Greeting</em>' attribute.
	 * @see #getGreeting()
	 * @generated
	 */
	void setGreeting(String value);

	/**
	 * Returns the value of the '<em><b>Signer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Signer</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signer</em>' attribute.
	 * @see #setSigner(String)
	 * @see it.naturtalent.office.model.address.AddressPackage#getSignature_Signer()
	 * @model
	 * @generated
	 */
	String getSigner();

	/**
	 * Sets the value of the '{@link it.naturtalent.office.model.address.Signature#getSigner <em>Signer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Signer</em>' attribute.
	 * @see #getSigner()
	 * @generated
	 */
	void setSigner(String value);

	/**
	 * Returns the value of the '<em><b>Cosigner</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cosigner</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cosigner</em>' attribute.
	 * @see #setCosigner(String)
	 * @see it.naturtalent.office.model.address.AddressPackage#getSignature_Cosigner()
	 * @model
	 * @generated
	 */
	String getCosigner();

	/**
	 * Sets the value of the '{@link it.naturtalent.office.model.address.Signature#getCosigner <em>Cosigner</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cosigner</em>' attribute.
	 * @see #getCosigner()
	 * @generated
	 */
	void setCosigner(String value);

	/**
	 * Returns the value of the '<em><b>Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Context</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context</em>' attribute.
	 * @see #setContext(String)
	 * @see it.naturtalent.office.model.address.AddressPackage#getSignature_Context()
	 * @model
	 * @generated
	 */
	String getContext();

	/**
	 * Sets the value of the '{@link it.naturtalent.office.model.address.Signature#getContext <em>Context</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Context</em>' attribute.
	 * @see #getContext()
	 * @generated
	 */
	void setContext(String value);

} // Signature
