/**
 */
package it.naturtalent.office.model.address.util;

import it.naturtalent.office.model.address.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see it.naturtalent.office.model.address.AddressPackage
 * @generated
 */
public class AddressSwitch<T> extends Switch<T>
{
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static AddressPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddressSwitch()
	{
		if (modelPackage == null)
		{
			modelPackage = AddressPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage)
	{
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject)
	{
		switch (classifierID)
		{
			case AddressPackage.ABSENDER:
			{
				Absender absender = (Absender)theEObject;
				T result = caseAbsender(absender);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AddressPackage.ADRESSE:
			{
				Adresse adresse = (Adresse)theEObject;
				T result = caseAdresse(adresse);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AddressPackage.EMPFAENGER:
			{
				Empfaenger empfaenger = (Empfaenger)theEObject;
				T result = caseEmpfaenger(empfaenger);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AddressPackage.RECEIVERS:
			{
				Receivers receivers = (Receivers)theEObject;
				T result = caseReceivers(receivers);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AddressPackage.KONTAKT:
			{
				Kontakt kontakt = (Kontakt)theEObject;
				T result = caseKontakt(kontakt);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AddressPackage.KONTAKTE:
			{
				Kontakte kontakte = (Kontakte)theEObject;
				T result = caseKontakte(kontakte);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AddressPackage.NT_PROJEKT_KONTAKTE:
			{
				NtProjektKontakte ntProjektKontakte = (NtProjektKontakte)theEObject;
				T result = caseNtProjektKontakte(ntProjektKontakte);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AddressPackage.FOOT_NOTE_ITEM:
			{
				FootNoteItem footNoteItem = (FootNoteItem)theEObject;
				T result = caseFootNoteItem(footNoteItem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AddressPackage.FOOT_NOTE:
			{
				FootNote footNote = (FootNote)theEObject;
				T result = caseFootNote(footNote);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AddressPackage.FOOTE_NOTE_SET:
			{
				FooteNoteSet footeNoteSet = (FooteNoteSet)theEObject;
				T result = caseFooteNoteSet(footeNoteSet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AddressPackage.REFERENZ:
			{
				Referenz referenz = (Referenz)theEObject;
				T result = caseReferenz(referenz);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AddressPackage.REFERENZEN:
			{
				Referenzen referenzen = (Referenzen)theEObject;
				T result = caseReferenzen(referenzen);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AddressPackage.SENDER:
			{
				Sender sender = (Sender)theEObject;
				T result = caseSender(sender);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AddressPackage.SIGNATURE:
			{
				Signature signature = (Signature)theEObject;
				T result = caseSignature(signature);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AddressPackage.SIGNATURE_SET:
			{
				SignatureSet signatureSet = (SignatureSet)theEObject;
				T result = caseSignatureSet(signatureSet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Adresse</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Adresse</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdresse(Adresse object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Referenz</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Referenz</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReferenz(Referenz object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Referenzen</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Referenzen</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReferenzen(Referenzen object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Empfaenger</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Empfaenger</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEmpfaenger(Empfaenger object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Receivers</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Receivers</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReceivers(Receivers object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Kontakt</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Kontakt</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKontakt(Kontakt object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Kontakte</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Kontakte</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKontakte(Kontakte object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Nt Projekt Kontakte</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Nt Projekt Kontakte</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNtProjektKontakte(NtProjektKontakte object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Foot Note Item</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Foot Note Item</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFootNoteItem(FootNoteItem object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Foot Note</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Foot Note</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFootNote(FootNote object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Foote Note Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Foote Note Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFooteNoteSet(FooteNoteSet object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sender</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sender</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSender(Sender object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Signature</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Signature</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSignature(Signature object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Signature Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Signature Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSignatureSet(SignatureSet object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Absender</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Absender</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbsender(Absender object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object)
	{
		return null;
	}

} //AddressSwitch
