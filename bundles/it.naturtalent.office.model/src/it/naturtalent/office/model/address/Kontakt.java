/**
 */
package it.naturtalent.office.model.address;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Kontakt</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.Kontakt#getName <em>Name</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.Kontakt#getAdresse <em>Adresse</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.Kontakt#getKommunikation <em>Kommunikation</em>}</li>
 * </ul>
 *
 * @see it.naturtalent.office.model.address.AddressPackage#getKontakt()
 * @model
 * @generated
 */
public interface Kontakt extends EObject
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
	 * @see it.naturtalent.office.model.address.AddressPackage#getKontakt_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link it.naturtalent.office.model.address.Kontakt#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Adresse</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Adresse</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Adresse</em>' containment reference.
	 * @see #setAdresse(Adresse)
	 * @see it.naturtalent.office.model.address.AddressPackage#getKontakt_Adresse()
	 * @model containment="true"
	 * @generated
	 */
	Adresse getAdresse();

	/**
	 * Sets the value of the '{@link it.naturtalent.office.model.address.Kontakt#getAdresse <em>Adresse</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Adresse</em>' containment reference.
	 * @see #getAdresse()
	 * @generated
	 */
	void setAdresse(Adresse value);

	/**
	 * Returns the value of the '<em><b>Kommunikation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kommunikation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kommunikation</em>' attribute.
	 * @see #setKommunikation(String)
	 * @see it.naturtalent.office.model.address.AddressPackage#getKontakt_Kommunikation()
	 * @model
	 * @generated
	 */
	String getKommunikation();

	/**
	 * Sets the value of the '{@link it.naturtalent.office.model.address.Kontakt#getKommunikation <em>Kommunikation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kommunikation</em>' attribute.
	 * @see #getKommunikation()
	 * @generated
	 */
	void setKommunikation(String value);

} // Kontakt
