/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.sweep.impl;

import eu.geclipse.jsdl.model.base.JsdlPackage;

import eu.geclipse.jsdl.model.base.impl.JsdlPackageImpl;

import eu.geclipse.jsdl.model.functions.FunctionsPackage;

import eu.geclipse.jsdl.model.functions.impl.FunctionsPackageImpl;

import eu.geclipse.jsdl.model.posix.PosixPackage;

import eu.geclipse.jsdl.model.posix.impl.PosixPackageImpl;

import eu.geclipse.jsdl.model.sweep.AssignmentType;
import eu.geclipse.jsdl.model.sweep.DocumentRoot;
import eu.geclipse.jsdl.model.sweep.SweepFactory;
import eu.geclipse.jsdl.model.sweep.SweepPackage;
import eu.geclipse.jsdl.model.sweep.SweepType;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SweepPackageImpl extends EPackageImpl implements SweepPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass assignmentTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass documentRootEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass sweepTypeEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see eu.geclipse.jsdl.model.sweep.SweepPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private SweepPackageImpl()
  {
    super(eNS_URI, SweepFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this
   * model, and for any others upon which it depends.  Simple
   * dependencies are satisfied by calling this method on all
   * dependent packages before doing anything else.  This method drives
   * initialization for interdependent packages directly, in parallel
   * with this package, itself.
   * <p>Of this package and its interdependencies, all packages which
   * have not yet been registered by their URI values are first created
   * and registered.  The packages are then initialized in two steps:
   * meta-model objects for all of the packages are created before any
   * are initialized, since one package's meta-model objects may refer to
   * those of another.
   * <p>Invocation of this method will not affect any packages that have
   * already been initialized.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static SweepPackage init()
  {
    if (isInited) return (SweepPackage)EPackage.Registry.INSTANCE.getEPackage(SweepPackage.eNS_URI);

    // Obtain or create and register package
    SweepPackageImpl theSweepPackage = (SweepPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof SweepPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new SweepPackageImpl());

    isInited = true;

    // Initialize simple dependencies
    XMLTypePackage.eINSTANCE.eClass();

    // Obtain or create and register interdependencies
    JsdlPackageImpl theJsdlPackage = (JsdlPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(JsdlPackage.eNS_URI) instanceof JsdlPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(JsdlPackage.eNS_URI) : JsdlPackage.eINSTANCE);
    PosixPackageImpl thePosixPackage = (PosixPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(PosixPackage.eNS_URI) instanceof PosixPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(PosixPackage.eNS_URI) : PosixPackage.eINSTANCE);
    FunctionsPackageImpl theFunctionsPackage = (FunctionsPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(FunctionsPackage.eNS_URI) instanceof FunctionsPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(FunctionsPackage.eNS_URI) : FunctionsPackage.eINSTANCE);

    // Create package meta-data objects
    theSweepPackage.createPackageContents();
    theJsdlPackage.createPackageContents();
    thePosixPackage.createPackageContents();
    theFunctionsPackage.createPackageContents();

    // Initialize created meta-data
    theSweepPackage.initializePackageContents();
    theJsdlPackage.initializePackageContents();
    thePosixPackage.initializePackageContents();
    theFunctionsPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theSweepPackage.freeze();

    return theSweepPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAssignmentType()
  {
    return assignmentTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAssignmentType_Parameter()
  {
    return (EAttribute)assignmentTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAssignmentType_FunctionGroup()
  {
    return (EAttribute)assignmentTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getAssignmentType_Function()
  {
    return (EReference)assignmentTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getDocumentRoot()
  {
    return documentRootEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDocumentRoot_Mixed()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDocumentRoot_XMLNSPrefixMap()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDocumentRoot_XSISchemaLocation()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDocumentRoot_Assignment()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDocumentRoot_Function()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDocumentRoot_Parameter()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDocumentRoot_Sweep()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSweepType()
  {
    return sweepTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getSweepType_Assignment()
  {
    return (EReference)sweepTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getSweepType_Sweep()
  {
    return (EReference)sweepTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SweepFactory getSweepFactory()
  {
    return (SweepFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents()
  {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    assignmentTypeEClass = createEClass(ASSIGNMENT_TYPE);
    createEAttribute(assignmentTypeEClass, ASSIGNMENT_TYPE__PARAMETER);
    createEAttribute(assignmentTypeEClass, ASSIGNMENT_TYPE__FUNCTION_GROUP);
    createEReference(assignmentTypeEClass, ASSIGNMENT_TYPE__FUNCTION);

    documentRootEClass = createEClass(DOCUMENT_ROOT);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
    createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
    createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
    createEReference(documentRootEClass, DOCUMENT_ROOT__ASSIGNMENT);
    createEReference(documentRootEClass, DOCUMENT_ROOT__FUNCTION);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__PARAMETER);
    createEReference(documentRootEClass, DOCUMENT_ROOT__SWEEP);

    sweepTypeEClass = createEClass(SWEEP_TYPE);
    createEReference(sweepTypeEClass, SWEEP_TYPE__ASSIGNMENT);
    createEReference(sweepTypeEClass, SWEEP_TYPE__SWEEP);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

    // Add supertypes to classes

    // Initialize classes and features; add operations and parameters
    initEClass(assignmentTypeEClass, AssignmentType.class, "AssignmentType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getAssignmentType_Parameter(), theXMLTypePackage.getString(), "parameter", null, 1, -1, AssignmentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAssignmentType_FunctionGroup(), ecorePackage.getEFeatureMapEntry(), "functionGroup", null, 1, 1, AssignmentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getAssignmentType_Function(), ecorePackage.getEObject(), null, "function", null, 1, 1, AssignmentType.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

    initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_Assignment(), this.getAssignmentType(), null, "assignment", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_Function(), ecorePackage.getEObject(), null, "function", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_Parameter(), theXMLTypePackage.getString(), "parameter", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_Sweep(), this.getSweepType(), null, "sweep", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

    initEClass(sweepTypeEClass, SweepType.class, "SweepType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getSweepType_Assignment(), this.getAssignmentType(), null, "assignment", null, 1, -1, SweepType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getSweepType_Sweep(), this.getSweepType(), null, "sweep", null, 0, -1, SweepType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);

    // Create annotations
    // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
    createExtendedMetaDataAnnotations();
  }

  /**
   * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void createExtendedMetaDataAnnotations()
  {
    String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
    addAnnotation
      (assignmentTypeEClass, 
       source, 
       new String[] 
       {
       "name", "Assignment_._type",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getAssignmentType_Parameter(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Parameter",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getAssignmentType_FunctionGroup(), 
       source, 
       new String[] 
       {
       "kind", "group",
       "name", "Function:group",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getAssignmentType_Function(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Function",
       "namespace", "##targetNamespace",
       "group", "Function:group"
       });		
    addAnnotation
      (documentRootEClass, 
       source, 
       new String[] 
       {
       "name", "",
       "kind", "mixed"
       });		
    addAnnotation
      (getDocumentRoot_Mixed(), 
       source, 
       new String[] 
       {
       "kind", "elementWildcard",
       "name", ":mixed"
       });		
    addAnnotation
      (getDocumentRoot_XMLNSPrefixMap(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "xmlns:prefix"
       });		
    addAnnotation
      (getDocumentRoot_XSISchemaLocation(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "xsi:schemaLocation"
       });		
    addAnnotation
      (getDocumentRoot_Assignment(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Assignment",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_Function(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Function",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_Parameter(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Parameter",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_Sweep(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Sweep",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (sweepTypeEClass, 
       source, 
       new String[] 
       {
       "name", "Sweep_Type",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getSweepType_Assignment(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Assignment",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getSweepType_Sweep(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Sweep",
       "namespace", "##targetNamespace"
       });
  }

} //SweepPackageImpl
