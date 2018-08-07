/**
 */
package it.naturtalent.office.model.address.util;

import it.naturtalent.office.model.address.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see it.naturtalent.office.model.address.AddressPackage
 * @generated
 */
public class AddressAdapterFactory extends AdapterFactoryImpl
{
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static AddressPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddressAdapterFactory()
	{
		if (modelPackage == null)
		{
			modelPackage = AddressPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object)
	{
		if (object == modelPackage)
		{
			return true;
		}
		if (object instanceof EObject)
		{
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AddressSwitch<Adapter> modelSwitch =
		new AddressSwitch<Adapter>()
		{
			@Override
			public Adapter caseSenders(Senders object)
			{
				return createSendersAdapter();
			}
			@Override
			public Adapter caseAbsender(Absender object)
			{
				return createAbsenderAdapter();
			}
			@Override
			public Adapter caseAdresse(Adresse object)
			{
				return createAdresseAdapter();
			}
			@Override
			public Adapter caseEmpfaenger(Empfaenger object)
			{
				return createEmpfaengerAdapter();
			}
			@Override
			public Adapter caseReceivers(Receivers object)
			{
				return createReceiversAdapter();
			}
			@Override
			public Adapter caseKontakt(Kontakt object)
			{
				return createKontaktAdapter();
			}
			@Override
			public Adapter caseKontakte(Kontakte object)
			{
				return createKontakteAdapter();
			}
			@Override
			public Adapter caseNtProjektKontakte(NtProjektKontakte object)
			{
				return createNtProjektKontakteAdapter();
			}
			@Override
			public Adapter caseFootNote(FootNote object)
			{
				return createFootNoteAdapter();
			}
			@Override
			public Adapter caseFootNotes(FootNotes object)
			{
				return createFootNotesAdapter();
			}
			@Override
			public Adapter caseFooterClass(FooterClass object)
			{
				return createFooterClassAdapter();
			}
			@Override
			public Adapter caseReferenz(Referenz object)
			{
				return createReferenzAdapter();
			}
			@Override
			public Adapter caseReferenzSet(ReferenzSet object)
			{
				return createReferenzSetAdapter();
			}
			@Override
			public Adapter caseReferenzenClass(ReferenzenClass object)
			{
				return createReferenzenClassAdapter();
			}
			@Override
			public Adapter caseReferenzGruppe(ReferenzGruppe object)
			{
				return createReferenzGruppeAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object)
			{
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target)
	{
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link it.naturtalent.office.model.address.Adresse <em>Adresse</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see it.naturtalent.office.model.address.Adresse
	 * @generated
	 */
	public Adapter createAdresseAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link it.naturtalent.office.model.address.Referenz <em>Referenz</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see it.naturtalent.office.model.address.Referenz
	 * @generated
	 */
	public Adapter createReferenzAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link it.naturtalent.office.model.address.Empfaenger <em>Empfaenger</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see it.naturtalent.office.model.address.Empfaenger
	 * @generated
	 */
	public Adapter createEmpfaengerAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link it.naturtalent.office.model.address.Receivers <em>Receivers</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see it.naturtalent.office.model.address.Receivers
	 * @generated
	 */
	public Adapter createReceiversAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link it.naturtalent.office.model.address.Kontakt <em>Kontakt</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see it.naturtalent.office.model.address.Kontakt
	 * @generated
	 */
	public Adapter createKontaktAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link it.naturtalent.office.model.address.Kontakte <em>Kontakte</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see it.naturtalent.office.model.address.Kontakte
	 * @generated
	 */
	public Adapter createKontakteAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link it.naturtalent.office.model.address.NtProjektKontakte <em>Nt Projekt Kontakte</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see it.naturtalent.office.model.address.NtProjektKontakte
	 * @generated
	 */
	public Adapter createNtProjektKontakteAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link it.naturtalent.office.model.address.FootNote <em>Foot Note</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see it.naturtalent.office.model.address.FootNote
	 * @generated
	 */
	public Adapter createFootNoteAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link it.naturtalent.office.model.address.FootNotes <em>Foot Notes</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see it.naturtalent.office.model.address.FootNotes
	 * @generated
	 */
	public Adapter createFootNotesAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link it.naturtalent.office.model.address.FooterClass <em>Footer Class</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see it.naturtalent.office.model.address.FooterClass
	 * @generated
	 */
	public Adapter createFooterClassAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link it.naturtalent.office.model.address.ReferenzSet <em>Referenz Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see it.naturtalent.office.model.address.ReferenzSet
	 * @generated
	 */
	public Adapter createReferenzSetAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link it.naturtalent.office.model.address.ReferenzenClass <em>Referenzen Class</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see it.naturtalent.office.model.address.ReferenzenClass
	 * @generated
	 */
	public Adapter createReferenzenClassAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link it.naturtalent.office.model.address.ReferenzGruppe <em>Referenz Gruppe</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see it.naturtalent.office.model.address.ReferenzGruppe
	 * @generated
	 */
	public Adapter createReferenzGruppeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link it.naturtalent.office.model.address.Absender <em>Absender</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see it.naturtalent.office.model.address.Absender
	 * @generated
	 */
	public Adapter createAbsenderAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link it.naturtalent.office.model.address.Senders <em>Senders</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see it.naturtalent.office.model.address.Senders
	 * @generated
	 */
	public Adapter createSendersAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter()
	{
		return null;
	}

} //AddressAdapterFactory