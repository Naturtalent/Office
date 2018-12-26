/**
 */
package it.naturtalent.office.model.address.impl;

import it.naturtalent.office.model.address.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AddressFactoryImpl extends EFactoryImpl implements AddressFactory
{
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AddressFactory init()
	{
		try
		{
			AddressFactory theAddressFactory = (AddressFactory)EPackage.Registry.INSTANCE.getEFactory(AddressPackage.eNS_URI);
			if (theAddressFactory != null)
			{
				return theAddressFactory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AddressFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddressFactoryImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass)
	{
		switch (eClass.getClassifierID())
		{
			case AddressPackage.ABSENDER: return createAbsender();
			case AddressPackage.ADRESSE: return createAdresse();
			case AddressPackage.EMPFAENGER: return createEmpfaenger();
			case AddressPackage.RECEIVERS: return createReceivers();
			case AddressPackage.KONTAKT: return createKontakt();
			case AddressPackage.KONTAKTE: return createKontakte();
			case AddressPackage.NT_PROJEKT_KONTAKTE: return createNtProjektKontakte();
			case AddressPackage.FOOT_NOTE_ITEM: return createFootNoteItem();
			case AddressPackage.FOOT_NOTE: return createFootNote();
			case AddressPackage.FOOTE_NOTE_SET: return createFooteNoteSet();
			case AddressPackage.REFERENZ: return createReferenz();
			case AddressPackage.REFERENZ_SET: return createReferenzSet();
			case AddressPackage.REFERENZEN_CLASS: return createReferenzenClass();
			case AddressPackage.REFERENZ_GRUPPE: return createReferenzGruppe();
			case AddressPackage.SENDER: return createSender();
			case AddressPackage.SIGNATURE: return createSignature();
			case AddressPackage.SIGNATURE_SET: return createSignatureSet();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue)
	{
		switch (eDataType.getClassifierID())
		{
			case AddressPackage.ADDRESS_TYPE:
				return createAddressTypeFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue)
	{
		switch (eDataType.getClassifierID())
		{
			case AddressPackage.ADDRESS_TYPE:
				return convertAddressTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Adresse createAdresse()
	{
		AdresseImpl adresse = new AdresseImpl();
		return adresse;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Referenz createReferenz()
	{
		ReferenzImpl referenz = new ReferenzImpl();
		return referenz;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Empfaenger createEmpfaenger()
	{
		EmpfaengerImpl empfaenger = new EmpfaengerImpl();
		return empfaenger;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Receivers createReceivers()
	{
		ReceiversImpl receivers = new ReceiversImpl();
		return receivers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Kontakt createKontakt()
	{
		KontaktImpl kontakt = new KontaktImpl();
		return kontakt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Kontakte createKontakte()
	{
		KontakteImpl kontakte = new KontakteImpl();
		return kontakte;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NtProjektKontakte createNtProjektKontakte()
	{
		NtProjektKontakteImpl ntProjektKontakte = new NtProjektKontakteImpl();
		return ntProjektKontakte;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FootNoteItem createFootNoteItem()
	{
		FootNoteItemImpl footNoteItem = new FootNoteItemImpl();
		return footNoteItem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FootNote createFootNote()
	{
		FootNoteImpl footNote = new FootNoteImpl();
		return footNote;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FooteNoteSet createFooteNoteSet()
	{
		FooteNoteSetImpl footeNoteSet = new FooteNoteSetImpl();
		return footeNoteSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReferenzSet createReferenzSet()
	{
		ReferenzSetImpl referenzSet = new ReferenzSetImpl();
		return referenzSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReferenzenClass createReferenzenClass()
	{
		ReferenzenClassImpl referenzenClass = new ReferenzenClassImpl();
		return referenzenClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReferenzGruppe createReferenzGruppe()
	{
		ReferenzGruppeImpl referenzGruppe = new ReferenzGruppeImpl();
		return referenzGruppe;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Sender createSender()
	{
		SenderImpl sender = new SenderImpl();
		return sender;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Signature createSignature()
	{
		SignatureImpl signature = new SignatureImpl();
		return signature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SignatureSet createSignatureSet()
	{
		SignatureSetImpl signatureSet = new SignatureSetImpl();
		return signatureSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Absender createAbsender()
	{
		AbsenderImpl absender = new AbsenderImpl();
		return absender;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddressType createAddressTypeFromString(EDataType eDataType, String initialValue)
	{
		AddressType result = AddressType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAddressTypeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddressPackage getAddressPackage()
	{
		return (AddressPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static AddressPackage getPackage()
	{
		return AddressPackage.eINSTANCE;
	}

} //AddressFactoryImpl
