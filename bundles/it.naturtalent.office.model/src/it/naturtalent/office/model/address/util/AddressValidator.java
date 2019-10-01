/**
 */
package it.naturtalent.office.model.address.util;

import it.naturtalent.office.model.address.*;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see it.naturtalent.office.model.address.AddressPackage
 * @generated
 */
public class AddressValidator extends EObjectValidator
{
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final AddressValidator INSTANCE = new AddressValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "it.naturtalent.office.model.address";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddressValidator()
	{
		super();
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage()
	{
	  return AddressPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		switch (classifierID)
		{
			case AddressPackage.ABSENDER:
				return validateAbsender((Absender)value, diagnostics, context);
			case AddressPackage.ADRESSE:
				return validateAdresse((Adresse)value, diagnostics, context);
			case AddressPackage.EMPFAENGER:
				return validateEmpfaenger((Empfaenger)value, diagnostics, context);
			case AddressPackage.RECEIVERS:
				return validateReceivers((Receivers)value, diagnostics, context);
			case AddressPackage.KONTAKT:
				return validateKontakt((Kontakt)value, diagnostics, context);
			case AddressPackage.KONTAKTE:
				return validateKontakte((Kontakte)value, diagnostics, context);
			case AddressPackage.NT_PROJEKT_KONTAKTE:
				return validateNtProjektKontakte((NtProjektKontakte)value, diagnostics, context);
			case AddressPackage.FOOT_NOTE_ITEM:
				return validateFootNoteItem((FootNoteItem)value, diagnostics, context);
			case AddressPackage.FOOT_NOTE:
				return validateFootNote((FootNote)value, diagnostics, context);
			case AddressPackage.REFERENZ:
				return validateReferenz((Referenz)value, diagnostics, context);
			case AddressPackage.REFERENZEN:
				return validateReferenzen((Referenzen)value, diagnostics, context);
			case AddressPackage.SENDER:
				return validateSender((Sender)value, diagnostics, context);
			case AddressPackage.SIGNATURE:
				return validateSignature((Signature)value, diagnostics, context);
			case AddressPackage.SIGNATURE_SET:
				return validateSignatureSet((SignatureSet)value, diagnostics, context);
			case AddressPackage.ADDRESS_TYPE:
				return validateAddressType((AddressType)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAbsender(Absender absender, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint(absender, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAdresse(Adresse adresse, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint(adresse, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEmpfaenger(Empfaenger empfaenger, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint(empfaenger, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateReceivers(Receivers receivers, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint(receivers, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateKontakt(Kontakt kontakt, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint(kontakt, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateKontakte(Kontakte kontakte, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint(kontakte, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNtProjektKontakte(NtProjektKontakte ntProjektKontakte, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint(ntProjektKontakte, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFootNoteItem(FootNoteItem footNoteItem, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint(footNoteItem, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFootNote(FootNote footNote, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint(footNote, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateReferenz(Referenz referenz, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint(referenz, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateReferenzen(Referenzen referenzen, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint(referenzen, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSender(Sender sender, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint(sender, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSignature(Signature signature, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint(signature, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSignatureSet(SignatureSet signatureSet, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint(signatureSet, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAddressType(AddressType addressType, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return true;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator()
	{
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //AddressValidator
