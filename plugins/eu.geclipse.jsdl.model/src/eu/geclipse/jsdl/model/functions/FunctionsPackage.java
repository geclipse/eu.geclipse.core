/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.functions;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see eu.geclipse.jsdl.model.functions.FunctionsFactory
 * @model kind="package"
 * @generated
 */
public interface FunctionsPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "functions";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://schemas.ogf.org/jsdl/2007/01/jsdl-sweep/functions";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "sweepfunc";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  FunctionsPackage eINSTANCE = eu.geclipse.jsdl.model.functions.impl.FunctionsPackageImpl.init();

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.model.functions.impl.DocumentRootImpl <em>Document Root</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.model.functions.impl.DocumentRootImpl
   * @see eu.geclipse.jsdl.model.functions.impl.FunctionsPackageImpl#getDocumentRoot()
   * @generated
   */
  int DOCUMENT_ROOT = 0;

  /**
   * The feature id for the '<em><b>Mixed</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__MIXED = 0;

  /**
   * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

  /**
   * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

  /**
   * The feature id for the '<em><b>Loop</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__LOOP = 3;

  /**
   * The feature id for the '<em><b>Values</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__VALUES = 4;

  /**
   * The number of structural features of the '<em>Document Root</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT_FEATURE_COUNT = 5;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.model.functions.impl.ExceptionTypeImpl <em>Exception Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.model.functions.impl.ExceptionTypeImpl
   * @see eu.geclipse.jsdl.model.functions.impl.FunctionsPackageImpl#getExceptionType()
   * @generated
   */
  int EXCEPTION_TYPE = 1;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXCEPTION_TYPE__VALUE = 0;

  /**
   * The number of structural features of the '<em>Exception Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXCEPTION_TYPE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.model.functions.impl.LoopTypeImpl <em>Loop Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.model.functions.impl.LoopTypeImpl
   * @see eu.geclipse.jsdl.model.functions.impl.FunctionsPackageImpl#getLoopType()
   * @generated
   */
  int LOOP_TYPE = 2;

  /**
   * The feature id for the '<em><b>Exception</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOP_TYPE__EXCEPTION = 0;

  /**
   * The feature id for the '<em><b>End</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOP_TYPE__END = 1;

  /**
   * The feature id for the '<em><b>Start</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOP_TYPE__START = 2;

  /**
   * The feature id for the '<em><b>Step</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOP_TYPE__STEP = 3;

  /**
   * The number of structural features of the '<em>Loop Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOP_TYPE_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.model.functions.impl.ValuesTypeImpl <em>Values Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.model.functions.impl.ValuesTypeImpl
   * @see eu.geclipse.jsdl.model.functions.impl.FunctionsPackageImpl#getValuesType()
   * @generated
   */
  int VALUES_TYPE = 3;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VALUES_TYPE__VALUE = 0;

  /**
   * The number of structural features of the '<em>Values Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VALUES_TYPE_FEATURE_COUNT = 1;


  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.model.functions.DocumentRoot <em>Document Root</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Document Root</em>'.
   * @see eu.geclipse.jsdl.model.functions.DocumentRoot
   * @generated
   */
  EClass getDocumentRoot();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.model.functions.DocumentRoot#getMixed <em>Mixed</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Mixed</em>'.
   * @see eu.geclipse.jsdl.model.functions.DocumentRoot#getMixed()
   * @see #getDocumentRoot()
   * @generated
   */
  EAttribute getDocumentRoot_Mixed();

  /**
   * Returns the meta object for the map '{@link eu.geclipse.jsdl.model.functions.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
   * @see eu.geclipse.jsdl.model.functions.DocumentRoot#getXMLNSPrefixMap()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_XMLNSPrefixMap();

  /**
   * Returns the meta object for the map '{@link eu.geclipse.jsdl.model.functions.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the map '<em>XSI Schema Location</em>'.
   * @see eu.geclipse.jsdl.model.functions.DocumentRoot#getXSISchemaLocation()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_XSISchemaLocation();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.functions.DocumentRoot#getLoop <em>Loop</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Loop</em>'.
   * @see eu.geclipse.jsdl.model.functions.DocumentRoot#getLoop()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_Loop();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.functions.DocumentRoot#getValues <em>Values</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Values</em>'.
   * @see eu.geclipse.jsdl.model.functions.DocumentRoot#getValues()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_Values();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.model.functions.ExceptionType <em>Exception Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Exception Type</em>'.
   * @see eu.geclipse.jsdl.model.functions.ExceptionType
   * @generated
   */
  EClass getExceptionType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.model.functions.ExceptionType#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see eu.geclipse.jsdl.model.functions.ExceptionType#getValue()
   * @see #getExceptionType()
   * @generated
   */
  EAttribute getExceptionType_Value();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.model.functions.LoopType <em>Loop Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Loop Type</em>'.
   * @see eu.geclipse.jsdl.model.functions.LoopType
   * @generated
   */
  EClass getLoopType();

  /**
   * Returns the meta object for the containment reference list '{@link eu.geclipse.jsdl.model.functions.LoopType#getException <em>Exception</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Exception</em>'.
   * @see eu.geclipse.jsdl.model.functions.LoopType#getException()
   * @see #getLoopType()
   * @generated
   */
  EReference getLoopType_Exception();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.model.functions.LoopType#getEnd <em>End</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>End</em>'.
   * @see eu.geclipse.jsdl.model.functions.LoopType#getEnd()
   * @see #getLoopType()
   * @generated
   */
  EAttribute getLoopType_End();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.model.functions.LoopType#getStart <em>Start</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Start</em>'.
   * @see eu.geclipse.jsdl.model.functions.LoopType#getStart()
   * @see #getLoopType()
   * @generated
   */
  EAttribute getLoopType_Start();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.model.functions.LoopType#getStep <em>Step</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Step</em>'.
   * @see eu.geclipse.jsdl.model.functions.LoopType#getStep()
   * @see #getLoopType()
   * @generated
   */
  EAttribute getLoopType_Step();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.model.functions.ValuesType <em>Values Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Values Type</em>'.
   * @see eu.geclipse.jsdl.model.functions.ValuesType
   * @generated
   */
  EClass getValuesType();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.model.functions.ValuesType#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Value</em>'.
   * @see eu.geclipse.jsdl.model.functions.ValuesType#getValue()
   * @see #getValuesType()
   * @generated
   */
  EAttribute getValuesType_Value();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  FunctionsFactory getFunctionsFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.model.functions.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.model.functions.impl.DocumentRootImpl
     * @see eu.geclipse.jsdl.model.functions.impl.FunctionsPackageImpl#getDocumentRoot()
     * @generated
     */
    EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

    /**
     * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

    /**
     * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

    /**
     * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

    /**
     * The meta object literal for the '<em><b>Loop</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DOCUMENT_ROOT__LOOP = eINSTANCE.getDocumentRoot_Loop();

    /**
     * The meta object literal for the '<em><b>Values</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DOCUMENT_ROOT__VALUES = eINSTANCE.getDocumentRoot_Values();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.model.functions.impl.ExceptionTypeImpl <em>Exception Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.model.functions.impl.ExceptionTypeImpl
     * @see eu.geclipse.jsdl.model.functions.impl.FunctionsPackageImpl#getExceptionType()
     * @generated
     */
    EClass EXCEPTION_TYPE = eINSTANCE.getExceptionType();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXCEPTION_TYPE__VALUE = eINSTANCE.getExceptionType_Value();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.model.functions.impl.LoopTypeImpl <em>Loop Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.model.functions.impl.LoopTypeImpl
     * @see eu.geclipse.jsdl.model.functions.impl.FunctionsPackageImpl#getLoopType()
     * @generated
     */
    EClass LOOP_TYPE = eINSTANCE.getLoopType();

    /**
     * The meta object literal for the '<em><b>Exception</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference LOOP_TYPE__EXCEPTION = eINSTANCE.getLoopType_Exception();

    /**
     * The meta object literal for the '<em><b>End</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LOOP_TYPE__END = eINSTANCE.getLoopType_End();

    /**
     * The meta object literal for the '<em><b>Start</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LOOP_TYPE__START = eINSTANCE.getLoopType_Start();

    /**
     * The meta object literal for the '<em><b>Step</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LOOP_TYPE__STEP = eINSTANCE.getLoopType_Step();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.model.functions.impl.ValuesTypeImpl <em>Values Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.model.functions.impl.ValuesTypeImpl
     * @see eu.geclipse.jsdl.model.functions.impl.FunctionsPackageImpl#getValuesType()
     * @generated
     */
    EClass VALUES_TYPE = eINSTANCE.getValuesType();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VALUES_TYPE__VALUE = eINSTANCE.getValuesType_Value();

  }

} //FunctionsPackage
