/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.sweep.impl;

import eu.geclipse.jsdl.model.sweep.*;

import org.eclipse.emf.ecore.EClass;
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
public class SweepFactoryImpl extends EFactoryImpl implements SweepFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static SweepFactory init()
  {
    try
    {
      SweepFactory theSweepFactory = (SweepFactory)EPackage.Registry.INSTANCE.getEFactory("http://schemas.ogf.org/jsdl/2007/01/jsdl-sweep"); 
      if (theSweepFactory != null)
      {
        return theSweepFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new SweepFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SweepFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case SweepPackage.ASSIGNMENT_TYPE: return createAssignmentType();
      case SweepPackage.DOCUMENT_ROOT: return createDocumentRoot();
      case SweepPackage.SWEEP_TYPE: return createSweepType();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AssignmentType createAssignmentType()
  {
    AssignmentTypeImpl assignmentType = new AssignmentTypeImpl();
    return assignmentType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DocumentRoot createDocumentRoot()
  {
    DocumentRootImpl documentRoot = new DocumentRootImpl();
    return documentRoot;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SweepType createSweepType()
  {
    SweepTypeImpl sweepType = new SweepTypeImpl();
    return sweepType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SweepPackage getSweepPackage()
  {
    return (SweepPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  public static SweepPackage getPackage()
  {
    return SweepPackage.eINSTANCE;
  }

} //SweepFactoryImpl
