/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.sweep;

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
 * @see eu.geclipse.jsdl.model.sweep.SweepFactory
 * @model kind="package"
 * @generated
 */
public interface SweepPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "sweep";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://schemas.ogf.org/jsdl/2007/01/jsdl-sweep";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "sweep";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  SweepPackage eINSTANCE = eu.geclipse.jsdl.model.sweep.impl.SweepPackageImpl.init();

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.model.sweep.impl.AssignmentTypeImpl <em>Assignment Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.model.sweep.impl.AssignmentTypeImpl
   * @see eu.geclipse.jsdl.model.sweep.impl.SweepPackageImpl#getAssignmentType()
   * @generated
   */
  int ASSIGNMENT_TYPE = 0;

  /**
   * The feature id for the '<em><b>Parameter</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT_TYPE__PARAMETER = 0;

  /**
   * The feature id for the '<em><b>Function Group</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT_TYPE__FUNCTION_GROUP = 1;

  /**
   * The feature id for the '<em><b>Function</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT_TYPE__FUNCTION = 2;

  /**
   * The number of structural features of the '<em>Assignment Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT_TYPE_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.model.sweep.impl.DocumentRootImpl <em>Document Root</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.model.sweep.impl.DocumentRootImpl
   * @see eu.geclipse.jsdl.model.sweep.impl.SweepPackageImpl#getDocumentRoot()
   * @generated
   */
  int DOCUMENT_ROOT = 1;

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
   * The feature id for the '<em><b>Assignment</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__ASSIGNMENT = 3;

  /**
   * The feature id for the '<em><b>Function</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__FUNCTION = 4;

  /**
   * The feature id for the '<em><b>Parameter</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__PARAMETER = 5;

  /**
   * The feature id for the '<em><b>Sweep</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__SWEEP = 6;

  /**
   * The number of structural features of the '<em>Document Root</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT_FEATURE_COUNT = 7;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.model.sweep.impl.SweepTypeImpl <em>Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.model.sweep.impl.SweepTypeImpl
   * @see eu.geclipse.jsdl.model.sweep.impl.SweepPackageImpl#getSweepType()
   * @generated
   */
  int SWEEP_TYPE = 2;

  /**
   * The feature id for the '<em><b>Assignment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SWEEP_TYPE__ASSIGNMENT = 0;

  /**
   * The feature id for the '<em><b>Sweep</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SWEEP_TYPE__SWEEP = 1;

  /**
   * The number of structural features of the '<em>Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SWEEP_TYPE_FEATURE_COUNT = 2;


  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.model.sweep.AssignmentType <em>Assignment Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Assignment Type</em>'.
   * @see eu.geclipse.jsdl.model.sweep.AssignmentType
   * @generated
   */
  EClass getAssignmentType();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.model.sweep.AssignmentType#getParameter <em>Parameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Parameter</em>'.
   * @see eu.geclipse.jsdl.model.sweep.AssignmentType#getParameter()
   * @see #getAssignmentType()
   * @generated
   */
  EAttribute getAssignmentType_Parameter();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.model.sweep.AssignmentType#getFunctionGroup <em>Function Group</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Function Group</em>'.
   * @see eu.geclipse.jsdl.model.sweep.AssignmentType#getFunctionGroup()
   * @see #getAssignmentType()
   * @generated
   */
  EAttribute getAssignmentType_FunctionGroup();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.sweep.AssignmentType#getFunction <em>Function</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Function</em>'.
   * @see eu.geclipse.jsdl.model.sweep.AssignmentType#getFunction()
   * @see #getAssignmentType()
   * @generated
   */
  EReference getAssignmentType_Function();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.model.sweep.DocumentRoot <em>Document Root</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Document Root</em>'.
   * @see eu.geclipse.jsdl.model.sweep.DocumentRoot
   * @generated
   */
  EClass getDocumentRoot();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.model.sweep.DocumentRoot#getMixed <em>Mixed</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Mixed</em>'.
   * @see eu.geclipse.jsdl.model.sweep.DocumentRoot#getMixed()
   * @see #getDocumentRoot()
   * @generated
   */
  EAttribute getDocumentRoot_Mixed();

  /**
   * Returns the meta object for the map '{@link eu.geclipse.jsdl.model.sweep.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
   * @see eu.geclipse.jsdl.model.sweep.DocumentRoot#getXMLNSPrefixMap()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_XMLNSPrefixMap();

  /**
   * Returns the meta object for the map '{@link eu.geclipse.jsdl.model.sweep.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the map '<em>XSI Schema Location</em>'.
   * @see eu.geclipse.jsdl.model.sweep.DocumentRoot#getXSISchemaLocation()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_XSISchemaLocation();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.sweep.DocumentRoot#getAssignment <em>Assignment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Assignment</em>'.
   * @see eu.geclipse.jsdl.model.sweep.DocumentRoot#getAssignment()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_Assignment();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.sweep.DocumentRoot#getFunction <em>Function</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Function</em>'.
   * @see eu.geclipse.jsdl.model.sweep.DocumentRoot#getFunction()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_Function();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.model.sweep.DocumentRoot#getParameter <em>Parameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Parameter</em>'.
   * @see eu.geclipse.jsdl.model.sweep.DocumentRoot#getParameter()
   * @see #getDocumentRoot()
   * @generated
   */
  EAttribute getDocumentRoot_Parameter();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.sweep.DocumentRoot#getSweep <em>Sweep</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Sweep</em>'.
   * @see eu.geclipse.jsdl.model.sweep.DocumentRoot#getSweep()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_Sweep();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.model.sweep.SweepType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type</em>'.
   * @see eu.geclipse.jsdl.model.sweep.SweepType
   * @generated
   */
  EClass getSweepType();

  /**
   * Returns the meta object for the containment reference list '{@link eu.geclipse.jsdl.model.sweep.SweepType#getAssignment <em>Assignment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Assignment</em>'.
   * @see eu.geclipse.jsdl.model.sweep.SweepType#getAssignment()
   * @see #getSweepType()
   * @generated
   */
  EReference getSweepType_Assignment();

  /**
   * Returns the meta object for the containment reference list '{@link eu.geclipse.jsdl.model.sweep.SweepType#getSweep <em>Sweep</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Sweep</em>'.
   * @see eu.geclipse.jsdl.model.sweep.SweepType#getSweep()
   * @see #getSweepType()
   * @generated
   */
  EReference getSweepType_Sweep();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  SweepFactory getSweepFactory();

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
     * The meta object literal for the '{@link eu.geclipse.jsdl.model.sweep.impl.AssignmentTypeImpl <em>Assignment Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.model.sweep.impl.AssignmentTypeImpl
     * @see eu.geclipse.jsdl.model.sweep.impl.SweepPackageImpl#getAssignmentType()
     * @generated
     */
    EClass ASSIGNMENT_TYPE = eINSTANCE.getAssignmentType();

    /**
     * The meta object literal for the '<em><b>Parameter</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ASSIGNMENT_TYPE__PARAMETER = eINSTANCE.getAssignmentType_Parameter();

    /**
     * The meta object literal for the '<em><b>Function Group</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ASSIGNMENT_TYPE__FUNCTION_GROUP = eINSTANCE.getAssignmentType_FunctionGroup();

    /**
     * The meta object literal for the '<em><b>Function</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ASSIGNMENT_TYPE__FUNCTION = eINSTANCE.getAssignmentType_Function();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.model.sweep.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.model.sweep.impl.DocumentRootImpl
     * @see eu.geclipse.jsdl.model.sweep.impl.SweepPackageImpl#getDocumentRoot()
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
     * The meta object literal for the '<em><b>Assignment</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DOCUMENT_ROOT__ASSIGNMENT = eINSTANCE.getDocumentRoot_Assignment();

    /**
     * The meta object literal for the '<em><b>Function</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DOCUMENT_ROOT__FUNCTION = eINSTANCE.getDocumentRoot_Function();

    /**
     * The meta object literal for the '<em><b>Parameter</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DOCUMENT_ROOT__PARAMETER = eINSTANCE.getDocumentRoot_Parameter();

    /**
     * The meta object literal for the '<em><b>Sweep</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DOCUMENT_ROOT__SWEEP = eINSTANCE.getDocumentRoot_Sweep();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.model.sweep.impl.SweepTypeImpl <em>Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.model.sweep.impl.SweepTypeImpl
     * @see eu.geclipse.jsdl.model.sweep.impl.SweepPackageImpl#getSweepType()
     * @generated
     */
    EClass SWEEP_TYPE = eINSTANCE.getSweepType();

    /**
     * The meta object literal for the '<em><b>Assignment</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SWEEP_TYPE__ASSIGNMENT = eINSTANCE.getSweepType_Assignment();

    /**
     * The meta object literal for the '<em><b>Sweep</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SWEEP_TYPE__SWEEP = eINSTANCE.getSweepType_Sweep();

  }

} //SweepPackage
