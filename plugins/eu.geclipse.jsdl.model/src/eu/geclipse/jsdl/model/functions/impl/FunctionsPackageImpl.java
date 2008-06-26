/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.functions.impl;

import eu.geclipse.jsdl.model.base.JsdlPackage;

import eu.geclipse.jsdl.model.base.impl.JsdlPackageImpl;

import eu.geclipse.jsdl.model.functions.DocumentRoot;
import eu.geclipse.jsdl.model.functions.ExceptionType;
import eu.geclipse.jsdl.model.functions.FunctionsFactory;
import eu.geclipse.jsdl.model.functions.FunctionsPackage;
import eu.geclipse.jsdl.model.functions.LoopType;
import eu.geclipse.jsdl.model.functions.ValuesType;

import eu.geclipse.jsdl.model.posix.PosixPackage;

import eu.geclipse.jsdl.model.posix.impl.PosixPackageImpl;

import eu.geclipse.jsdl.model.sweep.SweepPackage;

import eu.geclipse.jsdl.model.sweep.impl.SweepPackageImpl;

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
public class FunctionsPackageImpl extends EPackageImpl implements FunctionsPackage
{
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
  private EClass exceptionTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass loopTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass valuesTypeEClass = null;

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
   * @see eu.geclipse.jsdl.model.functions.FunctionsPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private FunctionsPackageImpl()
  {
    super(eNS_URI, FunctionsFactory.eINSTANCE);
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
  public static FunctionsPackage init()
  {
    if (isInited) return (FunctionsPackage)EPackage.Registry.INSTANCE.getEPackage(FunctionsPackage.eNS_URI);

    // Obtain or create and register package
    FunctionsPackageImpl theFunctionsPackage = (FunctionsPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof FunctionsPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new FunctionsPackageImpl());

    isInited = true;

    // Initialize simple dependencies
    XMLTypePackage.eINSTANCE.eClass();

    // Obtain or create and register interdependencies
    JsdlPackageImpl theJsdlPackage = (JsdlPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(JsdlPackage.eNS_URI) instanceof JsdlPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(JsdlPackage.eNS_URI) : JsdlPackage.eINSTANCE);
    PosixPackageImpl thePosixPackage = (PosixPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(PosixPackage.eNS_URI) instanceof PosixPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(PosixPackage.eNS_URI) : PosixPackage.eINSTANCE);
    SweepPackageImpl theSweepPackage = (SweepPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SweepPackage.eNS_URI) instanceof SweepPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SweepPackage.eNS_URI) : SweepPackage.eINSTANCE);

    // Create package meta-data objects
    theFunctionsPackage.createPackageContents();
    theJsdlPackage.createPackageContents();
    thePosixPackage.createPackageContents();
    theSweepPackage.createPackageContents();

    // Initialize created meta-data
    theFunctionsPackage.initializePackageContents();
    theJsdlPackage.initializePackageContents();
    thePosixPackage.initializePackageContents();
    theSweepPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theFunctionsPackage.freeze();

    return theFunctionsPackage;
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
  public EReference getDocumentRoot_Loop()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDocumentRoot_Values()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getExceptionType()
  {
    return exceptionTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getExceptionType_Value()
  {
    return (EAttribute)exceptionTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLoopType()
  {
    return loopTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLoopType_Exception()
  {
    return (EReference)loopTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLoopType_End()
  {
    return (EAttribute)loopTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLoopType_Start()
  {
    return (EAttribute)loopTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLoopType_Step()
  {
    return (EAttribute)loopTypeEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getValuesType()
  {
    return valuesTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getValuesType_Value()
  {
    return (EAttribute)valuesTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FunctionsFactory getFunctionsFactory()
  {
    return (FunctionsFactory)getEFactoryInstance();
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
    documentRootEClass = createEClass(DOCUMENT_ROOT);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
    createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
    createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
    createEReference(documentRootEClass, DOCUMENT_ROOT__LOOP);
    createEReference(documentRootEClass, DOCUMENT_ROOT__VALUES);

    exceptionTypeEClass = createEClass(EXCEPTION_TYPE);
    createEAttribute(exceptionTypeEClass, EXCEPTION_TYPE__VALUE);

    loopTypeEClass = createEClass(LOOP_TYPE);
    createEReference(loopTypeEClass, LOOP_TYPE__EXCEPTION);
    createEAttribute(loopTypeEClass, LOOP_TYPE__END);
    createEAttribute(loopTypeEClass, LOOP_TYPE__START);
    createEAttribute(loopTypeEClass, LOOP_TYPE__STEP);

    valuesTypeEClass = createEClass(VALUES_TYPE);
    createEAttribute(valuesTypeEClass, VALUES_TYPE__VALUE);
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
    initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_Loop(), this.getLoopType(), null, "loop", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_Values(), this.getValuesType(), null, "values", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

    initEClass(exceptionTypeEClass, ExceptionType.class, "ExceptionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getExceptionType_Value(), theXMLTypePackage.getInteger(), "value", null, 0, 1, ExceptionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(loopTypeEClass, LoopType.class, "LoopType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getLoopType_Exception(), this.getExceptionType(), null, "exception", null, 0, -1, LoopType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLoopType_End(), theXMLTypePackage.getInteger(), "end", null, 1, 1, LoopType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLoopType_Start(), theXMLTypePackage.getInteger(), "start", null, 1, 1, LoopType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLoopType_Step(), theXMLTypePackage.getPositiveInteger(), "step", "1", 0, 1, LoopType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(valuesTypeEClass, ValuesType.class, "ValuesType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getValuesType_Value(), theXMLTypePackage.getString(), "value", null, 1, -1, ValuesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
      (getDocumentRoot_Loop(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Loop",
       "namespace", "##targetNamespace",
       "affiliation", "http://schemas.ogf.org/jsdl/2007/01/jsdl-sweep#Function"
       });		
    addAnnotation
      (getDocumentRoot_Values(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Values",
       "namespace", "##targetNamespace",
       "affiliation", "http://schemas.ogf.org/jsdl/2007/01/jsdl-sweep#Function"
       });		
    addAnnotation
      (exceptionTypeEClass, 
       source, 
       new String[] 
       {
       "name", "Exception_._type",
       "kind", "empty"
       });		
    addAnnotation
      (getExceptionType_Value(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "value",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (loopTypeEClass, 
       source, 
       new String[] 
       {
       "name", "Loop_._type",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getLoopType_Exception(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Exception",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getLoopType_End(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "end",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getLoopType_Start(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "start",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getLoopType_Step(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "step",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (valuesTypeEClass, 
       source, 
       new String[] 
       {
       "name", "Values_._type",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getValuesType_Value(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Value",
       "namespace", "##targetNamespace"
       });
  }

} //FunctionsPackageImpl
