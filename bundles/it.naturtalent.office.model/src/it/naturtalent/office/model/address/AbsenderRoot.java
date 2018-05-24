/**
 */
package it.naturtalent.office.model.address;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Absender Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.AbsenderRoot#getAbsender <em>Absender</em>}</li>
 * </ul>
 *
 * @see it.naturtalent.office.model.address.AddressPackage#getAbsenderRoot()
 * @model
 * @generated
 */
public interface AbsenderRoot extends EObject
{
	/**
	 * Returns the value of the '<em><b>Absender</b></em>' containment reference list.
	 * The list contents are of type {@link it.naturtalent.office.model.address.Absender}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Absender</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Absender</em>' containment reference list.
	 * @see it.naturtalent.office.model.address.AddressPackage#getAbsenderRoot_Absender()
	 * @model containment="true"
	 * @generated
	 */
	EList<Absender> getAbsender();

} // AbsenderRoot
